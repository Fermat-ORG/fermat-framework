package com.bitdubai.wallet_platform_core.layer._8_communication.cloud.developer.bitdubai.version_1;

import com.bitdubai.wallet_platform_api.layer._4_user.User;
import com.bitdubai.wallet_platform_api.layer._8_communication.OnlineConnection;

/**
 * Created by ciencias on 2/12/15.
 */
public class CloudOnlineConnection implements OnlineConnection {

    User userFrom;
    User userTo;
    
    public CloudOnlineConnection (User userFrom, User userTo) {
        
        this.userFrom = userFrom;
        this.userTo = userTo;
    }
    
    @Override
    public User getLocalUser() {
        return this.userFrom;
    }

    @Override
    public User getRemoteUser() {
        return this.userTo;
    }

    @Override
    public void connect() {

    }

    @Override
    public void disconnect() {

    }
}
