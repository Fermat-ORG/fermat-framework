package com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The Class <code>package CantInitializeIncomingIntraUserTransactionDatabaseException</code>
 * is thrown when an error occurs initializing database
 * <p/>
 *
 * Created by Ezequiel Postan - (ezequiel.postan@gmail.com) on 02/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CantInitializeIncomingIntraUserTransactionDatabaseException extends FermatException {

    public static final String DEFAULT_MESSAGE = "CAN'T INTIALIZE INCOMING INTRA USER TRANSACTION DATABASE EXCEPTION";

    public CantInitializeIncomingIntraUserTransactionDatabaseException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantInitializeIncomingIntraUserTransactionDatabaseException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantInitializeIncomingIntraUserTransactionDatabaseException(final String message) {
        this(message, null);
    }

    public CantInitializeIncomingIntraUserTransactionDatabaseException() {
        this(DEFAULT_MESSAGE);
    }
}

