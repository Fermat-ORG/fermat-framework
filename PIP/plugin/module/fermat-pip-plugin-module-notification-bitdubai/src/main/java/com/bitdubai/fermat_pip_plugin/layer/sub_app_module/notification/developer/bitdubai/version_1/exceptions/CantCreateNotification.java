package com.bitdubai.fermat_pip_plugin.layer.sub_app_module.notification.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * <p/>
 * Created by Matias Furszyfer
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CantCreateNotification extends FermatException {

    public static final String DEFAULT_MESSAGE = "CAN'T CREATE NOTIFICATION EXCEPTION";

    public CantCreateNotification(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantCreateNotification(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantCreateNotification(final String message) {
        this(message, null);
    }

    public CantCreateNotification(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public CantCreateNotification() {
        this(DEFAULT_MESSAGE);
    }

}