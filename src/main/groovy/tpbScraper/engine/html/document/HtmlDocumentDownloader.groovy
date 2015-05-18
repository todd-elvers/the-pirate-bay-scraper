package tpbScraper.engine.html.document

import org.jsoup.Connection
import org.jsoup.HttpStatusException
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import tpbScraper.domain.FailedDownloadException

class HtmlDocumentDownloader {
    private static final String TPB_RETURNED_NON_404_STATUS_CODE = "The Pirate Bay returned a 404 and appears to be down. Application shutting down."
    private static final int NUM_TIMES_TO_RETRY_DOWNLOAD = 3

    Document download(String url) {
        return downloadWithRetry(url)
    }

    private static Document downloadWithRetry(String url){
        int numberOfAttempts = 0
        while (numberOfAttempts < NUM_TIMES_TO_RETRY_DOWNLOAD) {
            numberOfAttempts++

            try {
                return downloadHtmlDocument(url)
            } catch (ignored) {}
        }

        throw new FailedDownloadException(url, NUM_TIMES_TO_RETRY_DOWNLOAD)
    }

    private static Document downloadHtmlDocument(String url) {
        Connection.Response response = Jsoup
                .connect(url)
                .userAgent("Mozilla/5.0 Firefox/3.0.4")
                .timeout(10000)
                .execute();

        switch(response.statusCode()){
            case 200:
                return response.parse()
            case 404:
                throw new HttpStatusException(TPB_RETURNED_NON_404_STATUS_CODE, response.statusCode(), url)
            default:
                throw new HttpStatusException(response.statusMessage(), response.statusCode(), url)
        }
    }
}
