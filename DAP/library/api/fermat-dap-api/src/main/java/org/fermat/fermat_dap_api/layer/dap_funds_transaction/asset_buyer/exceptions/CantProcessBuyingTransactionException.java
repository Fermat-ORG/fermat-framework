package org.fermat.fermat_dap_api.layer.dap_funds_transaction.asset_buyer.exceptions;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 17/02/16.
 */
public class CantProcessBuyingTransactionException extends org.fermat.fermat_dap_api.layer.all_definition.exceptions.DAPException {

    //VARIABLE DECLARATION
    public static final String DEFAULT_MESSAGE = "CAN'T PROCESS ASSET BUYING TRANSACTIONS.";

    //CONSTRUCTORS
    public CantProcessBuyingTransactionException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CantProcessBuyingTransactionException(String context, Exception cause) {
        super(DEFAULT_MESSAGE, cause, context, "");
    }

    public CantProcessBuyingTransactionException(String context, Exception cause, String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CantProcessBuyingTransactionException(String context) {
        super(DEFAULT_MESSAGE, null, context, "");
    }

    public CantProcessBuyingTransactionException(Exception exception) {
        super(exception);
    }

    public CantProcessBuyingTransactionException() {
        this(DEFAULT_MESSAGE);
    }
    //PUBLIC METHODS

    //PRIVATE METHODS

    //GETTER AND SETTERS

    //INNER CLASSES
}
