package com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.exceptions;

import java.util.List;

/**
 * Created by Yordin Alayn on 30.09.15.
 */
public interface BankMoneyBalance {

    long getBalance()  throws CantCalculateBalanceException;

   // List<BankMoneyList> getBankMoneyBalancesAvailable() throws CantCalculateBalanceException;

   // List<BankMoneyList> getBankMoneyBalancesBook() throws CantCalculateBalanceException;

   // void debit(BankMoneyTransactionRecord BankMoneyTransactionRecord, BalanceType balanceType) throws CantRegisterDebitException;

    //void credit(BankMoneyTransactionRecord BankMoneyTransactionRecord, BalanceType balanceType)  throws CantRegisterCreditException;
    
}
