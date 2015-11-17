package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_broker_bank_sale.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;
/**
 * The Class <code>package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_broker_bank_sale.developer.bitdubai.version_1.exceptions.CantInitializeCustomerBrokerBankSaleBusinessTransactionDatabaseException</code>
 * is thrown when an error occurs initializing database
 * <p/>
 *
 * Created by Yordin Alayn - (y.alayn@gmail.com) on 29/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CantInitializeCustomerBrokerBankSaleBusinessTransactionDatabaseException extends FermatException {

    public static final String DEFAULT_MESSAGE = "CAN'T INTIALIZE CUSTOMER BROKER BANK SALE BUSINESS TRANSACTION DATABASE EXCEPTION";

    public CantInitializeCustomerBrokerBankSaleBusinessTransactionDatabaseException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantInitializeCustomerBrokerBankSaleBusinessTransactionDatabaseException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantInitializeCustomerBrokerBankSaleBusinessTransactionDatabaseException(final String message) {
        this(message, null);
    }

    public CantInitializeCustomerBrokerBankSaleBusinessTransactionDatabaseException() {
        this(DEFAULT_MESSAGE);
    }
}