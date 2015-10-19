package com.bitdubai.fermat_bnk_plugin.layer.bank_money_transaction.receive_offline_bank_transfer.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Yordin Alayn on 04.10.15.
 */
public class CantUpdateStatusReceiveOfflineBankTransferBankMoneyTransactionException extends FermatException {
    public CantUpdateStatusReceiveOfflineBankTransferBankMoneyTransactionException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}