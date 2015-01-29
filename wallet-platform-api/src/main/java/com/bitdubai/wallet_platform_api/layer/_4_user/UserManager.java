package com.bitdubai.wallet_platform_api.layer._4_user;

import com.bitdubai.wallet_platform_api.layer._4_user.manager.CantCreateUserException;
import com.bitdubai.wallet_platform_api.layer._4_user.manager.CantLoadUserException;

import java.util.UUID;

/**
 * Created by ciencias on 22.01.15.
 */
public interface UserManager {

    public User getLoggedInUser();

    public User createUser() throws CantCreateUserException;

    public void loadUser(UUID id)  throws CantLoadUserException;

}
