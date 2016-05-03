package com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by rodrigo on 1/7/16.
 */
public class CantGetActiveRedeemPointAddressesException extends FermatException {

    static public final String DEFAULT_MESSAGE = "There was an error getting the Active redeem point addresses";

    public CantGetActiveRedeemPointAddressesException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
