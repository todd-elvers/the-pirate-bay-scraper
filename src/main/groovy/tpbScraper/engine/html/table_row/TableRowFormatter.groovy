package tpbScraper.engine.html.table_row

import org.apache.commons.io.FileUtils
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import tpbScraper.domain.TpbsProperties

// TODO: This is so simple, maybe merge the formatter and writer?
@Component
class TableRowFormatter {
    private static final String RESULTS_TEMPLATE_FILENAME = "/template/TPBS Results Page - Template.htm"

    String format(StringBuilder tableRowsHtml, TpbsProperties tpbsProperties) {
        String resultsPageTemplate = getResultsPageTemplate(tpbsProperties)
        String resultsPageHtml = injectTableRowsIntoTemplate(tableRowsHtml, resultsPageTemplate)
        resultsPageHtml = fixAllLinksToWorkLocally(resultsPageHtml, tpbsProperties)
        return resultsPageHtml
    }

    private static String injectTableRowsIntoTemplate(StringBuilder tableRowsHtml, String resultsPageTemplate) {
        return resultsPageTemplate.replaceFirst("<!-- TABLE ROWS HTML HERE -->", tableRowsHtml.toString())
    }

    // TODO: Fix the bug in this
    private static String fixAllLinksToWorkLocally(String html, TpbsProperties properties) {
        return html
                .replace("/torrent/", "${properties.tpbUrl}/torrent/")
                .replace("/browse/", "${properties.tpbUrl}/browse/")
                .replace("/user/", "${properties.tpbUrl}/user/")
                .replace("/static/", "${properties.tpbUrl}/static/")
    }

    private static String getResultsPageTemplate(TpbsProperties properties) {
        File resultsPageTemplateFile = new File(properties.dataDirectory, RESULTS_TEMPLATE_FILENAME)
        FileUtils.readFileToString(resultsPageTemplateFile)
    }
}
