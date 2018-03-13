package te.tpb.scraper.util

import groovy.transform.CompileStatic
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Component

import java.awt.Desktop
import java.util.zip.ZipEntry

@Component
@CompileStatic
@SuppressWarnings("GrMethodMayBeStatic")
class BrowserFileHandler {
    private static final String ZIP_OF_TEMPLATE_DATA = "tpbs_files.zip"

    /**
     * Opens a given file in the System's default browser.
     */
    void openFileInDefaultBrowser(File resultsPageFile) {
        println "Opening \"${resultsPageFile.name}\" with this system's default browser."
        Desktop.getDesktop().browse(resultsPageFile.toURI())
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
            File destination = new File(dataDirectoryPath)
            extractInnerZipContents(ZIP_OF_TEMPLATE_DATA, destination)
        }
    }

    private void extractInnerZipContents(String zipFileName, File destination) {
        new ZipInputStream(new ClassPathResource(zipFileName).getInputStream()).withCloseable { inputStream ->

            ZipEntry zipEntry = null
            while ((zipEntry = inputStream.getNextEntry()) != null) {
                File fileFromZip = new File(destination, zipEntry.getName())

                if (zipEntry.isDirectory()) {
                    fileFromZip.mkdirs()
                } else {
                    extractFileFromZip(inputStream, fileFromZip)
                }
            }
        }
    }

    private void extractFileFromZip(InputStream inputStream, File fileFromZip) {

        new FileOutputStream(fileFromZip).withCloseable { outputStream ->
            byte[] buf = new byte[1024]
            int len
            while ((len = inputStream.read(buf)) > 0) {
                outputStream.write(buf, 0, len)
            }
        }
    }
}

import java.util.zip.ZipInputStream
