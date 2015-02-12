package com.bitdubai.wallet_platform_api.layer._8_communication;

import com.bitdubai.wallet_platform_api.layer._4_user.User;

/**
 * Created by ciencias on 2/12/15.
 */
public interface UserToUserOnlineConnection {


    public User getLocalUser ();

    public User getRemoteUser ();
    
    public void connect() throws CantConnectToUserException;
    
    public void disconnect();
    
}
