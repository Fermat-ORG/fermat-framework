package com.bitdubai.android_fermat_pip_addon_layer_2_os_file_system;

import com.bitdubai.android_fermat_pip_addon_layer_2_os_file_system.version_1.FileSystemOsAddonRoot;
import com.bitdubai.fermat_api.Addon;
import com.bitdubai.fermat_api.AddonDeveloper;

/**
 * Created by Natalia on 18/05/2015.
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

        addon = new FileSystemOsAddonRoot();


    }

}