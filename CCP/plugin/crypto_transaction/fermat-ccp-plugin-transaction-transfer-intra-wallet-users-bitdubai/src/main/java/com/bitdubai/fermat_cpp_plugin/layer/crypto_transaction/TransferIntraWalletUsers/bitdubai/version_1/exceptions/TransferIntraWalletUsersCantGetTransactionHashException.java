package com.bitdubai.fermat_cpp_plugin.layer.crypto_transaction.TransferIntraWalletUsers.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by rodrigo on 10/19/15.
 */
public class TransferIntraWalletUsersCantGetTransactionHashException extends FermatException {
    public TransferIntraWalletUsersCantGetTransactionHashException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
