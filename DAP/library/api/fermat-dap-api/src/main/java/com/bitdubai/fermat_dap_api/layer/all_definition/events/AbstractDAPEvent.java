package com.bitdubai.fermat_dap_api.layer.all_definition.events;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEventEnum;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;

/**
 * Created by Nerio on 27/10/15.
 */
public class AbstractDAPEvent implements FermatEvent {

    private final FermatEventEnum eventType;

    private EventSource eventSource;

    public AbstractDAPEvent(FermatEventEnum eventType) {
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
