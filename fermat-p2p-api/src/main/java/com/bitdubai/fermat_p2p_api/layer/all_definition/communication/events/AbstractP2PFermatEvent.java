package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events;

import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.EventType;


/**
 * Created by rodrigo on 2015.07.08..
 */
public abstract class AbstractP2PFermatEvent implements FermatEvent {

    private final EventType eventType;

    private EventSource eventSource;

    public AbstractP2PFermatEvent(EventType eventType) {
        this.eventType = eventType;
    }

    @Override
    public EventType getEventType() {
        return this.eventType;
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
