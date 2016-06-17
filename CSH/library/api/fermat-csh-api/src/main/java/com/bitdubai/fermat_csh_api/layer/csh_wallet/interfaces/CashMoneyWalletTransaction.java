package com.bitdubai.fermat_csh_api.layer.csh_wallet.interfaces;

import com.bitdubai.fermat_csh_api.all_definition.enums.BalanceType;
import com.bitdubai.fermat_csh_api.all_definition.enums.TransactionType;
import com.bitdubai.fermat_csh_api.all_definition.interfaces.CashTransaction;

/**
 * Created by Yordin Alayn on 26.09.15.
 * Modified by Alejandro Bicelis on 23/11/2015
 */
public interface CashMoneyWalletTransaction extends CashTransaction {

    /**
     * Returns the type of the transaction stored in the CSH wallet
     * @return      TransactionType, hold, unhold, deposit or withdraw
     */
    TransactionType getTransactionType();

    /**
     * Returns the type of the balance stored in the CSH wallet
     * @return      BalanceType, available, book or all
     */
    BalanceType getBalanceType();

    /**
     * Returns the timestamp when the transaction was applied to the wallet
     * @return      Transaction's timestamp
     */
    long getTimestamp();

    /**
     * Returns true if the transaction hasn't been applied to the wallet, false if otherwise
     * @return      Transaction's pending status
     */
    boolean isPending();

}
