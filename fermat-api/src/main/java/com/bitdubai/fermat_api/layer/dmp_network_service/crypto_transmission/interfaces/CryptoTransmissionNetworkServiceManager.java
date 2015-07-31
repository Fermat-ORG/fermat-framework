package com.bitdubai.fermat_api.layer.dmp_network_service.crypto_transmission.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.dmp_network_service.crypto_transmission.exceptions.CantAcceptCryptoRequestException;
import com.bitdubai.fermat_api.layer.dmp_network_service.crypto_transmission.exceptions.CouldNotTransmitCryptoException;

import java.util.UUID;

/**
 * Created by eze on 2015.07.31..
 */
public interface CryptoTransmissionNetworkServiceManager {

    public void acceptCryptoRequest(UUID requestId,
                                    CryptoCurrency cryptoCurrency,
                                    long cryptoAmount,
                                    String senderPublicKey,
                                    String destinationPublicKey,
                                    String associatedCryptoTransactionHash,
                                    String paymentDescription) throws CantAcceptCryptoRequestException;


    public void sendCrypto(CryptoCurrency cryptoCurrency,
                           long cryptoAmount,
                           String senderPublicKey,
                           String destinationPublicKey,
                           String associatedCryptoTransactionHash,
                           String paymentDescription
    ) throws CouldNotTransmitCryptoException;
}
