package com.bitdubai.wallet_platform_core.layer._2_platform_service.event_manager.developer;

import com.bitdubai.wallet_platform_api.Addon;
import com.bitdubai.wallet_platform_api.AddonDeveloper;
import com.bitdubai.wallet_platform_core.layer._2_platform_service.event_manager.developer.bitdubai.version_1.PlatformEventManagerAddonRoot;


/**
 * Created by ciencias on 23.01.15.
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

        addon = new PlatformEventManagerAddonRoot();

    }
}
