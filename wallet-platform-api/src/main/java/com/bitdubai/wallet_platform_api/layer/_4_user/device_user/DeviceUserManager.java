package com.bitdubai.wallet_platform_api.layer._4_user.device_user;

import com.bitdubai.wallet_platform_api.layer._4_user.DeviceUser;
import com.bitdubai.wallet_platform_api.layer._4_user.device_user.exceptions.CantCreateDeviceUserException;
import com.bitdubai.wallet_platform_api.layer._4_user.device_user.exceptions.CantLoadDeviceUserException;

import java.util.UUID;

/**
 * Created by ciencias on 22.01.15.
 */
public interface DeviceUserManager {

    public DeviceUser getLoggedInUser();

    public DeviceUser createUser() throws CantCreateDeviceUserException;

    public void loadUser(UUID id)  throws CantLoadDeviceUserException;

}
