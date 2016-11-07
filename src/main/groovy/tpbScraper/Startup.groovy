package tpbScraper

class Startup {

    //TODO: Convert to log statements?
    //TODO: Convert to Spring Boot with IoC
    static void main(String... args){
        new TpbsGUI().promptUserAndWaitForScrapeToBePressed()
    }

}
