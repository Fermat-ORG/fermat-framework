package com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.layer.DAPException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 07/09/15.
 */
public class CantIssueDigitalAssetException extends DAPException {

    static final String DEFAULT_MESSAGE = "There was an error Issuing an Digital Asset.";

    public CantIssueDigitalAssetException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE , cause, context, possibleReason);
    }

    public CantIssueDigitalAssetException(final String message) {
        this(null, DEFAULT_MESSAGE, message);
    }

}
