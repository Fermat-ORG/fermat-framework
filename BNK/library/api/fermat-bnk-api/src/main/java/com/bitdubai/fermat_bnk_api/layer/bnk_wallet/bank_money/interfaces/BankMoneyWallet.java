package com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces;

import com.bitdubai.fermat_bnk_api.all_definition.enums.BalanceType;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.exceptions.CantTransactionBankMoneyException;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.exceptions.CantTransactionSummaryBankMoneyException;


import java.util.List;

/**
 * Created by Yordin Alayn on 01,10,15.
 */
public interface BankMoneyWallet {

    //change to BankMoneyWallet

    BankMoneyWalletBalance getBookBalance() throws CantTransactionBankMoneyException;

    BankMoneyWalletBalance getAvailableBalance() throws CantTransactionBankMoneyException;

    List<BankMoneyTransaction> getTransactions(BalanceType balanceType, int max, int offset)throws CantTransactionBankMoneyException;

    BankMoneyTransactionSummary getBrokerTransactionSummary(BalanceType balanceType) throws CantTransactionSummaryBankMoneyException;



}