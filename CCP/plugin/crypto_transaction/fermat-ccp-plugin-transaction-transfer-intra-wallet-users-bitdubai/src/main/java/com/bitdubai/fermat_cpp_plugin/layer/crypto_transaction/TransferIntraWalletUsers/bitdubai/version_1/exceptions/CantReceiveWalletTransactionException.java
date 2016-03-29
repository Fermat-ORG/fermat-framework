package com.bitdubai.fermat_cpp_plugin.layer.crypto_transaction.TransferIntraWalletUsers.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by natalia on 24/03/16.
 */
public class CantReceiveWalletTransactionException extends FermatException {

    public static final String DEFAULT_MESSAGE = "CAN'T SENT TRANSACTION TO DESTINATION WALLET";

    public CantReceiveWalletTransactionException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantReceiveWalletTransactionException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantReceiveWalletTransactionException(final String message) {
        this(message, null);
    }

    public CantReceiveWalletTransactionException() {
        this(DEFAULT_MESSAGE);
    }
}

