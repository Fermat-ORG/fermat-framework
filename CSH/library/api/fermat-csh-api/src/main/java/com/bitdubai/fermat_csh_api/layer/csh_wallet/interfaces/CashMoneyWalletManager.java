package com.bitdubai.fermat_csh_api.layer.csh_wallet.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantCreateCashMoneyException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantLoadCashMoneyException;

/**
 * Created by Yordin Alayn on 26.09.15.
 */
public interface CashMoneyWalletManager {


    /**
     * Returns an object which allows getting and modifying (credit/debit) the Book Balance
     *
     * @return A CashMoneyWalletBalance object
     */
    CashMoneyWallet loadCashMoneyWallet(String walletPublicKey) throws CantLoadCashMoneyException;


    /**
     * Returns an object which allows getting and modifying (credit/debit) the Book Balance
     *
     * @return A CashMoneyWalletBalance object
     */
    void createCashMoney(String walletPublicKey, FiatCurrency fiatCurrency) throws CantCreateCashMoneyException;





    //TODO: verificar si esto va o no
       /* List<CashMoneyWallet> getTransactionsCashMoney() throws CantTransactionCashMoneyException;

        CashMoneyWallet registerCashMoney(
                 final String cashTransactionId
                ,final String publicKeyActorFrom
                ,final String publicKeyActorTo
                ,final String status
                ,final String balanceType
                ,final String transactionType
                ,final double amount
                ,final String cashCurrencyType
                ,final String cashReference
                ,final long runningBookBalance
                ,final long runningAvailableBalance
                ,final long timestamp
                ,final String memo
        ) throws CantCreateCashMoneyException;*/

}
