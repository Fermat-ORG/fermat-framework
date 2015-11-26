package com.bitdubai.fermat_csh_api.layer.csh_wallet.interfaces;

import com.bitdubai.fermat_csh_api.all_definition.enums.BalanceType;
import com.bitdubai.fermat_csh_api.all_definition.enums.CashCurrencyType;
import com.bitdubai.fermat_csh_api.all_definition.enums.CashTransactionStatus;
import com.bitdubai.fermat_csh_api.all_definition.enums.TransactionType;
import com.bitdubai.fermat_csh_api.all_definition.interfaces.CashTransaction;

import java.util.UUID;

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
     * Returns the timestamp when the transaction was applied to the wallet
     * @return      Transaction's timestamp
     */
    long getTimestamp();

}
