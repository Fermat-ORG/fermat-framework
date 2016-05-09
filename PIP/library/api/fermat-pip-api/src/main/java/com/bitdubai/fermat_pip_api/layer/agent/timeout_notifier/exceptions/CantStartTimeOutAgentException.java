package com.bitdubai.fermat_pip_api.layer.agent.timeout_notifier.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by rodrigo on 3/26/16.
 */
public class CantStartTimeOutAgentException extends FermatException {

    public static final String DEFAULT_MESSAGE = "There was an error starting the Timeout Agent";

    public CantStartTimeOutAgentException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CantStartTimeOutAgentException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
