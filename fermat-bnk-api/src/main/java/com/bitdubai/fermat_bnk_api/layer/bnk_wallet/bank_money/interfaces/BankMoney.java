package com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces;

import com.bitdubai.fermat_bnk_api.all_definition.enums.BalanceType;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.exceptions.CantTransactionBankMoneyException;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.exceptions.CantTransactionSummaryBankMoneyException;


import java.util.List;

/**
 * Created by Yordin Alayn on 01,10,15.
 */
public interface BankMoney {

    BankMoneyBalance getBookBalance(BalanceType balanceType) throws CantTransactionBankMoneyException;

    BankMoneyBalance getAvailableBalance(BalanceType balanceType) throws CantTransactionBankMoneyException;

    List<BankMoneyTransaction> getTransactions(BalanceType balanceType, int max, int offset)throws CantTransactionBankMoneyException;

    BankMoneyTransactionSummary getBrokerTransactionSummary(BalanceType balanceType) throws CantTransactionSummaryBankMoneyException;

}