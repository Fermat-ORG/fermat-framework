package com.bitdubai.smartwallet.platform.layer._2_event;

/**
 * Created by ciencias on 24.01.15.
 */
public interface PlatformEvent {


    public Event getEventType();

    public void setSource(EventSource eventSource);

    public EventSource getSource();

}
