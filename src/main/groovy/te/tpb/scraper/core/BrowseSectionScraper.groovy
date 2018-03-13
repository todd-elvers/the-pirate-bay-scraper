package te.tpb.scraper.core

import groovyx.gpars.GParsPool
import org.jsoup.nodes.Document
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import te.tpb.scraper.domain.TpbsProperties
import te.tpb.scraper.core.document.HtmlDocumentDownloader
import te.tpb.scraper.core.document.HtmlDocumentExtractor
import te.tpb.scraper.core.table_row.TableRowFormatter
import te.tpb.scraper.core.table_row.TableRowWriter

@Component
class BrowseSectionScraper {

    @Autowired protected HtmlDocumentDownloader htmlDocumentDownloader
    @Autowired protected HtmlDocumentExtractor htmlDocumentTableRowExtractor
    @Autowired protected TableRowFormatter tableRowFormatter
    @Autowired protected TableRowWriter tableRowWriter

    Optional<File> scrape(TpbsProperties tpbsProperties) {
        StringBuilder tableRowsHTML = new StringBuilder()

        print "Scraping"
        GParsPool.withPool(8) {
            generateURLsToScrape(tpbsProperties).eachWithIndexParallel { String tpbURL, int index ->
                Optional<Document> htmlDocument = htmlDocumentDownloader.download(tpbURL)
                if (htmlDocument.isPresent()) {
                    StringBuilder tableRows = htmlDocumentTableRowExtractor.extractRowsAboveThreshold(
                            htmlDocument.get(),
                            tpbsProperties.seederThreshold
                    )
                    tableRowsHTML.append(tableRows)
                    print "."
                } else {
                    print "x"
                }
            }
        }
        println "Done."

        File resultsFile = (tableRowsHTML) ? resultsAsFileIfPossible(tableRowsHTML, tpbsProperties) : null

        return Optional.ofNullable(resultsFile)
    }

    private List<String> generateURLsToScrape(TpbsProperties tpbsProperties) {
        (0 ..< tpbsProperties.numPagesToCrawl).collect { int pageNum ->
            "${tpbsProperties.tpbUrl}/browse/${tpbsProperties.mediaType.getUrlCode()}/${pageNum}/3"
        }
    }

    private File resultsAsFileIfPossible(StringBuilder tableRowsHTML, TpbsProperties tpbsProperties) {
        String formattedHtml = tableRowFormatter.format(tableRowsHTML, tpbsProperties)
        return tableRowWriter.writeFormattedHtmlToFile(
                formattedHtml,
                tpbsProperties
        )
    }

}
