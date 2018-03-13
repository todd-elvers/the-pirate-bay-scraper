package te.tpb.scraper.util;

import groovy.lang.Closure;

public class Retry {

    private static final int MS_TO_SLEEP_AFTER_EXCEPTION = 2_000;

    public static <T> T retryOrIgnore(int maxAttempts, Closure<T> closure) {
        T result = null;
        int attemptCount = 0;

        while(result == null && attemptCount < maxAttempts) {
            attemptCount++;

            try {
                result = closure.call();
            } catch (Throwable exception) {
                sleep();
            }
        }

        return result;
    }

    private static void sleep() {
        try {
            Thread.sleep(MS_TO_SLEEP_AFTER_EXCEPTION);
        }catch(Throwable ignored) {
            // NO-OP
        }
    }

}
