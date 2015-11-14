package com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_appropriation.exceptions;

import com.bitdubai.fermat_api.layer.DAPException;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 12/11/15.
 */
public class TransactionAlreadyStartedException extends DAPException {

    //VARIABLE DECLARATION

    public static final String DEFAULT_MESSAGE = "YOU ALREADY STARTED THIS TRANSACTION.";

    //CONSTRUCTORS

    public TransactionAlreadyStartedException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public TransactionAlreadyStartedException(String context, Exception cause) {
        super(DEFAULT_MESSAGE, cause, context, "");
    }

    public TransactionAlreadyStartedException(String context, Exception cause, String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public TransactionAlreadyStartedException(String context) {
        super(DEFAULT_MESSAGE, null, context, "");
    }

    public TransactionAlreadyStartedException(Exception exception) {
        super(exception);
    }

    public TransactionAlreadyStartedException() {
        this(DEFAULT_MESSAGE);
    }

    //PUBLIC METHODS

    //PRIVATE METHODS

    //GETTER AND SETTERS

    //INNER CLASSES
}
