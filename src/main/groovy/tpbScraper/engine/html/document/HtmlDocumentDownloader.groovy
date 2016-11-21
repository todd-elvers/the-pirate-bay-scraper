package tpbScraper.engine.html.document

import org.jsoup.Connection
import org.jsoup.HttpStatusException
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.springframework.stereotype.Service
import te.tpb.scraper.util.Retry

@Service
class HtmlDocumentDownloader {
    private static final String TPB_404_RESPONSE_MESSAGE = "The Pirate Bay returned a 404 and appears to be down."

    Document download(String url, int numRetriesPerDownload = 3) {
        Retry.retryOrThrow(3) {
            downloadHtmlDocument(url)
        }
    }

    private Document downloadHtmlDocument(String url) {
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
