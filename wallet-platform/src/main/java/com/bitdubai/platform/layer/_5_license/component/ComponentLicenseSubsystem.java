package com.bitdubai.platform.layer._5_license.component;

import com.bitdubai.platform.layer._5_license.CantStartSubsystemException;
import com.bitdubai.platform.layer._5_license.LicenseManager;
import com.bitdubai.platform.layer._5_license.LicenseSubsystem;
import com.bitdubai.platform.layer._5_license.component.developer.DeveloperBitDubai;


/**
 * Created by ciencias on 21.01.15.
 */
public class ComponentLicenseSubsystem implements LicenseSubsystem {

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
