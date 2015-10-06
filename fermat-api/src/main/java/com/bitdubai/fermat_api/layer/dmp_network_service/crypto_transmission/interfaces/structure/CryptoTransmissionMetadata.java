package com.bitdubai.fermat_api.layer.dmp_network_service.crypto_transmission.interfaces.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;

import java.util.UUID;

/**
 * Created by mati on 2015.10.03..
 */
public interface CryptoTransmissionMetadata {

    public UUID getTransmissionId();

    public UUID getRequestId();

    public CryptoCurrency getCryptoCurrency();

    public long getCryptoAmount();

    public String getSenderPublicKey();

    public String getDestinationPublicKey();

    public String getAssociatedCryptoTransactionHash();

    public String getPaymentDescription();
}
