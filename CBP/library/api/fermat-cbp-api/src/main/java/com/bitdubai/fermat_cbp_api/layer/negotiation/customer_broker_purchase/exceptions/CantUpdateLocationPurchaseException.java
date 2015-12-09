package com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Angel on 07/12/2015.
 */

public class CantUpdateLocationPurchaseException extends FermatException {
    public static final String DEFAULT_MESSAGE = "CAN'T UPDATE LOCATION SALE";

    public CantUpdateLocationPurchaseException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantUpdateLocationPurchaseException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantUpdateLocationPurchaseException(final String message) {
        this(message, null);
    }

    public CantUpdateLocationPurchaseException() {
        this(DEFAULT_MESSAGE);
    }
}
