package com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_bnk_api.all_definition.enums.TransactionType;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.exceptions.CantAddNewAccountException;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.exceptions.CantGetBankMoneyWalletTransactionsException;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.exceptions.CantGetHeldFundsException;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.exceptions.CantRegisterHoldException;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.exceptions.CantRegisterUnholdException;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Yordin Alayn on 01,10,15.
 * modified by Guillermo Gutierrez 24.11.2015
 */
public interface BankMoneyWalletManager extends FermatManager, Serializable {

    //change to BankMoneyWalletManager

    BankMoneyWalletBalance getBookBalance();

    BankMoneyWalletBalance getAvailableBalance();

    List<BankMoneyTransactionRecord> getTransactions(TransactionType type, int max, int offset,String account)throws CantGetBankMoneyWalletTransactionsException;

    double getHeldFunds(String account) throws  CantGetHeldFundsException;

    void hold(BankMoneyTransactionRecord bankMoneyTransactionRecord) throws CantRegisterHoldException;

    void unhold(BankMoneyTransactionRecord bankMoneyTransactionRecord) throws CantRegisterUnholdException;

    List<BankAccountNumber> getAccounts() ;

    void addNewAccount(BankAccountNumber bankAccountNumber) throws CantAddNewAccountException;

    void createBankName(String bankName);

    String getBankName();
}