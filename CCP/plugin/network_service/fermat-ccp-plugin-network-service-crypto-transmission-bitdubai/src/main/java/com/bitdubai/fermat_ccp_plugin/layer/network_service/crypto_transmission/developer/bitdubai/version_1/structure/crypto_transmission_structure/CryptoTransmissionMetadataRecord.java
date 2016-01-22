package com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.structure.crypto_transmission_structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_transmission.enums.CryptoTransmissionMetadataState;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_transmission.enums.CryptoTransmissionProtocolState;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_transmission.interfaces.structure.CryptoTransmissionMetadata;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_transmission.interfaces.structure.CryptoTransmissionMetadataType;

import java.util.UUID;

/**
 * Created by Matias Furszyfer on 2015.10.03..
 */
public class CryptoTransmissionMetadataRecord implements CryptoTransmissionMetadata {

    private UUID transactionId;
    private UUID requestId;
    private CryptoCurrency cryptoCurrency;
    private long cryptoAmount;
    private String senderPublicKey;
    private String destinationPublicKey;
    private String associatedCryptoTransactionHash;
    private String paymentDescription;
    private CryptoTransmissionProtocolState cryptoTransmissionProtocolState;
    private CryptoTransmissionMetadataType cryptoTransmissionMetadataType;
    private long timestamp;
    private boolean pendingFlag;
    private int sentCount;
    private CryptoTransmissionMetadataState cryptoTransmissionMetadataState;

    public CryptoTransmissionMetadataRecord(String associatedCryptoTransactionHash,
                                            long cryptoAmount,
                                            CryptoCurrency cryptoCurrency,
                                            String destinationPublicKey,
                                            String paymentDescription,
                                            UUID requestId,
                                            String senderPublicKey,
                                            UUID transactionId,
                                            CryptoTransmissionProtocolState cryptoTransmissionState,
                                            CryptoTransmissionMetadataType cryptoTransmissionMetadataType,
                                            long timestamp,
                                            boolean pendigFlag,
                                            int sentCount,
                                            CryptoTransmissionMetadataState notificationState) {

        this.associatedCryptoTransactionHash    = associatedCryptoTransactionHash;
        this.cryptoAmount                       = cryptoAmount;
        this.cryptoCurrency                     = cryptoCurrency;
        this.destinationPublicKey               = destinationPublicKey;
        this.paymentDescription                 = paymentDescription;
        this.requestId                          = requestId;
        this.senderPublicKey                    = senderPublicKey;
        this.transactionId = transactionId;
        this.cryptoTransmissionProtocolState = cryptoTransmissionState;
        this.cryptoTransmissionMetadataType = cryptoTransmissionMetadataType;
        this.pendingFlag = pendigFlag;
        this.timestamp =timestamp;
        this.sentCount = sentCount;
        this.cryptoTransmissionMetadataState = notificationState;
    }

    public CryptoTransmissionMetadataRecord(String associatedCryptoTransactionHash,
                                            long cryptoAmount,
                                            CryptoCurrency cryptoCurrency,
                                            String destinationPublicKey,
                                            String paymentDescription,
                                            UUID requestId,
                                            String senderPublicKey,
                                            UUID transactionId,
                                            CryptoTransmissionProtocolState cryptoTransmissionState,
                                            CryptoTransmissionMetadataType cryptoTransmissionMetadataType,
                                            int sentCount,
                                            CryptoTransmissionMetadataState notificationState) {

        this.associatedCryptoTransactionHash    = associatedCryptoTransactionHash;
        this.cryptoAmount                       = cryptoAmount;
        this.cryptoCurrency                     = cryptoCurrency;
        this.destinationPublicKey               = destinationPublicKey;
        this.paymentDescription                 = paymentDescription;
        this.requestId                          = requestId;
        this.senderPublicKey                    = senderPublicKey;
        this.transactionId = transactionId;
        this.cryptoTransmissionProtocolState = cryptoTransmissionState;
        this.cryptoTransmissionMetadataType = cryptoTransmissionMetadataType;
        pendingFlag = false;
        this.sentCount = sentCount;
        this.cryptoTransmissionMetadataState = notificationState;
    }

    @Override
    public UUID getTransactionId() {
        return transactionId;
    }

    @Override
    public UUID getRequestId() {
        return requestId;
    }

    @Override
    public CryptoCurrency getCryptoCurrency() {
        return cryptoCurrency;
    }

    @Override
    public long getCryptoAmount() {
        return cryptoAmount;
    }

    @Override
    public String getSenderPublicKey() {
        return senderPublicKey;
    }

    @Override
    public String getDestinationPublicKey() {
        return destinationPublicKey;
    }

    @Override
    public String getAssociatedCryptoTransactionHash() {
        return associatedCryptoTransactionHash;
    }

    @Override
    public String getPaymentDescription() {
        return paymentDescription;
    }


    @Override
    public int getSentCount() {
        return sentCount;
    }


    public CryptoTransmissionMetadataType getCryptoTransmissionMetadataType() {
        return cryptoTransmissionMetadataType;
    }


    @Override
    public CryptoTransmissionProtocolState getCryptoTransmissionProtocolState() {
        return cryptoTransmissionProtocolState;
    }


    @Override
    public CryptoTransmissionMetadataState getCryptoTransmissionMetadataStates() {
        return this.cryptoTransmissionMetadataState;
    }


    @Override
    public void changeCryptoTransmissionProtocolState(CryptoTransmissionProtocolState cryptoTransmissionProtocolState) {
        this.cryptoTransmissionProtocolState = cryptoTransmissionProtocolState;
    }
    @Override
    public void changeMetadataState(CryptoTransmissionMetadataState cryptoTransmissionNotificationStates) {
        this.cryptoTransmissionMetadataState = cryptoTransmissionNotificationStates;
    }

    @Override
    public void setTypeMetadata(CryptoTransmissionMetadataType cryptoTransmissionMetadataType) {
        this.cryptoTransmissionMetadataType =cryptoTransmissionMetadataType;
    }

    @Override
    public boolean isPendigToRead() {
        return pendingFlag;
    }

    @Override
    public void confirmRead() {
        this.pendingFlag = true;
    }

    @Override
    public long getTimestamp(){
        return timestamp;
    }

    @Override
    public void setPendingToRead(boolean pending) {
        this.pendingFlag = pending;
    }

    @Override
    public void setDestinationPublickKey(String destinationPublicKey) {
        this.destinationPublicKey = destinationPublicKey;
    }
    @Override
    public void setSenderPublicKey(String senderPublicKey) {
        this.senderPublicKey = senderPublicKey;
    }
}
