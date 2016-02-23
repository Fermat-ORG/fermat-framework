package com.bitdubai.fermat_ccp_api.layer.network_service.crypto_transmission.interfaces.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_transmission.enums.CryptoTransmissionMetadataState;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_transmission.enums.CryptoTransmissionProtocolState;

import java.util.UUID;

/**
 * Created by mati on 2015.10.03..
 */
public interface CryptoTransmissionMetadata {

    UUID getTransactionId();

    UUID getRequestId();

    CryptoCurrency getCryptoCurrency();

    long getCryptoAmount();

    String getSenderPublicKey();

    String getDestinationPublicKey();

    String getAssociatedCryptoTransactionHash();

    String getPaymentDescription();

    CryptoTransmissionMetadataType getCryptoTransmissionMetadataType();

    void setTypeMetadata(CryptoTransmissionMetadataType cryptoTransmissionMetadataType);

    CryptoTransmissionProtocolState getCryptoTransmissionProtocolState();

    void changeCryptoTransmissionProtocolState(CryptoTransmissionProtocolState cryptoTransmissionProtocolState);

    void changeMetadataState(CryptoTransmissionMetadataState cryptoTransmissionNotificationStates);

    CryptoTransmissionMetadataState getCryptoTransmissionMetadataStates();

    boolean isPendigToRead();

    void confirmRead();

    long getTimestamp();

    int getSentCount();

    void setPendingToRead(boolean pending);

}
