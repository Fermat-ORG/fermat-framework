package com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.events;

import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.enums.EventType;

/**
 * Created by loui on 05/02/15.
 */
public class NavigationStructureUpdatedEvent extends AbstractFermatEvent {


    public NavigationStructureUpdatedEvent (EventType eventType){
        super(eventType);
    }
}
