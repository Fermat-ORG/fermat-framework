package com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces;

import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.enums.EventType;

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
