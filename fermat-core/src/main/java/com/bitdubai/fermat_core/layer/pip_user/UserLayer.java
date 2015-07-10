package com.bitdubai.fermat_core.layer.pip_user;

import com.bitdubai.fermat_api.Addon;
import com.bitdubai.fermat_api.layer.CantStartLayerException;
import com.bitdubai.fermat_api.layer.PlatformLayer;
import com.bitdubai.fermat_api.layer.pip_user.UserSubsystem;
import com.bitdubai.fermat_api.layer.pip_user.CantStartSubsystemException;
import com.bitdubai.fermat_core.layer.pip_user.device_user.DeviceUserSubsystem;
import com.bitdubai.fermat_core.layer.pip_user.intra_user.IntraUserSubsystem;

/**
 * Created by ciencias on 22.01.15.
 */
public class UserLayer implements PlatformLayer {

    Addon deviceUser;
    Addon intraUser;


    public Addon getDeviceUser() {
        return deviceUser;
    }

    public Addon getIntraUser() {
        return intraUser;
    }


    public void start() throws CantStartLayerException {

        /**
         * Let's start the Login Subsystem;
         */
        UserSubsystem deviceUserSubsystem = new DeviceUserSubsystem();

        try {
            deviceUserSubsystem.start();
            deviceUser = deviceUserSubsystem.getAddon();

        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartSubsystemException: " + e.getMessage());

            throw new CantStartLayerException();
        }

        /**
         * Let's start the Intra User Subsystem;
         */
        UserSubsystem intraUserSubsystem = new IntraUserSubsystem();

        try {
            intraUserSubsystem.start();
            intraUser = intraUserSubsystem.getAddon();

        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartSubsystemException: " + e.getMessage());

            throw new CantStartLayerException();
        }
    }
}
