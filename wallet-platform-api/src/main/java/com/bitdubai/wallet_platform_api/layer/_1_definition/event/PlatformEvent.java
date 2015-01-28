package com.bitdubai.wallet_platform_api.layer._1_definition.event;

import com.bitdubai.wallet_platform_api.layer._2_event.manager.EventSource;
import com.bitdubai.wallet_platform_api.layer._2_event.manager.EventType;

/**
 * Created by ciencias on 24.01.15.
 */
public interface PlatformEvent {


    public EventType getEventType();

    public void setSource(EventSource eventSource);

    public EventSource getSource();

}
