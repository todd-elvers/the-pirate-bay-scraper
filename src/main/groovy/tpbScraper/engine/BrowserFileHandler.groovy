package tpbScraper.engine

import org.apache.commons.io.FileUtils
import tpbScraper.util.ClassPathUtil

/**
 * User: Todd Elvers
 * DateTime: 7/28/13 6:54 PM
 */
class BrowserFileHandler {

    static void openFileInChrome(File resultsPageFilePath) {
        println "Opening \"${resultsPageFilePath.name}\" with Chrome."
        Runtime.getRuntime().exec("cmd /c start chrome \"${resultsPageFilePath.absolutePath}\"")
    }

    static void writeResultPageFilesToDirIfTheyAreMissing(String dataDirectoryPath){
        File staticContentDir = new File(dataDirectoryPath, "/static_content/")
        File templateDir = new File(dataDirectoryPath, "/template/")
        if(!staticContentDir.exists() || !templateDir.exists()){
            FileUtils.copyDirectory(ClassPathUtil.getResourceAsFile("tpbc_files"), new File(dataDirectoryPath))
        }
    }
}
