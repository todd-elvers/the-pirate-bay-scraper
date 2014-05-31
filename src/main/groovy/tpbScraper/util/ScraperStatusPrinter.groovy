package tpbScraper.util

import tpbScraper.domain.TpbsProperties

class ScraperStatusPrinter {

    static void printScrapingStatus(TpbsProperties properties, numPagesDownloaded) {
        String version = PropertiesReader.readAppProperty("version")
        if (numPagesDownloaded == 0) {
            println "TPBC - The Pirate Bay Crawler (v$version)\n\n" +
                    "Settings:\n" +
                    "\tCategory: ${properties.mediaType.toString().replaceAll("_", " > ")}\n" +
                    "\tSeeder Threshold: ${properties.seederThreshold}\n" +
                    "\tNumber of pages to crawl back: ${properties.numPagesToCrawl}"
            println "\nCrawling..."
            print "Reading page ${numPagesDownloaded + 1}...".padRight(19)
        } else {
            print "Reading page ${numPagesDownloaded + 1}...".padRight(19)
        }
    }

}
