package com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.hold.interfaces;

import com.bitdubai.fermat_bnk_api.all_definition.bank_money_transaction.BankMoneyTransaction;
import com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.hold.exceptions.CantGetConfirmedHoldTransactionException;
import com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.hold.exceptions.CantMakeHoldTransactionException;

import java.util.List;

/**
 * Created by memo on 17/11/15.
 */
public interface HoldManager {

    void hold(BankMoneyTransaction bankMoneyTransaction)throws CantMakeHoldTransactionException;

    List<BankMoneyTransaction> getConfirmedHoldTransactions() throws CantGetConfirmedHoldTransactionException;

}
