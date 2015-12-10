package com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.hold.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The Class <code>CantUpdateHoldTransactionException</code>
 * is thrown when an error occurs initializing database
 * <p/>
 *
 * Created by Franklin Marcano 23/11/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CantUpdateHoldTransactionException extends FermatException {

    public static final String DEFAULT_MESSAGE = "CAN'T UPDATE HOLD CASH MONEY TRANSACTION STATUS EXCEPTION";

    public CantUpdateHoldTransactionException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantUpdateHoldTransactionException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantUpdateHoldTransactionException(final String message) {
        this(message, null);
    }

    public CantUpdateHoldTransactionException() {
        this(DEFAULT_MESSAGE);
    }
}