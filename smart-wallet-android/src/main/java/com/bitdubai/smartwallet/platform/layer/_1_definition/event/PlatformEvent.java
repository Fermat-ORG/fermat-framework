package com.bitdubai.smartwallet.platform.layer._1_definition.event;

import com.bitdubai.smartwallet.platform.layer._2_event.manager.EventSource;
import com.bitdubai.smartwallet.platform.layer._2_event.manager.EventType;

/**
 * Created by ciencias on 24.01.15.
 */
public interface PlatformEvent {


    public EventType getEventType();

    public void setSource(EventSource eventSource);

    public EventSource getSource();

}
