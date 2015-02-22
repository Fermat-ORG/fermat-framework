package com.bitdubai.fermat_core.layer._9_communication.cloud.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.layer._4_user.device_user.DeviceUser;
import com.bitdubai.fermat_api.layer._9_communication.CantConnectToRemoteServiceException;
import com.bitdubai.fermat_api.layer._9_communication.ServiceToServiceOnlineConnection;

/**
 * Created by ciencias on 2/12/15.
 */
public class CloudServiceToServiceOnlineConnection implements ServiceToServiceOnlineConnection {

    DeviceUser deviceUserFrom;
    DeviceUser deviceUserTo;
    
    public CloudServiceToServiceOnlineConnection(DeviceUser deviceUserFrom, DeviceUser deviceUserTo) {
        
        this.deviceUserFrom = deviceUserFrom;
        this.deviceUserTo = deviceUserTo;
    }
    


    @Override
    public void connect() throws CantConnectToRemoteServiceException {

    }

    @Override
    public void disconnect() {

    }
}
