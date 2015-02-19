package com.bitdubai.wallet_platform_api.layer._11_network_service;

import com.bitdubai.wallet_platform_api.Plugin;

/**
 * Created by ciencias on 20.01.15.
 */
public interface NetworkSubsystem {
    public void start () throws CantStartSubsystemException;
    public Plugin getPlugin();
}
