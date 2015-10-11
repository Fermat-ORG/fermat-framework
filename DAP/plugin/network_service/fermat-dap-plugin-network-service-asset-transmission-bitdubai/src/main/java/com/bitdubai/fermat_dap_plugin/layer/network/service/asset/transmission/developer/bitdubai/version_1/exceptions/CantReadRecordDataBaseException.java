/*
 * @#CantReadRecordDataBaseException.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_dap_plugin.layer.network.service.asset.transmission.developer.bitdubai.version_1.exceptions;


import com.bitdubai.fermat_api.FermatException;

/**
 * The Class <code>com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.user.developer.bitdubai.version_1.exceptions.CantReadRecordDataBaseException</code> is
 * throw when error occurred updating new record in a table of the data base
 * <p/>
 * Created by Hendry Rodriguez - (elnegroevaristo@gmail.com) on 07/10/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CantReadRecordDataBaseException extends FermatException {

    /**
     * Represent the default message
     */
    public static final String DEFAULT_MESSAGE = "CAN'T READ RECORD ON DATABASE";

    /**
     * Constructor with parameters
     *
     * @param message
     * @param cause
     * @param context
     * @param possibleReason
     */
    public CantReadRecordDataBaseException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    /**
     * Constructor with parameters
     *
     * @param message
     * @param cause
     */
    public CantReadRecordDataBaseException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    /**
     * Constructor with parameter
     *
     * @param message
     */
    public CantReadRecordDataBaseException(final String message) {
        this(message, null);
    }

    /**
     * Constructor with parameter
     *
     * @param exception
     */
    public CantReadRecordDataBaseException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    /**
     * Constructor
     */
    public CantReadRecordDataBaseException() {
        this(DEFAULT_MESSAGE);
    }
}
