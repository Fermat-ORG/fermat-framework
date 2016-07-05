package com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.utils;

import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.common.AbstractCBPActorExposingData;

/**
 * The interface <code>com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.utils.CryptoBrokerExposingData</code>
 * represents a crypto broker and exposes all the functionality of it.
 * <p>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 17/11/2015.
 */
public final class CryptoBrokerExposingData extends AbstractCBPActorExposingData {

    /**
     * Default constructor with parameters
     * @param publicKey
     * @param alias
     * @param image
     * @param location
     * @param refreshInterval
     * @param accuracy
     */
    public CryptoBrokerExposingData(
            String publicKey,
            String alias,
            byte[] image,
            Location location,
            long refreshInterval,
            long accuracy) {
        super(
                publicKey,
                alias,
                image,
                location,
                refreshInterval,
                accuracy);
    }

    /**
     * This class only extends AbstractCBPActorExposingData, it can be changed in future versions
     */

}
