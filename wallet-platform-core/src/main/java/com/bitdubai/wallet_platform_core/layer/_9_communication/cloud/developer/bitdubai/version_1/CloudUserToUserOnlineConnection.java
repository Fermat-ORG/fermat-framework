package com.bitdubai.wallet_platform_core.layer._9_communication.cloud.developer.bitdubai.version_1;

import com.bitdubai.wallet_platform_api.layer._4_user.DeviceUser;
import com.bitdubai.wallet_platform_api.layer._9_communication.CantConnectToUserException;
import com.bitdubai.wallet_platform_api.layer._9_communication.UserToUserOnlineConnection;

/**
 * Created by ciencias on 2/12/15.
 */
public class CloudUserToUserOnlineConnection implements UserToUserOnlineConnection {

    DeviceUser deviceUserFrom;
    DeviceUser deviceUserTo;
    
    public CloudUserToUserOnlineConnection(DeviceUser deviceUserFrom, DeviceUser deviceUserTo) {
        
        this.deviceUserFrom = deviceUserFrom;
        this.deviceUserTo = deviceUserTo;
    }
    
    @Override
    public DeviceUser getLocalUser() {
        return this.deviceUserFrom;
    }

    @Override
    public DeviceUser getRemoteUser() {
        return this.deviceUserTo;
    }

    @Override
    public void connect() throws CantConnectToUserException {

    }

    @Override
    public void disconnect() {

    }
}
