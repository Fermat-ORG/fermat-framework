package com.bitdubai.wallet_platform_core;

import com.bitdubai.wallet_platform_api.PlatformContext;
import com.bitdubai.wallet_platform_api.layer._4_user.User;

/**
 * Created by ciencias on 2/12/15.
 */
public class CorePlatformContext implements PlatformContext {

    User loggedInUser;
    
    public User getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }


    
    
    
}
