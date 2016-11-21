package te.tpb.scraper.util;

import groovy.lang.Closure;
import te.tpb.scraper.domain.RetryFailedException;

public class Retry {

    public static <T> T retryOrThrow(int maxAttempts, Closure<T> closure) throws RetryFailedException {
        T result = null;
        int attemptCount = 0;

        Throwable capturedException = null;
        while(result == null && attemptCount < maxAttempts) {
            attemptCount++;

            try {
                result = closure.call();
            } catch (Throwable exception) {
                capturedException = exception;
            }
        }

        if(capturedException != null) {
            throw new RetryFailedException(
                    capturedException.getMessage(),
                    capturedException.getCause(),
                    maxAttempts
            );
        }

        return result;
    }

}
