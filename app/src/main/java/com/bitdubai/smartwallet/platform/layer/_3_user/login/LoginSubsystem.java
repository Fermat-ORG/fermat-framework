package com.bitdubai.smartwallet.platform.layer._3_user.login;

import com.bitdubai.smartwallet.platform.layer._3_user.CantStartSubsystemException;
import com.bitdubai.smartwallet.platform.layer._3_user.User;
import com.bitdubai.smartwallet.platform.layer._3_user.UserSubsystem;

/**
 * Created by ciencias on 22.01.15.
 */
public class LoginSubsystem implements UserSubsystem {

    @Override
    public User getUser() {
        return null;
    }

    @Override
    public void start() throws CantStartSubsystemException {

    }
}
