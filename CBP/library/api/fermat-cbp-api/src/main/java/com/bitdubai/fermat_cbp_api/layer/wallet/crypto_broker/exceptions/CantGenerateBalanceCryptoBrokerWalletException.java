package com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Yordin Alayn on 24.09.15.
 */

public class CantGenerateBalanceCryptoBrokerWalletException extends FermatException {
    public static final String DEFAULT_MESSAGE = "Falled To Generate Balance in the Crypto Broker Wallet.";

    public CantGenerateBalanceCryptoBrokerWalletException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
