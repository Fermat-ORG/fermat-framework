package com.bitdubai.wallet_platform_api.layer._6_world;

import com.bitdubai.wallet_platform_api.Plugin;
import com.bitdubai.wallet_platform_api.layer._13_module.CantStartSubsystemException;

/**
 * Created by ciencias on 2/6/15.
 */
public interface WorldSubsystem {
    public void start() throws CantStartSubsystemException;
    public Plugin getPlugin();
}
