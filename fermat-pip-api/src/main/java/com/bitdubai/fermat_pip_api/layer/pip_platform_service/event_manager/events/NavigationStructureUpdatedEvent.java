package com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.events;

import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.PlatformEvent;
import com.bitdubai.fermat_api.layer.all_definition.event.EventSource;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.enums.EventType;

/**
 * Created by loui on 05/02/15.
 */
public class NavigationStructureUpdatedEvent extends AbstractPlatformEvent {


    public NavigationStructureUpdatedEvent (EventType eventType){
        super(eventType);
    }
}
