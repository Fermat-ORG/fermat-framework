package com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.events;

import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.enums.EventType;

/**
 * Created by José Vilchez on 28/01/16.
 */
public class ReviewNegotiationEvent extends AbstractFermatEvent {
    public ReviewNegotiationEvent(EventType eventType) {
        super(eventType);
    }
}
