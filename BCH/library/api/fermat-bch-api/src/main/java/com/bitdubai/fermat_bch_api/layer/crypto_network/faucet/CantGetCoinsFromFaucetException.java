package com.bitdubai.fermat_bch_api.layer.crypto_network.faucet;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by rodrigo on 7/11/16.
 */
public class CantGetCoinsFromFaucetException extends FermatException {
    public static final String DEFAULT_MESSAGE = "There was an error getting the requested coins from the faucet";


    public CantGetCoinsFromFaucetException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantGetCoinsFromFaucetException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }
}
