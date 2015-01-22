package com.bitdubai.smartwallet.platform.layer._3_user;

import com.bitdubai.smartwallet.platform.layer._3_user.login.User_Status;

import java.util.UUID;

/**
 * Created by ciencias on 22.01.15.
 */
public interface User {


    public UUID getId();

    public String getUserName();

    public User_Status getStatus();


    public void login (String password) throws LoginFailedException ;

}
