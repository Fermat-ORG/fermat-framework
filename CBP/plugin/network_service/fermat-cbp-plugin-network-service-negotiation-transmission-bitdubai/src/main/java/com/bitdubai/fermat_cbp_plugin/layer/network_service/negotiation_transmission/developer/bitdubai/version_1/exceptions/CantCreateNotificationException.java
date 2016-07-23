package com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by loui on 22/02/15.
 */
public class CantCreateNotificationException extends FermatException {

    /**
     *
     */
    private static final long serialVersionUID = 7137746546837677676L;

    public static final String DEFAULT_MESSAGE = "CAN'T CREATE NOTIFICATION";

    public CantCreateNotificationException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantCreateNotificationException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantCreateNotificationException(final String message) {
        this(message, null);
    }

    public CantCreateNotificationException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public CantCreateNotificationException() {
        this(DEFAULT_MESSAGE);
    }
}
