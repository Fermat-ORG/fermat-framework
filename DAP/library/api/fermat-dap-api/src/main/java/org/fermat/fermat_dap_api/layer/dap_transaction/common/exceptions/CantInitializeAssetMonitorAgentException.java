package org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 13/09/15.
 */
public class CantInitializeAssetMonitorAgentException extends org.fermat.fermat_dap_api.layer.all_definition.exceptions.DAPException {
    public static final String DEFAULT_MESSAGE = "There was an error Initializing Asset Monitor Agent.";

    public CantInitializeAssetMonitorAgentException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }
}
