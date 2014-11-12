package tpbScraper.util

import tpbScraper.domain.TpbsProperties

class ScraperStatusPrinter {

    static void printScrapingStatus(TpbsProperties properties, int numPagesDownloaded) {
        String version = PropertiesReader.readAppProperty("version")
        if (numPagesDownloaded == 0) {
            print """\
                TPBC - The Pirate Bay Crawler (v$version)
                "Settings:
                    Category: ${properties.mediaType.toString().replaceAll("_", " > ")}
                    Seeder Threshold: ${properties.seederThreshold}
                    Number of pages to crawl back: ${properties.numPagesToCrawl}

                Crawling...
            """
            print "Reading page ${numPagesDownloaded + 1}...\r".padRight(19)
        } else {
            print "Reading page ${numPagesDownloaded + 1}...\r".padRight(19)
        }
    }

}
