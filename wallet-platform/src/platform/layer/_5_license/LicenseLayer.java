package platform.layer._5_license;

import platform.layer.CantStartLayerException;
import platform.layer.PlatformLayer;
import platform.layer._5_license.component.ComponentLicenseSubsystem;
import platform.layer._5_license.use.UseLicenseSubsystem;

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
             * The platform cannot run without performing licensing operations, that's why I throw this exception.
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
             * The platform cannot run without performing licensing operations, that's why I throw this exception.
             */
            throw new CantStartLayerException();
        }

    }
}
