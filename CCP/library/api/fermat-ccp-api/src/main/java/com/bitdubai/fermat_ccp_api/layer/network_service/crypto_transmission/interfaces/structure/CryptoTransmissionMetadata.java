package com.bitdubai.fermat_ccp_api.layer.network_service.crypto_transmission.interfaces.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_transmission.enums.CryptoTransmissionStates;

import java.util.UUID;

/**
 * Created by mati on 2015.10.03..
 */
public interface CryptoTransmissionMetadata {

    public UUID getTransactionId();

    public UUID getRequestId();

    public CryptoCurrency getCryptoCurrency();

    public long getCryptoAmount();

    public String getSenderPublicKey();

    public String getDestinationPublicKey();

    public String getAssociatedCryptoTransactionHash();

    public String getPaymentDescription();

    public CryptoTransmissionStates getCryptoTransmissionStates();

    public CryptoTransmissionMetadataType getCryptoTransmissionMetadataType();

    public void changeState(CryptoTransmissionStates cryptoTransmissionStates);

    public void setTypeMetadata(CryptoTransmissionMetadataType cryptoTransmissionMetadataType);

    public boolean isPendigToRead();

    public void confirmRead();

    public long getTimestamp();


}
