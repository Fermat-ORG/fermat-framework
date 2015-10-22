package com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_purchase.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

public class CantGetListPurchaseClauseException extends FermatException {

    public static final String DEFAULT_MESSAGE = "CAN'T GET LIST CUSTOMER BROKER PURCHASE CLAUSES EXCEPTION";

    public CantGetListPurchaseClauseException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantGetListPurchaseClauseException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantGetListPurchaseClauseException(final String message) {
        this(message, null);
    }

    public CantGetListPurchaseClauseException() {
        this(DEFAULT_MESSAGE);
    }
}
