package com.bitdubai.wallet_platform_core.layer._5_license;

import com.bitdubai.wallet_platform_api.layer.CantStartLayerException;
import com.bitdubai.wallet_platform_api.layer.PlatformLayer;
import com.bitdubai.wallet_platform_api.layer._5_license.CantStartSubsystemException;
import com.bitdubai.wallet_platform_api.layer._5_license.LicenseManager;
import com.bitdubai.wallet_platform_api.layer._5_license.LicenseSubsystem;
import com.bitdubai.wallet_platform_core.layer._5_license.component.ComponentLicenseSubsystem;
import com.bitdubai.wallet_platform_core.layer._5_license.use.UseLicenseSubsystem;

/**
 * Created by ciencias on 30.12.14.
 */
public class LicenseLayer implements PlatformLayer {

    LicenseManager mComponentLicenseManager;
    LicenseManager mUseLicenseManager;

    public LicenseManager getComponentLicenseManager() {
        return mComponentLicenseManager;
    }

    public LicenseManager getUseLicenseManager() {
        return mUseLicenseManager;
    }

    @Override
    public void start() throws CantStartLayerException {

        /**
         * Let's start the Component License Manager;
         */
        LicenseSubsystem componentLicenseSubsystem = new ComponentLicenseSubsystem();

        try {
            componentLicenseSubsystem.start();
            mComponentLicenseManager = ((LicenseSubsystem) componentLicenseSubsystem).getLicenseManager();

        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartSubsystemException: " + e.getMessage());

            /**
             * The com.bitdubai.platform cannot start without performing licensing operations, that's why I throw this exception.
             */
            throw new CantStartLayerException();
        }

        /**
         * Let's start the Component License Manager;
         */
        LicenseSubsystem useLicenseSubsystem = new UseLicenseSubsystem();

        try {
            useLicenseSubsystem.start();
            mUseLicenseManager = ((LicenseSubsystem) useLicenseSubsystem).getLicenseManager();

        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartSubsystemException: " + e.getMessage());

            /**
             * The com.bitdubai.platform cannot start without performing licensing operations, that's why I throw this exception.
             */
            throw new CantStartLayerException();
        }

    }
}
