package com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_bank_money_purchase.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by angel on 18/9/15.
 */
public class CantupdateCustomerBrokerBankMoneyPurchaseException extends FermatException {

    public static final String DEFAULT_MESSAGE = "CAN'T UPDATE THE CONTRACT CUSTOMER BROKER BANK MONEY PURCHASE";

    /**
     * This is the constructor that every inherited FermatException must implement
     *
     * @param message        the short description of the why this exception happened, there is a public static constant called DEFAULT_MESSAGE that can be used here
     * @param cause          the exception that triggered the throwing of the current exception, if there are no other exceptions to be declared here, the cause should be null
     * @param context        a String that provides the values of the variables that could have affected the exception
     * @param possibleReason an explicative reason of why we believe this exception was most likely thrown
     */
    public CantupdateCustomerBrokerBankMoneyPurchaseException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
