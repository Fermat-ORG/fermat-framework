package com.bitdubai.fermat_cbp_api.layer.business_transaction.customer_broke_crypto_purchase.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Yordin Alayn on 24.09.15.
 */

public class CantUpdateStatusCustomerBrokerCryptoPurchaseException extends FermatException {

    public static final String DEFAULT_MESSAGE = "Falled To Update the Status the Business Transaction Customer Broker Crypto Purchase.";
    public CantUpdateStatusCustomerBrokerCryptoPurchaseException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
