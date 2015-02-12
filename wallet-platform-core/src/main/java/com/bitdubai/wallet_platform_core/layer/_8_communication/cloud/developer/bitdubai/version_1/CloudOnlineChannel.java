package com.bitdubai.wallet_platform_core.layer._8_communication.cloud.developer.bitdubai.version_1;

import com.bitdubai.wallet_platform_api.layer._4_user.User;
import com.bitdubai.wallet_platform_api.layer._8_communication.OnlineChannel;
import com.bitdubai.wallet_platform_api.layer._8_communication.OnlineConnection;

/**
 * Created by ciencias on 2/12/15.
 */
public class CloudOnlineChannel implements OnlineChannel {
    @Override
    public OnlineConnection createOnlineConnection(User userFrom, User userTo) {
        return null;
    }
}
