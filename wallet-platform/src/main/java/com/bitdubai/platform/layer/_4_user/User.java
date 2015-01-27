package com.bitdubai.platform.layer._4_user;

import com.bitdubai.platform.layer._4_user.manager.CantCreateUserException;
import com.bitdubai.platform.layer._4_user.manager.CantLoadUserException;
import com.bitdubai.platform.layer._4_user.manager.LoginFailedException;
import com.bitdubai.platform.layer._4_user.manager.UserStatus;

import java.util.UUID;

/**
 * Created by ciencias on 22.01.15.
 */
public interface User {

    public void createUser()  throws CantCreateUserException;

    public void loadUser  (UUID id) throws CantLoadUserException;

    public UUID getId();

    public String getUserName();

    public UserStatus getStatus();


    public void login (String password) throws LoginFailedException;

}
