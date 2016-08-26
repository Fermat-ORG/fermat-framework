package com.bitdubai.sub_app.intra_user_community.app_connection;

import com.bitdubai.fermat_android_api.engine.NotificationPainter;
import com.bitdubai.fermat_ccp_api.all_definition.constants.CCPBroadcasterConstants;
import com.bitdubai.fermat_ccp_api.layer.actor.intra_user.interfaces.IntraWalletUserActor;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantGetIntraUsersListException;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserModuleManager;

/**
 * Created by natalia on 22/02/16.
 */
public class CryptoWalletUserCommunityBuildNotification {

    public static NotificationPainter getNotification(int code,String involvedActor, String appPublicKey)
    {
        NotificationPainter notification = null;
        try {


            switch (code){
                case CCPBroadcasterConstants.CONNECTION_REQUEST:

                    notification = new UserCommunityNotificationPainter("New Connection Request", "A new connection request was received from  " + involvedActor, "", "",appPublicKey);
                    break;
                case CCPBroadcasterConstants.CONNECTION_ACCEPT:
                    notification = new UserCommunityNotificationPainter("Connection Request", "Your connection request was accepted to " + involvedActor, "", "",appPublicKey);

                    break;

                case CCPBroadcasterConstants.CONNECTION_DISCONNECT:
                    notification = new UserCommunityNotificationPainter("Connection Request", " A user remove you from your connections.", "", "",appPublicKey);

                    break;

                case CCPBroadcasterConstants.CONNECTION_DENIED:
                    notification = new UserCommunityNotificationPainter("Connection Request", "Your connection request was denied.", "", "",appPublicKey);

                    break;

            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return notification;
    }
}
