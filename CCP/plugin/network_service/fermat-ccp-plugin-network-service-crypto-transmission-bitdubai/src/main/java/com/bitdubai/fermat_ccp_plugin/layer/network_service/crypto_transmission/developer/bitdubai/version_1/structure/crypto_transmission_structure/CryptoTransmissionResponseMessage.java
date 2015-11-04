package com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.structure.crypto_transmission_structure;

import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_transmission.enums.CryptoTransmissionStates;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_transmission.interfaces.structure.CryptoTransmissionMetadataType;

import java.util.UUID;

/**
 * Created by mati on 2015.10.12..
 */
public class CryptoTransmissionResponseMessage {

    private UUID transactionId;
    private CryptoTransmissionStates cryptoTransmissionStates;
    private CryptoTransmissionMetadataType cryptoTransmissionMetadataType;


    public CryptoTransmissionResponseMessage(UUID transactionId, CryptoTransmissionStates cryptoTransmissionStates, CryptoTransmissionMetadataType cryptoTransmissionMetadataType) {
        this.transactionId = transactionId;
        this.cryptoTransmissionStates = cryptoTransmissionStates;
        this.cryptoTransmissionMetadataType = cryptoTransmissionMetadataType;
    }

    public UUID getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(UUID transactionId) {
        this.transactionId = transactionId;
    }

    public CryptoTransmissionStates getCryptoTransmissionStates() {
        return cryptoTransmissionStates;
    }

    public void setCryptoTransmissionStates(CryptoTransmissionStates cryptoTransmissionStates) {
        this.cryptoTransmissionStates = cryptoTransmissionStates;
    }

    public CryptoTransmissionMetadataType getCryptoTransmissionMetadataType() {
        return cryptoTransmissionMetadataType;
    }

    @Override
    public String toString() {
        return "CryptoTransmissionResponseMessage{" +
                "transactionId=" + transactionId +
                ", cryptoTransmissionStates=" + cryptoTransmissionStates +
                '}';
    }
}
