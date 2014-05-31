package tpbScraper.engine

import tpbScraper.domain.TpbsProperties
import tpbScraper.engine.html.HtmlReader
import tpbScraper.engine.html.tablerow.TableRowScraper
import tpbScraper.engine.html.tablerow.TableRowWriter
import tpbScraper.util.ScraperStatusPrinter

import static tpbScraper.engine.BrowserFileHandler.openFileInChrome
import static tpbScraper.engine.BrowserFileHandler.writeResultPageFilesToDirIfTheyAreMissing

class TpbScraperEngine {
    private final TpbsProperties properties
    private final TableRowScraper scraper = new TableRowScraper()
    private final TableRowWriter writer

    //TODO: Possibly refactor TableRowWriter to not require an instance of TpbsProperties
    TpbScraperEngine(TpbsProperties properties) {
        this.properties = properties
        writer = new TableRowWriter(properties)
    }

    void scrapeTheBrowseDirectory() {
        writeResultPageFilesToDirIfTheyAreMissing(properties.dataDirectory)

        StringBuilder tableRowsHTML = scrapeTableRowsHTML()
        if (tableRowsHTML.length() > 0) {
            writer.writeTableRowsToResultsPage(tableRowsHTML);
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
//                ScraperStatusPrinter.printScrapingStatus(properties, index)
//                StringBuilder html = reader.readInHTML(tpbURL)
//                tableRowsHTML.append(scraper.scrapeTableRowElementsFromHTML(html, properties.seederThreshold))
//            }
//        }
        generateURLsToCrawl().eachWithIndex { String tpbURL, index ->
            ScraperStatusPrinter.printScrapingStatus(properties, index)
            StringBuilder html = reader.readInHTML(tpbURL)
            tableRowsHTML.append(scraper.scrapeTableRowElementsFromHTML(html, properties.seederThreshold))
        }
        println "Crawling completed."

        return tableRowsHTML
    }

    private List<String> generateURLsToCrawl() {
        (0..< properties.numPagesToCrawl).collect { int pageNum ->
            "${properties.tpbUrl}/browse/${properties.mediaType.getUrlCode()}/${pageNum}/3"
        }
    }
}
