package tpbScraper.engine
import org.apache.commons.io.FileUtils

import java.awt.Desktop

class BrowserFileHandler {

    /**
     * Opens a given file in the System's default browser.
     */
    static void openFileInDefaultBrowser(File resultsPageFile) {
        println "Opening \"${resultsPageFile.name}\" with this system's default browser."
        Desktop.getDesktop().browse(resultsPageFile.toURI());
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

    //TODO: Convert to Spring way
    private static File getResourceAsFile(String resourceName) {
        new File(Thread.currentThread().getContextClassLoader().getResource(resourceName).file)
    }
}
