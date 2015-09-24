package com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.events;

import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.enums.EventType;

/**
 * Created by natalia on 01/09/15.
 */
public class IntraUserActorConnectionAcceptedNotificactionEvent extends AbstractFermatEvent {


    public IntraUserActorConnectionAcceptedNotificactionEvent(EventType eventType){
        super(eventType);
    }


}
