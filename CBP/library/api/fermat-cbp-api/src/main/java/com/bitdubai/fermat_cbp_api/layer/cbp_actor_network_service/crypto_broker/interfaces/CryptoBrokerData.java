package com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.interfaces;

/**
 * The interface <code>com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.interfaces.CryptoBrokerData</code>
 * represents a crypto broker and exposes all the functionality of it.
 * <p>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 17/11/2015.
 */
public interface CryptoBrokerData {

    /**
     * @return a string representing the public key.
     */
    String getPublicKey();

    /**
     * @return a string representing the alias of the crypto broker.
     */
    String getAlias();

    /**
     * @return an array of bytes with the image exposed by the Crypto Broker.
     */
    byte[] getImage();

}
