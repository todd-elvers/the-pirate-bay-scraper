package te.tpb.scraper.domain;

public class RetryFailedException extends RuntimeException {

    private int numRetriesAttempted;

    public RetryFailedException() {}

    public RetryFailedException(String message, Throwable cause, int numRetriesAttempted) {
        super(formatMessage(message, numRetriesAttempted), cause);
        this.numRetriesAttempted = numRetriesAttempted;
    }

    public int getNumRetriesAttempted() {
        return numRetriesAttempted;
    }

    public static String formatMessage(String message, int numRetriesAttempted) {
        return "After " + numRetriesAttempted + " attempts, " +
                "the task failed with the following:\n" + message;
    }
}
