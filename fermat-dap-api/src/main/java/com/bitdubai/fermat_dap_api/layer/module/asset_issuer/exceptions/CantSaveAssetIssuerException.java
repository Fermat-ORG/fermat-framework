package com.bitdubai.fermat_dap_api.layer.module.asset_issuer.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by franklin on 08/09/15.
 */
public class CantSaveAssetIssuerException extends FermatException {
    static final String DEFAULT_MESSAGE = "There was an error Save Asset Issuer.";

    public CantSaveAssetIssuerException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE , cause, context, possibleReason);
    }
}
