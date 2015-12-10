package com.bitdubai.fermat_bnk_plugin.layer.wallet.bank_money.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by memo on 24/11/15.
 */
public class CantMakeUnholdException extends FermatException {
    public static final String DEFAULT_MESSAGE = "Failed to make unhold on bank Wallet";
    public CantMakeUnholdException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
