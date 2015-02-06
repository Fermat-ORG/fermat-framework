package com.bitdubai.wallet_platform_core.layer._5_license.plugin.developer;

import com.bitdubai.wallet_platform_api.layer._5_license.LicenseDeveloper;
import com.bitdubai.wallet_platform_api.layer._5_license.LicenseManager;
import com.bitdubai.wallet_platform_core.layer._5_license.plugin.developer.bitdubai.version_1.PluginLicenseManager;

/**
 * Created by ciencias on 21.01.15.
 */
public class DeveloperBitDubai implements LicenseDeveloper {

    LicenseManager mLicenseManager;

    @Override
    public LicenseManager getLicenseManager() {
        return mLicenseManager;
    }

    public DeveloperBitDubai () {

        /**
         * I will choose from the different versions of my implementations which one to start. Now there is only one, so
         * it is easy to choose.
         */

        mLicenseManager = new PluginLicenseManager();

    }
}
