package org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 18/09/15.
 */
public class CantGetDigitalAssetFromLocalStorageException extends org.fermat.fermat_dap_api.layer.all_definition.exceptions.DAPException {
    public static final String DEFAULT_MESSAGE = "There was an error getting a Digital Asset from local storage.";

    public CantGetDigitalAssetFromLocalStorageException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CantGetDigitalAssetFromLocalStorageException() {
    }
}
