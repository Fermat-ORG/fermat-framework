package com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions;

import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.DAPException;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 20/01/16.
 */
public class NotEnoughAcceptsException extends DAPException {

    //VARIABLE DECLARATION

    public static final String DEFAULT_MESSAGE = "There are no available assets in this wallet.";

    //CONSTRUCTORS

    public NotEnoughAcceptsException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public NotEnoughAcceptsException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public NotEnoughAcceptsException(String message, Exception cause) {
        super(message, cause);
    }

    public NotEnoughAcceptsException(String message) {
        super(message);
    }

    public NotEnoughAcceptsException() {
    }

    public NotEnoughAcceptsException(Exception exception) {
        super(exception);
    }

    //PUBLIC METHODS

    //PRIVATE METHODS

    //GETTER AND SETTERS

    //INNER CLASSES
}
