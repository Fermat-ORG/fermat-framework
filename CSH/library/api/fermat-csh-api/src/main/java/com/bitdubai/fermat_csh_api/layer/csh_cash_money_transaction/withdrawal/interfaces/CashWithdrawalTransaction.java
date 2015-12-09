package com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.withdrawal.interfaces;

import com.bitdubai.fermat_csh_api.all_definition.interfaces.CashTransaction;

/**
 * Created by Alejandro Bicelis on 11/27/2015.
 */
public interface CashWithdrawalTransaction extends CashTransaction {

    /**
     * Returns the timestamp when the transaction was received by the Withdrawal Plugin
     * @return      Acknowledge timestamp
     */
    long getTimestamp();

}
