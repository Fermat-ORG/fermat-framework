package com.bitdubai.wallet_platform_core.layer._3_os;

import com.bitdubai.wallet_platform_api.layer.CantStartLayerException;
import com.bitdubai.wallet_platform_api.layer.PlatformLayer;
import com.bitdubai.wallet_platform_api.layer._3_os.Os;
import com.bitdubai.wallet_platform_api.layer._3_os.OsSubsystem;
import com.bitdubai.wallet_platform_api.layer._3_os.WrongOsException;
import com.bitdubai.wallet_platform_core.layer._3_os.android.AndroidOsSubsystem;
import com.bitdubai.wallet_platform_core.layer._3_os.ios.IosOsSubsystem;
import com.bitdubai.wallet_platform_core.layer._3_os.linux.LinuxOsSubsystem;
import com.bitdubai.wallet_platform_core.layer._3_os.windows.WindowsOsSubsystem;


/**
 * Created by ciencias on 30.12.14.
 */

/**
 * This object does not know in which OS is running, so it has no choice but to try to start each subsystem until one
 * of them reports it can be started, and in that way the OS is automatically discovered.
 */

public class OsLayer implements PlatformLayer {

    Os mOs;

    public Os getOs() {
        return mOs;
    }

    @Override
    public void start() throws CantStartLayerException {

        OsSubsystem osSubsystem;

        if (mOs == null) {
            osSubsystem = new AndroidOsSubsystem();
            try {
                osSubsystem.start();
                mOs = osSubsystem.getOs();
            }
            catch (WrongOsException e) {
                System.err.println("WrongOsException: " + e.getMessage());
            }
        }

        if (mOs == null) {
            osSubsystem = new IosOsSubsystem();
            try {
                osSubsystem.start();
                mOs = osSubsystem.getOs();
            }
            catch (WrongOsException e) {
                System.err.println("WrongOsException: " + e.getMessage());
            }
        }

        if (mOs == null) {
            osSubsystem = new LinuxOsSubsystem();
            try {
                osSubsystem.start();
                mOs = osSubsystem.getOs();
            }
            catch (WrongOsException e) {
                System.err.println("WrongOsException: " + e.getMessage());
            }
        }

        if (mOs == null) {
            osSubsystem = new WindowsOsSubsystem();
            try {
                osSubsystem.start();
                mOs = osSubsystem.getOs();
            }
            catch (WrongOsException e) {
                System.err.println("WrongOsException: " + e.getMessage());
            }
        }

        if (mOs == null) {
            throw new CantStartLayerException();
        }
    }
}
