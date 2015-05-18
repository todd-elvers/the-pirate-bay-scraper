package tpbScraper.engine.html.table_row

import org.apache.commons.io.FileUtils
import tpbScraper.domain.TpbsProperties

class TableRowWriter {

    File writeFormattedHtmlToFile(StringBuilder formattedHtml, TpbsProperties tpbsProperties) {
        println "Writing results page to disk."
        File outputFile = generateOutputFile(tpbsProperties)
        FileUtils.writeStringToFile(outputFile, formattedHtml.toString());
        return outputFile
    }

    File generateOutputFile(TpbsProperties tpbsProperties){
        return new File(tpbsProperties.dataDirectory, "/results/TPBS Results Page - " + tpbsProperties.uniqueIdentifierForFilenames.call() + ".htm")
    }

}
