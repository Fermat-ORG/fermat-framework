package org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions;

import org.fermat.fermat_dap_api.layer.all_definition.exceptions.DAPException;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 25/02/16.
 */
public class CantExecuteLockOperationException extends DAPException {

    //VARIABLE DECLARATION
    public static final String DEFAULT_MESSAGE = "There was an error while doing a lock operation.";

    //CONSTRUCTORS

    public CantExecuteLockOperationException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantExecuteLockOperationException(Exception cause, String context, String possibleReason) {
        super(cause, context, possibleReason);
    }

    public CantExecuteLockOperationException(String message, Exception cause) {
        super(message, cause);
    }

    public CantExecuteLockOperationException(String message) {
        super(message);
    }

    public CantExecuteLockOperationException(Exception exception) {
        super(exception);
    }

    public CantExecuteLockOperationException() {
    }

    //PUBLIC METHODS

    //PRIVATE METHODS

    //GETTER AND SETTERS

    //INNER CLASSES
}
