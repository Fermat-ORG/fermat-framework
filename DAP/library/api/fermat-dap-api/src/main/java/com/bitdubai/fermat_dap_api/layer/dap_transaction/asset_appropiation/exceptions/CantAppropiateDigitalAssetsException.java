package com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_appropiation.exceptions;

import com.bitdubai.fermat_api.layer.DAPException;

/**
 * Created by frank on 18/10/15.
 */
public class CantAppropiateDigitalAssetsException extends DAPException {
    static final String DEFAULT_MESSAGE = "There was an error Appropiating Digital Assets.";

    public CantAppropiateDigitalAssetsException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }
}
