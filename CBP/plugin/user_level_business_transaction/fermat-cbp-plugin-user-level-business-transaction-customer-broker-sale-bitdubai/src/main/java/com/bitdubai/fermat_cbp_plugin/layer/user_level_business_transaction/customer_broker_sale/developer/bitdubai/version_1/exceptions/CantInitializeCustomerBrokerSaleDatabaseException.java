package com.bitdubai.fermat_cbp_plugin.layer.user_level_business_transaction.customer_broker_sale.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by franklin on 16/11/15.
 */
public class CantInitializeCustomerBrokerSaleDatabaseException extends FermatException {
    public static final String DEFAULT_MESSAGE = "CAN'T INTIALIZE BANK MONEY RESTOCK  DATABASE EXCEPTION";

    public CantInitializeCustomerBrokerSaleDatabaseException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantInitializeCustomerBrokerSaleDatabaseException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantInitializeCustomerBrokerSaleDatabaseException(final String message) {
        this(message, null);
    }

    public CantInitializeCustomerBrokerSaleDatabaseException() {
        this(DEFAULT_MESSAGE);
    }
}
