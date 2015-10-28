package com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_reception.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.layer.DAPException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 26/10/15.
 */
public class CantCheckAssetReceptionProgressException extends DAPException {
    public static final String DEFAULT_MESSAGE = "There was an error checking the Assets Distribution progress.";

    public CantCheckAssetReceptionProgressException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CantCheckAssetReceptionProgressException(final String message) {
        this(null, DEFAULT_MESSAGE, message);
    }
}
