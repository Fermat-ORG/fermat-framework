package com.bitdubai.fermat_pip_api.layer.pip_user.device_user;

import com.bitdubai.fermat_pip_api.layer.pip_user.device_user.exceptions.DEPRECATED_CantCreateDeviceUserException;

import java.util.UUID;

/**
 * Created by ciencias on 22.01.15.
 */
public interface DeviceUserManager {

    public DeviceUser getLoggedInUser();

    public DeviceUser createUser() throws DEPRECATED_CantCreateDeviceUserException;

    public void loadUser(UUID id)  throws com.bitdubai.fermat_pip_api.layer.pip_user.device_user.exceptions.CantLoadDeviceUserException;

    public com.bitdubai.fermat_pip_api.layer.pip_user.User getUser(UUID id);
}
