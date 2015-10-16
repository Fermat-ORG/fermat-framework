package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_broker_bank_purchase.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Yordin Alayn on 29.09.15.
 */
public class CantInsertRecordCustomerBrokerBankPurchaseBusinessTransactionException extends FermatException {
    public CantInsertRecordCustomerBrokerBankPurchaseBusinessTransactionException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
