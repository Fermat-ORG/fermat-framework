package com.bitdubai.sub_app.intra_user_community.app_connection;

import com.bitdubai.fermat_android_api.engine.NotificationPainter;
import com.bitdubai.fermat_ccp_api.layer.actor.intra_user.interfaces.IntraWalletUserActor;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantGetIntraUsersListException;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserModuleManager;

/**
 * Created by natalia on 22/02/16.
 */
public class CryptoWalletUserCommunityBuildNotification {

    public static NotificationPainter getNotification(IntraUserModuleManager moduleManager, String code, String appPublicKey)
    {
        NotificationPainter notification = null;
        try {
            String[] params = code.split("_");
            String notificationType = params[0];
            String senderActorPublicKey = params[1];

            switch (notificationType){
                case "CONNECTIONREQUEST":

                    if(moduleManager != null) {
                        //find last notification by sender actor public key
                        IntraWalletUserActor senderActor = null;

                            senderActor = moduleManager.getLastNotification(senderActorPublicKey);

                        notification = new UserCommunityNotificationPainter("New Connection Request", "A new connection request was received from " + senderActor.getName(), "", "",appPublicKey);

                    }else
                    {
                        notification = new UserCommunityNotificationPainter("New Connection Request", "A new connection request was received.", "", "",appPublicKey);
                    }
                    break;
                case "CONNECTIONACCEPT":

                    if(moduleManager != null) {
                        //find last notification by sender actor public key
                        IntraWalletUserActor senderActor = null;

                        senderActor = moduleManager.getLastNotification(senderActorPublicKey);

                        notification = new UserCommunityNotificationPainter("Connection Request", "Your connection request was accepted to " + senderActor.getName(), "", "",appPublicKey);

                    }else
                    {
                        notification = new UserCommunityNotificationPainter("Connection Request Accepted", "Your connection request was accepted.", "", "",appPublicKey);
                    }
                    break;

            }
        } catch (CantGetIntraUsersListException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return notification;
    }
}
