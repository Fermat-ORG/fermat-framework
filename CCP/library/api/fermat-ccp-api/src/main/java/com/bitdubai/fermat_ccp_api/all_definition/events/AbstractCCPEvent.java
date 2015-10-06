package com.bitdubai.fermat_ccp_api.all_definition.events;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEventEnum;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;

/**
 * The enum <code>com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.events.EventType</code>
 * haves all the basic functionality of a Fermat Event of the Platform CCP.<p/>
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 22/09/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public abstract class AbstractCCPEvent implements FermatEvent {

    private final FermatEventEnum eventType;

    private EventSource eventSource;

    public AbstractCCPEvent(FermatEventEnum eventType) {
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
