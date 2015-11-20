package com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.interfaces;

import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.enums.ConnectionRequestAction;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.enums.RequestType;

/**
 * The interface <code>com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.interfaces.CryptoBrokerConnectionNew</code>
 * represents a crypto broker connection new, it can be a connection request or a disconnection.
 * <p>
 * Created by lnacosta (laion.cj91@gmail.com) on 17/11/2015.
 */
public interface CryptoBrokerConnectionNew {

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

    /**
     * @return ain element of RequestType indicating the type of the crypto broker connection request.
     */
    RequestType getType();

    /**
     * @return an element of ConnectionRequestAction enum indicating the action that must to be done.
     */
    ConnectionRequestAction getAction();

}
