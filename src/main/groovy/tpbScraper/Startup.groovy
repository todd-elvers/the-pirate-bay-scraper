package tpbScraper

class Startup {

    //TODO: Convert to log statements
    //TODO: When multiple scraper engine's exist, re-implement the scraper interface stuff
    static void main(String... args){
        new TpbsGUI().promptUserAndWaitForScrapeToBePressed()
    }

}
