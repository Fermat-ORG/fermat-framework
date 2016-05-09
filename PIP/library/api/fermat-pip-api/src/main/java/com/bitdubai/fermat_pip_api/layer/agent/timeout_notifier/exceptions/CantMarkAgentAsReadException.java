package com.bitdubai.fermat_pip_api.layer.agent.timeout_notifier.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by rodrigo on 4/9/16.
 */
public class CantMarkAgentAsReadException extends FermatException {
    public static final String DEFAULT_MESSAGE = "There was an error marking the Agent as read. Notification may still happen.";

    public CantMarkAgentAsReadException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CantMarkAgentAsReadException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
