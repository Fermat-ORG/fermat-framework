package com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.dmp_network_service.intra_user.enums.IntraUserNotificationDescriptor;
import com.bitdubai.fermat_api.layer.dmp_network_service.intra_user.interfaces.IntraUserNotification;

/**
 * Created by natalia on 03/09/15.
 */
public class IntraUserNetworkServiceNotification implements IntraUserNotification {
    @Override
    public String getPublicKeyOfTheIntraUserSendingUsANotification() {
        return null;
    }

    @Override
    public IntraUserNotificationDescriptor getNotificationDescriptor() {
        return null;
    }
}
