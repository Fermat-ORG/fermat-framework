package com.bitdubai.android_fermat_pip_addon.layer_2_os.location_system;

import com.bitdubai.fermat_api.Addon;
import com.bitdubai.fermat_api.AddonDeveloper;
import com.bitdubai.android_fermat_pip_addon.layer_2_os.location_system.version_1.DeviceLocationOsAddonRoot;

/**
 * Created by loui on 29/04/15.
 */
public class DeveloperBitDubai implements AddonDeveloper {

    Addon addon;





    @Override
    public Addon getAddon(){
        return addon;
    }





    public DeveloperBitDubai(){
        /**
         * I will choose from the different versions of my implementations which one to start. Now there is only one, so
         * it is easy to choose.
         */

        addon = new DeviceLocationOsAddonRoot();


    }

}