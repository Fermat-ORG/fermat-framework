package com.bitdubai.fermat_api.layer.pip_platform_service.event_manager;

import com.bitdubai.fermat_api.layer.all_definition.event.PlatformEvent;

/**
 * Created by ciencias on 23.01.15.
 */
public interface EventManager {

public EventListener getNewListener (EventType eventType);

public PlatformEvent getNewEvent(EventType eventType);

public void addListener(EventListener listener);
    
public void removeListener(EventListener listener);

public void raiseEvent(PlatformEvent platformEvent);

}
