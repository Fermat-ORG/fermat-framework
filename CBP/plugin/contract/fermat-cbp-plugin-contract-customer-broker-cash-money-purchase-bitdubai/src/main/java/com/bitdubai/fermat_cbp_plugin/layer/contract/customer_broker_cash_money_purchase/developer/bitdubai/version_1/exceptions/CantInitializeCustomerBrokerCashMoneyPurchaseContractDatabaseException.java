package com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_cash_money_purchase.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;
/**
 * The Class <code>package com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_cash_money_purchase.developer.bitdubai.version_1.exceptions.CantInitializeCustomerBrokerCashMoneyPurchaseContractDatabaseException</code>
 * is thrown when an error occurs initializing database
 * <p/>
 *
 * Created by Angel Veloz - (vlzangel91@gmail.com) on 28/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CantInitializeCustomerBrokerCashMoneyPurchaseContractDatabaseException extends FermatException {

    public static final String DEFAULT_MESSAGE = "CAN'T INTIALIZE CUSTOMER BROKER CASH MONEY PURCHASE CONTRACT DATABASE EXCEPTION";

    public CantInitializeCustomerBrokerCashMoneyPurchaseContractDatabaseException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantInitializeCustomerBrokerCashMoneyPurchaseContractDatabaseException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantInitializeCustomerBrokerCashMoneyPurchaseContractDatabaseException(final String message) {
        this(message, null);
    }

    public CantInitializeCustomerBrokerCashMoneyPurchaseContractDatabaseException() {
        this(DEFAULT_MESSAGE);
    }
}