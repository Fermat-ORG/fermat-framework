package com.bitdubai.fermat_core.layer.pip_user;

import com.bitdubai.fermat_api.Addon;
import com.bitdubai.fermat_api.layer.CantStartLayerException;
import com.bitdubai.fermat_api.layer.PlatformLayer;
import com.bitdubai.fermat_pip_api.layer.pip_user.UserSubsystem;
import com.bitdubai.fermat_pip_api.layer.pip_user.CantStartSubsystemException;
import com.bitdubai.fermat_core.layer.pip_user.device_user.DeviceUserSubsystem;

/**
 * Created by ciencias on 22.01.15.
 */
public class UserLayer implements PlatformLayer {

    Addon deviceUser;



    public Addon getDeviceUser() {
        return deviceUser;
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


    }
}
