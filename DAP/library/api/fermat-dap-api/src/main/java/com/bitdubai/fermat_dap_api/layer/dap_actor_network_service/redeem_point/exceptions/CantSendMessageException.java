package com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.redeem_point.exceptions;

import com.bitdubai.fermat_api.layer.DAPException;

/**
 * Created by franklin on 15/10/15.
 */
public class CantSendMessageException extends DAPException {


    public static final String DEFAULT_MESSAGE = "CAN'T SEND MESSAGE";


    public CantSendMessageException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantSendMessageException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantSendMessageException(final String message) {
        this(message, null);
    }

    public CantSendMessageException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public CantSendMessageException() {
        this(DEFAULT_MESSAGE);
    }
}
