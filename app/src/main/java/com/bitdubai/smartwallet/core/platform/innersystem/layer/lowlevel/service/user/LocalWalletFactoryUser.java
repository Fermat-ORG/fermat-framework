package com.bitdubai.smartwallet.core.platform.innersystem.layer.lowlevel.service.user;

/**
 * Created by ciencias on 30.12.14.
 */
public class LocalWalletFactoryUser implements WalletFactoryUser, LocalUser {
    private String mId;
    private LoginType mLoginType;

    public LocalWalletFactoryUser(String userId) {
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
