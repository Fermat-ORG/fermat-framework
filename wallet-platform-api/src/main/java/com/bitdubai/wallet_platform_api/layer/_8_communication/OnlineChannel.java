package com.bitdubai.wallet_platform_api.layer._8_communication;

import com.bitdubai.wallet_platform_api.layer._4_user.DeviceUser;

/**
 * Created by ciencias on 2/12/15.
 */
public interface OnlineChannel {

    public UserToUserOnlineConnection createOnlineConnection (DeviceUser deviceUserFrom, DeviceUser deviceUserTo);
}
