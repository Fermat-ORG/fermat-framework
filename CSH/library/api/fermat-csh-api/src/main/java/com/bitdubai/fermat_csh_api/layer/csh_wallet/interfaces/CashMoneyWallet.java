package com.bitdubai.fermat_csh_api.layer.csh_wallet.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_csh_api.all_definition.enums.TransactionType;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantChangeCashMoneyWalletException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantGetCashMoneyWalletCurrencyException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantGetCashMoneyWalletTransactionsException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantGetCashMoneyWalletBalanceException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantGetHeldFundsException;

import java.util.List;

/**
 * Created by Yordin Alayn on 01,10,15.
 * Modified by Alejandro Bicelis on 23/11/2015
 */
public interface CashMoneyWallet {


    /**
     * Allows the wallet to be changed, so that the other methods of this class
     * affect that wallet.
     */
    void changeWalletTo(String walletPublicKey) throws CantChangeCashMoneyWalletException;

    /**
     * Returns an object which allows getting and modifying (credit/debit) the Book Balance
     *
     * @return A CashMoneyWalletBalance object
     */
    FiatCurrency getCurrency() throws CantGetCashMoneyWalletCurrencyException;

    /**
     * Returns an object which allows getting and modifying (credit/debit) the Book Balance
     *
     * @return A CashMoneyWalletBalance object
     */
    CashMoneyWalletBalance getBookBalance() throws CantGetCashMoneyWalletBalanceException;

    /**
     * Returns an object which allows getting and modifying (credit/debit) the Available Balance
     *
     * @return A CashMoneyWalletBalance object
     */
    CashMoneyWalletBalance getAvailableBalance() throws CantGetCashMoneyWalletBalanceException;

    /**
     * Returns a list of CashMoneyWalletTransactions filtered by TransactionType () object which allows getting and modifying (credit/debit) the Available Balance
     *
     * @return A CashMoneyWalletBalance object
     */
    List<CashMoneyWalletTransaction> getTransactions(TransactionType transactionType, int max, int offset) throws CantGetCashMoneyWalletTransactionsException;


    /**
     * Returns the funds held on this Wallet by a specific Actor
     *
     * @return A double containing the amount of cash held
     */
    double getHeldFunds(String publicKeyActor) throws CantGetHeldFundsException;

}