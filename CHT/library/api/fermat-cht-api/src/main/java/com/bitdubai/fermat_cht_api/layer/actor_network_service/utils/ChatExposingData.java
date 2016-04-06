package com.bitdubai.fermat_cht_api.layer.actor_network_service.utils;

import java.util.Arrays;

/**
 * Created by José D. Vilchez A. (josvilchezalmera@gmail.com) on 05/04/16.
 */
public class ChatExposingData {

    private final String publicKey;
    private final String alias    ;
    private final byte[] image    ;

    public ChatExposingData(final String publicKey,
                                    final String alias    ,
                                    final byte[] image    ) {

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
        return "CryptoBrokerExposingData{" +
                "publicKey='" + publicKey + '\'' +
                ", alias='" + alias + '\'' +
                ", image=" + Arrays.toString(image) +
                '}';
    }
}
