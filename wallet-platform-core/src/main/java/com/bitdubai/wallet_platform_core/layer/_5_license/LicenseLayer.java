package com.bitdubai.wallet_platform_core.layer._5_license;

import com.bitdubai.wallet_platform_api.Addon;
import com.bitdubai.wallet_platform_api.layer.CantStartLayerException;
import com.bitdubai.wallet_platform_api.layer.PlatformLayer;
import com.bitdubai.wallet_platform_api.layer._5_license.CantStartSubsystemException;
import com.bitdubai.wallet_platform_api.layer._5_license.LicenseSubsystem;
import com.bitdubai.wallet_platform_core.layer._5_license.plugin.PluginLicenseSubsystem;
import com.bitdubai.wallet_platform_core.layer._5_license.wallet.WalletLicenseSubsystem;

/**
 * Created by ciencias on 30.12.14.
 */
public class LicenseLayer implements PlatformLayer {

    Addon mComponentLicenseManager;
    Addon mUseLicenseManager;

    public Addon getComponentLicenseManager() {
        return mComponentLicenseManager;
    }

    public Addon getUseLicenseManager() {
        return mUseLicenseManager;
    }

    @Override
    public void start() throws CantStartLayerException {

        /**
         * Let's start the Component License Manager;
         */
        LicenseSubsystem componentLicenseSubsystem = new PluginLicenseSubsystem();

        try {
            componentLicenseSubsystem.start();
            mComponentLicenseManager = ((LicenseSubsystem) componentLicenseSubsystem).getAddon();

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
        LicenseSubsystem useLicenseSubsystem = new WalletLicenseSubsystem();

        try {
            useLicenseSubsystem.start();
            mUseLicenseManager = ((LicenseSubsystem) useLicenseSubsystem).getAddon();

        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartSubsystemException: " + e.getMessage());

            /**
             * The com.bitdubai.platform cannot start without performing licensing operations, that's why I throw this exception.
             */
            throw new CantStartLayerException();
        }

    }
}
