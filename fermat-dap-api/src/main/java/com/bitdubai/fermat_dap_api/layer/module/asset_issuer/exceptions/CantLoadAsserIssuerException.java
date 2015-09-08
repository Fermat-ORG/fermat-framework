package com.bitdubai.fermat_dap_api.layer.module.asset_issuer.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by franklin on 08/09/15.
 */
public class CantLoadAsserIssuerException extends FermatException {
    static final String DEFAULT_MESSAGE = "There was an error load Asset Issuer.";

    public CantLoadAsserIssuerException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE , cause, context, possibleReason);
    }
}
