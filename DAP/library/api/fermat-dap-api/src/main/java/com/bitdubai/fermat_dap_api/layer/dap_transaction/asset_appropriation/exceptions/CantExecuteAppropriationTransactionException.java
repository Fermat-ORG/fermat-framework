package com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_appropriation.exceptions;

import com.bitdubai.fermat_api.layer.DAPException;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 12/11/15.
 */
public class CantExecuteAppropriationTransactionException extends DAPException {

    //VARIABLE DECLARATION

    public static final String DEFAULT_MESSAGE = "There was an error in Appropriation Assets Transaction.";

    //CONSTRUCTORS

    public CantExecuteAppropriationTransactionException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CantExecuteAppropriationTransactionException(String context, Exception cause) {
        super(DEFAULT_MESSAGE, cause, context, "");
    }

    public CantExecuteAppropriationTransactionException(String context, Exception cause, String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CantExecuteAppropriationTransactionException(String context) {
        super(DEFAULT_MESSAGE, null, context, "");
    }

    public CantExecuteAppropriationTransactionException(Exception exception) {
        super(exception);
    }

    public CantExecuteAppropriationTransactionException() {
        this(DEFAULT_MESSAGE);
    }

    //PUBLIC METHODS

    //PRIVATE METHODS

    //GETTER AND SETTERS

    //INNER CLASSES
}
