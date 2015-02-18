package com.bitdubai.wallet_platform_api.layer._4_user;

import com.bitdubai.wallet_platform_api.layer._4_user.device_user.exceptions.CantCreateDeviceUserException;
import com.bitdubai.wallet_platform_api.layer._4_user.device_user.exceptions.CantLoadDeviceUserException;
import com.bitdubai.wallet_platform_api.layer._4_user.device_user.exceptions.LoginFailedException;
import com.bitdubai.wallet_platform_api.layer._4_user.device_user.DeviceUserStatus;

import java.util.UUID;

/**
 * Created by ciencias on 22.01.15.
 */
public interface DeviceUser {

    public void createUser()  throws CantCreateDeviceUserException;

    public void loadUser  (UUID id) throws CantLoadDeviceUserException;

    public UUID getId();

    public String getUserName();

    public DeviceUserStatus getStatus();


    public void login (String password) throws LoginFailedException;

}
