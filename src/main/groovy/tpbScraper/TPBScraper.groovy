package tpbScraper

import tpbScraper.domain.MediaType
import tpbScraper.domain.TpbsProperties
import tpbScraper.engine.TpbScraperEngine

class TPBScraper {

    static main(args) {
        TpbsProperties properties = new TpbsProperties(
                mediaType: MediaType.GAMES_PC,
                seederThreshold: 800,
                numPagesToCrawl: 100
        )

        new TpbScraperEngine(properties).scrapeTheBrowseDirectory()
    }
}
