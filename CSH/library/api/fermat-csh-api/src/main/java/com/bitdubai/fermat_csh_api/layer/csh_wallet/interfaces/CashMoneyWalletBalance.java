package com.bitdubai.fermat_csh_api.layer.csh_wallet.interfaces;

import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantGetBalanceException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantRegisterCreditException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantRegisterDebitException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantRegisterHoldException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantRegisterUnholdException;

/**
 * Created by Yordin Alayn on 30.09.15.
 * Modified by Alejandro Bicelis on 23/11/2015
 */

public interface CashMoneyWalletBalance {


    /**
     * Returns the Balance this CashMoneyWalletBalance belongs to. (Can be available or book)
     *
     * @return A double, containing the balance.
     */
    double getBalance() throws CantGetBalanceException;

    /**
     * Registers a debit on the Balance of this CashMoneyWalletBalance. (Can be available or book)
     *
     * @param cashMoneyWalletTransaction Contains the details of the debit transaction
     */
    void debit(CashMoneyWalletTransaction cashMoneyWalletTransaction) throws CantRegisterDebitException;

    /**
     * Registers a credit on the Balance of his CashMoneyWalletBalance. (Can be available or book)
     *
     * @param cashMoneyWalletTransaction Contains the details of the credit transaction
     */
    void credit(CashMoneyWalletTransaction cashMoneyWalletTransaction) throws CantRegisterCreditException;

    /**
     * Registers a hold on the Available Balance of this CashMoneyWalletBalance.
     *
     * @param cashMoneyWalletTransaction Contains the details of the hold transaction
     */
    void hold(CashMoneyWalletTransaction cashMoneyWalletTransaction) throws CantRegisterHoldException;

    /**
     * Registers an unhold on the Availiable Balance of this CashMoneyWalletBalance.
     *
     * @param cashMoneyWalletTransaction Contains the details of the unhold transaction
     */
    void unhold(CashMoneyWalletTransaction cashMoneyWalletTransaction) throws CantRegisterUnholdException;

}
