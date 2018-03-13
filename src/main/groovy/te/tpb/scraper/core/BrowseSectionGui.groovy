package te.tpb.scraper.core
import groovy.swing.SwingBuilder
import te.tpb.scraper.domain.MediaType
import te.tpb.scraper.domain.TpbsProperties
import org.springframework.stereotype.Component

import javax.annotation.PostConstruct
import javax.swing.*
import java.awt.*

//TODO: Add input validation & validation messages to this class
@Component
class BrowseSectionGui {

    void promptUserToScrape(Closure taskToExecuteAfterScrapeIsPressed){
        TpbsProperties tpbsProperties = new TpbsProperties()

        // Build the GUI on the Event Dispatch Thread
        new SwingBuilder().edt {
            lookAndFeel 'system'

            frame(id: 'mainWindow', title: 'ThePirateBay Scraper (TPBS)', size: [300, 180], locationRelativeTo: null, defaultCloseOperation: JFrame.EXIT_ON_CLOSE, show: true, resizable: false) {
                borderLayout()

                panel(id: 'inputFields', constraints: BorderLayout.CENTER, border: compoundBorder([emptyBorder(10), titledBorder('Scrape Options')])) {
                    tableLayout() {
                        tr {
                            td { label("Media type:   ", horizontalAlignment: SwingConstants.CENTER) }
                            td { comboBox(id: 'mediaTypeField', items: MediaType.values()) }
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

                        // Pass the tpbsProperties set by the user to the scraper engine & begin scraping
                        taskToExecuteAfterScrapeIsPressed(tpbsProperties)
                    })
                }

                bean(tpbsProperties,
                        seederThreshold: bind { safeParseInt(seederThresholdField.text) },
                        numPagesToCrawl: bind { safeParseInt(numPagesToCrawlField.text) },
                        mediaType: bind { MediaType.valueOf(mediaTypeField.renderer.text) }
                )
            }
        }
    }

    @PostConstruct
    private void ensureHeadlessModeIsOff() {
        System.setProperty('java.awt.headless', 'false')
    }

    private static Integer safeParseInt(String fieldValue){
        fieldValue.isInteger() ? fieldValue.toInteger() : 0
    }

}
