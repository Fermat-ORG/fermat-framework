package com.bitdubai.fermat_bch_plugin.layer.asset_vault.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by rodrigo on 9/20/15.
 */
public class InvalidAccountNumberException extends FermatException {
    public static final String DEFAULT_MESSAGE = "The account number specified doesn't no exists. Valid account numbers are 0 for Asset Vault and > 0 for RedeemPoints.";

    public InvalidAccountNumberException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
