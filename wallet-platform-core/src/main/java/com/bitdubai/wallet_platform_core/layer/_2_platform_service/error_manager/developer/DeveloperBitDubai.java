package com.bitdubai.wallet_platform_core.layer._2_platform_service.error_manager.developer;

import com.bitdubai.wallet_platform_api.Addon;
import com.bitdubai.wallet_platform_api.AddonDeveloper;
import com.bitdubai.wallet_platform_core.layer._2_platform_service.error_manager.developer.version_1.PlatformErrorManagerAddonRoot;

/**
 * Created by ciencias on 05.02.15.
 */
public class DeveloperBitDubai implements AddonDeveloper {

    Addon addon;

    @Override
    public Addon getAddon() {
        return addon;
    }


    public DeveloperBitDubai () {

        /**
         * I will choose from the different versions of my implementations which one to start. Now there is only one, so
         * it is easy to choose.
         */

        addon = new PlatformErrorManagerAddonRoot();

    }
}
