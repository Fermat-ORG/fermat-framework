package com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Yordin Alayn on 18.09.15.
 */
public class CantCalculateBalanceException extends FermatException {
    public static final String DEFAULT_MESSAGE = "Falled To Calculate Balance in Bank Money Transaction Wallet Bank Money.";

    public CantCalculateBalanceException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
