package com.bitdubai.fermat_dap_api.layer.dap_transaction.issuer_appropriation.exceptions;

import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.DAPException;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 12/11/15.
 */
public class CantLoadIssuerAppropriationTransactionListException extends DAPException {

    //VARIABLE DECLARATION

    public static final String DEFAULT_MESSAGE = "CAN'T LOAD ISSUER APPROPRIATION TRANSACTION LIST.";

    //CONSTRUCTORS

    public CantLoadIssuerAppropriationTransactionListException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CantLoadIssuerAppropriationTransactionListException(String context, Exception cause) {
        super(DEFAULT_MESSAGE, cause, context, "");
    }

    public CantLoadIssuerAppropriationTransactionListException(String context, Exception cause, String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CantLoadIssuerAppropriationTransactionListException(String context) {
        super(DEFAULT_MESSAGE, null, context, "");
    }

    public CantLoadIssuerAppropriationTransactionListException(Exception exception) {
        super(exception);
    }

    public CantLoadIssuerAppropriationTransactionListException() {
        this(DEFAULT_MESSAGE);
    }

    //PUBLIC METHODS

    //PRIVATE METHODS

    //GETTER AND SETTERS

    //INNER CLASSES
}
