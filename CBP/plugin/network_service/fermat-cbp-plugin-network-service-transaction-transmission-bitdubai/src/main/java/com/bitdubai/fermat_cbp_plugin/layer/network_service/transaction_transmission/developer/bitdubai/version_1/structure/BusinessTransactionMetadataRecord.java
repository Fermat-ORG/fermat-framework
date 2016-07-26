package com.bitdubai.fermat_cbp_plugin.layer.network_service.transaction_transmission.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractTransactionStatus;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.enums.BusinessTransactionTransactionType;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.enums.TransactionTransmissionStates;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.interfaces.BusinessTransactionMetadata;

import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 23/11/15.
 */
public class BusinessTransactionMetadataRecord implements BusinessTransactionMetadata {

    String contractHash;
    ContractTransactionStatus contractTransactionStatus;
    String receiverId;
    PlatformComponentType receiverType;
    String senderId;
    PlatformComponentType senderType;
    String contractId;
    String negotiationId;
    BusinessTransactionTransactionType transactionType;
    Long timestamp;
    UUID transactionId;
    UUID transactionContractId;
    TransactionTransmissionStates transactionTransmissionStates;
    boolean pendingFlag;
    Plugins remoteBusinessTransaction;

    public BusinessTransactionMetadataRecord(String contractHash,
                                             ContractTransactionStatus contractTransactionStatus,
                                             String senderId,
                                             PlatformComponentType receiverType,
                                             String receiverId,
                                             PlatformComponentType senderType,
                                             String contractId,
                                             String negotiationId,
                                             BusinessTransactionTransactionType transactionType,
                                             Long timestamp,
                                             UUID transactionId,
                                             TransactionTransmissionStates transactionTransmissionStates,
                                             Plugins remoteBusinessTransaction
    ) {
        this.contractHash = contractHash;
        this.contractTransactionStatus = contractTransactionStatus;
        this.receiverId = receiverId;
        this.receiverType = receiverType;
        this.senderId = senderId;
        this.senderType = senderType;
        if (contractId == null) {
            this.contractId = contractHash;
        } else {
            this.contractId = contractId;
        }
        this.negotiationId = negotiationId;
        this.transactionType = transactionType;
        this.timestamp = timestamp;
        this.transactionId = transactionId;
        this.transactionContractId = transactionId;
        this.transactionTransmissionStates = transactionTransmissionStates;
        this.pendingFlag = false;
        this.remoteBusinessTransaction = remoteBusinessTransaction;
    }

    public BusinessTransactionMetadataRecord(String contractHash,
                                             ContractTransactionStatus contractTransactionStatus,
                                             String senderId,
                                             PlatformComponentType receiverType,
                                             String receiverId,
                                             PlatformComponentType senderType,
                                             String contractId,
                                             String negotiationId,
                                             BusinessTransactionTransactionType transactionType,
                                             Long timestamp,
                                             UUID transactionId,
                                             UUID transactionContractId,
                                             TransactionTransmissionStates transactionTransmissionStates,
                                             Plugins remoteBusinessTransaction
    ) {
        this.contractHash = contractHash;
        this.contractTransactionStatus = contractTransactionStatus;
        this.receiverId = receiverId;
        this.receiverType = receiverType;
        this.senderId = senderId;
        this.senderType = senderType;
        if (contractId == null) {
            this.contractId = contractHash;
        } else {
            this.contractId = contractId;
        }
        this.negotiationId = negotiationId;
        this.transactionType = transactionType;
        this.timestamp = timestamp;
        this.transactionId = transactionId;
        this.transactionContractId = transactionContractId;
        this.transactionTransmissionStates = transactionTransmissionStates;
        this.pendingFlag = false;
        this.remoteBusinessTransaction = remoteBusinessTransaction;
    }

    @Override
    public String getContractHash() {
        return this.contractHash;
    }

    @Override
    public ContractTransactionStatus getContractTransactionStatus() {
        return this.contractTransactionStatus;
    }

    @Override
    public String getReceiverId() {
        return this.receiverId;
    }

    @Override
    public PlatformComponentType getReceiverType() {
        return this.receiverType;
    }

    @Override
    public String getSenderId() {
        return this.senderId;
    }

    @Override
    public PlatformComponentType getSenderType() {
        return this.senderType;
    }

    @Override
    public String getContractId() {
        return this.contractId;
    }

    @Override
    public String getNegotiationId() {
        return this.negotiationId;
    }

    @Override
    public BusinessTransactionTransactionType getType() {
        return this.transactionType;
    }

    @Override
    public Long getTimestamp() {
        return this.timestamp;
    }

    @Override
    public UUID getTransactionId() {
        return this.transactionId;
    }

    @Override
    public UUID getTransactionContractId() {
        return this.transactionId;
    }

    @Override
    public void setBusinessTransactionTransactionType(BusinessTransactionTransactionType businessTransactionTransactionType) {
        this.transactionType = businessTransactionTransactionType;
    }

    @Override
    public void setState(TransactionTransmissionStates transactionTransmissionStates) {
        this.transactionTransmissionStates = transactionTransmissionStates;
    }

    @Override
    public boolean isPendingToRead() {
        return this.pendingFlag;
    }

    @Override
    public TransactionTransmissionStates getState() {
        return this.transactionTransmissionStates;
    }

    @Override
    public void confirmRead() {
        this.pendingFlag = true;
    }

    @Override
    public Plugins getRemoteBusinessTransaction() {
        return this.remoteBusinessTransaction;
    }

    @Override
    public void setRemoteBusinessTransaction(Plugins remoteBusinessTransaction) {
        this.remoteBusinessTransaction = remoteBusinessTransaction;
    }

}
