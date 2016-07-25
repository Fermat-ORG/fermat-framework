package com.bitdubai.fermat_cbp_api.layer.agent.crypto_broker.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Yordin Alayn on 18.09.15.
 */

public class CantCalculateBasePriceMerchandiseCurrencyException extends FermatException {
    public static final String DEFAULT_MESSAGE = "Falled To Calculate Base Price Merchandise Currency.";

    public CantCalculateBasePriceMerchandiseCurrencyException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
