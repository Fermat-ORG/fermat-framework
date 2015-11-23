package com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.enums.ConnectionRequestAction;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.enums.RequestType;

import java.util.UUID;

/**
 * The interface <code>com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.interfaces.CryptoBrokerConnectionNew</code>
 * represents a crypto broker connection new, it can be a connection request or a disconnection.
 * <p>
 * Created by lnacosta (laion.cj91@gmail.com) on 17/11/2015.
 */
public interface CryptoBrokerConnectionNew {

    /**
     * @return an uuid representing the request id.
     */
    UUID getRequestId();

    /**
     * @return a string representing the sender public key.
     */
    String getSenderPublicKey();

    /**
     * @return an element of actors enum representing the actor type of the sender.
     */
    Actors getSenderActorType();

    /**
     * @return a string representing the alias of the crypto broker.
     */
    String getSenderAlias();

    /**
     * @return an array of bytes with the image exposed by the Crypto Broker.
     */
    byte[] getSenderImage();

    /**
     * @return a string representing the destination public key.
     */
    String getDestinationPublicKey();

    /**
     * @return an element of RequestType indicating the type of the crypto broker connection request.
     */
    RequestType getRequestType();

    /**
     * @return an element of ConnectionRequestAction enum indicating the action that must to be done.
     */
    ConnectionRequestAction getRequestAction();

    /**
     * @return the time when the action was performed.
     */
    long getSentTime();

}
