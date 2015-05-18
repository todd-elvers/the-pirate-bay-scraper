package tpbScraper.engine
import org.apache.commons.io.FileUtils

class BrowserFileHandler {

    /**
     * Opens a given file locally in Chrome.  This method assumes you
     * have 'chrome' on your path and are running on a Windows machine.
     */
    static void openFileInChrome(File resultsPageFilePath) {
        println "Opening \"${resultsPageFilePath.name}\" with Chrome."
        "cmd /c start chrome \"${resultsPageFilePath.absolutePath}\"".execute()
    }

    /**
     * If the /static_content/ or /template/ directories are missing in the data directory,
     * this will re-copy them over so that results can be properly displayed to the user when
     * a scrape completes.
     *
     * @param dataDirectoryPath the path to the directory where TPBS-related files are stored
     */
    static void copyNecessaryFilesToDataDirIfMissing(String dataDirectoryPath){
        File staticContentDir = new File(dataDirectoryPath, "/static_content/")
        File templateDir = new File(dataDirectoryPath, "/template/")
        if(!staticContentDir.exists() || !templateDir.exists()){
            FileUtils.copyDirectory(getResourceAsFile("tpbs_files"), new File(dataDirectoryPath))
        }
    }

    private static File getResourceAsFile(String resourceName) {
        new File(Thread.currentThread().getContextClassLoader().getResource(resourceName).file)
    }
}
