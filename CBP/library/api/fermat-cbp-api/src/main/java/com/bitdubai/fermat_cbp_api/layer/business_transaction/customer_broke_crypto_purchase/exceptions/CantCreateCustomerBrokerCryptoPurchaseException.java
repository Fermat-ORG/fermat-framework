package com.bitdubai.fermat_cbp_api.layer.business_transaction.customer_broke_crypto_purchase.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Yordin Alayn on 18.09.15.
 */

public class CantCreateCustomerBrokerCryptoPurchaseException extends FermatException {
    public static final String DEFAULT_MESSAGE = "Falled To Create Business Transaction Customer Broker Crypto Purchase.";
    public CantCreateCustomerBrokerCryptoPurchaseException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
