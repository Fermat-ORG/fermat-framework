package com.bitdubai.fermat_pip_api.layer.agent.timeout_notifier.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by rodrigo on 3/26/16.
 */
public class CantStopTimeOutAgentException extends FermatException {

    public static final String DEFAULT_MESSAGE = "There was an error stpping the timeout Agent. Verify current status.";

    public CantStopTimeOutAgentException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CantStopTimeOutAgentException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
