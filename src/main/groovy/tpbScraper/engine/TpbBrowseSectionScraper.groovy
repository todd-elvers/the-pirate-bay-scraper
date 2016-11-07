package tpbScraper.engine

import groovyx.gpars.GParsPool
import org.jsoup.nodes.Document
import tpbScraper.domain.TpbsProperties
import tpbScraper.engine.html.document.HtmlDocumentDownloader
import tpbScraper.engine.html.document.HtmlDocumentExtractor
import tpbScraper.engine.html.table_row.TableRowFormatter
import tpbScraper.engine.html.table_row.TableRowWriter
import tpbScraper.util.PropertiesReader

import static tpbScraper.engine.BrowserFileHandler.copyNecessaryFilesToDataDirIfMissing
import static tpbScraper.engine.BrowserFileHandler.openFileInDefaultBrowser

class TpbBrowseSectionScraper {

    private htmlDocumentDownloader = new HtmlDocumentDownloader()
    private htmlDocumentTableRowExtractor = new HtmlDocumentExtractor()
    private tableRowFormatter = new TableRowFormatter()
    private tableRowWriter = new TableRowWriter()

    TpbsProperties tpbsProperties

    TpbBrowseSectionScraper(TpbsProperties tpbsProperties) {
        this.tpbsProperties = tpbsProperties
        printStartupMessage()
        copyNecessaryFilesToDataDirIfMissing(tpbsProperties.dataDirectory)
    }

    void scrape() {
        StringBuilder tableRowsHTML = new StringBuilder()

        //TODO: Fix the Windows bug causing this to write to multiple lines
        print "Scraping"
        GParsPool.withPool {
            generateURLsToScrape().eachWithIndexParallel { String tpbURL, int index ->
                Document htmlDocument = htmlDocumentDownloader.download(tpbURL)
                StringBuilder tableRows = htmlDocumentTableRowExtractor.extractRowsAboveThreshold(htmlDocument, tpbsProperties.seederThreshold)
                tableRowsHTML.append(tableRows)
                print "."
            }
        }
        println "Done."


        if (tableRowsHTML) {
            String formattedHtml = tableRowFormatter.format(tableRowsHTML, tpbsProperties)
            File outputFile = tableRowWriter.writeFormattedHtmlToFile(formattedHtml, tpbsProperties)
            openFileInDefaultBrowser(outputFile)
        } else {
            println "No results found."
        }
    }

    private List<String> generateURLsToScrape() {
        (0 ..< tpbsProperties.numPagesToCrawl).collect { int pageNum ->
            "${tpbsProperties.tpbUrl}/browse/${tpbsProperties.mediaType.getUrlCode()}/${pageNum}/3"
        }
    }

    private void printStartupMessage() {
        print """\
            TPBS - The Pirate Bay Scraper v${PropertiesReader.readAppProperty("version")}
            Settings:
                Category: ${tpbsProperties.mediaType.toString().replaceAll("_", " > ")}
                Seeder Threshold: ${tpbsProperties.seederThreshold}
                Number of pages to scrape back: ${tpbsProperties.numPagesToCrawl}

        """.stripIndent()
    }
}
