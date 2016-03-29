package com.bitdubai.fermat_pip_api.layer.agent.timeout_notifier.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by rodrigo on 3/26/16.
 */
public class CantResetTimeOutAgentException extends FermatException {

    public static final String DEFAULT_MESSAGE = "There was an error reseting the counter of the Timeout Agent";

    public CantResetTimeOutAgentException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
