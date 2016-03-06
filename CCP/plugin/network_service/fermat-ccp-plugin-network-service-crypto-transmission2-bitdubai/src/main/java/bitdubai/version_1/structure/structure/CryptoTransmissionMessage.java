package bitdubai.version_1.structure.structure;

import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_transmission.enums.CryptoTransmissionMetadataState;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_transmission.enums.CryptoTransmissionProtocolState;
import com.google.gson.Gson;

import java.util.UUID;

/**
 * Created by mati on 2016.01.28..
 */
public class CryptoTransmissionMessage {

    private UUID transactionId;
    private CryptoTransmissionMessageType cryptoTransmissionMessageType;
    private CryptoTransmissionProtocolState cryptoTransmissionProtocolState;
    private CryptoTransmissionMetadataState cryptoTransmissionMetadataState;
    private String destinationPublicKey;
    private String senderPublicKey;
    private boolean pendigToRead;
    private int sentCount;

    public CryptoTransmissionMessage(
            UUID transactionId,
            CryptoTransmissionMessageType cryptoTransmissionMessage,
            String senderPublicKey,
            String destinationPublicKey,
            CryptoTransmissionProtocolState cryptoTransmissionProtocolState,
            CryptoTransmissionMetadataState cryptoTransmissionMetadataState,
            boolean pendigToRead,
            int sentCount) {
        this.transactionId = transactionId;
        this.cryptoTransmissionMessageType = cryptoTransmissionMessage;
        this.senderPublicKey = senderPublicKey;
        this.destinationPublicKey = destinationPublicKey;
        this.cryptoTransmissionProtocolState = cryptoTransmissionProtocolState;
        this.cryptoTransmissionMetadataState = cryptoTransmissionMetadataState;
        this.pendigToRead = pendigToRead;
        this.sentCount = sentCount;
    }


    public UUID getTransactionId() {
        return transactionId;
    }

    public CryptoTransmissionMessageType getCryptoTransmissionMessageType() {
        return cryptoTransmissionMessageType;
    }

    public String toJson() {
        return new Gson().toJson(this);
    }

    public String getDestinationPublicKey() {
        return destinationPublicKey;
    }

    public String getSenderPublicKey() {
        return senderPublicKey;
    }

    public void setDestinationPublickKey(String destinationPublickKey) {
        this.destinationPublicKey = destinationPublickKey;
    }

    public void setSenderPublicKey(String senderPublicKey) {
        this.senderPublicKey = senderPublicKey;
    }

    public boolean isPendigToRead() {
        return pendigToRead;
    }

    public int getSentCount() {
        return sentCount;
    }

    public void setPendigToRead(boolean pendigToRead) {
        this.pendigToRead = pendigToRead;
    }

    public void setSentCount(int sentCount) {
        this.sentCount = sentCount;
    }

    public CryptoTransmissionProtocolState getCryptoTransmissionProtocolState() {
        return cryptoTransmissionProtocolState;
    }

    public void setProtocolState(CryptoTransmissionProtocolState protocolState) {
        this.cryptoTransmissionProtocolState = protocolState;
    }

    public CryptoTransmissionMetadataState getCryptoTransmissionMetadataState() {
        return cryptoTransmissionMetadataState;
    }

    public void setCryptoTransmissionMetadataState(CryptoTransmissionMetadataState cryptoTransmissionMetadataState) {
        this.cryptoTransmissionMetadataState = cryptoTransmissionMetadataState;
    }
}
