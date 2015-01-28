package com.bitdubai.wallet_platform_api.layer._2_event;

/**
 * Created by ciencias on 23.01.15.
 */
public interface EventSubsystem {
    public void start () throws CantStartSubsystemException;
    public EventManager getEventManager();
}
