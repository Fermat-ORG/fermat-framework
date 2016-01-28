package bitdubai.version_1.structure.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_transmission.enums.CryptoTransmissionMetadataState;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_transmission.enums.CryptoTransmissionProtocolState;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_transmission.interfaces.structure.CryptoTransmissionMetadata;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_transmission.interfaces.structure.CryptoTransmissionMetadataType;

import java.util.UUID;

/**
 * Created by Matias Furszyfer on 2015.10.03..
 */
public class CryptoTransmissionMetadataRecord extends CryptoTransmissionMessage implements CryptoTransmissionMetadata {


    private UUID requestId;
    private CryptoCurrency cryptoCurrency;
    private long cryptoAmount;
    private String associatedCryptoTransactionHash;
    private String paymentDescription;
    private CryptoTransmissionProtocolState cryptoTransmissionProtocolState;
    private CryptoTransmissionMetadataType cryptoTransmissionMetadataType;
    private long timestamp;
    private boolean pendingFlag;
    private int sentCount;
    private CryptoTransmissionMetadataState cryptoTransmissionMetadataState;

    public CryptoTransmissionMetadataRecord(UUID transactionId, CryptoTransmissionMessageType cryptoTransmissionMessage, UUID requestId, CryptoCurrency cryptoCurrency, long cryptoAmount, String senderPublicKey, String destinationPublicKey, String associatedCryptoTransactionHash, String paymentDescription, CryptoTransmissionProtocolState cryptoTransmissionProtocolState, CryptoTransmissionMetadataType cryptoTransmissionMetadataType, long timestamp, boolean pendingFlag, int sentCount, CryptoTransmissionMetadataState cryptoTransmissionMetadataState) {
        super(transactionId, cryptoTransmissionMessage,senderPublicKey,destinationPublicKey);
        this.requestId = requestId;
        this.cryptoCurrency = cryptoCurrency;
        this.cryptoAmount = cryptoAmount;
        this.associatedCryptoTransactionHash = associatedCryptoTransactionHash;
        this.paymentDescription = paymentDescription;
        this.cryptoTransmissionProtocolState = cryptoTransmissionProtocolState;
        this.cryptoTransmissionMetadataType = cryptoTransmissionMetadataType;
        this.timestamp = timestamp;
        this.pendingFlag = pendingFlag;
        this.sentCount = sentCount;
        this.cryptoTransmissionMetadataState = cryptoTransmissionMetadataState;
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
        return super.getSenderPublicKey();
    }

    @Override
    public String getDestinationPublicKey() {
        return super.getDestinationPublicKey();
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



}
