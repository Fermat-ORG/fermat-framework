package com.bitdubai.smartwallet.platform.layer._3_user.login.developer;

import com.bitdubai.smartwallet.platform.layer._3_user.UserManager;
import com.bitdubai.smartwallet.platform.layer._3_user.UserDeveloper;
import com.bitdubai.smartwallet.platform.layer._3_user.login.developer.bitdubai.version_1.SystemUserManager;

/**
 * Created by ciencias on 22.01.15.
 */
public class DeveloperBitDubai implements UserDeveloper {

    UserManager mUserManager;

    @Override
    public UserManager getUser() {
        return mUserManager;
    }

    public DeveloperBitDubai () {

        /**
         * I will choose from the different versions of my implementations which one to run. Now there is only one, so
         * it is easy to choose.
         */

        mUserManager = new SystemUserManager();

    }
}
