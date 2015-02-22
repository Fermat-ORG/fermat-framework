package com.bitdubai.fermat_core.layer._4_user;

import com.bitdubai.fermat_api.Addon;
import com.bitdubai.fermat_api.layer.CantStartLayerException;
import com.bitdubai.fermat_api.layer.PlatformLayer;
import com.bitdubai.fermat_api.layer._4_user.UserSubsystem;
import com.bitdubai.fermat_api.layer._4_user.CantStartSubsystemException;
import com.bitdubai.fermat_core.layer._4_user.device_user.DeviceUserSubsystem;
import com.bitdubai.fermat_core.layer._4_user.extra_user.ExtraUserSubsystem;
import com.bitdubai.fermat_core.layer._4_user.intra_user.IntraUserSubsystem;

/**
 * Created by ciencias on 22.01.15.
 */
public class UserLayer implements PlatformLayer {

    Addon addon;

    public Addon getUserManager() {
        return addon;
    }

    public void start() throws CantStartLayerException {

        /**
         * Let's start the Login Subsystem;
         */
        UserSubsystem loginSubsystem = new DeviceUserSubsystem();

        try {
            loginSubsystem.start();
            addon = ((UserSubsystem) loginSubsystem).getAddon();

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
            addon = ((UserSubsystem) intraUserSubsystem).getAddon();

        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartSubsystemException: " + e.getMessage());

            throw new CantStartLayerException();
        }

        /**
         * Let's start the Extra User Subsystem;
         */
        UserSubsystem extraUserSubsystem = new ExtraUserSubsystem();

        try {
            extraUserSubsystem.start();
            addon = ((UserSubsystem) extraUserSubsystem).getAddon();

        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartSubsystemException: " + e.getMessage());

            throw new CantStartLayerException();
        }
        
    }



}
