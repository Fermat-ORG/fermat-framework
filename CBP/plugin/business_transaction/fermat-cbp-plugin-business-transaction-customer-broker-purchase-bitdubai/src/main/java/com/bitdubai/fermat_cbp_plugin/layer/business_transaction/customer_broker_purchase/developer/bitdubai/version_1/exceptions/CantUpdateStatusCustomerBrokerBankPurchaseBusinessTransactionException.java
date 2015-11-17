package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_broker_bank_purchase.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Yordin Alayn on 04.10.15.
 */
public class CantUpdateStatusCustomerBrokerBankPurchaseBusinessTransactionException extends FermatException {
    public CantUpdateStatusCustomerBrokerBankPurchaseBusinessTransactionException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}