package tpbScraper.engine
import groovyx.gpars.GParsPool
import org.jsoup.nodes.Document
import tpbScraper.domain.TpbsProperties
import tpbScraper.engine.html.document.HtmlDocumentDownloader
import tpbScraper.engine.html.document.HtmlDocumentExtractor
import tpbScraper.engine.html.table_row.TableRowFormatter
import tpbScraper.engine.html.table_row.TableRowWriter
import tpbScraper.util.PropertiesReader

import static tpbScraper.engine.BrowserFileHandler.openFileInChrome

class TpbBrowseSectionScraper extends TpbScraper {

    def htmlDocumentDownloader = new HtmlDocumentDownloader(),
        htmlDocumentTableRowExtractor = new HtmlDocumentExtractor(),
        tableRowFormatter = new TableRowFormatter(),
        tableRowWriter = new TableRowWriter()

    TpbBrowseSectionScraper(TpbsProperties tpbcProperties) {
        super(tpbcProperties)
    }

    @Override
    void scrape() {
        printStartupMessage()

        StringBuilder tableRowsHTML = new StringBuilder()
        GParsPool.withPool {
            generateURLsToScrape().eachWithIndexParallel { String tpbURL, int index ->
                Document htmlDocument = htmlDocumentDownloader.download(tpbURL)
                StringBuilder tableRows = htmlDocumentTableRowExtractor.extractRowsAboveThreshold(htmlDocument, tpbcProperties.seederThreshold)
                tableRowsHTML.append(tableRows)
                print "."
            }
        }
        println "Done."

        if (tableRowsHTML) {
            StringBuilder formattedHtml = tableRowFormatter.format(tableRowsHTML, tpbcProperties)
            File outputFile = tableRowWriter.writeFormattedHtmlToFile(formattedHtml, tpbcProperties)
            openFileInChrome(outputFile)
        } else {
            println "No results found."
        }
    }

    private List<String> generateURLsToScrape() {
        (0 ..< tpbcProperties.numPagesToCrawl).collect { int pageNum ->
            "${tpbcProperties.tpbUrl}/browse/${tpbcProperties.mediaType.getUrlCode()}/${pageNum}/3"
        }
    }

    private void printStartupMessage() {
        print """\
            TPBS - The Pirate Bay Scraper v${PropertiesReader.readAppProperty("version")}
            Settings:
                Category: ${tpbcProperties.mediaType.toString().replaceAll("_", " > ")}
                Seeder Threshold: ${tpbcProperties.seederThreshold}
                Number of pages to scrape back: ${tpbcProperties.numPagesToCrawl}

            Scraping""".stripIndent()
    }
}
