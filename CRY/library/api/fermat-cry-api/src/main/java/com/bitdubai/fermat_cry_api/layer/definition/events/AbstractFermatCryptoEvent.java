package com.bitdubai.fermat_cry_api.layer.definition.events;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEventEnum;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;

/**
 * The enum <code>com.bitdubai.fermat_cry_api.layer.definition.events.AbstractFermatCryptoEvent</code>
 * haves all the basic functionality of a Fermat Crypto Event.<p/>
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 18/09/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public abstract class AbstractFermatCryptoEvent implements FermatEvent {

    private final FermatEventEnum eventType;

    private EventSource eventSource;

    public AbstractFermatCryptoEvent(FermatEventEnum eventType) {
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
