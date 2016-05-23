package com.bitdubai.fermat_api;

/**
 * The exception <code>com.bitdubai.fermat_api.CantStartAgentException</code>
 * is thrown when there is an error trying to start an agent.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 04/12/2015.
 */
public class CantStartAgentException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T START AGENT EXCEPTION";

    public CantStartAgentException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantStartAgentException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CantStartAgentException() {
        this(DEFAULT_MESSAGE, null, null, null);
    }

}
