package com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.events;

import com.bitdubai.fermat_api.layer.all_definition.event.EventSource;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.enums.EventType;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.PlatformEvent;

/**
 * Created by natalia on 01/09/15.
 */
public class IntraUserActorConnectionAcceptedNotificactionEvent extends AbstractPlatformEvent {


    public IntraUserActorConnectionAcceptedNotificactionEvent(EventType eventType){
        super(eventType);
    }


}
