package com.bitdubai.smartwallet.platform.layer._4_user;

/**
 * Created by ciencias on 22.01.15.
 */
public interface UserSubsystem {
    public void start () throws CantStartSubsystemException;
    public UserManager getUserManager();
}
