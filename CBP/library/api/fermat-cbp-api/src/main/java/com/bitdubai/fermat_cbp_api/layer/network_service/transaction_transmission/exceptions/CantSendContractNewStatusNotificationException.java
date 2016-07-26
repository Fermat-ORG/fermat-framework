package com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.exceptions;

import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CBPException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 20/11/15.
 */
public class CantSendContractNewStatusNotificationException extends CBPException {

    public static final String DEFAULT_MESSAGE = "CAN'T SEND THE CONTRACT NEW STATUS NOTIFICATION";

    public CantSendContractNewStatusNotificationException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantSendContractNewStatusNotificationException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantSendContractNewStatusNotificationException(final String message) {
        this(message, null);
    }

    public CantSendContractNewStatusNotificationException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public CantSendContractNewStatusNotificationException() {
        this(DEFAULT_MESSAGE);
    }
}
