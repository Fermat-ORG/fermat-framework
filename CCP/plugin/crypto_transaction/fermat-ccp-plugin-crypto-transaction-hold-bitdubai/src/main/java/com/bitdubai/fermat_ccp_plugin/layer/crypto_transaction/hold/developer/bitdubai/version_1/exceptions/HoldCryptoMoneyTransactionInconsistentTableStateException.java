package com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.hold.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Alejandro Bicelis on 23/18/2015.
 */

public class HoldCryptoMoneyTransactionInconsistentTableStateException extends FermatException {
    public HoldCryptoMoneyTransactionInconsistentTableStateException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}