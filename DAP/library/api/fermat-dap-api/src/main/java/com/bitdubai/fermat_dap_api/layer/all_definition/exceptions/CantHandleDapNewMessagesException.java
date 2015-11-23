package com.bitdubai.fermat_dap_api.layer.all_definition.exceptions;

import com.bitdubai.fermat_api.layer.DAPException;

/**
 * Created by Nerio on 23/11/15.
 */
public class CantHandleDapNewMessagesException extends DAPException {

    private static final String DEFAULT_MESSAGE = "CAN'T HANDLE NEW MESSAGES EXCEPTION";

    public CantHandleDapNewMessagesException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantHandleDapNewMessagesException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CantHandleDapNewMessagesException(String context, String possibleReason) {
        this(DEFAULT_MESSAGE, null, context, possibleReason);
    }
}
