package com.bitdubai.wallet_platform_api.layer._2_platform_service;

import com.bitdubai.wallet_platform_api.Addon;

/**
 * Created by ciencias on 23.01.15.
 */
public interface PlatformServiceSubsystem {
    public void start () throws CantStartSubsystemException;
    public Addon getAddon();
}


