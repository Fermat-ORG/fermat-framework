package com.bitdubai.wallet_platform_core.layer._4_user;

import com.bitdubai.wallet_platform_api.Service;
import com.bitdubai.wallet_platform_api.layer.CantStartLayerException;
import com.bitdubai.wallet_platform_api.layer.PlatformLayer;
import com.bitdubai.wallet_platform_api.layer._4_user.UserManager;
import com.bitdubai.wallet_platform_api.layer._4_user.UserSubsystem;
import com.bitdubai.wallet_platform_api.layer._4_user.manager.CantStartSubsystemException;
import com.bitdubai.wallet_platform_core.layer._4_user.manager.UserManagerSubsystem;

/**
 * Created by ciencias on 22.01.15.
 */
public class UserLayer implements PlatformLayer {

    Service mUserManager;

    public Service getUserManager() {
        return mUserManager;
    }

    public void start() throws CantStartLayerException {

        /**
         * Let's start the Login Subsystem;
         */
        UserSubsystem loginSubsystem = new UserManagerSubsystem();

        try {
            loginSubsystem.start();
            mUserManager = ((UserSubsystem) loginSubsystem).getUserManager();

        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartSubsystemException: " + e.getMessage());

            /**
             * The com.bitdubai.platform cannot start without performing licensing operations, that's why I throw this exception.
             */
            throw new CantStartLayerException();
        }
    }



}
