package com.bitdubai.fermat_cbp_plugin.layer.user_level_business_transaction.customer_broker_purchase.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by franklin on 16/11/15.
 */
public class CantInitializeCustomerBrokerPurchaseDatabaseException extends FermatException {
    public static final String DEFAULT_MESSAGE = "CAN'T INTIALIZE BANK MONEY RESTOCK  DATABASE EXCEPTION";

    public CantInitializeCustomerBrokerPurchaseDatabaseException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantInitializeCustomerBrokerPurchaseDatabaseException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantInitializeCustomerBrokerPurchaseDatabaseException(final String message) {
        this(message, null);
    }

    public CantInitializeCustomerBrokerPurchaseDatabaseException() {
        this(DEFAULT_MESSAGE);
    }
}
