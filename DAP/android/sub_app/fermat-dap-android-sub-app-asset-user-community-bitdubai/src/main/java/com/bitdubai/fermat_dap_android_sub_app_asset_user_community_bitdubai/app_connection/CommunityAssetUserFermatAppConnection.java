package com.bitdubai.fermat_dap_android_sub_app_asset_user_community_bitdubai.app_connection;

import android.app.Activity;

import com.bitdubai.fermat_android_api.engine.FermatFragmentFactory;
import com.bitdubai.fermat_android_api.engine.FooterViewPainter;
import com.bitdubai.fermat_android_api.engine.HeaderViewPainter;
import com.bitdubai.fermat_android_api.engine.NavigationViewPainter;
import com.bitdubai.fermat_android_api.engine.NotificationPainter;
import com.bitdubai.fermat_android_api.layer.definition.wallet.abstracts.AbstractFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.AppConnections;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Developers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_dap_android_sub_app_asset_user_community_bitdubai.factory.CommunityUserFragmentFactory;
import com.bitdubai.fermat_dap_android_sub_app_asset_user_community_bitdubai.navigation_drawer.UserCommunityNavigationViewPainter;
import com.bitdubai.fermat_dap_android_sub_app_asset_user_community_bitdubai.sessions.AssetUserCommunitySubAppSession;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import com.bitdubai.fermat_dap_api.layer.dap_sub_app_module.asset_user_community.interfaces.AssetUserCommunitySubAppModuleManager;

/**
 * Created by Matias Furszyfer on 2015.12.09..
 */
public class CommunityAssetUserFermatAppConnection extends AppConnections<AssetUserCommunitySubAppSession> {

    private AssetUserCommunitySubAppModuleManager manager;
    private AssetUserCommunitySubAppSession assetUserCommunitySubAppSession;

    public CommunityAssetUserFermatAppConnection(Activity activity) {
        super(activity);
    }

    @Override
    public FermatFragmentFactory getFragmentFactory() {
        return new CommunityUserFragmentFactory();
    }

    @Override
    public PluginVersionReference getPluginVersionReference() {
        return new PluginVersionReference(
                Platforms.DIGITAL_ASSET_PLATFORM,
                Layers.SUB_APP_MODULE,
                Plugins.ASSET_USER_COMMUNITY,
                Developers.BITDUBAI,
                new Version()
        );
    }

    @Override
    public AbstractFermatSession getSession() {
        return new AssetUserCommunitySubAppSession();
    }

    @Override
    public NavigationViewPainter getNavigationViewPainter() {
        return new UserCommunityNavigationViewPainter(getActivity(), getActiveIdentity());
    }

    @Override
    public HeaderViewPainter getHeaderViewPainter() {
        return null;
    }

    @Override
    public FooterViewPainter getFooterViewPainter() {
        return null;
    }

    @Override
    public NotificationPainter getNotificationPainter(String code) {
        NotificationPainter notification = null;
        try {
            this.assetUserCommunitySubAppSession = (AssetUserCommunitySubAppSession) this.getSession();
            if (assetUserCommunitySubAppSession != null)
                manager = assetUserCommunitySubAppSession.getModuleManager();
            String[] params = code.split("_");
            String notificationType = params[0];
            String senderActorPublicKey = params[1];

            switch (notificationType) {
                case "CONNECTION-REQUEST":
                    if (manager != null) {
                        //find last notification by sender actor public key
                        ActorAssetUser senderActor = manager.getLastNotification(senderActorPublicKey);
                        notification = new UserAssetCommunityNotificationPainter("New Connection Request", "Was Received From: " + senderActor.getName(), "", "");
                        break;
                    } else {
                        notification = new UserAssetCommunityNotificationPainter("New Connection Request", "A new connection request was received.", "", "");
                    }
                case "CRYPTO-REQUEST":
                    if (manager != null) {
                        //find last notification by sender actor public key
//                        ActorAssetUser senderActor = manager.getLastNotification(senderActorPublicKey);
                        notification = new UserAssetCommunityNotificationPainter("CryptoAddress Arrive", "A New CryptoAddress was Received From: " + senderActorPublicKey, "", "");
                        break;
                    } else {
                        notification = new UserAssetCommunityNotificationPainter("CryptoAddress Arrive", "Was Received for: "+ senderActorPublicKey, "", "");
                    }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return notification;
    }
}
