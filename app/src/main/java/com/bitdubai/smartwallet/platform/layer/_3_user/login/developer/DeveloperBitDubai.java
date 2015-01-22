package com.bitdubai.smartwallet.platform.layer._3_user.login.developer;

import com.bitdubai.smartwallet.platform.layer._3_user.User;
import com.bitdubai.smartwallet.platform.layer._3_user.UserDeveloper;
import com.bitdubai.smartwallet.platform.layer._3_user.login.developer.bitdubai.version_1.SystemUser;

/**
 * Created by ciencias on 22.01.15.
 */
public class DeveloperBitDubai implements UserDeveloper {

    User mUser;

    @Override
    public User getUser() {
        return mUser;
    }

    public DeveloperBitDubai () {

        /**
         * I will choose from the different versions of my implementations which one to run. Now there is only one, so
         * it is easy to choose.
         */

        mUser = new SystemUser();

    }
}
