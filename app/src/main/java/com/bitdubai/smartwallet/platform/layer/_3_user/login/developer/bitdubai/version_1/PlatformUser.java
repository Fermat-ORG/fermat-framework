package com.bitdubai.smartwallet.platform.layer._3_user.login.developer.bitdubai.version_1;

import com.bitdubai.smartwallet.platform.layer._3_user.LoginFailedException;
import com.bitdubai.smartwallet.platform.layer._3_user.User;
import com.bitdubai.smartwallet.platform.layer._3_user.login.User_Status;

import java.util.UUID;

/**
 * Created by ciencias on 22.01.15.
 */
public class PlatformUser implements User {

    UUID mId;
    String mUserName;
    String mPassword;
    User_Status mStatus;


    PlatformUser (String userName, String password) {
        mUserName = userName;
        mPassword = password;
        mId = UUID.randomUUID();
        mStatus = User_Status.LOGGED_OUT;
    }

    @Override
    public UUID getId() {
        return mId;
    }

    @Override
    public String getUserName() {
        return mUserName;
    }

    @Override
    public User_Status getStatus() {
        return mStatus;
    }

    @Override
    public void login(String password) throws LoginFailedException {
        if (mPassword != password) {
            throw new LoginFailedException();
        }
        else
        {
            mStatus = User_Status.LOGGED_IN;
        }
    }
}
