package com.bitdubai.fermat_cbp_api.layer.cbp_negotiation.customer_broker_purchase.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by jorge on 10-10-2015.
 */
public class CantUpdateCustomerBrokerPurchaseNegotiationException extends FermatException {
    public static final String DEFAULT_MESSAGE = "CAN'T UPDATE CUSTOMER BROKER PURCHASE NEGOTIATION";

    public CantUpdateCustomerBrokerPurchaseNegotiationException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantUpdateCustomerBrokerPurchaseNegotiationException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantUpdateCustomerBrokerPurchaseNegotiationException(final String message) {
        this(message, null);
    }

    public CantUpdateCustomerBrokerPurchaseNegotiationException() {
        this(DEFAULT_MESSAGE);
    }
}
