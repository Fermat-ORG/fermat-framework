package com.bitdubai.fermat_dap_plugin.layer.transaction.asset_issuing.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.layer.DAPException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 13/09/15.
 */
public class CantInitializeAssetIssuingMonitorAgentException extends DAPException {
    public static final String DEFAULT_MESSAGE = "There was an error Initializing Asset Issuing Monitor Agent.";

    public CantInitializeAssetIssuingMonitorAgentException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE , cause, context, possibleReason);
    }
}
