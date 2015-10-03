package com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces;

import com.bitdubai.fermat_bnk_api.all_definition.enums.BalanceType;
import  com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.exceptions.CantCalculateBalanceException;
import  com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.exceptions.CantRegisterCreditException;
import  com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.exceptions.CantRegisterDebitException;

import java.util.List;

/**
 * Created by Yordin Alayn on 30.09.15.
 */

public interface BankMoneyBalance {

    long getBalance()  throws CantCalculateBalanceException;

    List<BankMoney> getBankMoneyBalancesAvailable() throws CantCalculateBalanceException;

    List<BankMoney> getBankMoneyBalancesBook() throws CantCalculateBalanceException;

    void debit(BankMoneyTransactionRecord BankMoneyTransactionRecord, BalanceType balanceType) throws CantRegisterDebitException;

    void credit(BankMoneyTransactionRecord BankMoneyTransactionRecord, BalanceType balanceType)  throws CantRegisterCreditException;
    
}
