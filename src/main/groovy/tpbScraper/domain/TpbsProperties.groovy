package tpbScraper.domain

class TpbsProperties {

    /**
     * The type of media to browse for (e.g. AUDIO, VIDEO, GAMES, etc.)
     * Default value is VIDEO
     */
    MediaType mediaType = MediaType.VIDEO

    /**
     * The number of seeders required before a post is seen as valuable or important
     */
    int seederThreshold = 300

    /**
     * Number of pages to crawl.
     * NOTE: The Pirate Bay limits the number of pages a user can browse (or crawl) to 100
     */
    int numPagesToCrawl = 30

    /**
     * The Pirate Bay's fully qualified URL
     */
    String tpbUrl = "http://www.thepiratebay.se"

    /**
     * This is the directory containing folders 'results', 'static_content' and 'template'.
     * These folders contain the necessary files for rendering the results of the crawl.
     * The default directory is %TEMP%/TPBScraper
     */
    String dataDirectory = new File(System.getenv("TEMP"), "TPBScraper").absolutePath

    /**
     * This function must return a uniquely identifying string, because TPBScraper will overwrite
     * a file in the case of a collision. The default unique identifier is a datetime string.
     */
    Closure<String> uniqueIdentifierForFilenames = {
        return new Date().format("yyyy-MM-dd'T'HH.mm.ss")
    }
}
