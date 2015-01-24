package com.bitdubai.smartwallet.platform.layer._2_event;

/**
 * Created by ciencias on 23.01.15.
 */
public interface EventManager {

public EventListener getNewListener (Event eventType);

public PlatformEvent getNewEvent(Event eventType);

public void registerListener(EventListener listener);

public void raiseEvent(PlatformEvent platformEvent);

}
