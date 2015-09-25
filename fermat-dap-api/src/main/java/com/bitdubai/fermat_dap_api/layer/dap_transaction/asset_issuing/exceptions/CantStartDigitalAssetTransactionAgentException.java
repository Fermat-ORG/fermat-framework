package com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_issuing.exceptions;

import com.bitdubai.fermat_api.layer.DAPException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 20/09/15.
 */
public class CantStartDigitalAssetTransactionAgentException extends DAPException {
    public static final String DEFAULT_MESSAGE = "CAN'T START DIGITAL ASSET TRANSACTION MONITOR AGENT";

    public CantStartDigitalAssetTransactionAgentException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantStartDigitalAssetTransactionAgentException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantStartDigitalAssetTransactionAgentException(final String message) {
        this(message, null);
    }

    public CantStartDigitalAssetTransactionAgentException() {
        this(DEFAULT_MESSAGE);
    }
}
