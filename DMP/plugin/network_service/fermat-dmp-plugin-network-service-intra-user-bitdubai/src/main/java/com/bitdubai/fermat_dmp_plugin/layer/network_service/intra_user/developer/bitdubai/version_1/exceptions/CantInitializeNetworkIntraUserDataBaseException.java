/*
 * @#CantInitializeNetworkIntraUserDataBaseException.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.exceptions;


import com.bitdubai.fermat_api.FermatException;

/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.exceptions.CantInitializeNetworkIntraUserDataBaseException</code> is
 * throw when error occurred initialize the data base
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 31/05/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CantInitializeNetworkIntraUserDataBaseException extends FermatException {

    public static final String DEFAULT_MESSAGE = "CAN'T INITIALIZE NETWORK INTRAUSER DATABASE";

    public CantInitializeNetworkIntraUserDataBaseException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantInitializeNetworkIntraUserDataBaseException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantInitializeNetworkIntraUserDataBaseException(final String message) {
        this(message, null);
    }

    public CantInitializeNetworkIntraUserDataBaseException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public CantInitializeNetworkIntraUserDataBaseException() {
        this(DEFAULT_MESSAGE);
    }
}
