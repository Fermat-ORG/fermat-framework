package com.bitdubai.fermat_api.layer.all_definition.events.common;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEventEnum;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;

/**
 * The abstract class <code>com.bitdubai.fermat_api.layer.all_definition.events.common.AbstractEvent</code>
 * contains all the basic functionality of a Fermat Event.<p/>
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 17/11/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public abstract class AbstractEvent implements FermatEvent {

    private final FermatEventEnum eventType;

    private EventSource eventSource;

    public AbstractEvent(final FermatEventEnum eventType) {
        this.eventType = eventType;
    }

    @Override
    public final FermatEventEnum getEventType() {
        return eventType;
    }

    @Override
    public final void setSource(EventSource eventSource) {
        this.eventSource = eventSource;
    }

    @Override
    public final EventSource getSource() {
        return this.eventSource;
    }

}
