package com.bitdubai.smartwallet.platform.layer._3_user.login.developer.bitdubai.version_1;

import com.bitdubai.smartwallet.platform.layer._3_user.User;
import com.bitdubai.smartwallet.platform.layer._3_user.UserManager;

import java.util.UUID;

/**
 * Created by ciencias on 22.01.15.
 */
public class SystemUserManager implements UserManager {

    User mLoggedInUser;

    @Override
    public User getLoggedInUser() {
        return mLoggedInUser;
    }

    @Override
    public User createUser() {

        User user = new PlatformUser("", "");
        return user;
    }

    @Override
    public void loadUser(UUID id) {

        User user = new PlatformUser(id);
        mLoggedInUser = user;
    }
}
