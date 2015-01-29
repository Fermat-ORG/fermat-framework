package com.bitdubai.wallet_platform_core.layer._5_license.use;

import com.bitdubai.wallet_platform_api.layer._5_license.CantStartSubsystemException;
import com.bitdubai.wallet_platform_api.layer._5_license.LicenseManager;
import com.bitdubai.wallet_platform_api.layer._5_license.LicenseSubsystem;
import com.bitdubai.wallet_platform_core.layer._5_license.use.developer.DeveloperBitDubai;

/**
 * Created by ciencias on 21.01.15.
 */
public class UseLicenseSubsystem implements LicenseSubsystem {

    private LicenseManager mLicenseManager;

    @Override
    public LicenseManager getLicenseManager() {
        return mLicenseManager;
    }

    @Override
    public void start() throws CantStartSubsystemException {
        /**
         * I will choose from the different versions available of this functionality.
         */

        try {
            DeveloperBitDubai developerBitDubai = new DeveloperBitDubai();
            mLicenseManager = developerBitDubai.getLicenseManager();
        }
        catch (Exception e)
        {
            System.err.println("Exception: " + e.getMessage());
            throw new CantStartSubsystemException();
        }
    }


}
