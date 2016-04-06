package com.bitdubai.fermat_pip_api.all_definition.event_manager.events;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEventEnum;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;

/**
 * Created by rodrigo on 4/4/16.
 */
public class AbstractFermatPipEvent implements FermatEvent {
    private final FermatEventEnum eventType;

    private EventSource eventSource;

    public AbstractFermatPipEvent(FermatEventEnum eventType) {
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
}
