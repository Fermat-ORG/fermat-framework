package com.bitdubai.smartwallet.core.system.user;

import com.bitdubai.smartwallet.core.interfaces.LocalUser;

/**
 * Created by ciencias on 23.12.14.
 */
public class LocalPersonalUser extends PersonalUser implements LocalUser{

    private LoginType mLoginType;

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
