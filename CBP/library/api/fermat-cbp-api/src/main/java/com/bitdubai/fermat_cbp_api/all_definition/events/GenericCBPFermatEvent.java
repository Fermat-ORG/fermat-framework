package com.bitdubai.fermat_cbp_api.all_definition.events;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEventEnum;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_cbp_api.all_definition.events.enums.EventType;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 25/11/15.
 */
public class GenericCBPFermatEvent implements FermatEvent {

    private EventType eventType;

    private EventSource eventSource;

    public GenericCBPFermatEvent(EventType eventType) {
        this.eventType = eventType;
    }

    @Override
    public FermatEventEnum getEventType() {
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

    @Override
    public String toString() {
        return new StringBuilder()
                .append("GenericCBPFermatEvent{")
                .append("eventType=").append(eventType)
                .append(", eventSource=").append(eventSource)
                .append('}').toString();
    }
}
