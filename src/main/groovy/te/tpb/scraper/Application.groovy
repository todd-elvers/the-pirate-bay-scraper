package te.tpb.scraper

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import tpbScraper.BrowseScraperGui
import tpbScraper.domain.TpbsProperties
import tpbScraper.engine.TpbBrowseSectionScraper

//@CompileStatic
@SpringBootApplication(scanBasePackages = ["tpbScraper", "te.tpb.scraper"])
class Application implements CommandLineRunner {

    static void main(String[] args) { SpringApplication.run(Application, args) }

    @Autowired BrowseScraperGui browseScraperGui

    @Override
    void run(String... args) {
        println "HEYYYY\n\n\n\n\n\n\n"
        browseScraperGui.promptUserToScrape { TpbsProperties props ->
            new TpbBrowseSectionScraper(props).scrape()
        }
    }
}
