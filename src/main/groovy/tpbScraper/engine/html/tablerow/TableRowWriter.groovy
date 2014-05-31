package tpbScraper.engine.html.tablerow

import org.apache.commons.io.FileUtils
import tpbScraper.domain.TpbsProperties

class TableRowWriter {

    private TpbsProperties properties
    File resultsPageFile

    TableRowWriter(TpbsProperties properties) {
        this.properties = properties
    }

    void writeTableRowsToResultsPage(StringBuilder tableRowsHtml) {
        println "Writing results page to disk."

        //TODO: This should be extracted to a TableRowFormatter, it's scope creep
        StringBuilder resultsPageHtml
        resultsPageHtml = combineTableRowsWithTemplate(tableRowsHtml, getResultsPageTemplate())
        resultsPageHtml = fixAllLinksToWorkLocally(resultsPageHtml)

        writeResultsPageHtmlToDisk(resultsPageHtml)
    }

    private String getResultsPageTemplate() {
        File resultsPageTemplateFile = new File(properties.dataDirectory, "/template/TPBS Results Page - Template.htm")
        FileUtils.readFileToString(resultsPageTemplateFile)
    }

    private StringBuilder combineTableRowsWithTemplate(StringBuilder tableRowsHtml, String resultsPageTemplate) {
        new StringBuilder(resultsPageTemplate.replaceFirst("<!-- TABLE ROWS HTML HERE -->", tableRowsHtml.toString()))
    }

    private StringBuilder fixAllLinksToWorkLocally(StringBuilder html) {
        new StringBuilder(
                html.toString()
                        .replace("/torrent/", "${properties.tpbUrl}/torrent/")
                        .replace("/browse/", "${properties.tpbUrl}/browse/")
                        .replace("/user/", "${properties.tpbUrl}/user/")
                        .replace("/static/", "${properties.tpbUrl}/static/")
        )
    }

    private void writeResultsPageHtmlToDisk(StringBuilder resultPageHtml) {
        resultsPageFile = new File(properties.dataDirectory, "/results/TPBS Results Page - " + properties.uniqueIdentifierForFilenames.call() + ".htm")
        FileUtils.writeStringToFile(resultsPageFile, resultPageHtml.toString());
    }
}
