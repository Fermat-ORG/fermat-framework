package com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.unhold.interfaces;

import com.bitdubai.fermat_bnk_api.all_definition.bank_money_transaction.BankMoneyTransaction;
import com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.unhold.exceptions.CantMakeUnholdTransactionException;

import java.util.List;

/**
 * Created by memo on 17/11/15.
 */
public interface UnholdManager {

    void unHold(BankMoneyTransaction bankMoneyTransaction)throws CantMakeUnholdTransactionException;

    List<BankMoneyTransaction> getConfirmedUnholdTransactions() throws CantMakeUnholdTransactionException;
}
