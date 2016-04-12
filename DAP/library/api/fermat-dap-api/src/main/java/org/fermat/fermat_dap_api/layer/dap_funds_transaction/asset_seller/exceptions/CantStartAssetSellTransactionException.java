package org.fermat.fermat_dap_api.layer.dap_funds_transaction.asset_seller.exceptions;

import org.fermat.fermat_dap_api.layer.all_definition.exceptions.DAPException;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 10/02/16.
 */
public class CantStartAssetSellTransactionException extends DAPException {

    //VARIABLE DECLARATION
    public static final String DEFAULT_MESSAGE = "CAN'T START ASSET SELL TRANSACTION.";

    //CONSTRUCTORS
    public CantStartAssetSellTransactionException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CantStartAssetSellTransactionException(String context, Exception cause) {
        super(DEFAULT_MESSAGE, cause, context, "");
    }

    public CantStartAssetSellTransactionException(String context, Exception cause, String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CantStartAssetSellTransactionException(String context) {
        super(DEFAULT_MESSAGE, null, context, "");
    }

    public CantStartAssetSellTransactionException(Exception exception) {
        super(exception);
    }

    public CantStartAssetSellTransactionException() {
        this(DEFAULT_MESSAGE);
    }
    //PUBLIC METHODS

    //PRIVATE METHODS

    //GETTER AND SETTERS

    //INNER CLASSES
}
