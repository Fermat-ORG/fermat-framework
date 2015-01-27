package platform.layer._4_user;

import platform.layer.CantStartLayerException;
import platform.layer.PlatformLayer;
import platform.layer._4_user.manager.CantStartSubsystemException;
import platform.layer._4_user.manager.ManagerSubsystem;

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
        UserSubsystem loginSubsystem = new ManagerSubsystem();

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
