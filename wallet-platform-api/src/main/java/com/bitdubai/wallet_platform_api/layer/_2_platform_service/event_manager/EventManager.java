package com.bitdubai.wallet_platform_api.layer._2_platform_service.event_manager;

import com.bitdubai.wallet_platform_api.layer._2_platform_service.event_manager.EventType;
import com.bitdubai.wallet_platform_api.layer._2_platform_service.event_manager.EventListener;
import com.bitdubai.wallet_platform_api.layer._1_definition.event.PlatformEvent;

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
