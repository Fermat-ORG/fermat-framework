package com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces;

import com.bitdubai.fermat_bnk_api.all_definition.enums.TransactionType;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.exceptions.CantGetBankMoneyWalletTransactionsException;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.exceptions.CantGetHeldFundsException;


import java.util.List;

/**
 * Created by Yordin Alayn on 01,10,15.
 */
public interface BankMoneyWallet {

    //change to BankMoneyWallet

    BankMoneyWalletBalance getBookBalance();

    BankMoneyWalletBalance getAvailableBalance();

    List<BankMoneyTransactionRecord> getTransactions(TransactionType type, int max, int offset)throws CantGetBankMoneyWalletTransactionsException;

    double getHeldFunds() throws CantGetHeldFundsException, CantGetHeldFundsException;

}