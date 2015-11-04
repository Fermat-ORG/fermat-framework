package com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.structure.crypto_transmission_structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_transmission.enums.CryptoTransmissionStates;
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
    private CryptoTransmissionStates cryptoTransmissionState;
    private CryptoTransmissionMetadataType cryptoTransmissionMetadataType;
    private long timestamp;
    private boolean pendigFlag;

    public CryptoTransmissionMetadataRecord(String associatedCryptoTransactionHash,
                                            long cryptoAmount,
                                            CryptoCurrency cryptoCurrency,
                                            String destinationPublicKey,
                                            String paymentDescription,
                                            UUID requestId,
                                            String senderPublicKey,
                                            UUID transactionId,
                                            CryptoTransmissionStates cryptoTransmissionState,
                                            CryptoTransmissionMetadataType cryptoTransmissionMetadataType,
                                            long timestamp,
                                            boolean pendigFlag) {

        this.associatedCryptoTransactionHash    = associatedCryptoTransactionHash;
        this.cryptoAmount                       = cryptoAmount;
        this.cryptoCurrency                     = cryptoCurrency;
        this.destinationPublicKey               = destinationPublicKey;
        this.paymentDescription                 = paymentDescription;
        this.requestId                          = requestId;
        this.senderPublicKey                    = senderPublicKey;
        this.transactionId = transactionId;
        this.cryptoTransmissionState = cryptoTransmissionState;
        this.cryptoTransmissionMetadataType = cryptoTransmissionMetadataType;
        this.pendigFlag = pendigFlag;
        this.timestamp =timestamp;
    }

    public CryptoTransmissionMetadataRecord(String associatedCryptoTransactionHash,
                                            long cryptoAmount,
                                            CryptoCurrency cryptoCurrency,
                                            String destinationPublicKey,
                                            String paymentDescription,
                                            UUID requestId,
                                            String senderPublicKey,
                                            UUID transactionId,
                                            CryptoTransmissionStates cryptoTransmissionState,
                                            CryptoTransmissionMetadataType cryptoTransmissionMetadataType) {

        this.associatedCryptoTransactionHash    = associatedCryptoTransactionHash;
        this.cryptoAmount                       = cryptoAmount;
        this.cryptoCurrency                     = cryptoCurrency;
        this.destinationPublicKey               = destinationPublicKey;
        this.paymentDescription                 = paymentDescription;
        this.requestId                          = requestId;
        this.senderPublicKey                    = senderPublicKey;
        this.transactionId = transactionId;
        this.cryptoTransmissionState = cryptoTransmissionState;
        this.cryptoTransmissionMetadataType = cryptoTransmissionMetadataType;
        pendigFlag = false;
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

    public CryptoTransmissionMetadataType getCryptoTransmissionMetadataType() {
        return cryptoTransmissionMetadataType;
    }


    @Override
    public CryptoTransmissionStates getCryptoTransmissionStates() {
        return cryptoTransmissionState;
    }

    @Override
    public void changeState(CryptoTransmissionStates cryptoTransmissionStates) {
        cryptoTransmissionState = cryptoTransmissionStates;
    }

    @Override
    public void setTypeMetadata(CryptoTransmissionMetadataType cryptoTransmissionMetadataType) {
        this.cryptoTransmissionMetadataType =cryptoTransmissionMetadataType;
    }

    @Override
    public boolean isPendigToRead() {
        return pendigFlag;
    }

    @Override
    public void confirmRead() {
        this.pendigFlag = true;
    }

    @Override
    public long getTimestamp(){
        return timestamp;
    }
}
