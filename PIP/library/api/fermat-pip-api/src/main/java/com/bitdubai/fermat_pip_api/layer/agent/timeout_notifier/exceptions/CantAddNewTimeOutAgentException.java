package com.bitdubai.fermat_pip_api.layer.agent.timeout_notifier.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by rodrigo on 3/26/16.
 */
public class CantAddNewTimeOutAgentException extends FermatException {

    public static final String DEFAULT_MESSAGE = "There was an error adding a new Time Out Agent to the pool.";

    public CantAddNewTimeOutAgentException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CantAddNewTimeOutAgentException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
