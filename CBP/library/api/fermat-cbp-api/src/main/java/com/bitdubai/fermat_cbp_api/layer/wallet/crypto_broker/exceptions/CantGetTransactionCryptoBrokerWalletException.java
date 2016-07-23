package com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Yordin Alayn on 24.09.15.
 */

public class CantGetTransactionCryptoBrokerWalletException extends FermatException {
    public static final String DEFAULT_MESSAGE = "Falled To Get Transaction in the Crypto Broker Wallet.";

    public CantGetTransactionCryptoBrokerWalletException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
