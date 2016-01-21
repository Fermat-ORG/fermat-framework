package com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.structure.crypto_transmission_structure;

import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_transmission.enums.CryptoTransmissionMetadataState;
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
    private CryptoTransmissionMetadataState cryptoTransmissionMetadataState;

    public CryptoTransmissionResponseMessage(UUID transactionId, CryptoTransmissionStates cryptoTransmissionStates, CryptoTransmissionMetadataType cryptoTransmissionMetadataType,CryptoTransmissionMetadataState cryptoTransmissionMetadataState) {
        this.transactionId = transactionId;
        this.cryptoTransmissionStates = cryptoTransmissionStates;
        this.cryptoTransmissionMetadataType = cryptoTransmissionMetadataType;
        this.cryptoTransmissionMetadataState = cryptoTransmissionMetadataState;
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

    public CryptoTransmissionMetadataState getCryptoTransmissionMetadataState() {
        return cryptoTransmissionMetadataState;
    }

    @Override
    public String toString() {
        return "CryptoTransmissionResponseMessage{" +
                "transactionId=" + transactionId +
                ", cryptoTransmissionStates=" + cryptoTransmissionStates +
                '}';
    }
}
