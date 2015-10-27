package com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by natalia on 04/09/15.
 */
public class ErrorGetNotificationsIntraUserException extends FermatException {

    private static final String DEFAULT_MESSAGE = "ERROR GETTING NOTIFICATIONS";

    public ErrorGetNotificationsIntraUserException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public ErrorGetNotificationsIntraUserException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }
}
