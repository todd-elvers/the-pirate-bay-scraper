package tpbScraper.util

import te.tpb.scraper.domain.RetryFailedException
import tpbScraper.domain.FailedDownloadException

class Retry {

    static <T> T retryOrThrow(int maxAttempts, Closure<T> closure) {
        T result = null
        int attemptCount = 0

        Throwable capturedException = null;
        while (!result && attemptCount < maxAttempts) {
            attemptCount++

            try {
                result = closure()
            } catch (Throwable ignored) {
                capturedException = ignored
            }
        }

        if (capturedException) {
            throw new RetryFailedException(
                    capturedException.message,
                    capturedException.cause,
                    maxAttempts
            )
        }

        return result
    }

}
