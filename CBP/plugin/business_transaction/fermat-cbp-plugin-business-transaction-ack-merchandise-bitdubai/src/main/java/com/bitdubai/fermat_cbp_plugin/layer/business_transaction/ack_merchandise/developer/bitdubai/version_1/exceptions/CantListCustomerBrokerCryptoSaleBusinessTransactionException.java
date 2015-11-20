package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.ack_merchandise.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Yordin Alayn on 09.10.15.
 */
public class CantListCustomerBrokerCryptoSaleBusinessTransactionException extends FermatException {
    public CantListCustomerBrokerCryptoSaleBusinessTransactionException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}