package com.bitdubai.fermat_api.layer._10_communication;

import com.bitdubai.fermat_api.layer._5_user.device_user.DeviceUser;

/**
 * Created by ciencias on 2/12/15.
 */
public interface OnlineConnection {


    public DeviceUser getLocalUser ();

    public DeviceUser getRemoteUser ();

    public void connect() throws CantConnectToRemoteServiceException;

    public void disconnect();

}
