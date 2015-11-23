package com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Yordin Alayn on 18.09.15.
 */
public class CantLoadCashMoneyException extends FermatException{
    public static final String DEFAULT_MESSAGE = "Falled To Load Cash Money Wallet Cash Money Wallet.";
    public CantLoadCashMoneyException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
