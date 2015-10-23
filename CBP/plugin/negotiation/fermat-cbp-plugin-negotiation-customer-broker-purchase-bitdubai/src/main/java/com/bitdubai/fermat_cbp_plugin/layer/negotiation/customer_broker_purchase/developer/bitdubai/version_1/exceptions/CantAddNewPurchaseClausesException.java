package com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_purchase.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

public class CantAddNewPurchaseClausesException extends FermatException {

    public static final String DEFAULT_MESSAGE = "CAN'T ADD NEW PURCHASE CLAUSES EXCEPTION";

    public CantAddNewPurchaseClausesException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantAddNewPurchaseClausesException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantAddNewPurchaseClausesException(final String message) {
        this(message, null);
    }

    public CantAddNewPurchaseClausesException() {
        this(DEFAULT_MESSAGE);
    }
}