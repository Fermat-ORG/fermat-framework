package org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions;

import org.fermat.fermat_dap_api.layer.all_definition.exceptions.DAPException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 08/09/15.
 */
public class CantPersistDigitalAssetException extends DAPException {
    public static final String DEFAULT_MESSAGE = "There was an error persisting a Digital Asset in Asset Issuing database.";

    public CantPersistDigitalAssetException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }
}
