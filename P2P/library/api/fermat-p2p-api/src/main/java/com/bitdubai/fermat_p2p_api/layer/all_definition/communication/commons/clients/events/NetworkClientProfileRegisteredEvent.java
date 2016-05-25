package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.events;

import com.bitdubai.fermat_api.layer.all_definition.events.common.AbstractEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.P2pEventType;

/**
 * The class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.events.NetworkClientProfileRegisteredEvent</code>
 * is the representation of a network client registered profile event.
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 02/05/2016.
 *
 * @author  lnacosta
 * @version 1.0
 * @since   Java JDK 1.7
 */
public class NetworkClientProfileRegisteredEvent extends AbstractEvent<P2pEventType> {

    /**
     * Represent the registered profile.
     */
    private String publicKey;

    /**
     * Constructor with parameters
     *
     * @param p2pEventType
     */
    public NetworkClientProfileRegisteredEvent(P2pEventType p2pEventType) {
        super(p2pEventType);
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    @Override
    public String toString() {
        return "NetworkClientProfileRegisteredEvent{" +
                "publicKey='" + publicKey + '\'' +
                '}';
    }
}
