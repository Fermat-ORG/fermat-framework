package com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_fermat_monitor.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseSystemException;

/**
 * Created by mati
 */
public class CantDeleteRecordDataBaseException extends DatabaseSystemException {

    /**
     *
     */
    private static final long serialVersionUID = 1055830576804455604L;

    public static final String DEFAULT_MESSAGE = "CAN'T DELETE RECORD";

    public CantDeleteRecordDataBaseException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantDeleteRecordDataBaseException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantDeleteRecordDataBaseException(final String message) {
        this(message, null);
    }

    public CantDeleteRecordDataBaseException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public CantDeleteRecordDataBaseException() {
        this(DEFAULT_MESSAGE);
    }
}