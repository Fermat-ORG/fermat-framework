package com.bitdubai.wallet_platform_plugin.layer._11_module.wallet_manager.developer.bitdubai;

import com.bitdubai.wallet_platform_api.PlatformService;
import com.bitdubai.wallet_platform_api.layer._11_module.ModuleDeveloper;
import com.bitdubai.wallet_platform_plugin.layer._11_module.wallet_manager.developer.bitdubai.version_1.WalletManagerModule;


/**
 * Created by ciencias on 21.01.15.
 */
public class DeveloperBitDubai  implements ModuleDeveloper {

    PlatformService mModule;

    @Override
    public PlatformService getModule() {
        return mModule;
    }

    public DeveloperBitDubai() {

        /**
         * I will choose from the different versions of my implementations which one to start. Now there is only one, so
         * it is easy to choose.
         */

        mModule = new WalletManagerModule();

    }
}
