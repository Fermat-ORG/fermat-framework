package com.bitdubai.fermat_cry_api.layer.crypto_network.bitcoin.exceptions;

import com.bitdubai.fermat_cry_api.CryptoException;

/**
 * Created by rodrigo on 20/05/15.
 */
public class CantConnectToBitcoinNetwork extends CryptoException {
    public CantConnectToBitcoinNetwork(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
