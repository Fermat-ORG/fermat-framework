package com.bitdubai.sub_app.intra_user_community.app_connection;

import android.content.Context;

import com.bitdubai.fermat_android_api.engine.NotificationPainter;
import com.bitdubai.fermat_ccp_api.all_definition.constants.CCPBroadcasterConstants;
import com.bitdubai.sub_app.intra_user_community.R;

/**
 * Created by natalia on 22/02/16.
 */
public class CryptoWalletUserCommunityBuildNotification {

    public static NotificationPainter getNotification(int code,String involvedActor, String appPublicKey, Context context)
    {
        NotificationPainter notification = null;
        try {


            switch (code){
                case CCPBroadcasterConstants.CONNECTION_REQUEST:

                    notification = new UserCommunityNotificationPainter(context.getResources().getString(R.string.notification_connection_request), context.getResources().getString(R.string.notification_connection_request2)+"  " + (involvedActor != null ? involvedActor : context.getResources().getString(R.string.notification_connection_Unknown) ), "", "",appPublicKey);
                   break;
                case CCPBroadcasterConstants.CONNECTION_ACCEPT:
                        notification = new UserCommunityNotificationPainter(context.getResources().getString(R.string.notification_connection_accept), context.getResources().getString(R.string.notification_connection_accept2)+" " + (involvedActor != null ? involvedActor : context.getResources().getString(R.string.notification_connection_Unknown)), "", "",appPublicKey);


                    break;

                case CCPBroadcasterConstants.CONNECTION_DISCONNECT:
                    notification = new UserCommunityNotificationPainter(context.getResources().getString(R.string.notification_connection_disconnet),  (involvedActor != null ? involvedActor : context.getResources().getString(R.string.notification_connection_Unknown) ) + " "+ context.getResources().getString(R.string.notification_connection_disconnet2), "", "",appPublicKey);

                    break;

                case CCPBroadcasterConstants.CONNECTION_DENIED:
                        notification = new UserCommunityNotificationPainter(context.getResources().getString(R.string.notification_connection_denied), context.getResources().getString(R.string.notification_connection_denied2)+" " + (involvedActor != null ? involvedActor : context.getResources().getString(R.string.notification_connection_Unknown) ), "", "",appPublicKey);

                    break;

            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return notification;
    }
}
