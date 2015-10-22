package com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.redeem_point.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by franklin on 17/10/15.
 */
public class CantDeleteRecordDataBaseException extends FermatException {

    /**
     * Represent the default message
     */
    public static final String DEFAULT_MESSAGE = "CAN'T DELETE RECORD ON DATABASE";

    /**
     * Constructor with parameters
     *
     * @param message
     * @param cause
     * @param context
     * @param possibleReason
     */
    public CantDeleteRecordDataBaseException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    /**
     * Constructor with parameters
     *
     * @param message
     * @param cause
     */
    public CantDeleteRecordDataBaseException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    /**
     * Constructor with parameter
     *
     * @param message
     */
    public CantDeleteRecordDataBaseException(final String message) {
        this(message, null);
    }

    /**
     * Constructor with parameter
     *
     * @param exception
     */
    public CantDeleteRecordDataBaseException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    /**
     * Constructor
     */
    public CantDeleteRecordDataBaseException() {
        this(DEFAULT_MESSAGE);
    }
}
