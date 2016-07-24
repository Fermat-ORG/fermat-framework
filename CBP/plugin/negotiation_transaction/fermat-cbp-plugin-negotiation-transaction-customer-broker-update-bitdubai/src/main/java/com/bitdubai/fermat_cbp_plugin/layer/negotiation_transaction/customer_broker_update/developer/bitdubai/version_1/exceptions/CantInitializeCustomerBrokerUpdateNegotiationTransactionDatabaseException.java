package com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_update.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The Class <code>package com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_update.developer.bitdubai.version_1.exceptions.CantInitializeCustomerBrokerUpdateNegotiationTransactionDatabaseException</code>
 * is thrown when an error occurs initializing database
 * <p/>
 * <p/>
 * Created by Yordin Alayn - (y.alayn@gmail.com) on 17/12/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CantInitializeCustomerBrokerUpdateNegotiationTransactionDatabaseException extends FermatException {

    public static final String DEFAULT_MESSAGE = "CBP-NEGOTIATION TRANSACTION-CUSTOMER BROKER UPDATE. CAN'T INTIALIZE DATABASE EXCEPTION";

    public CantInitializeCustomerBrokerUpdateNegotiationTransactionDatabaseException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantInitializeCustomerBrokerUpdateNegotiationTransactionDatabaseException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantInitializeCustomerBrokerUpdateNegotiationTransactionDatabaseException(final String message) {
        this(message, null);
    }

    public CantInitializeCustomerBrokerUpdateNegotiationTransactionDatabaseException() {
        this(DEFAULT_MESSAGE);
    }
}