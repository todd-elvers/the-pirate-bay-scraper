package tpbScraper.engine.html.document

import org.jsoup.Connection
import org.jsoup.HttpStatusException
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import tpbScraper.domain.FailedDownloadException

//TODO: Rewrite this, it's hard to read
class HtmlDocumentDownloader {
    private static final String TPB_404_RESPONSE_MESSAGE = "The Pirate Bay returned a 404 and appears to be down."
    private static final int NUM_TIMES_TO_RETRY_DOWNLOAD = 3

    Document download(String url) {
        return downloadWithRetry(url)
    }

    private static Document downloadWithRetry(String url){
        Document htmlDocument = null

        int attemptCount = 0
        while (!htmlDocument && attemptCount < NUM_TIMES_TO_RETRY_DOWNLOAD) {
            attemptCount++

            try {
                htmlDocument = downloadHtmlDocument(url)
            } catch (ignored) {}
        }

        if(htmlDocument) {
            return htmlDocument
        } else {
            throw new FailedDownloadException(url, NUM_TIMES_TO_RETRY_DOWNLOAD)
        }
    }

    private static Document downloadHtmlDocument(String url) {
        Connection.Response response = Jsoup
                .connect(url)
                .userAgent("Mozilla/5.0 Firefox/3.0.4")
                .timeout(10000)
                .execute();

        switch(response.statusCode()){
            case 200: return response.parse()
            case 404: throw new HttpStatusException(TPB_404_RESPONSE_MESSAGE, response.statusCode(), url)
            default : throw new HttpStatusException(response.statusMessage(), response.statusCode(), url)
        }
    }
}
