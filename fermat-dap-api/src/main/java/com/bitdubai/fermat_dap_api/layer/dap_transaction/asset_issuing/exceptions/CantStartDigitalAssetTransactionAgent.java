package com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_issuing.exceptions;

import com.bitdubai.fermat_api.layer.DAPException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 20/09/15.
 */
public class CantStartDigitalAssetTransactionAgent extends DAPException {
    public static final String DEFAULT_MESSAGE = "CAN'T START DIGITAL ASSET TRANSACTION MONITOR AGENT";

    public CantStartDigitalAssetTransactionAgent(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantStartDigitalAssetTransactionAgent(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantStartDigitalAssetTransactionAgent(final String message) {
        this(message, null);
    }

    public CantStartDigitalAssetTransactionAgent() {
        this(DEFAULT_MESSAGE);
    }
}
