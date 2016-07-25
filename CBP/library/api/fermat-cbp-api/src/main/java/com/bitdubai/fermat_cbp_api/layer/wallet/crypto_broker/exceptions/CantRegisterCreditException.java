package com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Yordin Alayn on 18.09.15.
 */
public class CantRegisterCreditException extends FermatException {
    public static final String DEFAULT_MESSAGE = "Falled To Register Credit in Crypto Broker Transaction Wallet";

    public CantRegisterCreditException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
