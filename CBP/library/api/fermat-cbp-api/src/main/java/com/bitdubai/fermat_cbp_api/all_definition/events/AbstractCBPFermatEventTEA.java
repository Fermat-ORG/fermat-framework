package com.bitdubai.fermat_cbp_api.all_definition.events;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEventEnum;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;

/**
 * Created by yordin on 16/01/16.
 */
public class AbstractCBPFermatEventTEA implements FermatEvent {

    private final FermatEventEnum eventType;

    private EventSource eventSource;

    public AbstractCBPFermatEventTEA(FermatEventEnum eventType) {
        this.eventType = eventType;
    }

    @Override
    public FermatEventEnum getEventType() {
        return eventType;
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
