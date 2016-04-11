package org.fermat.fermat_dap_api.layer.dap_funds_transaction.asset_buyer.exceptions;

import org.fermat.fermat_dap_api.layer.all_definition.exceptions.DAPException;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 17/02/16.
 */
public class CantGetBuyingTransactionsException extends DAPException {

    //VARIABLE DECLARATION
    public static final String DEFAULT_MESSAGE = "CAN'T SEARCH THE BUYING TRANSACTIONS.";

    //CONSTRUCTORS
    public CantGetBuyingTransactionsException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CantGetBuyingTransactionsException(String context, Exception cause) {
        super(DEFAULT_MESSAGE, cause, context, "");
    }

    public CantGetBuyingTransactionsException(String context, Exception cause, String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CantGetBuyingTransactionsException(String context) {
        super(DEFAULT_MESSAGE, null, context, "");
    }

    public CantGetBuyingTransactionsException(Exception exception) {
        super(exception);
    }

    public CantGetBuyingTransactionsException() {
        this(DEFAULT_MESSAGE);
    }
    //PUBLIC METHODS

    //PRIVATE METHODS

    //GETTER AND SETTERS

    //INNER CLASSES
}
