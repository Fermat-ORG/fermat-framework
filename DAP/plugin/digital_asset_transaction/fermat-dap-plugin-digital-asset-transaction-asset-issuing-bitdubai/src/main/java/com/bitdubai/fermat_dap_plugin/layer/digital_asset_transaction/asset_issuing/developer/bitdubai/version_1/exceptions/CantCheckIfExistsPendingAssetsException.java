package com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.layer.DAPException;

/**
 * Created by frank on 23/10/15.
 */
public class CantCheckIfExistsPendingAssetsException extends DAPException {
    public static final String DEFAULT_MESSAGE = "There was an error checking if exists pending Assets in Asset Issuing.";

    public CantCheckIfExistsPendingAssetsException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE , cause, context, possibleReason);
    }

    public CantCheckIfExistsPendingAssetsException(final String message) {
        this(null, DEFAULT_MESSAGE, message);
    }
}
