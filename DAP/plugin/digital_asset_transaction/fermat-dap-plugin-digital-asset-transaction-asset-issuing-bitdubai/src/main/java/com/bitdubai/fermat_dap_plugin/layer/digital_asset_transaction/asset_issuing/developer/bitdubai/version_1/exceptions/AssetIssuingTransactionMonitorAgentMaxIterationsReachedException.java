package com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.layer.DAPException;

/**
 * Created by Manuel Perez on 22/09/2015
 */
public class AssetIssuingTransactionMonitorAgentMaxIterationsReachedException extends DAPException {
    public static final String DEFAULT_MESSAGE = "The maximum number of interactions has been reached.";

    public AssetIssuingTransactionMonitorAgentMaxIterationsReachedException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE , cause, context, possibleReason);
    }

    public AssetIssuingTransactionMonitorAgentMaxIterationsReachedException(final String message) {
        this(null, DEFAULT_MESSAGE, message);
    }

}
