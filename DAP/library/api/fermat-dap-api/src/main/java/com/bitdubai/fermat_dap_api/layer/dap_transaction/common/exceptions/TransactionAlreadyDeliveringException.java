package com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions;

import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.DAPException;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 22/12/15.
 */
public class TransactionAlreadyDeliveringException extends DAPException {

    //VARIABLE DECLARATION

    public static final String DEFAULT_MESSAGE = "This transaction is already trying to be delivered.";

    //CONSTRUCTORS

    public TransactionAlreadyDeliveringException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public TransactionAlreadyDeliveringException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public TransactionAlreadyDeliveringException(String message, Exception cause) {
        super(message, cause);
    }

    public TransactionAlreadyDeliveringException(String message) {
        super(message);
    }

    public TransactionAlreadyDeliveringException(Exception exception) {
        super(exception);
    }

    //PUBLIC METHODS

    //PRIVATE METHODS

    //GETTER AND SETTERS

    //INNER CLASSES
}
