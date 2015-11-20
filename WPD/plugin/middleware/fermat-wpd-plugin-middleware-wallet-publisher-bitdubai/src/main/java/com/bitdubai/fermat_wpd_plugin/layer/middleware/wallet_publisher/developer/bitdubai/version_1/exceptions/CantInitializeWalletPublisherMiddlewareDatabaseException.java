/*
 * @#CantInitializeWalletPublisherMiddlewareDatabaseException.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_publisher.developer.bitdubai.version_1.exceptions;


import com.bitdubai.fermat_api.FermatException;

/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_publisher.developer.bitdubai.version_1.exceptions.CantInitializeWalletPublisherMiddlewareDatabaseException</code> is
 * throw when error occurred initialize the data base
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 03/08/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CantInitializeWalletPublisherMiddlewareDatabaseException extends FermatException {

    /**
     * Represent the default message
     */
    public static final String DEFAULT_MESSAGE = "CAN'T INTIALIZE REQUESTED PUBLISHER MIDDLEWARE DATABASE EXCEPTION";

    /**
     * Constructor with parameters
     *
     * @param message
     * @param cause
     * @param context
     * @param possibleReason
     */
    public CantInitializeWalletPublisherMiddlewareDatabaseException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    /**
     * Constructor with parameters
     *
     * @param message
     * @param cause
     */
    public CantInitializeWalletPublisherMiddlewareDatabaseException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    /**
     * Constructor with parameter
     *
     * @param message
     */
    public CantInitializeWalletPublisherMiddlewareDatabaseException(final String message) {
        this(message, null);
    }

    /**
     * Constructor with parameter
     *
     * @param exception
     */
    public CantInitializeWalletPublisherMiddlewareDatabaseException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    /**
     * Constructor
     */
    public CantInitializeWalletPublisherMiddlewareDatabaseException() {
        this(DEFAULT_MESSAGE);
    }
}
