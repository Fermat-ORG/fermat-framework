package com.bitdubai.smartwallet.platform.layer._2_event.manager;

/**
 * Created by ciencias on 24.01.15.
 */
public interface PlatformEvent {


    public EventType getEventType();

    public void setSource(EventSource eventSource);

    public EventSource getSource();

}
