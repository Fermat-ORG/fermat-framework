package com.bitdubai.fermat_csh_api.layer.csh_wallet.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_csh_api.all_definition.enums.BalanceType;
import com.bitdubai.fermat_csh_api.all_definition.enums.TransactionType;
import com.bitdubai.fermat_csh_api.all_definition.exceptions.CashMoneyWalletInsufficientFundsException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantGetCashMoneyWalletCurrencyException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantGetCashMoneyWalletTransactionsException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantGetCashMoneyWalletBalanceException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantGetHeldFundsException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantRegisterHoldException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantRegisterUnholdException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CashMoneyWalletNotLoadedException;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * Created by Yordin Alayn on 01,10,15.
 * Modified by Alejandro Bicelis on 23/11/2015
 */
public interface CashMoneyWallet {


    String getWalletPublicKey() throws CashMoneyWalletNotLoadedException;
    /**
     * Allows the wallet to be changed, so that the other methods of this class
     * affect that wallet.
     */
   // void changeWalletTo(String walletPublicKey) throws CashMoneyWalletDoesNotExistException;

    /**
     * Returns an object which allows getting and modifying (credit/debit) the Book Balance
     *
     * @return A FiatCurrency object
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
     * @return A List of CashMoneyWalletTransaction objects
     */
    public List<CashMoneyWalletTransaction> getTransactions(List<TransactionType> transactionTypes, List<BalanceType> balanceTypes, int max, int offset) throws CantGetCashMoneyWalletTransactionsException;


    /**
     * Returns the funds held on this Wallet by a specific Actor
     *
     * @return A BigDecimal containing the amount of cash held
     */
    BigDecimal getHeldFunds(String actorPublicKey) throws CantGetHeldFundsException;


    /**
     * Registers a hold on the Available Balance of this CashMoneyWalletBalance.
     *
     */
    void hold(UUID transactionId, String publicKeyActor, String publicKeyPlugin, BigDecimal amount, String memo) throws CantRegisterHoldException, CashMoneyWalletInsufficientFundsException;

    /**
     * Registers an unhold on the Availiable Balance of this CashMoneyWalletBalance.
     *
     */
    void unhold(UUID transactionId, String publicKeyActor, String publicKeyPlugin, BigDecimal amount, String memo) throws CantRegisterUnholdException;

}