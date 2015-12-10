package com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.deposit.interfaces;

import com.bitdubai.fermat_csh_api.all_definition.interfaces.CashTransaction;

/**
 * Created by Alejandro Bicelis on 11/27/2015.
 */
public interface CashDepositTransaction extends CashTransaction {

    /**
     * Returns the timestamp when the transaction was received by the Deposit Plugin
     * @return      Acknowledge timestamp
     */
    long getTimestamp();

}
