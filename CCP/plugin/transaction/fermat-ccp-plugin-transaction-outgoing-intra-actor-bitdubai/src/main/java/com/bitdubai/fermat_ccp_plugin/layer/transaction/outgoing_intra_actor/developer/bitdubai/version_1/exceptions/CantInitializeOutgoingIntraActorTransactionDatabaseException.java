package com.bitdubai.fermat_ccp_plugin.layer.transaction.outgoing_intra_actor.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The Class <code>package CantInitializeOutgoingIntraActorTransactionDatabaseException</code>
 * is thrown when an error occurs initializing database
 * <p/>
 *
 * Created by Ezequiel Postan - (ezequiel.postan@gmail.com) on 21/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CantInitializeOutgoingIntraActorTransactionDatabaseException extends FermatException {

    public static final String DEFAULT_MESSAGE = "CAN'T INTIALIZE OUTGOING INTRA USER TRANSACTION DATABASE EXCEPTION";

    public CantInitializeOutgoingIntraActorTransactionDatabaseException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantInitializeOutgoingIntraActorTransactionDatabaseException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantInitializeOutgoingIntraActorTransactionDatabaseException(final String message) {
        this(message, null);
    }

    public CantInitializeOutgoingIntraActorTransactionDatabaseException() {
        this(DEFAULT_MESSAGE);
    }
}

