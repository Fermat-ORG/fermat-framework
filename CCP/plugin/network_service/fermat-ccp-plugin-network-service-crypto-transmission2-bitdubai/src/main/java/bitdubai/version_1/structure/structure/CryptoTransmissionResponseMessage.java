package bitdubai.version_1.structure.structure;

import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_transmission.enums.CryptoTransmissionMetadataState;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_transmission.enums.CryptoTransmissionProtocolState;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_transmission.interfaces.structure.CryptoTransmissionMetadataType;

import java.util.UUID;

/**
 * Created by mati on 2015.10.12..
 */
public class CryptoTransmissionResponseMessage  extends CryptoTransmissionMessage{

    private CryptoTransmissionMetadataType cryptoTransmissionMetadataType;

    public CryptoTransmissionResponseMessage(
            UUID transactionId,
            CryptoTransmissionMessageType cryptoTransmissionMessageType,
            CryptoTransmissionProtocolState cryptoTransmissionStates,
            CryptoTransmissionMetadataType cryptoTransmissionMetadataType,
            CryptoTransmissionMetadataState cryptoTransmissionMetadataState,
            String senderPublicKey,
            String destinationPublicKey,
            boolean isPendingToRead,
            int sentCount) {
        super(transactionId,cryptoTransmissionMessageType,senderPublicKey,destinationPublicKey,cryptoTransmissionStates,cryptoTransmissionMetadataState,isPendingToRead,sentCount);
        this.cryptoTransmissionMetadataType = cryptoTransmissionMetadataType;
    }



    public CryptoTransmissionMetadataType getCryptoTransmissionMetadataType() {
        return cryptoTransmissionMetadataType;
    }

    public CryptoTransmissionMetadataState getCryptoTransmissionMetadataState() {
        return super.getCryptoTransmissionMetadataState();
    }

    @Override
    public String toString() {
        return "CryptoTransmissionResponseMessage{" +
                "transactionId=" + getTransactionId() +
                ", cryptoTransmissionStates=" + getCryptoTransmissionProtocolState() +
                '}';
    }


}
