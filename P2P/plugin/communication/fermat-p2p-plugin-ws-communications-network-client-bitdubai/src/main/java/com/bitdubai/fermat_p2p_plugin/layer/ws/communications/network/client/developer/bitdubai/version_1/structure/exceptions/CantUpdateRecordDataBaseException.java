/*
* @#CantUpdateRecordDataBaseException.java - 2015
* Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
* BITDUBAI/CONFIDENTIAL
*/
package com.bitdubai.fermat_p2p_plugin.layer.ws.communications.network.client.developer.bitdubai.version_1.structure.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.ws.communications.network.client.developer.bitdubai.version_1.structure.exceptions.CantUpdateRecordDataBaseException</code>
 * <p/>
 * Created by Hendry Rodriguez - (elnegroevaristo@gmail.com) on 13/11/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CantUpdateRecordDataBaseException extends FermatException {

    /**
     * Represent the default message
     */
    public static final String DEFAULT_MESSAGE = "CAN'T UPDATE RECORD ON DATABASE";

    /**
     * Constructor with parameters
     *
     * @param message
     * @param cause
     * @param context
     * @param possibleReason
     */
    public CantUpdateRecordDataBaseException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    /**
     * Constructor with parameters
     *
     * @param message
     * @param cause
     */
    public CantUpdateRecordDataBaseException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    /**
     * Constructor with parameter
     *
     * @param message
     */
    public CantUpdateRecordDataBaseException(final String message) {
        this(message, null);
    }

    /**
     * Constructor with parameter
     *
     * @param exception
     */
    public CantUpdateRecordDataBaseException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    /**
     * Constructor
     */
    public CantUpdateRecordDataBaseException() {
        this(DEFAULT_MESSAGE);
    }
}
