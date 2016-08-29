package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.events;

import com.bitdubai.fermat_api.layer.all_definition.events.common.AbstractEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.P2pEventType;

import java.util.UUID;

/**
 * The class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.events.NetworkClientNewMessageTransmitEvent</code>
 * is the representation of a network client registered event <code>P2pEventType.NETWORK_CLIENT_NEW_MESSAGE_TRANSMIT</code>.
 * The event indicates that one message is received.
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 17/05/2016.
 *
 * @author  lnacosta
 * @version 1.0
 * @since   Java JDK 1.7
 */
public class NetworkClientNewMessageTransmitEvent extends AbstractEvent<P2pEventType> {

    /**
     * Represent the content value
     */
    private String content;

    private UUID packageId;

    /**
     * Constructor with parameters
     *
     * @param p2pEventType
     */
    public NetworkClientNewMessageTransmitEvent(P2pEventType p2pEventType) {
        super(p2pEventType);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UUID getPackageId() {
        return packageId;
    }

    public void setPackageId(UUID packageId) {
        this.packageId = packageId;
    }

    @Override
    public String toString() {
        return "NetworkClientNewMessageTransmitEvent{" +
                "content='" + content + '\'' +
                '}';
    }
}
