package com.bitdubai.platform.layer._5_license.use.developer;

import com.bitdubai.platform.layer._5_license.LicenseDeveloper;
import com.bitdubai.platform.layer._5_license.LicenseManager;
import com.bitdubai.platform.layer._5_license.use.developer.bitdubai.version_1.UseLicenseManager;

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
         * I will choose from the different versions of my implementations which one to run. Now there is only one, so
         * it is easy to choose.
         */

        mLicenseManager = new UseLicenseManager();

    }
}
