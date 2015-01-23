package com.bitdubai.smartwallet.platform.layer._4_user;

import com.bitdubai.smartwallet.platform.layer._4_user.manager.User_Status;

import java.util.UUID;

/**
 * Created by ciencias on 22.01.15.
 */
public interface User {

    public void createUser()  throws CantCreateUserException ;

    public void loadUser  (UUID id) throws CantLoadUserException;

    public UUID getId();

    public String getUserName();

    public User_Status getStatus();


    public void login (String password) throws LoginFailedException ;

}
