package com.bitdubai.fermat_cbp_api.all_definition.events;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEventEnum;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;

/**
 * Created by jorgegonzalez on 23-09-2015.
 */
public abstract class CBPEvent implements FermatEvent {

    private final FermatEventEnum eventType;
    private EventSource eventSource;

    public CBPEvent(final FermatEventEnum eventType){
        this.eventType = eventType;
    }

    @Override
    public FermatEventEnum getEventType() {
        return eventType;
    }

    @Override
    public void setSource(final EventSource eventSource) {
        this.eventSource = eventSource;
    }

    @Override
    public EventSource getSource() {
        return eventSource;
    }
}
