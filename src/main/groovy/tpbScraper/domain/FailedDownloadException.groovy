package tpbScraper.domain

class FailedDownloadException extends RuntimeException {
    FailedDownloadException(String url, int numAttempts){
        super("""
            The page '$url' returned 200, but downloading the HTML document has unsuccessful after $numAttempts attempts.
            Possible causes of this issue:
                - ThePirateBay is going really slowly
                - Your ISP is going really slowly
                - Your internet is down
            It may also be a fluke.  Feel free to try again.

        """.stripIndent())
    }
}
