package com.bitdubai.fermat_cbp_plugin.layer.request.customer_broker_sale.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;
/**
 * The Class <code>package com.bitdubai.fermat_cbp_plugin.layer.request.customer_broker_sale.developer.bitdubai.version_1.exceptions.CantInitializeCustomerBrokerSaleRequestDatabaseException</code>
 * is thrown when an error occurs initializing database
 * <p/>
 *
 * Created by Angel Veloz - (vlzangel91@gmail.com) on 28/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CantInitializeCustomerBrokerSaleRequestDatabaseException extends FermatException {

    public static final String DEFAULT_MESSAGE = "CAN'T INTIALIZE CUSTOMER BROKER SALE REQUEST DATABASE EXCEPTION";

    public CantInitializeCustomerBrokerSaleRequestDatabaseException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantInitializeCustomerBrokerSaleRequestDatabaseException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantInitializeCustomerBrokerSaleRequestDatabaseException(final String message) {
        this(message, null);
    }

    public CantInitializeCustomerBrokerSaleRequestDatabaseException() {
        this(DEFAULT_MESSAGE);
    }
}
