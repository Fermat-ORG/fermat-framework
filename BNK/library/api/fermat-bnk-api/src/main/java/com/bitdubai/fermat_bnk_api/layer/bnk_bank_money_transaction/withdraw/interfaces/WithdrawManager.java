package com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.withdraw.interfaces;

import com.bitdubai.fermat_bnk_api.all_definition.bank_money_transaction.BankTransaction;
import com.bitdubai.fermat_bnk_api.all_definition.bank_money_transaction.BankTransactionParameters;
import com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.withdraw.exceptions.CantMakeWithdrawTransactionException;

/**
 * Created by memo on 17/11/15.
 */
public interface WithdrawManager {
    BankTransaction makeWithdraw(BankTransactionParameters parameters) throws CantMakeWithdrawTransactionException;
}
