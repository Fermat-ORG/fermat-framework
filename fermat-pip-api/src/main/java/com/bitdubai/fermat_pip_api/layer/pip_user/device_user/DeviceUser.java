package com.bitdubai.fermat_pip_api.layer.pip_user.device_user;

import com.bitdubai.fermat_pip_api.layer.pip_user.device_user.exceptions.DEPRECATED_CantCreateDeviceUserException;
import com.bitdubai.fermat_pip_api.layer.pip_user.device_user.exceptions.LoginFailedException;

import java.util.UUID;

/**
 * Created by ciencias on 22.01.15.
 */
public interface DeviceUser {

    public void createUser()  throws DEPRECATED_CantCreateDeviceUserException;

    public void loadUser (UUID id) throws com.bitdubai.fermat_pip_api.layer.pip_user.device_user.exceptions.CantLoadDeviceUserException;

    public UUID getId();

    public String getUserName();

    public DeviceUserStatus getStatus();

    public void login (String password) throws LoginFailedException;
}
