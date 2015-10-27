package com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by natalia on 04/09/15.
 */
public class CantConfirmNotificationException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T CONFIRM NOTIFICATION EXCEPTION";

    public CantConfirmNotificationException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantConfirmNotificationException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }
}
