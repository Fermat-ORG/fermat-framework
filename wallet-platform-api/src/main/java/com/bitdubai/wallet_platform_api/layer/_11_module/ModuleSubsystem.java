package com.bitdubai.wallet_platform_api.layer._11_module;

import com.bitdubai.wallet_platform_api.PlatformService;

/**
 * Created by ciencias on 21.01.15.
 */
public interface ModuleSubsystem {
    public void start () throws CantStartSubsystemException;
    public PlatformService getModule();
}
