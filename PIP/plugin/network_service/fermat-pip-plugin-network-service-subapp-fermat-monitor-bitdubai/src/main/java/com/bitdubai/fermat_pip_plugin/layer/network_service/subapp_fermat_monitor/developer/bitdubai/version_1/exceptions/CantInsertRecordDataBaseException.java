package com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_fermat_monitor.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseSystemException;

/**
 * Created by toshiba on 24/03/2015.
 */
public class CantInsertRecordDataBaseException extends DatabaseSystemException {

    /**
     *
     */
    private static final long serialVersionUID = -327709783649435604L;

    public static final String DEFAULT_MESSAGE = "CAN'T INSERT RECORD";

    public CantInsertRecordDataBaseException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantInsertRecordDataBaseException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantInsertRecordDataBaseException(final String message) {
        this(message, null);
    }

    public CantInsertRecordDataBaseException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public CantInsertRecordDataBaseException() {
        this(DEFAULT_MESSAGE);
    }
}
