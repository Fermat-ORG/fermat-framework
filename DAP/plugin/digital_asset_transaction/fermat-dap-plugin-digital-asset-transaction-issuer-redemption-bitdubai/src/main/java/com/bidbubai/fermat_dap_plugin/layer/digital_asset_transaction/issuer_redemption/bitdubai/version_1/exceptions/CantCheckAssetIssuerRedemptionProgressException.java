package com.bidbubai.fermat_dap_plugin.layer.digital_asset_transaction.issuer_redemption.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.layer.DAPException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 03/11/15.
 */
public class CantCheckAssetIssuerRedemptionProgressException extends DAPException {
    public static final String DEFAULT_MESSAGE = "There was an error checking the Asset User Redemption progress.";

    public CantCheckAssetIssuerRedemptionProgressException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE , cause, context, possibleReason);
    }

    public CantCheckAssetIssuerRedemptionProgressException(final String message) {
        this(null, DEFAULT_MESSAGE, message);
    }
}
