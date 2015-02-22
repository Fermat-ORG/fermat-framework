package com.bitdubai.fermat_core.layer._9_communication.cloud.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.layer._4_user.DeviceUser;
import com.bitdubai.fermat_api.layer._9_communication.OnlineChannel;
import com.bitdubai.fermat_api.layer._9_communication.UserToUserOnlineConnection;

/**
 * Created by ciencias on 2/12/15.
 */
public class CloudOnlineChannel implements OnlineChannel {
    @Override
    public UserToUserOnlineConnection createOnlineConnection(DeviceUser deviceUserFrom, DeviceUser deviceUserTo) {
        return null;
    }
}
