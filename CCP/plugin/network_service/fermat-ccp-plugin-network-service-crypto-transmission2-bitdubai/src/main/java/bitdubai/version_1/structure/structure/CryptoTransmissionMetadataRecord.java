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
    private CryptoTransmissionMetadataType cryptoTransmissionMetadataType;
    private long timestamp;
    public CryptoTransmissionMetadataRecord(UUID transactionId, CryptoTransmissionMessageType cryptoTransmissionMessage, UUID requestId, CryptoCurrency cryptoCurrency, long cryptoAmount, String senderPublicKey, String destinationPublicKey, String associatedCryptoTransactionHash, String paymentDescription, CryptoTransmissionProtocolState cryptoTransmissionProtocolState, CryptoTransmissionMetadataType cryptoTransmissionMetadataType, long timestamp, boolean pendingFlag, int sentCount, CryptoTransmissionMetadataState cryptoTransmissionMetadataState) {
        super(transactionId, cryptoTransmissionMessage,senderPublicKey,destinationPublicKey,cryptoTransmissionProtocolState,cryptoTransmissionMetadataState,pendingFlag,sentCount);
        this.requestId = requestId;
        this.cryptoCurrency = cryptoCurrency;
        this.cryptoAmount = cryptoAmount;
        this.associatedCryptoTransactionHash = associatedCryptoTransactionHash;
        this.paymentDescription = paymentDescription;
        this.cryptoTransmissionMetadataType = cryptoTransmissionMetadataType;
        this.timestamp = timestamp;
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
    public CryptoTransmissionMetadataType getCryptoTransmissionMetadataType() {
        return cryptoTransmissionMetadataType;
    }
    @Override
    public CryptoTransmissionProtocolState getCryptoTransmissionProtocolState() {
        return super.getCryptoTransmissionProtocolState();
    }
    @Override
    public CryptoTransmissionMetadataState getCryptoTransmissionMetadataStates() {
        return super.getCryptoTransmissionMetadataState();
    }
    @Override
    public void changeCryptoTransmissionProtocolState(CryptoTransmissionProtocolState cryptoTransmissionProtocolState) {
        setProtocolState(cryptoTransmissionProtocolState);
    }
    @Override
    public void changeMetadataState(CryptoTransmissionMetadataState cryptoTransmissionNotificationStates) {
        setCryptoTransmissionMetadataState(cryptoTransmissionNotificationStates);
    }
    @Override
    public void setTypeMetadata(CryptoTransmissionMetadataType cryptoTransmissionMetadataType) {
        this.cryptoTransmissionMetadataType =cryptoTransmissionMetadataType;
    }
    @Override
    public boolean isPendigToRead() {
        return super.isPendigToRead();
    }
    @Override
    public void confirmRead() {
        setPendingToRead(true);
    }
    @Override
    public long getTimestamp(){
        return timestamp;
    }
    @Override
    public void setPendingToRead(boolean pending) {
        super.setPendigToRead(pending);
    }
}
