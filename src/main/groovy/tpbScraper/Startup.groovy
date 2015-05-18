package tpbScraper

import tpbScraper.engine.TpbBrowseSectionScraper

class Startup {

    //TODO: Convert to log statements
    static void main(String... args){
        new TpbsGUI().with {
            scraperEngineToUse = TpbBrowseSectionScraper
            promptUserAndWaitForScrapeToBePressed()
        }
    }

}
