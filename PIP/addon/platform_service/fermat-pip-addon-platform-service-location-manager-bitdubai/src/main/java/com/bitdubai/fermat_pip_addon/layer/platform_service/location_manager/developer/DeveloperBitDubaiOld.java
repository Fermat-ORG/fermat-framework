package com.bitdubai.fermat_pip_addon.layer.platform_service.location_manager.developer;

import com.bitdubai.fermat_api.Addon;
import com.bitdubai.fermat_api.AddonDeveloper;
import com.bitdubai.fermat_pip_addon.layer.platform_service.location_manager.developer.bitdubai.LocationSubsystemPlatformServicePluginRootOld;

/**
 * Created by loui on 28/04/15.
 */
public class DeveloperBitDubaiOld implements AddonDeveloper {

    Addon addon;





    @Override
    public Addon getAddon() {
        return addon;
    }





    public DeveloperBitDubaiOld() {

        /**
         * I will choose from the different versions of my implementations which one to start. Now there is only one, so
         * it is easy to choose.
         */

        addon = new LocationSubsystemPlatformServicePluginRootOld();

    }
}
