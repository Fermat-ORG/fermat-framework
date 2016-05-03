package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.events;

import com.bitdubai.fermat_api.layer.all_definition.events.common.AbstractEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.Profile;
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
public class NetworkClientProfileRegisteredEvent<T extends Profile> extends AbstractEvent {

    /**
     * Represent the registered profile.
     */
    private T profile;

    /**
     * Constructor with parameters
     *
     * @param p2pEventType
     */
    public NetworkClientProfileRegisteredEvent(P2pEventType p2pEventType) {
        super(p2pEventType);
    }

    public T getProfile() {
        return profile;
    }

    public void setProfile(T profile) {
        this.profile = profile;
    }

    @Override
    public String toString() {
        return "NetworkClientProfileRegisteredEvent{" +
                "profile=" + profile +
                '}';
    }
}
