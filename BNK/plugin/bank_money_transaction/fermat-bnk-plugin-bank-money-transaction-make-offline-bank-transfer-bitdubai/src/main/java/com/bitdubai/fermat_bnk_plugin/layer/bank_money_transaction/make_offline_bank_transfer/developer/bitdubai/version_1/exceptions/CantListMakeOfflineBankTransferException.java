package com.bitdubai.fermat_bnk_plugin.layer.bank_money_transaction.make_offline_bank_transfer.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Yordin Alayn on 09.10.15.
 */
public class CantListMakeOfflineBankTransferException extends FermatException {
    public CantListMakeOfflineBankTransferException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}