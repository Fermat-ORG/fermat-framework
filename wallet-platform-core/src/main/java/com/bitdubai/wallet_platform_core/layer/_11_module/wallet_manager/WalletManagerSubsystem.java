package com.bitdubai.wallet_platform_core.layer._11_module.wallet_manager;

import com.bitdubai.wallet_platform_api.layer._11_module.CantStartSubsystemException;
import com.bitdubai.wallet_platform_api.PlatformService;
import com.bitdubai.wallet_platform_api.layer._11_module.ModuleSubsystem;

/**
 * Created by ciencias on 30.12.14.
 */
public class WalletManagerSubsystem  implements ModuleSubsystem {

    @Override
    public PlatformService getModule() {
        return null;
    }

    @Override
    public void start() throws CantStartSubsystemException {

    }

}