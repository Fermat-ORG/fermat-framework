package com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.crypto_transmission_structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.dmp_network_service.crypto_transmission.interfaces.structure.CryptoTransmissionMetadata;

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


    public CryptoTransmissionMetadataRecord(String associatedCryptoTransactionHash,
                                            long cryptoAmount,
                                            CryptoCurrency cryptoCurrency,
                                            String destinationPublicKey,
                                            String paymentDescription,
                                            UUID requestId,
                                            String senderPublicKey,
                                            UUID transmissionId) {

        this.associatedCryptoTransactionHash    = associatedCryptoTransactionHash;
        this.cryptoAmount                       = cryptoAmount;
        this.cryptoCurrency                     = cryptoCurrency;
        this.destinationPublicKey               = destinationPublicKey;
        this.paymentDescription                 = paymentDescription;
        this.requestId                          = requestId;
        this.senderPublicKey                    = senderPublicKey;
        this.transmissionId                     = transmissionId;
    }

    @Override
    public UUID getTransmissionId() {
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
}
