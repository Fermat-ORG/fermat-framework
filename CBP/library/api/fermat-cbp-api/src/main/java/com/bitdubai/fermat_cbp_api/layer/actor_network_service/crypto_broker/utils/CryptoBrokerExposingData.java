package com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.utils;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;

import java.util.Arrays;

/**
 * The interface <code>com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.utils.CryptoBrokerExposingData</code>
 * represents a crypto broker and exposes all the functionality of it.
 * <p>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 17/11/2015.
 */
public final class CryptoBrokerExposingData {

    private final String publicKey;
    private final String alias    ;
    private final byte[] image    ;
    private final Location location;

    public CryptoBrokerExposingData(final String publicKey,
                                    final String alias    ,
                                    final byte[] image    ,
                                    final Location location) {

        this.publicKey = publicKey;
        this.alias     = alias    ;
        this.image     = image    ;
        this.location  = location;
    }

    /**
     * @return a string representing the public key.
     */
    public final String getPublicKey() {
        return publicKey;
    }

    /**
     * @return a string representing the alias of the crypto broker.
     */
    public final String getAlias() {
        return alias;
    }

    /**
     * @return an array of bytes with the image exposed by the Crypto Broker.
     */
    public final byte[] getImage() {
        return image;
    }

    /**
     * @return an locarion  with the image exposed by the Crypto Broker.
     */
    public final Location getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return "CryptoBrokerExposingData{" +
                "publicKey='" + publicKey + '\'' +
                ", alias='" + alias + '\'' +
                ", image=" + Arrays.toString(image) +
                '}';
    }

}
