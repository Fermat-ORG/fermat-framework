package com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Franklin Marcano on 14.02.16.
 */

public class CantGetTransactionCryptoBrokerWalletMatchingException extends FermatException {
    public static final String DEFAULT_MESSAGE = "Falled To Get Transaction in the Crypto Broker Wallet.";

    public CantGetTransactionCryptoBrokerWalletMatchingException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantGetTransactionCryptoBrokerWalletMatchingException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }
}
