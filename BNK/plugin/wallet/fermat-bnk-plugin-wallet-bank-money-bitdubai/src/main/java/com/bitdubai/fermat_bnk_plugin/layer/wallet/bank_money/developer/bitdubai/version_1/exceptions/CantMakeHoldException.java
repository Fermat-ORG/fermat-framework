package com.bitdubai.fermat_bnk_plugin.layer.wallet.bank_money.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by memo on 24/11/15.
 */
public class CantMakeHoldException extends FermatException {
    public static final String DEFAULT_MESSAGE = "failed to make hold on bank wallet";
    public CantMakeHoldException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
