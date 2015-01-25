package com.bitdubai.smartwallet.platform.layer._2_event;

import com.bitdubai.smartwallet.platform.layer._2_event.manager.EventType;
import com.bitdubai.smartwallet.platform.layer._2_event.manager.EventListener;
import com.bitdubai.smartwallet.platform.layer._2_event.manager.PlatformEvent;

/**
 * Created by ciencias on 23.01.15.
 */
public interface EventManager {

public EventListener getNewListener (EventType eventType);

public PlatformEvent getNewEvent(EventType eventType);

public void registerListener(EventListener listener);

public void raiseEvent(PlatformEvent platformEvent);

}
