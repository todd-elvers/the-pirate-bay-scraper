package tpbScraper.engine

import tpbScraper.domain.TpbsProperties
import tpbScraper.engine.html.HtmlReader
import tpbScraper.engine.html.tablerow.TableRowScraper
import tpbScraper.engine.html.tablerow.TableRowWriter
import tpbScraper.util.ScraperStatusPrinter

import static tpbScraper.engine.BrowserFileHandler.openFileInChrome
import static tpbScraper.engine.BrowserFileHandler.writeResultPageFilesToDirIfTheyAreMissing

class TpbScraperEngine {
    private final TableRowScraper scraper = new TableRowScraper()
    private final TableRowWriter writer = new TableRowWriter()
    private final TpbsProperties tpbcProperties

    TpbScraperEngine(TpbsProperties tpbcProperties) {
        this.tpbcProperties = tpbcProperties
    }

    void scrapeTheBrowseDirectory() {
        writeResultPageFilesToDirIfTheyAreMissing(tpbcProperties.dataDirectory)

        StringBuilder tableRowsHTML = scrapeTableRowsHTML()
        if (tableRowsHTML.length() > 0) {
            writer.writeTableRowsToResultsPage(tableRowsHTML, tpbcProperties)
            openFileInChrome(writer.resultsPageFile)
        } else {
            println "No results found."
        }
    }

    private StringBuilder scrapeTableRowsHTML(){
        StringBuilder tableRowsHTML = new StringBuilder()

        HtmlReader reader = new HtmlReader()
//        GParsPool.withPool {
//            generateURLsToCrawl().eachWithIndexParallel { String tpbURL, index ->
//                ScraperStatusPrinter.printScrapingStatus(tpbcProperties, index)
//                StringBuilder html = reader.readInHTML(tpbURL)
//                tableRowsHTML.append(scraper.scrapeTableRowElementsFromHTML(html, tpbcProperties.seederThreshold))
//            }
//        }
        generateURLsToCrawl().eachWithIndex { String tpbURL, index ->
            ScraperStatusPrinter.printScrapingStatus(tpbcProperties, index)
            StringBuilder html = reader.readInHTML(tpbURL)
            tableRowsHTML.append(scraper.scrapeTableRowElementsFromHTML(html, tpbcProperties.seederThreshold))
        }
        println "Crawling completed."

        return tableRowsHTML
    }

    private List<String> generateURLsToCrawl() {
        (0..<tpbcProperties.numPagesToCrawl).collect { int pageNum ->
            "${tpbcProperties.tpbUrl}/browse/${tpbcProperties.mediaType.getUrlCode()}/${pageNum}/3"
        }
    }
}
