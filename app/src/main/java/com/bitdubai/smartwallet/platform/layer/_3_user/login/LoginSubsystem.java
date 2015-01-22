package com.bitdubai.smartwallet.platform.layer._3_user.login;

import com.bitdubai.smartwallet.platform.layer._3_user.CantStartSubsystemException;
import com.bitdubai.smartwallet.platform.layer._3_user.UserManager;
import com.bitdubai.smartwallet.platform.layer._3_user.UserSubsystem;
import com.bitdubai.smartwallet.platform.layer._3_user.login.developer.DeveloperBitDubai;

/**
 * Created by ciencias on 22.01.15.
 */
public class LoginSubsystem implements UserSubsystem {

    UserManager mUserManager;

    @Override
    public UserManager getUserManager() {
        return mUserManager;
    }

    @Override
    public void start() throws CantStartSubsystemException {

        /**
         * I will choose from the different versions available of this functionality.
         */

        try {
            DeveloperBitDubai developerBitDubai = new DeveloperBitDubai();
            mUserManager = developerBitDubai.getUser();
        }
        catch (Exception e)
        {
            System.err.println("Exception: " + e.getMessage());
            throw new CantStartSubsystemException();
        }
    }


}
