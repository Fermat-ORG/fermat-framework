package com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_fermat_monitor.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseSystemException;

/**
 * Created by toshiba on 24/03/2015.
 */
public class CantUpdateRecordDataBaseException extends DatabaseSystemException {

    /**
     *
     */
    private static final long serialVersionUID = 9099890596007453950L;

    public static final String DEFAULT_MESSAGE = "CAN'T UPDATE RECORD";

    public CantUpdateRecordDataBaseException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantUpdateRecordDataBaseException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantUpdateRecordDataBaseException(final String message) {
        this(message, null);
    }

    public CantUpdateRecordDataBaseException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public CantUpdateRecordDataBaseException() {
        this(DEFAULT_MESSAGE);
    }
}
