package tpbScraper.engine.html.table_row

import org.apache.commons.io.FileUtils
import tpbScraper.domain.TpbsProperties

class TableRowFormatter {
    private static final String RESULTS_TEMPLATE_FILENAME = "/template/TPBS Results Page - Template.htm"

    StringBuilder format(StringBuilder tableRowsHtml, TpbsProperties tpbcProperties){
        StringBuilder resultsPageHtml
        resultsPageHtml = combineTableRowsWithTemplate(tableRowsHtml, getResultsPageTemplate(tpbcProperties))
        resultsPageHtml = fixAllLinksToWorkLocally(resultsPageHtml, tpbcProperties)
        return resultsPageHtml
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

    private static String getResultsPageTemplate(TpbsProperties properties) {
        File resultsPageTemplateFile = new File(properties.dataDirectory, RESULTS_TEMPLATE_FILENAME)
        FileUtils.readFileToString(resultsPageTemplateFile)
    }
}
