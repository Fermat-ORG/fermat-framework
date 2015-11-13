package com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_purchase.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;
/**
 * The Class <code>package com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_purchase.developer.bitdubai.version_1.exceptions.CantInitializeCustomerBrokerContractPurchaseDatabaseException</code>
 * is thrown when an error occurs initializing database
 * <p/>
 *
 * Created by Angel Veloz - (vlzangel91@gmail.com) on 02/11/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CantInitializeCustomerBrokerContractPurchaseDatabaseException extends FermatException {

    public static final String DEFAULT_MESSAGE = "CAN'T INTIALIZE CUSTOMER BROKER PURCHASE CONTRACT DATABASE EXCEPTION";

    public CantInitializeCustomerBrokerContractPurchaseDatabaseException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantInitializeCustomerBrokerContractPurchaseDatabaseException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantInitializeCustomerBrokerContractPurchaseDatabaseException(final String message) {
        this(message, null);
    }

    public CantInitializeCustomerBrokerContractPurchaseDatabaseException() {
        this(DEFAULT_MESSAGE);
    }
}
