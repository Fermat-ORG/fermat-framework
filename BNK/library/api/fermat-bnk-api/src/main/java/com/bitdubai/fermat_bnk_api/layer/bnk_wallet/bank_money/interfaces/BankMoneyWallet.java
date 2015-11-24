package com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces;

import com.bitdubai.fermat_bnk_api.all_definition.enums.TransactionType;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.exceptions.CantGetBankMoneyWalletTransactionsException;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.exceptions.CantGetHeldFundsException;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.exceptions.CantRegisterHoldException;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.exceptions.CantRegisterUnholdException;


import java.util.List;
import java.util.UUID;

/**
 * Created by Yordin Alayn on 01,10,15.
 * modified by Guillermo Gutierrez 24.11.2015
 */
public interface BankMoneyWallet {

    //change to BankMoneyWallet

    BankMoneyWalletBalance getBookBalance();

    BankMoneyWalletBalance getAvailableBalance();

    List<BankMoneyTransactionRecord> getTransactions(TransactionType type, int max, int offset,String account)throws CantGetBankMoneyWalletTransactionsException;

    double getHeldFunds() throws CantGetHeldFundsException, CantGetHeldFundsException;

    void hold() throws CantRegisterHoldException;

    void unhold() throws CantRegisterUnholdException;

    List<BankAccountNumber> getAccounts(UUID walletPublicKey) ;
}