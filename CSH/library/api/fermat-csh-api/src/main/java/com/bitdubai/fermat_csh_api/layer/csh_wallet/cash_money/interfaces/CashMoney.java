package com.bitdubai.fermat_csh_api.layer.csh_wallet.cash_money.interfaces;

import com.bitdubai.fermat_csh_api.all_definition.enums.BalanceType;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.cash_money.exceptions.CantTransactionCashMoneyException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.cash_money.exceptions.CantTransactionSummaryCashMoneyException;

import java.util.List;

/**
 * Created by Yordin Alayn on 01,10,15.
 */
public interface CashMoney {

    //CashMoneyBalance getBookBalance(BalanceType balanceType) throws CantTransactionCashMoneyException;

    //CashMoneyBalance getAvailableBalance(BalanceType balanceType) throws CantTransactionCashMoneyException;
    double getBookBalance(BalanceType balanceType) throws CantTransactionCashMoneyException;

    double getAvailableBalance(BalanceType balanceType) throws CantTransactionCashMoneyException;

    List<CashMoneyTransaction> getTransactions(BalanceType balanceType, int max, int offset )throws CantTransactionCashMoneyException;

    CashMoneyTransactionSummary getBrokerTransactionSummary(BalanceType balanceType) throws CantTransactionSummaryCashMoneyException;
}