package com.bitdubai.wallet_platform_api.layer._12_module;

import com.bitdubai.wallet_platform_api.Plugin;


/**
 * Created by ciencias on 21.01.15.
 */
public interface ModuleSubsystem {
    public void start () throws CantStartSubsystemException;
    public Plugin getPlugin();
}
