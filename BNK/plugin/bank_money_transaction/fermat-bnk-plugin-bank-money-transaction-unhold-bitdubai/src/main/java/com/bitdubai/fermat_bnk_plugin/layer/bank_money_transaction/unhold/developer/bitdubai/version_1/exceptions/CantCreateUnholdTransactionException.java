package com.bitdubai.fermat_bnk_plugin.layer.bank_money_transaction.unhold.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by memo on 26/11/15.
 */
public class CantCreateUnholdTransactionException extends FermatException {

    public static final String DEFAULT_MESSAGE = "CAN'T CREATE HOLD TRANSACTION";

    public CantCreateUnholdTransactionException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
