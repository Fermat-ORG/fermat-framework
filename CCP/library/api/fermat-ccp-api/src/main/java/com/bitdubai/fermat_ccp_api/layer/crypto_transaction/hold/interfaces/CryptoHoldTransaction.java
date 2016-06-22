package com.bitdubai.fermat_ccp_api.layer.crypto_transaction.hold.interfaces;


import com.bitdubai.fermat_ccp_api.all_definition.enums.CryptoTransactionStatus;

/**
 * Created by Franklin Marcano on 21/11/2015.
 */
public interface CryptoHoldTransaction extends CryptoTransaction {

    /**
     * Returns the status of the transaction as defined by the CashTransactionStatus enum
     * @return      Status of the transaction
     * @see         CryptoTransactionStatus
     */
    CryptoTransactionStatus getStatus();
    void                    setStatus(CryptoTransactionStatus status);

    /**
     * Returns the timestamp when the transaction was received by the Hold Plugin
     * @return      Acknowledge timestamp
     */
    long getTimestampAcknowledged();
    void setTimestampAcknowledged(long timestampAcknowledged);

    /**
     * Returns the timestamp when the transaction was either confirmed or rejected by the Hold Plugin
     * @return      Confirm/Rejected timestamp
     */
    long getTimestampConfirmedRejected();
    void setTimestampConfirmedRejected(long timestampConfirmedRejected);
}
