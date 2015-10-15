package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events;

import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.P2pEventType;


/**
 * Created by rodrigo on 2015.07.08..
 */
public abstract class AbstractP2PFermatEvent implements FermatEvent {

    private final P2pEventType p2pEventType;

    private EventSource eventSource;

    public AbstractP2PFermatEvent(P2pEventType p2pEventType) {
        this.p2pEventType = p2pEventType;
    }

    @Override
    public P2pEventType getEventType() {
        return this.p2pEventType;
    }

    @Override
    public void setSource(EventSource eventSource) {
        this.eventSource = eventSource;
    }

    @Override
    public EventSource getSource() {
        return this.eventSource;
    }
}
