package tpbScraper.engine.html.tablerow

import org.apache.commons.io.FileUtils
import tpbScraper.domain.TpbsProperties

class TableRowWriter {

    File resultsPageFile

    void writeTableRowsToResultsPage(StringBuilder tableRowsHtml, TpbsProperties properties) {
        println "Writing results page to disk."

        //TODO: This should be extracted to a TableRowFormatter, it's scope creep
        StringBuilder resultsPageHtml
        resultsPageHtml = combineTableRowsWithTemplate(tableRowsHtml, getResultsPageTemplate(properties))
        resultsPageHtml = fixAllLinksToWorkLocally(resultsPageHtml, properties)

        writeResultsPageHtmlToDisk(resultsPageHtml, properties)
    }

    private static String getResultsPageTemplate(TpbsProperties properties) {
        File resultsPageTemplateFile = new File(properties.dataDirectory, "/template/TPBS Results Page - Template.htm")
        FileUtils.readFileToString(resultsPageTemplateFile)
    }

    private static StringBuilder combineTableRowsWithTemplate(StringBuilder tableRowsHtml, String resultsPageTemplate) {
        new StringBuilder(resultsPageTemplate.replaceFirst("<!-- TABLE ROWS HTML HERE -->", tableRowsHtml.toString()))
    }

    private static StringBuilder fixAllLinksToWorkLocally(StringBuilder html, TpbsProperties properties) {
        new StringBuilder(
                html.toString()
                        .replace("/torrent/", "${properties.tpbUrl}/torrent/")
                        .replace("/browse/", "${properties.tpbUrl}/browse/")
                        .replace("/user/", "${properties.tpbUrl}/user/")
                        .replace("/static/", "${properties.tpbUrl}/static/")
        )
    }

    private void writeResultsPageHtmlToDisk(StringBuilder resultPageHtml, TpbsProperties properties) {
        resultsPageFile = new File(properties.dataDirectory, "/results/TPBS Results Page - " + properties.uniqueIdentifierForFilenames.call() + ".htm")
        FileUtils.writeStringToFile(resultsPageFile, resultPageHtml.toString());
    }
}
