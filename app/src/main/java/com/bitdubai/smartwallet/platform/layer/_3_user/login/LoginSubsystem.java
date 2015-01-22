package com.bitdubai.smartwallet.platform.layer._3_user.login;

import com.bitdubai.smartwallet.platform.layer._3_user.CantStartSubsystemException;
import com.bitdubai.smartwallet.platform.layer._3_user.User;
import com.bitdubai.smartwallet.platform.layer._3_user.UserSubsystem;
import com.bitdubai.smartwallet.platform.layer._3_user.login.developer.DeveloperBitDubai;

/**
 * Created by ciencias on 22.01.15.
 */
public class LoginSubsystem implements UserSubsystem {

    User mUser;

    @Override
    public User getUser() {
        return null;
    }

    @Override
    public void start() throws CantStartSubsystemException {

        /**
         * I will choose from the different versions available of this functionality.
         */

        try {
            DeveloperBitDubai developerBitDubai = new DeveloperBitDubai();
            mUser = developerBitDubai.getUser();
        }
        catch (Exception e)
        {
            System.err.println("Exception: " + e.getMessage());
            throw new CantStartSubsystemException();
        }
    }


}
