package com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by franklin on 08/09/15.
 */
public class CantAddDebitException extends FermatException {
    public static final String DEFAULT_MESSAGE = "Falled To Register Debit in Crypto Broker Transaction Wallet.";

    public CantAddDebitException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
