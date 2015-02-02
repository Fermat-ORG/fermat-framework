package com.bitdubai.wallet_platform_core.layer._4_user.manager.developer;

import com.bitdubai.wallet_platform_api.layer._4_user.UserManager;
import com.bitdubai.wallet_platform_api.layer._4_user.UserDeveloper;
import com.bitdubai.wallet_platform_core.layer._4_user.manager.developer.bitdubai.version_1.PlatformUserManager;

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

        mUserManager = new PlatformUserManager();

    }
}
