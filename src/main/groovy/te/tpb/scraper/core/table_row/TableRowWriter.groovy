package te.tpb.scraper.core.table_row

import org.apache.commons.io.FileUtils
import org.springframework.stereotype.Component
import te.tpb.scraper.domain.TpbsProperties

@Component
class TableRowWriter {

    File writeFormattedHtmlToFile(String formattedHtml, TpbsProperties tpbsProperties) {
        println "Writing results page to disk."
        File outputFile = generateOutputFile(tpbsProperties)
        FileUtils.writeStringToFile(outputFile, formattedHtml)
        return outputFile
    }

    File generateOutputFile(TpbsProperties tpbsProperties){
        return new File(
                tpbsProperties.dataDirectory,
                "/results/TPBS Results Page - " + tpbsProperties.uniqueIdentifierForFilenames.call() + ".htm"
        )
    }

}
