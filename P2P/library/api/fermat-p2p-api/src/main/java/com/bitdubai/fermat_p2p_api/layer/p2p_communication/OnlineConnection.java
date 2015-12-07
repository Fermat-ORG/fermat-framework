package com.bitdubai.fermat_p2p_api.layer.p2p_communication;

import com.bitdubai.fermat_pip_api.layer.user.device_user.interfaces.DeviceUser;

/**
 * Created by ciencias on 2/12/15.
 */
public interface OnlineConnection {

    DeviceUser getLocalUser ();

    DeviceUser getRemoteUser ();

    void connect() throws CantConnectToRemoteServiceException;

    void disconnect();

}
