package com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.events;

import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;

/**
 * Created by natalia on 01/09/15.
 */
public class IntraUserActorConnectionRequestRecivedNotificactionEvent extends AbstractFermatEvent {
    private com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.enums.EventType eventType;
    private EventSource eventSource;

    public IntraUserActorConnectionRequestRecivedNotificactionEvent(com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.enums.EventType eventType){
        super(eventType);
    }



}
