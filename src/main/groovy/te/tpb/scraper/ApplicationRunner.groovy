package te.tpb.scraper

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import te.tpb.scraper.core.BrowseSectionScraper
import te.tpb.scraper.core.BrowseSectionGui
import te.tpb.scraper.domain.TpbsProperties
import te.tpb.scraper.util.BrowserFileHandler

@Component
class ApplicationRunner implements CommandLineRunner {

    @Autowired protected BrowseSectionGui browseScraperGui
    @Autowired protected BrowseSectionScraper browseSectionScraper
    @Autowired protected BrowserFileHandler browserFileHandler

    @Override
    void run(String... args) {
        browseScraperGui.promptUserToScrape { TpbsProperties props ->
            browserFileHandler.copyTemplateFilesToDataDirIfMissing(props.dataDirectory)

            printStartupMessage(props)

            Optional<File> outputFile = browseSectionScraper.scrape(props)
            if (outputFile.isPresent()) {
                browserFileHandler.openFileInDefaultBrowser(outputFile.get())
            } else {
                println("No results found.")
            }
        }
    }

    private static void printStartupMessage(TpbsProperties tpbsProperties) {
        print """\
            TPBS - The Pirate Bay Scraper v3.0.0
            Settings:
                Category: ${tpbsProperties.mediaType.toString().replaceAll("_", " > ")}
                Seeder Threshold: ${tpbsProperties.seederThreshold}
                Number of pages to scrape back: ${tpbsProperties.numPagesToCrawl}

        """.stripIndent()
    }
}