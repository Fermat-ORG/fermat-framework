package com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.crypto_transmission_structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.dmp_network_service.crypto_transmission.enums.CryptoTransmissionStates;
import com.bitdubai.fermat_api.layer.dmp_network_service.crypto_transmission.interfaces.structure.CryptoTransmissionMetadata;
import com.bitdubai.fermat_api.layer.dmp_network_service.crypto_transmission.interfaces.structure.CryptoTransmissionMetadataType;

import java.util.UUID;

/**
 * Created by Matias Furszyfer on 2015.10.03..
 */
public class CryptoTransmissionMetadataRecord implements CryptoTransmissionMetadata {

    private UUID transmissionId;
    private UUID requestId;
    private CryptoCurrency cryptoCurrency;
    private long cryptoAmount;
    private String senderPublicKey;
    private String destinationPublicKey;
    private String associatedCryptoTransactionHash;
    private String paymentDescription;
    private CryptoTransmissionStates cryptoTransmissionStates;
    private CryptoTransmissionMetadataType cryptoTransmissionMetadataType;


    public CryptoTransmissionMetadataRecord(String associatedCryptoTransactionHash,
                                            long cryptoAmount,
                                            CryptoCurrency cryptoCurrency,
                                            String destinationPublicKey,
                                            String paymentDescription,
                                            UUID requestId,
                                            String senderPublicKey,
                                            UUID transmissionId,
                                            CryptoTransmissionStates cryptoTransmissionStates,
                                            CryptoTransmissionMetadataType cryptoTransmissionMetadataType) {

        this.associatedCryptoTransactionHash    = associatedCryptoTransactionHash;
        this.cryptoAmount                       = cryptoAmount;
        this.cryptoCurrency                     = cryptoCurrency;
        this.destinationPublicKey               = destinationPublicKey;
        this.paymentDescription                 = paymentDescription;
        this.requestId                          = requestId;
        this.senderPublicKey                    = senderPublicKey;
        this.transmissionId                     = transmissionId;
        this.cryptoTransmissionStates = cryptoTransmissionStates;
        this.cryptoTransmissionMetadataType  = cryptoTransmissionMetadataType;
    }

    @Override
    public UUID getTransactionId() {
        return transmissionId;
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

    @Override
    public CryptoTransmissionStates getCryptoTransmissionStates() {
        return cryptoTransmissionStates;
    }

    @Override
    public CryptoTransmissionMetadataType getCryptoTransmissionMetadataType() {
        return cryptoTransmissionMetadataType;
    }

    @Override
    public void changeState(CryptoTransmissionStates cryptoTransmissionStates) {
        this.cryptoTransmissionStates = cryptoTransmissionStates;
    }

    @Override
    public void setTypeMetadata(CryptoTransmissionMetadataType cryptoTransmissionMetadataType) {
            this.cryptoTransmissionMetadataType = cryptoTransmissionMetadataType;
    }
}
