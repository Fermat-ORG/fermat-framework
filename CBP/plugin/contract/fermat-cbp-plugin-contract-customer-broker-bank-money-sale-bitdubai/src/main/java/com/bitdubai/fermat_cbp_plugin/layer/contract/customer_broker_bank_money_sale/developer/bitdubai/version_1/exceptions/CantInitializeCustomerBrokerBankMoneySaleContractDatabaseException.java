package com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_bank_money_sale.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;
/**
 * The Class <code>package com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_bank_money_sale.developer.bitdubai.version_1.exceptions.CantInitializeCustomerBrokerBankMoneySaleContractDatabaseException</code>
 * is thrown when an error occurs initializing database
 * <p/>
 *
 * Created by Angel Veloz - (vlzangel91@gmail.com) on 28/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CantInitializeCustomerBrokerBankMoneySaleContractDatabaseException extends FermatException {

    public static final String DEFAULT_MESSAGE = "CAN'T INTIALIZE CUSTOMER BROKER BANK MONEY SALE CONTRACT DATABASE EXCEPTION";

    public CantInitializeCustomerBrokerBankMoneySaleContractDatabaseException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantInitializeCustomerBrokerBankMoneySaleContractDatabaseException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantInitializeCustomerBrokerBankMoneySaleContractDatabaseException(final String message) {
        this(message, null);
    }

    public CantInitializeCustomerBrokerBankMoneySaleContractDatabaseException() {
        this(DEFAULT_MESSAGE);
    }
}