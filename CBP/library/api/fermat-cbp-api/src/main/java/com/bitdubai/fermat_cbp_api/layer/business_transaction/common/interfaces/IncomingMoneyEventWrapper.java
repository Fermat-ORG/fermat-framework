package com.bitdubai.fermat_cbp_api.layer.business_transaction.common.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.events.IncomingMoneyNotificationEvent;

import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 16/12/15.
 */
public class IncomingMoneyEventWrapper {

    String eventId;
    String receiverPublicKey;
    long cryptoAmount;
    CryptoCurrency cryptoCurrency;
    String walletPublicKey;
    String senderPublicKey;
    long timestamp;
    String transactionHash;

    public IncomingMoneyEventWrapper(
            String eventId,
            String receiverPublicKey,
            long cryptoAmount,
            CryptoCurrency cryptoCurrency,
            String walletPublicKey,
            String senderPublicKey,
            long timestamp,
            String transactionHash) {
        this.eventId = eventId;
        this.receiverPublicKey = receiverPublicKey;
        this.cryptoAmount = cryptoAmount;
        this.cryptoCurrency = cryptoCurrency;
        this.walletPublicKey = walletPublicKey;
        this.senderPublicKey = senderPublicKey;
        this.timestamp = timestamp;
        this.transactionHash = transactionHash;
    }

    public IncomingMoneyEventWrapper(
            IncomingMoneyNotificationEvent incomingMoneyNotificationEvent
    ) {
        this.eventId = UUID.randomUUID().toString();
        this.receiverPublicKey = incomingMoneyNotificationEvent.getActorId();
        this.cryptoAmount = incomingMoneyNotificationEvent.getAmount();
        this.cryptoCurrency = incomingMoneyNotificationEvent.getCryptoCurrency();
        this.walletPublicKey = incomingMoneyNotificationEvent.getWalletPublicKey();
        this.senderPublicKey = incomingMoneyNotificationEvent.getIntraUserIdentityPublicKey();
        this.timestamp = System.currentTimeMillis();
        this.transactionHash = incomingMoneyNotificationEvent.getTransactionHash();
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getReceiverPublicKey() {
        return receiverPublicKey;
    }

    public void setReceiverPublicKey(String receiverPublicKey) {
        this.receiverPublicKey = receiverPublicKey;
    }

    public long getCryptoAmount() {
        return cryptoAmount;
    }

    public void setCryptoAmount(long cryptoAmount) {
        this.cryptoAmount = cryptoAmount;
    }

    public CryptoCurrency getCryptoCurrency() {
        return cryptoCurrency;
    }

    public void setCryptoCurrency(CryptoCurrency cryptoCurrency) {
        this.cryptoCurrency = cryptoCurrency;
    }

    public String getWalletPublicKey() {
        return walletPublicKey;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setWalletPublicKey(String walletPublicKey) {
        this.walletPublicKey = walletPublicKey;
    }

    public String getSenderPublicKey() {
        return senderPublicKey;
    }

    public void setSenderPublicKey(String senderPublicKey) {
        this.senderPublicKey = senderPublicKey;
    }

    public String getTransactionHash() {
        return transactionHash;
    }

    public void setTransactionHash(String transactionHash) {
        this.transactionHash = transactionHash;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("IncomingMoneyEventWrapper{")
                .append("eventId='").append(eventId)
                .append('\'')
                .append(", receiverPublicKey='").append(receiverPublicKey)
                .append('\'')
                .append(", cryptoAmount=").append(cryptoAmount)
                .append(", cryptoCurrency=").append(cryptoCurrency)
                .append(", walletPublicKey='").append(walletPublicKey)
                .append('\'')
                .append(", senderPublicKey='").append(senderPublicKey)
                .append('\'')
                .append(", transactionHash='").append(transactionHash)
                .append('\'')
                .append('}').toString();
    }
}
