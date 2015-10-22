package com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Yordin Alayn on 18.10.15.
 */
public class CantGetCryptoBrokerWalletTableException extends FermatException {

    public CantGetCryptoBrokerWalletTableException(String message, String context, String possibleReason) {
        this(message, null, context, possibleReason);
    }

    public CantGetCryptoBrokerWalletTableException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}