package com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.fan.util;

import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.ExposingData;

import java.util.Arrays;

/**
 * The interface <code>FanExposingData</code>
 * represents a crypto broker and exposes all the functionality of it.
 * <p>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 17/11/2015.
 */
public final class FanExposingData implements ExposingData {

    private final String publicKey;
    private final String alias    ;
    private final byte[] image    ;

    public FanExposingData(final String publicKey,
                           final String alias,
                           final byte[] image) {

        this.publicKey = publicKey;
        this.alias     = alias    ;
        this.image     = image    ;
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

    @Override
    public String toString() {
        return "FanExposingData{" +
                "publicKey='" + publicKey + '\'' +
                ", alias='" + alias + '\'' +
                ", image=" + Arrays.toString(image) +
                '}';
    }

}
