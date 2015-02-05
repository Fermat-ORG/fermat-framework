package com.bitdubai.wallet_platform_api.layer._2_platform_service;

import com.bitdubai.wallet_platform_api.Service;
import com.bitdubai.wallet_platform_api.layer._2_platform_service.CantStartSubsystemException;
import com.bitdubai.wallet_platform_api.layer._2_platform_service.event_manager.EventManager;

/**
 * Created by ciencias on 23.01.15.
 */
public interface PlatformServiceSubsystem {
    public void start () throws CantStartSubsystemException;
    public Service getService();
}


