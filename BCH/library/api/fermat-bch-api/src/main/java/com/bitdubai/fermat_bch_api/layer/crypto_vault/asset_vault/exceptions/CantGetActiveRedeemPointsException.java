package com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by rodrigo on 1/8/16.
 */
public class CantGetActiveRedeemPointsException extends FermatException {

    public static final String DEFAULT_MESSAGE = "There was an error getting the active list of redeem points.";
    public CantGetActiveRedeemPointsException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
