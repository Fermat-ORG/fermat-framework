package com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_appropriation.exceptions;

import com.bitdubai.fermat_api.layer.DAPException;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 12/11/15.
 */
public class CantLoadAssetAppropriationTransactionListException extends DAPException {

    //VARIABLE DECLARATION

    public static final String DEFAULT_MESSAGE = "CAN'T LOAD ASSET APPROPRIATION TRANSACTION LIST.";

    //CONSTRUCTORS

    public CantLoadAssetAppropriationTransactionListException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CantLoadAssetAppropriationTransactionListException(String context, Exception cause) {
        super(DEFAULT_MESSAGE, cause, context, "");
    }

    public CantLoadAssetAppropriationTransactionListException(String context, Exception cause, String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CantLoadAssetAppropriationTransactionListException(String context) {
        super(DEFAULT_MESSAGE, null, context, "");
    }

    public CantLoadAssetAppropriationTransactionListException(Exception exception) {
        super(exception);
    }

    public CantLoadAssetAppropriationTransactionListException() {
        this(DEFAULT_MESSAGE);
    }

    //PUBLIC METHODS

    //PRIVATE METHODS

    //GETTER AND SETTERS

    //INNER CLASSES
}
