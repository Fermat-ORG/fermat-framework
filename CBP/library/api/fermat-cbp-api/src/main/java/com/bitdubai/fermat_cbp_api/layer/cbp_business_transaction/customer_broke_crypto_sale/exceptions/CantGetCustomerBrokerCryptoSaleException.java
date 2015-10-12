package com.bitdubai.fermat_cbp_api.layer.cbp_business_transaction.customer_broke_crypto_sale.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Yordin Alayn on 24.09.15.
 */

public class CantGetCustomerBrokerCryptoSaleException extends FermatException {
    public static final String DEFAULT_MESSAGE = "Falled To Get Business Transaction Customer Broker Crypto Sale.";
    public CantGetCustomerBrokerCryptoSaleException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
