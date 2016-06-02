package org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 07/09/15.
 */
public class CantCreateDigitalAssetFileException extends org.fermat.fermat_dap_api.layer.all_definition.exceptions.DAPException {
    public static final String DEFAULT_MESSAGE = "There was an error creating a Digital Asset storage file.";

    public CantCreateDigitalAssetFileException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }
}
