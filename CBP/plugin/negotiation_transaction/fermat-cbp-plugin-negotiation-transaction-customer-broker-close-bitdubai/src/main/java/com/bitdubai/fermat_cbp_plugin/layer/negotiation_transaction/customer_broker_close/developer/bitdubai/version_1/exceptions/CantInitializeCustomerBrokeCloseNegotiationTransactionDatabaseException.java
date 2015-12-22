package com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_close.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The Class <code>package com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_new.developer.bitdubai.version_1.exceptions.CantInitializeCustomerBrokerNewNegotiationTransactionDatabaseException</code>
 * is thrown when an error occurs initializing database
 * <p/>
 *
 * Created by Yordin Alayn - (y.alayn@gmail.com) on 22/12/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CantInitializeCustomerBrokeCloseNegotiationTransactionDatabaseException extends FermatException {

    public static final String DEFAULT_MESSAGE = "CBP-NEGOTIATION TRANSACTION-CUSTOMER BROKER CLOSE. CAN'T INTIALIZE DATABASE EXCEPTION";

    public CantInitializeCustomerBrokeCloseNegotiationTransactionDatabaseException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantInitializeCustomerBrokeCloseNegotiationTransactionDatabaseException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantInitializeCustomerBrokeCloseNegotiationTransactionDatabaseException(final String message) {
        this(message, null);
    }

    public CantInitializeCustomerBrokeCloseNegotiationTransactionDatabaseException() {
        this(DEFAULT_MESSAGE);
    }
}