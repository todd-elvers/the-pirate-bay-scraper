package te.tpb.scraper.core.document

import org.jsoup.Connection
import org.jsoup.HttpStatusException
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.springframework.stereotype.Service
import te.tpb.scraper.util.Retry

@Service
class HtmlDocumentDownloader {
    private static final String TPB_404_RESPONSE_MESSAGE = "The Pirate Bay returned a 404 and appears to be down."

    Optional<Document> download(String url, int numRetriesPerDownload = 3) {
        Retry.retryOrIgnore(numRetriesPerDownload) {
            downloadHtmlDocument(url)
        }
    }

    private Optional<Document> downloadHtmlDocument(String url) {
        Connection.Response response = Jsoup
                .connect(url)
                .userAgent("Mozilla/5.0 Firefox/3.0.4")
                .timeout(10000)
                .execute()

        switch(response.statusCode()){
            case 200: return Optional.of(response.parse())
            case 503: return Optional.empty()
            case 404: throw new HttpStatusException(TPB_404_RESPONSE_MESSAGE, response.statusCode(), url)
            default : throw new HttpStatusException(response.statusMessage(), response.statusCode(), url)
        }
    }
}
