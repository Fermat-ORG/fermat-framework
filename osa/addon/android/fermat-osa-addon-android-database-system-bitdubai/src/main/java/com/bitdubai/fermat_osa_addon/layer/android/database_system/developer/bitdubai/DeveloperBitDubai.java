package com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai;

import com.bitdubai.fermat_api.Addon;
import com.bitdubai.fermat_api.AddonDeveloper;
import com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.DataBaseSystemAndroidAddonRoot;

/**
 * Created by toshiba on 20/05/2015.
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

        addon = new DataBaseSystemAndroidAddonRoot();


    }
}

