package com.bitdubai.fermat_cbp_plugin.layer.network_service.transaction_transmission.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;
import com.bitdubai.fermat_cbp_api.layer.network_service.TransactionTransmission.enums.BusinessTransactionTransactionType;
import com.bitdubai.fermat_cbp_api.layer.network_service.TransactionTransmission.interfaces.BusinessTransaction;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 23/11/15.
 */
public class BusinessTransactionRecord implements BusinessTransaction {

    String contractHash;
    ContractStatus contractStatus;
    String receiverId;
    PlatformComponentType receiverType;
    String senderId;
    PlatformComponentType senderType;
    String contractId;
    String negotiationId;
    BusinessTransactionTransactionType transactionType;
    Long timestamp;

    public BusinessTransactionRecord(String contractHash,
                                     ContractStatus contractStatus,
                                     String receiverId,
                                     PlatformComponentType receiverType,
                                     String senderId,
                                     PlatformComponentType senderType,
                                     String contractId,
                                     String negotiationId,
                                     BusinessTransactionTransactionType transactionType,
                                     Long timestamp
                                     ){
        this.contractHash=contractHash;
        this.contractStatus=contractStatus;
        this.receiverId=receiverId;
        this.receiverType = receiverType;
        this.senderId=senderId;
        this.senderType=senderType;
        this.contractId=contractId;
        this.negotiationId=negotiationId;
        this.transactionType=transactionType;
        this.timestamp=timestamp;

    }

    @Override
    public String getContractHash() {
        return this.contractHash;
    }

    @Override
    public ContractStatus getContractStatus() {
        return this.contractStatus;
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
}
