package org.fermat.fermat_dap_android_sub_app_asset_user_community.app_connection;

import com.bitdubai.fermat_android_api.engine.NotificationPainter;

import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import org.fermat.fermat_dap_api.layer.dap_sub_app_module.asset_user_community.interfaces.AssetUserCommunitySubAppModuleManager;

/**
 * Created by Nerio on 07/03/16.
 */
public class UserCommunityBuildNotificationPainter {

    public static NotificationPainter getNotification(AssetUserCommunitySubAppModuleManager manager, String code, String subAppPublicKey) {
        NotificationPainter notification = null;

        try {
            String[] params = code.split("_");
            String notificationType = params[0];
            String senderActorPublicKey = params[1];

            //find last transaction
//            switch (notificationType){
//                case "TRANSACTIONARRIVE":
//                    if(moduleManager != null){
//                        loggedIntraUserPublicKey = moduleManager.getActiveIdentities().get(0).getPublicKey();
//                        transaction= moduleManager.getTransaction(UUID.fromString(transactionId), walletPublicKey,loggedIntraUserPublicKey);
//
//                        notification = new BitcoinWalletNotificationPainter("Received money", transaction.getInvolvedActor().getName() + " send "+ WalletUtils.formatBalanceString(transaction.getAmount()) + " BTC","","");
//
//                    }else{
//                        notification = new BitcoinWalletNotificationPainter("Received money", "BTC Arrived","","");
//                    }
//                    break;
            switch (notificationType) {
                case "CONNECTION-REQUEST":
                    if (manager != null) {
                        //find last notification by sender actor public key
                        ActorAssetUser senderActor = manager.getLastNotification(senderActorPublicKey);
                        notification = new UserAssetCommunityNotificationPainter("New Connection Request", "Was Received From: " + senderActor.getName(), "", "");
                    } else {
                        notification = new UserAssetCommunityNotificationPainter("New Connection Request", "A new connection request was received.", "", "");
                    }
                    break;
                case "CRYPTO-REQUEST":
                    if (manager != null) {
                        //find last notification by sender actor public key
//                        ActorAssetUser senderActor = manager.getLastNotification(senderActorPublicKey);
                        notification = new UserAssetCommunityNotificationPainter("CryptoAddress Arrive", "A New CryptoAddress was Received From: " + senderActorPublicKey, "", "");
                    } else {
                        notification = new UserAssetCommunityNotificationPainter("CryptoAddress Arrive", "Was Received for: " + senderActorPublicKey, "", "");
                    }
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return notification;
    }
}
