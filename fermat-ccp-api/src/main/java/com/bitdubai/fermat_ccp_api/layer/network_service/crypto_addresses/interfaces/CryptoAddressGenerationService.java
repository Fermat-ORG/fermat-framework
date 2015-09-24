package com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.interfaces;

import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.exceptions.CantHandleCryptoAddressRequestEventException;

import java.util.UUID;

/**
 * The interface <code>com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.interfaces.CryptoAddressGenerationService</code>
 * is intended to contain all the necessary business logic to generate and deliver address requested to an specific type of Actor.
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 23/09/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public interface CryptoAddressGenerationService {

    void handleCryptoAddressRequestedEvent(UUID requestId) throws CantHandleCryptoAddressRequestEventException;

}
