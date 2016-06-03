package org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions;

/**
 * Created by Frankli Marcano (franklinmarcano1970@gmail.com) on 01/09/15.
 */
public class CantExecuteDatabaseOperationException extends org.fermat.fermat_dap_api.layer.all_definition.exceptions.DAPException {
    static final String DEFAULT_MESSAGE = "There was an error executing a database operation.";

    public CantExecuteDatabaseOperationException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }
}
