package com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by rodrigo on 10/13/15.
 */
public class CantSendAssetBitcoinsToUserException extends FermatException {
    public static final String DEFAULT_MESSAGE = "There was an error sending asset bitcoins to user.";

    public CantSendAssetBitcoinsToUserException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
