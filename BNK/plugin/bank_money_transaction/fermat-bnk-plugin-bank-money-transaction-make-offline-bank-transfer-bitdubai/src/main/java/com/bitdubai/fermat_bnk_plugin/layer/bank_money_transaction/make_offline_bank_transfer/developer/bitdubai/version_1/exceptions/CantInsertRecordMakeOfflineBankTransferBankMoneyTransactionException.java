package com.bitdubai.fermat_bnk_plugin.layer.bank_money_transaction.make_offline_bank_transfer.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Yordin Alayn on 29.09.15.
 */
public class CantInsertRecordMakeOfflineBankTransferBankMoneyTransactionException extends FermatException {
    public CantInsertRecordMakeOfflineBankTransferBankMoneyTransactionException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
