package com.bitdubai.fermat_pip_addon.layer.hardware.local_device.developer.bitdubai;

import com.bitdubai.fermat_api.Addon;
import com.bitdubai.fermat_api.AddonDeveloper;
import com.bitdubai.fermat_pip_addon.layer.hardware.local_device.developer.bitdubai.version_1.LocalDeviceHardwareAddonRoot;

/**
 * Created by loui on 05/03/15.
 */
public class DeveloperBitDubai implements AddonDeveloper {

    Addon addon;


    @Override
    public Addon getAddon() {
        return addon;
    }


    public DeveloperBitDubai() {

        /**
         * I will choose from the different versions of my implementations which one to start. Now there is only one, so
         * it is easy to choose.
         */

        addon = new LocalDeviceHardwareAddonRoot();

    }

}
