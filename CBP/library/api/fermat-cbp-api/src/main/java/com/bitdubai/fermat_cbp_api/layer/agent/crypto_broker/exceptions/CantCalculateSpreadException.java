package com.bitdubai.fermat_cbp_api.layer.agent.crypto_broker.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Yordin Alayn on 18.09.15.
 */

public class CantCalculateSpreadException extends FermatException {
    public static final String DEFAULT_MESSAGE = "Falled To Calculate Spread.";

    public CantCalculateSpreadException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
