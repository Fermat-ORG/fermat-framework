package com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by rodrigo on 4/11/16.
 */
public class CantGetActiveBlockchainNetworkTypeException extends FermatException {

    public static final String DEFAUL_MESSAGE = "There was an error getting Bitcoin Active network";

    public CantGetActiveBlockchainNetworkTypeException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CantGetActiveBlockchainNetworkTypeException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
