package com.bitdubai.fermat_api.layer.osa_android.location_system.exceptions;

import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseSystemException;

public class CantGetDeviceLocationException extends DatabaseSystemException {

    /**
     *
     */
    private static final long serialVersionUID = 4831581407768860533L;

    public static final String DEFAULT_MESSAGE = "CAN'T GET DEVICE LOCATION";

    public CantGetDeviceLocationException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantGetDeviceLocationException(final Exception cause, final String context, final String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CantGetDeviceLocationException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantGetDeviceLocationException(final String message) {
        this(message, null);
    }

    public CantGetDeviceLocationException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public CantGetDeviceLocationException() {
        this(DEFAULT_MESSAGE);
    }
}
