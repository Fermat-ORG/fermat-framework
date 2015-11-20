package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.ack_merchandise.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Yordin Alayn on 04.10.15.
 */
public class CantUpdateStatusCustomerBrokerCryptoSaleBusinessTransactionException extends FermatException {
    public CantUpdateStatusCustomerBrokerCryptoSaleBusinessTransactionException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}