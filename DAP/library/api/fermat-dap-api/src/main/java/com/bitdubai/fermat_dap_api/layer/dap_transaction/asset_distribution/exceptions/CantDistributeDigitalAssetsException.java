package com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_distribution.exceptions;

import com.bitdubai.fermat_api.layer.DAPException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 24/09/15.
 */
public class CantDistributeDigitalAssetsException extends DAPException {
    static final String DEFAULT_MESSAGE = "There was an error Distributing Digital Assets.";

    public CantDistributeDigitalAssetsException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE , cause, context, possibleReason);
    }

    public CantDistributeDigitalAssetsException(final String message) {
        this(null, DEFAULT_MESSAGE, message);
    }
}
