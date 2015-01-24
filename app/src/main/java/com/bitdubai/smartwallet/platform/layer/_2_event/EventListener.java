package com.bitdubai.smartwallet.platform.layer._2_event;

/**
 * Created by ciencias on 24.01.15.
 */
public interface EventListener {

    public Event getEventType();

    public void setEventHandler(EventHandler eventHandler);

    public void getEventHandler();

    public void raiseEvent(PlatformEvent platformEvent);

}
