package com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.events;

import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.enums.EventType;

/**
 * Created by mati on 2016.02.19..
 */
public class GenericEvent extends AbstractFermatEvent {


    public GenericEvent(EventType eventType) {
        super(eventType);
    }
}
