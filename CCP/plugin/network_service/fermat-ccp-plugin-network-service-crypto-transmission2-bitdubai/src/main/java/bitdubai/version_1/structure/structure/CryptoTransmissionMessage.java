package bitdubai.version_1.structure.structure;

import com.google.gson.Gson;

import java.util.UUID;

/**
 * Created by mati on 2016.01.28..
 */
public class CryptoTransmissionMessage {

    private UUID transactionId;
    private CryptoTransmissionMessageType cryptoTransmissionMessageType;
    private String destinationPublicKey;
    private String senderPublicKey;
    private String destinationPublickKey;

    public CryptoTransmissionMessage(
            UUID transactionId,
            CryptoTransmissionMessageType cryptoTransmissionMessage,
            String senderPublicKey,
            String destinationPublicKey) {
        this.transactionId = transactionId;
        this.cryptoTransmissionMessageType = cryptoTransmissionMessage;
        this.senderPublicKey = senderPublicKey;
        this.destinationPublicKey = destinationPublicKey;
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
        this.destinationPublickKey = destinationPublickKey;
    }

    public void setSenderPublicKey(String senderPublicKey) {
        this.senderPublicKey = senderPublicKey;
    }
}
