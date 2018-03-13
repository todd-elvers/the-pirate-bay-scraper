package te.tpb.scraper.util
import org.apache.commons.io.FileUtils
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service

import java.awt.Desktop

@Component
@SuppressWarnings("GrMethodMayBeStatic")
class BrowserFileHandler {

    /**
     * Opens a given file in the System's default browser.
     */
    void openFileInDefaultBrowser(File resultsPageFile) {
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
    void copyTemplateFilesToDataDirIfMissing(String dataDirectoryPath){
        File staticContentDir = new File(dataDirectoryPath, "/static_content/")
        File templateDir = new File(dataDirectoryPath, "/template/")
        if(!staticContentDir.exists() || !templateDir.exists()){
            FileUtils.copyDirectory(getResourceAsFile("tpbs_files"), new File(dataDirectoryPath))
        }
    }

    private static File getResourceAsFile(String resourceName) {
        return new ClassPathResource(resourceName).file
    }
}
