package com.bitdubai.fermat_p2p_api.layer.p2p_communication;

import com.bitdubai.fermat_pip_api.layer.pip_user.device_user.DeviceUser;

/**
 * Created by ciencias on 2/12/15.
 */
public interface OnlineConnection {


    public DeviceUser getLocalUser ();

    public DeviceUser getRemoteUser ();

    public void connect() throws CantConnectToRemoteServiceException;

    public void disconnect();

}
