package com.bitdubai.fermat_cbp_plugin.layer.network_service.transaction_transmission.developer.bitdubai.version_1.messages;

import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.enums.BusinessTransactionTransactionType;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.enums.TransactionTransmissionStates;

import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 24/11/15.
 */
public class TransactionTransmissionResponseMessage {

    private UUID transactionId;
    private TransactionTransmissionStates transactionTransmissionStates;
    private BusinessTransactionTransactionType businessTransactionTransactionType;


    public TransactionTransmissionResponseMessage(UUID transactionId, TransactionTransmissionStates transactionTransmissionStates, BusinessTransactionTransactionType businessTransactionTransactionType) {
        this.transactionId = transactionId;
        this.transactionTransmissionStates = transactionTransmissionStates;
        this.businessTransactionTransactionType = businessTransactionTransactionType;
    }

    public UUID getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(UUID transactionId) {
        this.transactionId = transactionId;
    }

    public TransactionTransmissionStates getTransactionTransmissionStates() {
        return transactionTransmissionStates;
    }

    public void setTransactionTransmissionStates(TransactionTransmissionStates transactionTransmissionStates) {
        this.transactionTransmissionStates = transactionTransmissionStates;
    }

    public BusinessTransactionTransactionType getBusinessTransactionTransactionType() {
        return businessTransactionTransactionType;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("TransactionTransmissionResponseMessage{")
                .append("transactionId=").append(transactionId)
                .append(", transactionTransmissionStates=").append(transactionTransmissionStates)
                .append('}').toString();
    }

}
