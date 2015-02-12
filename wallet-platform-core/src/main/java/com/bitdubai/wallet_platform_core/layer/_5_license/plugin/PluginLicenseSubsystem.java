package com.bitdubai.wallet_platform_core.layer._5_license.plugin;

import com.bitdubai.wallet_platform_api.Addon;
import com.bitdubai.wallet_platform_api.layer._5_license.CantStartSubsystemException;
import com.bitdubai.wallet_platform_api.layer._5_license.LicenseSubsystem;
import com.bitdubai.wallet_platform_core.layer._5_license.plugin.developer.DeveloperBitDubai;


/**
 * Created by ciencias on 21.01.15.
 */
public class PluginLicenseSubsystem implements LicenseSubsystem {

    private Addon addon;

    @Override
    public Addon getAddon() {
        return addon;
    }

    @Override
    public void start() throws CantStartSubsystemException {
        /**
         * I will choose from the different versions available of this functionality.
         */

        try {
            DeveloperBitDubai developerBitDubai = new DeveloperBitDubai();
            addon = developerBitDubai.getAddon();
        }
        catch (Exception e)
        {
            System.err.println("Exception: " + e.getMessage());
            throw new CantStartSubsystemException();
        }
    }


}
