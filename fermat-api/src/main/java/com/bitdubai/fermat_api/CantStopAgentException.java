package com.bitdubai.fermat_api;

/**
 * The exception <code>com.bitdubai.fermat_api.CantStartAgentException</code>
 * is thrown when there is an error trying to start an agent.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 04/12/2015.
 */
public class CantStopAgentException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T STOP AGENT EXCEPTION";

    public CantStopAgentException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantStopAgentException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CantStopAgentException() {
        this(DEFAULT_MESSAGE, null, null, null);
    }

    public CantStopAgentException(String message) {
        this(DEFAULT_MESSAGE, null, null, message);
    }

}
