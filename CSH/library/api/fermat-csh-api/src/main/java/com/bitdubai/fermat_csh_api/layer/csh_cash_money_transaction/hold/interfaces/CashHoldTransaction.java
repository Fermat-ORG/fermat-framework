package com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.hold.interfaces;

import com.bitdubai.fermat_csh_api.all_definition.cash_money_transaction.CashTransaction;
import com.bitdubai.fermat_csh_api.all_definition.enums.CashTransactionStatus;

/**
 * Created by Alejandro Bicelis on 11/17/2015.
 */
public interface CashHoldTransaction extends CashTransaction {

    /**
     * Returns the status of the transaction as defined by the CashTransactionStatus enum
     * @return      Status of the transaction
     * @see         CashTransactionStatus
     */
    CashTransactionStatus getStatus();

    /**
     * Returns the timestamp when the transaction was received by the Hold Plugin
     * @return      Acknowledge timestamp
     */
    long getTimestampAcknowledged();

    /**
     * Returns the timestamp when the transaction was either confirmed or rejected by the Hold Plugin
     * @return      Confirm/Rejected timestamp
     */
    long getTimestampConfirmedRejected();
}
