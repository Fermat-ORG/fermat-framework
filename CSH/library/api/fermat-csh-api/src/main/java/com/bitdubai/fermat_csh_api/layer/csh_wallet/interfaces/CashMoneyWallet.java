package com.bitdubai.fermat_csh_api.layer.csh_wallet.interfaces;

import com.bitdubai.fermat_csh_api.all_definition.enums.TransactionType;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantGetCashMoneyTransactionsException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantGetCashMoneyWalletException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantGetHeldFundsException;

import java.util.List;

/**
 * Created by Yordin Alayn on 01,10,15.
 * Modified by Alejandro Bicelis on 23/11/2015
 */
public interface CashMoneyWallet {

    /**
     * Returns an object which allows getting and modifying (credit/debit) the Book Balance
     *
     * @return A CashMoneyWalletBalance object
     */
    CashMoneyWalletBalance getBookBalance() throws CantGetCashMoneyWalletException;

    /**
     * Returns an object which allows getting and modifying (credit/debit) the Available Balance
     *
     * @return A CashMoneyWalletBalance object
     */
    CashMoneyWalletBalance getAvailableBalance() throws CantGetCashMoneyWalletException;

    /**
     * Returns a list of CashMoneyWalletTransactions filtered by TransactionType () object which allows getting and modifying (credit/debit) the Available Balance
     *
     * @return A CashMoneyWalletBalance object
     */
    List<CashMoneyWalletTransaction> getTransactions(TransactionType transactionType, int max, int offset) throws CantGetCashMoneyTransactionsException;


    /**
     * Returns the funds held on this Wallet by a specific Actor
     *
     * @return A double containing the amount of cash held
     */
    double getHeldFunds(String publicKeyActor) throws CantGetHeldFundsException;

}