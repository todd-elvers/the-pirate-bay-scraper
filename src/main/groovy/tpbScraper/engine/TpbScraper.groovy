package tpbScraper.engine
import tpbScraper.domain.TpbsProperties

import static tpbScraper.engine.BrowserFileHandler.copyNecessaryFilesToDataDirIfMissing

abstract class TpbScraper {
    TpbsProperties tpbcProperties

    TpbScraper(TpbsProperties tpbcProperties) {
        copyNecessaryFilesToDataDirIfMissing(tpbcProperties.dataDirectory)
        this.tpbcProperties = tpbcProperties
    }

    abstract void scrape()

}
