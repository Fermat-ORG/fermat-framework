package com.bitdubai.fermat_api.layer._5_user.device_user;

import com.bitdubai.fermat_api.layer._5_user.User;
import com.bitdubai.fermat_api.layer._5_user.device_user.exceptions.CantCreateDeviceUserException;
import com.bitdubai.fermat_api.layer._5_user.device_user.exceptions.CantLoadDeviceUserException;

import java.util.UUID;

/**
 * Created by ciencias on 22.01.15.
 */
public interface DeviceUserManager {

    public DeviceUser getLoggedInUser();

    public DeviceUser createUser() throws CantCreateDeviceUserException;

    public void loadUser(UUID id)  throws CantLoadDeviceUserException;

    public User getUser(UUID id);
}
