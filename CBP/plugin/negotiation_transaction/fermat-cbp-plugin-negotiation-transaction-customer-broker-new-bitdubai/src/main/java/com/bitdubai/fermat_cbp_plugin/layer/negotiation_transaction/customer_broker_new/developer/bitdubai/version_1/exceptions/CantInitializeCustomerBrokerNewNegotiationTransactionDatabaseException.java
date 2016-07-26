package com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_new.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The Class <code>package com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_new.developer.bitdubai.version_1.exceptions.CantInitializeCustomerBrokerNewNegotiationTransactionDatabaseException</code>
 * is thrown when an error occurs initializing database
 * <p/>
 * <p/>
 * Created by Yordin Alayn - (y.alayn@gmail.com) on 23/11/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CantInitializeCustomerBrokerNewNegotiationTransactionDatabaseException extends FermatException {

    public static final String DEFAULT_MESSAGE = "CBP-NEGOTIATION TRANSACTION-CUSTOMER BROKER NEW. CAN'T INTIALIZE DATABASE EXCEPTION";

    public CantInitializeCustomerBrokerNewNegotiationTransactionDatabaseException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantInitializeCustomerBrokerNewNegotiationTransactionDatabaseException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantInitializeCustomerBrokerNewNegotiationTransactionDatabaseException(final String message) {
        this(message, null);
    }

    public CantInitializeCustomerBrokerNewNegotiationTransactionDatabaseException() {
        this(DEFAULT_MESSAGE);
    }
}