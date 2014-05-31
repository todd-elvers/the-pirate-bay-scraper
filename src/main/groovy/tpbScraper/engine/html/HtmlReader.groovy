package tpbScraper.engine.html

import groovyx.net.http.HTTPBuilder

import static groovyx.net.http.ContentType.TEXT
import static groovyx.net.http.Method.GET

class HtmlReader {

    StringBuilder readInHTML(String url) {
        StringBuilder html
        HTTPBuilder webpage

        int numberOfAttempts = 0
        while (!html && numberOfAttempts < 3) {
            try {
                numberOfAttempts++
                webpage = new HTTPBuilder(url)
                html = readHTMLFromWebpage(webpage)
            } catch (all) {
                println "[HtmlReader] FAILURE: ${all.getMessage()}"
            }
        }

        if (numberOfAttempts == 3) {
            println "[HtmlReader] FAILURE: 3 failed attempts to read URL"
        } else {
            println " SUCCESS"
        }

        html ?: new StringBuilder()
    }

    private StringBuilder readHTMLFromWebpage(HTTPBuilder webpage) {
        StringBuilder html = new StringBuilder()

        webpage.request(GET, TEXT) {
            headers.'User-Agent' = "Mozilla/5.0 Firefox/3.0.4"
            headers.Accept = 'text/plain'

            response.success = { resp, htmlReader ->
                assert resp.statusLine.statusCode == 200: "[HtmlReader] Non-200 status code returned for URL ${url}"

                for (String lineOfHtml : htmlReader) {
                    html.append(lineOfHtml);
                }
            }

            response.failure = { resp ->
                println "[HtmlReader] Failure response while attempting to access URL \"${url}\" - HTTP Code: ${resp.statusLine.statusCode}, Reason: ${resp.statusLine.reasonPhrase}"
            }
        }

        return html
    }
}
