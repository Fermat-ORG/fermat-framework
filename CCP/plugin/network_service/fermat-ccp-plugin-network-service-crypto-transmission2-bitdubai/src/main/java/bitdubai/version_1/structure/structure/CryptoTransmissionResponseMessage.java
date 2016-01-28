package bitdubai.version_1.structure.structure;

import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_transmission.enums.CryptoTransmissionMetadataState;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_transmission.enums.CryptoTransmissionProtocolState;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_transmission.interfaces.structure.CryptoTransmissionMetadataType;

import java.util.UUID;

/**
 * Created by mati on 2015.10.12..
 */
public class CryptoTransmissionResponseMessage  extends CryptoTransmissionMessage{

    private CryptoTransmissionProtocolState cryptoTransmissionStates;
    private CryptoTransmissionMetadataType cryptoTransmissionMetadataType;
    private CryptoTransmissionMetadataState cryptoTransmissionMetadataState;

    public CryptoTransmissionResponseMessage(
            UUID transactionId,
            CryptoTransmissionMessageType cryptoTransmissionMessageType,
            CryptoTransmissionProtocolState cryptoTransmissionStates,
            CryptoTransmissionMetadataType cryptoTransmissionMetadataType,
            CryptoTransmissionMetadataState cryptoTransmissionMetadataState,
            String senderPublicKey,
            String destinationPublicKey) {
        super(transactionId,cryptoTransmissionMessageType,senderPublicKey,destinationPublicKey);
        this.cryptoTransmissionStates = cryptoTransmissionStates;
        this.cryptoTransmissionMetadataType = cryptoTransmissionMetadataType;
        this.cryptoTransmissionMetadataState = cryptoTransmissionMetadataState;
    }


    public CryptoTransmissionProtocolState getCryptoTransmissionProtocolState() {
        return cryptoTransmissionStates;
    }

    public void setCryptoTransmissionProtocolState(CryptoTransmissionProtocolState cryptoTransmissionStates) {
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
                "transactionId=" + getTransactionId() +
                ", cryptoTransmissionStates=" + cryptoTransmissionStates +
                '}';
    }
}
