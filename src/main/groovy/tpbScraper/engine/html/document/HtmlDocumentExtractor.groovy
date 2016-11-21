package tpbScraper.engine.html.document

import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service

@Component
class HtmlDocumentExtractor {

    StringBuilder extractRowsAboveThreshold(Document document, int seederCountThreshold){
        StringBuilder tableRowsHtml = new StringBuilder()

        // Find all tr elements (excluding the header row)
        Elements tableRowElements = document.select('#searchResult tr:not(.header)')

        tableRowElements.each { tr ->
            // Get the td column containing the 'seeder' value
            Element tdContainingSeederValue = tr.select('td[align="right"]').first()

            if(tdContainingSeederValue){
                int seederCount = tdContainingSeederValue.text().replaceAll(/[^0-9]/, "").toInteger()

                // If the seeder value is greater than the threshold, grab the tableRow's html
                if(seederCount >= seederCountThreshold){
                    tableRowsHtml.append(tr.outerHtml())
                }
            }
        }

        return tableRowsHtml
    }

}
