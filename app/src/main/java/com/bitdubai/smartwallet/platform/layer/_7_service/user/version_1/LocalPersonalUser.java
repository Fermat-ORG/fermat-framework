package com.bitdubai.smartwallet.platform.layer._7_service.user.version_1;

/**
 * Created by ciencias on 23.12.14.
 */
public class LocalPersonalUser implements  LocalUser,  PersonalUser {

    private String mId;
    private LoginType mLoginType;

    public LocalPersonalUser(String userId) {
        mId = userId;
        mLoginType = LoginType.NONE;
    }

    public LoginType getLoginType() {
        return mLoginType;
    }

    @java.lang.Override
    public boolean Login() {
        return false;
    }

    @java.lang.Override
    public boolean Login(int pPIN) {
        return false;
    }

    @java.lang.Override
    public boolean Login(String pPassword) {
        return false;
    }


}
