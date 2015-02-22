package com.bitdubai.fermat_api.layer._9_communication;

import com.bitdubai.fermat_api.layer._10_network_service.intra_user.IntraUser;
import com.bitdubai.fermat_api.layer._4_user.DeviceUser;

/**
 * Created by ciencias on 2/12/15.
 */
public interface OnlineChannel {

    public UserToUserOnlineConnection createOnlineConnection (IntraUser localIntraUser, IntraUser remoteIntraUser);
}
