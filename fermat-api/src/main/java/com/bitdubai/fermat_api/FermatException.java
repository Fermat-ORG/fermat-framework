package com.bitdubai.fermat_api;

public class FermatException extends Exception {

    public static final String CONTEXT_CONTENT_SEPARATOR = "&&";

    /**
     *
     */
    private static final long serialVersionUID = 6066719615565752788L;

    public static final String DEFAULT_MESSAGE = "FERMAT HAS DETECTED AN EXCEPTION: ";


    private final String exceptionName;
    private final FermatException cause;
    private final String context;
    private final String possibleReason;
    private final Integer depth;

    private FermatException(final String exceptionName, final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause);
        this.exceptionName = exceptionName;
        if (cause != null)
            this.cause = cause instanceof FermatException ? (FermatException) cause : FermatException.wrapException(cause);
        else
            this.cause = null;
        this.context = context == null || context.isEmpty() ? "N/A" : context;
        this.possibleReason = possibleReason == null || possibleReason.isEmpty() ? "N/A" : possibleReason;
        this.depth = (this.cause == null) ? Integer.valueOf(1) : Integer.valueOf(this.cause.getDepth() + 1);
    }

    /**
     * This is the constructor that every inherited FermatException must implement
     *
     * @param message        the short description of the why this exception happened, there is a public static constant called DEFAULT_MESSAGE that can be used here
     * @param cause          the exception that triggered the throwing of the current exception, if there are no other exceptions to be declared here, the cause should be null
     * @param context        a String that provides the values of the variables that could have affected the exception
     * @param possibleReason an explicative reason of why we believe this exception was most likely thrown
     */
    public FermatException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause);
        this.exceptionName = getClass().toString();
        if (cause != null)
            this.cause = cause instanceof FermatException ? (FermatException) cause : FermatException.wrapException(cause);
        else
            this.cause = null;
        this.context = context == null || context.isEmpty() ? "N/A" : context;
        this.possibleReason = possibleReason == null || possibleReason.isEmpty() ? "N/A" : possibleReason;
        this.depth = (this.cause == null) ? Integer.valueOf(1) : Integer.valueOf(this.cause.getDepth() + 1);
    }

    public static FermatException wrapException(final Exception exception) {
        if (exception instanceof FermatException)
            return (FermatException) exception;
        FermatException fermatException = new FermatException(exception.getClass().toString(), exception.getMessage(), null, "", "");
        fermatException.setStackTrace(exception.getStackTrace());
        return fermatException;
    }

    @Override
    public FermatException getCause() {
        return cause;
    }

    public String getContext() {
        return context;
    }

    public String getPossibleReason() {
        return possibleReason;
    }

    public int getDepth() {
        return depth.intValue();
    }

    public String getFormattedContext() {
        StringBuilder builder = new StringBuilder("");
        for (String contextPart : context.split(CONTEXT_CONTENT_SEPARATOR))
            if (!contextPart.isEmpty())
                builder.append(contextPart).append("\n");
        return builder.toString();
    }

    public String getFormattedTrace() {
        StringBuilder builder = new StringBuilder();
        for (StackTraceElement element : getStackTrace())
            //if(element.getClassName().contains("com.bitdubai"))
            builder.append("Class: ").append(element.getClassName()).append(" - Line: ").append(element.getLineNumber()).append("\n");
        return builder.toString();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        if (cause != null) {
            builder.append(cause.toString());

            builder.append("Exception Number: ").append(depth.toString()).append("\n");
            builder.append("Exception Type: ").append(exceptionName).append("\n");
            builder.append("Exception Message: ").append(getMessage()).append("\n");
            builder.append("Exception Possible Cause: ");
            builder.append(getPossibleReason()).append("\n");
            builder.append("Exception Context: ");
            builder.append("\n---------------------------------------------------------------------------------\n");
            builder.append(getFormattedContext());
            builder.append("---------------------------------------------------------------------------------\n");

        } else {

            builder.append("Exception Number: ").append(depth.toString()).append("\n");
            builder.append("Exception Type: ").append(exceptionName).append("\n");
            builder.append("Exception Message: ").append(getMessage()).append("\n");
            builder.append("Exception Possible Cause: ");
            builder.append(getPossibleReason()).append("\n");
            builder.append("Exception Context: ");
            builder.append("\n---------------------------------------------------------------------------------\n");
            builder.append(getFormattedContext());
            builder.append("---------------------------------------------------------------------------------\n");
            builder.append("Exception Stack Trace: \n");
            builder.append("---------------------------------------------------------------------------------\n");
            builder.append(getFormattedTrace());
            builder.append("---------------------------------------------------------------------------------\n");

        }

        return builder.toString();
    }
}
