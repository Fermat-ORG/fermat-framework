package com.bitdubai.smartwallet.platform.layer._2_event.manager;

import com.bitdubai.smartwallet.platform.layer._1_definition.event.PlatformEvent;

/**
 * Created by ciencias on 24.01.15.
 */
public interface EventListener {

    public EventType getEventType();

    public void setEventHandler(EventHandler eventHandler);

    public void getEventHandler();

    public void raiseEvent(PlatformEvent platformEvent);

}
