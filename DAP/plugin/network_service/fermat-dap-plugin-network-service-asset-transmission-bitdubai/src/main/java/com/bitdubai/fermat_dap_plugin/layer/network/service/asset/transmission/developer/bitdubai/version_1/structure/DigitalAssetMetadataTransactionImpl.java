/*
 * @#DigitalAssetMetadataTransactionImpl.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_dap_plugin.layer.network.service.asset.transmission.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.DistributionStatus;
import com.bitdubai.fermat_dap_api.layer.dap_network_services.asset_transmission.enums.DigitalAssetMetadataTransactionType;
import com.bitdubai.fermat_dap_api.layer.dap_network_services.asset_transmission.interfaces.DigitalAssetMetadataTransaction;

import java.util.Objects;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_dap_plugin.layer.network.service.asset.transmission.developer.bitdubai.version_1.structure.DigitalAssetMetadataTransactionImpl</code> implements the
 * transaction make by the Asset Transmission Network Service
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 15/10/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class DigitalAssetMetadataTransactionImpl implements DigitalAssetMetadataTransaction {

    /**
     * Represent the value of PROCESSED
     */
    public final static String PROCESSED = "Y";

    /**
     * Represent the value of NO_PROCESSED
     */
    public final static String NO_PROCESSED = "N";

    /**
     * Represent the value of transactionId
     */
    private UUID transactionId;

    /**
     * Represent the value of genesisTransaction
     */
    private String genesisTransaction;

    /**
     * Represent the value of senderId
     */
    private String senderId;

    /**
     * Represent the value of senderType
     */
    private PlatformComponentType senderType;

    /**
     * Represent the value of receiverId
     */
    private String receiverId;

    /**
     * Represent the value of receiverType
     */
    private PlatformComponentType receiverType;

    /**
     * Represent the value of digitalAssetMetadata
     */
    private DigitalAssetMetadata digitalAssetMetadata;

    /**
     * Represent the value of distributionStatus
     */
    private DistributionStatus distributionStatus;

    /**
     * Represent the value of type
     */
    private DigitalAssetMetadataTransactionType type;

    /**
     * Represent the value of processed
     */
    private String processed;

    /**
     * Represent the value of timestamp
     */
    private Long timestamp;


    /**
     * Constructor
     */
    public DigitalAssetMetadataTransactionImpl() {
        super();
        this.transactionId = UUID.randomUUID();
        this.timestamp     = System.currentTimeMillis();
    }

    /**
     * Constructor whit parameters
     * @param type
     * @param digitalAssetMetadata
     * @param distributionStatus
     * @param genesisTransaction
     * @param processed
     * @param receiverId
     * @param receiverType
     * @param senderId
     * @param senderType
     * @param transactionId
     */
    public DigitalAssetMetadataTransactionImpl(DigitalAssetMetadataTransactionType type, DigitalAssetMetadata digitalAssetMetadata, DistributionStatus distributionStatus, String genesisTransaction, String processed, String receiverId, PlatformComponentType receiverType, String senderId, PlatformComponentType senderType, UUID transactionId) {
        this.type = type;
        this.digitalAssetMetadata = digitalAssetMetadata;
        this.distributionStatus = distributionStatus;
        this.genesisTransaction = genesisTransaction;
        this.processed = processed;
        this.receiverId = receiverId;
        this.receiverType = receiverType;
        this.senderId = senderId;
        this.senderType = senderType;
        this.transactionId = transactionId;
        this.timestamp     = System.currentTimeMillis();
    }

    /**
     * (non-javadoc)
     * @see DigitalAssetMetadataTransaction#getDigitalAssetMetadata()
     */
    public DigitalAssetMetadata getDigitalAssetMetadata() {
        return digitalAssetMetadata;
    }

    /**
     * Set the DigitalAssetMetadata
     * @param digitalAssetMetadata
     */
    public void setDigitalAssetMetadata(DigitalAssetMetadata digitalAssetMetadata) {
        this.digitalAssetMetadata = digitalAssetMetadata;
    }

    /**
     * (non-javadoc)
     * @see DigitalAssetMetadataTransaction#getDistributionStatus()
     */
    public DistributionStatus getDistributionStatus() {
        return distributionStatus;
    }

    /**
     * Set the DistributionStatus
     * @param distributionStatus
     */
    public void setDistributionStatus(DistributionStatus distributionStatus) {
        this.distributionStatus = distributionStatus;
    }

    /**
     * Get the Processed
     * @return String
     */
    public String getProcessed() {
        return processed;
    }

    /**
     * Set the Processed
     * @param processed
     */
    public void setProcessed(String processed) {
        this.processed = processed;
    }

    /**
     * (non-javadoc)
     * @see DigitalAssetMetadataTransaction#getReceiverId()
     */
    public String getReceiverId() {
        return receiverId;
    }

    /**
     * Set the ReceiverId
     * @param receiverId
     */
    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    /**
     * (non-javadoc)
     * @see DigitalAssetMetadataTransaction#getSenderId()
     */
    public String getSenderId() {
        return senderId;
    }

    /**
     * Set the senderId
     * @param senderId
     */
    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    /**
     * Get the TransactionId
     * @return UUID
     */
    public UUID getTransactionId() {
        return transactionId;
    }

    /**
     * Set the TransactionId
     * @param transactionId
     */
    public void setTransactionId(UUID transactionId) {
        this.transactionId = transactionId;
    }

    /**
     * (non-javadoc)
     * @see DigitalAssetMetadataTransaction#getType()
     */
    public DigitalAssetMetadataTransactionType getType() {
        return type;
    }

    /**
     * Set the Type
     * @param type
     */
    public void setType(DigitalAssetMetadataTransactionType type) {
        this.type = type;
    }

    /**
     * (non-javadoc)
     * @see DigitalAssetMetadataTransaction#getGenesisTransaction()
     */
    public String getGenesisTransaction() {
        return genesisTransaction;
    }

    /**
     * Set the GenesisTransaction
     * @param genesisTransaction
     */
    public void setGenesisTransaction(String genesisTransaction) {
        this.genesisTransaction = genesisTransaction;
    }

    /**
     * Get the ReceiverType
     * @return PlatformComponentType
     */
    @Override
    public PlatformComponentType getReceiverType() {
        return receiverType;
    }

    /**
     * Set the ReceiverType
     * @param receiverType
     */
    public void setReceiverType(PlatformComponentType receiverType) {
        this.receiverType = receiverType;
    }

    /**
     * Get the SenderType
     * @return PlatformComponentType
     */
   @Override
    public PlatformComponentType getSenderType() {
        return senderType;
    }

    /**
     * Set the SenderType
     *
     * @param senderType
     */
    public void setSenderType(PlatformComponentType senderType) {
        this.senderType = senderType;
    }

    /**
     * (non-javadoc)
     * @see DigitalAssetMetadataTransaction#getTimestamp()
     */
    public Long getTimestamp() {
        return timestamp;
    }

    /**
     * Set the Timestamp
     * @param timestamp
     */
    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * (non-javadoc)
     * @see  Object#equals(Object)
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DigitalAssetMetadataTransactionImpl)) return false;
        DigitalAssetMetadataTransactionImpl that = (DigitalAssetMetadataTransactionImpl) o;
        return Objects.equals(getTransactionId(), that.getTransactionId()) &&
                Objects.equals(getGenesisTransaction(), that.getGenesisTransaction()) &&
                Objects.equals(getSenderId(), that.getSenderId()) &&
                Objects.equals(getSenderType(), that.getSenderType()) &&
                Objects.equals(getReceiverId(), that.getReceiverId()) &&
                Objects.equals(getReceiverType(), that.getReceiverType()) &&
                Objects.equals(getDigitalAssetMetadata(), that.getDigitalAssetMetadata()) &&
                Objects.equals(getDistributionStatus(), that.getDistributionStatus()) &&
                Objects.equals(getType(), that.getType()) &&
                Objects.equals(getProcessed(), that.getProcessed()) &&
                Objects.equals(getTimestamp(), that.getTimestamp());
    }

    /**
     * (non-javadoc)
     * @see  Object#hashCode()
     */
    @Override
    public int hashCode() {
        return Objects.hash(getTransactionId(), getGenesisTransaction(), getSenderId(), getSenderType(), getReceiverId(), getReceiverType(), getDigitalAssetMetadata(), getDistributionStatus(), getType(), getProcessed(), getTimestamp());
    }

    /**
     * (non-javadoc)
     * @see  Object#toString()
     */
    @Override
    public String toString() {
        return "DigitalAssetMetadataTransactionImpl{" +
                "digitalAssetMetadata=" + digitalAssetMetadata +
                ", transactionId=" + transactionId +
                ", genesisTransaction='" + genesisTransaction + '\'' +
                ", senderId='" + senderId + '\'' +
                ", senderType=" + senderType +
                ", receiverId='" + receiverId + '\'' +
                ", receiverType=" + receiverType +
                ", distributionStatus=" + distributionStatus +
                ", type=" + type +
                ", processed='" + processed + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
