package com.bitdubai.wallet_platform_api;

import com.bitdubai.wallet_platform_api.layer._4_user.User;

/**
 * Created by ciencias on 2/12/15.
 */
public interface PlatformContext {

    public User getLoggedInUser();

    public void setLoggedInUser(User loggedInUser);
    
}
