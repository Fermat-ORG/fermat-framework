package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.ack_merchandise.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;
/**
 * The Class <code>package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_broker_crypto_sale.developer.bitdubai.version_1.exceptions.CantInitializeCustomerBrokerCryptoSaleBusinessTransactionDatabaseException</code>
 * is thrown when an error occurs initializing database
 * <p/>
 *
 * Created by Yordin Alayn - (y.alayn@gmail.com) on 29/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CantInitializeCustomerBrokerCryptoSaleBusinessTransactionDatabaseException extends FermatException {

    public static final String DEFAULT_MESSAGE = "CAN'T INTIALIZE CUSTOMER BROKER CRYPTO SALE BUSINESS TRANSACTION DATABASE EXCEPTION";

    public CantInitializeCustomerBrokerCryptoSaleBusinessTransactionDatabaseException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantInitializeCustomerBrokerCryptoSaleBusinessTransactionDatabaseException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantInitializeCustomerBrokerCryptoSaleBusinessTransactionDatabaseException(final String message) {
        this(message, null);
    }

    public CantInitializeCustomerBrokerCryptoSaleBusinessTransactionDatabaseException() {
        this(DEFAULT_MESSAGE);
    }
}