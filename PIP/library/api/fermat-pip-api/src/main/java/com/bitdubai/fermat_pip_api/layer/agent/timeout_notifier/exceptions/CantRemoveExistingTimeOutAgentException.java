package com.bitdubai.fermat_pip_api.layer.agent.timeout_notifier.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by rodrigo on 3/26/16.
 */
public class CantRemoveExistingTimeOutAgentException extends FermatException {

    public static final String DEFAULT_MESSAGE = "There was an error removing an existing Time Out Agent from the pool.";

    public CantRemoveExistingTimeOutAgentException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantRemoveExistingTimeOutAgentException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }
}
