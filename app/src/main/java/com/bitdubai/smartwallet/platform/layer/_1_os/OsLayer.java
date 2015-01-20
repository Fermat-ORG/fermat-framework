package com.bitdubai.smartwallet.platform.layer._1_os;

import com.bitdubai.smartwallet.platform.layer.CantStartLayerException;
import com.bitdubai.smartwallet.platform.layer.PlatformLayer;
import com.bitdubai.smartwallet.platform.layer._1_os.android.AndroidOsSubsystem;
import com.bitdubai.smartwallet.platform.layer._1_os.ios.IosOsSubsystem;
import com.bitdubai.smartwallet.platform.layer._1_os.linux.LinuxOsSubsystem;
import com.bitdubai.smartwallet.platform.layer._1_os.windows.WindowsOsSubsystem;


/**
 * Created by ciencias on 30.12.14.
 */

/**
 * This object does not know in which OS is running, so it has no choice but to try to start each subsystem until one
 * of them reports it can be started, and in that way the OS is automatically discovered.
 */

public class OsLayer implements PlatformLayer {

    OsSubsystem mOsSubsystem;

    public OsSubsystem getOsSubsystem() {
        return mOsSubsystem;
    }

    @Override
    public void start() throws CantStartLayerException {

        OsSubsystem osSubsystem;

        if (mOsSubsystem == null) {
            osSubsystem = new AndroidOsSubsystem();
            try {
                osSubsystem.start();
                mOsSubsystem = osSubsystem;
            }
            catch (WrongOsException e) {
                System.err.println("WrongOsException: " + e.getMessage());
            }
        }

        if (mOsSubsystem == null) {
            osSubsystem = new IosOsSubsystem();
            try {
                osSubsystem.start();
                mOsSubsystem = osSubsystem;
            }
            catch (WrongOsException e) {
                System.err.println("WrongOsException: " + e.getMessage());
            }
        }

        if (mOsSubsystem == null) {
            osSubsystem = new LinuxOsSubsystem();
            try {
                osSubsystem.start();
                mOsSubsystem = osSubsystem;
            }
            catch (WrongOsException e) {
                System.err.println("WrongOsException: " + e.getMessage());
            }
        }

        if (mOsSubsystem == null) {
            osSubsystem = new WindowsOsSubsystem();
            try {
                osSubsystem.start();
                mOsSubsystem = osSubsystem;
            }
            catch (WrongOsException e) {
                System.err.println("WrongOsException: " + e.getMessage());
            }
        }

        if (mOsSubsystem == null) {
            throw new CantStartLayerException();
        }
    }
}
