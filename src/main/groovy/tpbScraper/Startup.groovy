package tpbScraper
import groovy.swing.SwingBuilder
import tpbScraper.domain.MediaType
import tpbScraper.domain.TpbsProperties
import tpbScraper.engine.TpbScraperEngine

import javax.swing.*
import java.awt.*

class Startup {

    static void main(String... args){
        promptUserForInputAndWhenScrapeIsPressed { TpbsProperties tpbsProperties ->
            new TpbScraperEngine(tpbsProperties).scrapeTheBrowseDirectory()
        }
    }

    //TODO: Add input validation & validation messages
    //TODO: Move this to its own class
    private static void promptUserForInputAndWhenScrapeIsPressed(Closure tpbScraperLogic){
        TpbsProperties tpbsProperties = new TpbsProperties()
        new SwingBuilder().edt {
            lookAndFeel 'system'

            frame(id: 'mainWindow', title: 'The Pirate Bay Scraper (TPBS)', size: [300, 180], locationRelativeTo: null, defaultCloseOperation: JFrame.EXIT_ON_CLOSE, show: true, resizable: false) {
                borderLayout()

                panel(id: 'inputFields', constraints: BorderLayout.CENTER, border: compoundBorder([emptyBorder(10), titledBorder('Options')])) {
                    tableLayout() {
                        tr {
                            td { label("Media type:   ", horizontalAlignment: SwingConstants.CENTER) }
                            td { comboBox(focusable: false, id: 'mediaTypeField', items: MediaType.values()) }
                        }
                        tr {
                            td(colfill: false, rowfill: false) { label("Seeder threshold:   ", horizontalAlignment: SwingConstants.CENTER) }
                            td(colfill: false, rowfill: false) { textField(id: 'seederThresholdField', columns: 5, text: tpbsProperties.seederThreshold) }
                        }
                        tr {
                            td { label("# pages to crawl:   ", horizontalAlignment: SwingConstants.CENTER) }
                            td { textField(id: 'numPagesToCrawlField', columns: 2, text: tpbsProperties.numPagesToCrawl) }
                        }
                    }
                }

                panel(id: 'buttonContainer', constraints: BorderLayout.SOUTH) {
                    button(text: 'Scrape', actionPerformed: {
                        dispose()
                        tpbScraperLogic(tpbsProperties)
                    })
                }

                // Inject GUI values into UserInput bean
                bean(tpbsProperties,
                        seederThreshold: bind { safeParseInt(seederThresholdField.text) },
                        numPagesToCrawl: bind { safeParseInt(numPagesToCrawlField.text) },
                        mediaType: bind { MediaType.fromString(mediaTypeField.renderer.text) }
                )
            }
        }
    }

    private static Integer safeParseInt(String fieldValue){
        fieldValue.isInteger() ? fieldValue as Integer : 0
    }

}
