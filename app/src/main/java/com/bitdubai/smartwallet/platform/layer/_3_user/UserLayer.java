package com.bitdubai.smartwallet.platform.layer._3_user;

import com.bitdubai.smartwallet.platform.layer.CantStartLayerException;
import com.bitdubai.smartwallet.platform.layer.PlatformLayer;
import com.bitdubai.smartwallet.platform.layer._3_user.login.LoginSubsystem;

/**
 * Created by ciencias on 22.01.15.
 */
public class UserLayer implements PlatformLayer {

    UserManager mUserManager;

    public UserManager getUserManager() {
        return mUserManager;
    }

    public void start() throws CantStartLayerException {

        /**
         * Let's start the Login Subsystem;
         */
        UserSubsystem loginSubsystem = new LoginSubsystem();

        try {
            loginSubsystem.start();
            mUserManager = ((UserSubsystem) loginSubsystem).getUserManager();

        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartSubsystemException: " + e.getMessage());

            /**
             * The platform cannot run without performing licensing operations, that's why I throw this exception.
             */
            throw new CantStartLayerException();
        }
    }



}
