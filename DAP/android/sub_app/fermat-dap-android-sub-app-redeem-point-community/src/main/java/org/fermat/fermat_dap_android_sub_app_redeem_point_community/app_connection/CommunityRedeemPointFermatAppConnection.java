package org.fermat.fermat_dap_android_sub_app_redeem_point_community.app_connection;

import android.content.Context;

import com.bitdubai.fermat_android_api.core.ResourceSearcher;
import com.bitdubai.fermat_android_api.engine.FermatFragmentFactory;
import com.bitdubai.fermat_android_api.engine.FooterViewPainter;
import com.bitdubai.fermat_android_api.engine.HeaderViewPainter;
import com.bitdubai.fermat_android_api.engine.NavigationViewPainter;
import com.bitdubai.fermat_android_api.engine.NotificationPainter;
import com.bitdubai.fermat_android_api.layer.definition.wallet.abstracts.AbstractReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.AppConnections;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Developers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;

import org.fermat.fermat_dap_android_sub_app_redeem_point_community.factory.AssetRedeemPointCommunityFragmentFactory;
import org.fermat.fermat_dap_android_sub_app_redeem_point_community.navigation_drawer.RedeemPointCommunityNavigationViewPainter;
import org.fermat.fermat_dap_android_sub_app_redeem_point_community.sessions.AssetRedeemPointCommunitySubAppSessionReferenceApp;
import org.fermat.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPoint;
import org.fermat.fermat_dap_api.layer.dap_sub_app_module.redeem_point_community.interfaces.RedeemPointCommunitySubAppModuleManager;

/**
 * Created by Matias Furszyfer on 2015.12.09..
 */
public class CommunityRedeemPointFermatAppConnection extends AppConnections<ReferenceAppFermatSession<RedeemPointCommunitySubAppModuleManager>> {

    ReferenceAppFermatSession<RedeemPointCommunitySubAppModuleManager> assetRedeemPointCommunitySubAppSession;

    public CommunityRedeemPointFermatAppConnection(Context activity) {
        super(activity);
    }

    @Override
    public FermatFragmentFactory getFragmentFactory() {
        return new AssetRedeemPointCommunityFragmentFactory();
    }

    @Override
    public PluginVersionReference[] getPluginVersionReference() {
        return new PluginVersionReference[]{new PluginVersionReference(
                Platforms.DIGITAL_ASSET_PLATFORM,
                Layers.SUB_APP_MODULE,
                Plugins.REDEEM_POINT_COMMUNITY,
                Developers.BITDUBAI,
                new Version()
        )};
    }

    @Override
    public AbstractReferenceAppFermatSession getSession() {
        return new AssetRedeemPointCommunitySubAppSessionReferenceApp();
    }

    @Override
    public NavigationViewPainter getNavigationViewPainter() {
        return new RedeemPointCommunityNavigationViewPainter(getContext(), getFullyLoadedSession(), getApplicationManager());
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
            boolean enabledNotification = true;

            this.assetRedeemPointCommunitySubAppSession = this.getFullyLoadedSession();
            if (assetRedeemPointCommunitySubAppSession != null) {
                if (assetRedeemPointCommunitySubAppSession.getModuleManager() != null) {
                    enabledNotification = assetRedeemPointCommunitySubAppSession.getModuleManager().loadAndGetSettings(assetRedeemPointCommunitySubAppSession.getAppPublicKey()).getNotificationEnabled();
                }
            }

            if (enabledNotification) {
                String[] params = code.split("_");
                String notificationType = params[0];
                String senderActorPublicKey = params[1];

                switch (notificationType) {
                    case "CONNECTION-REQUEST":
                        if (assetRedeemPointCommunitySubAppSession.getModuleManager() != null) {
                            //find last notification by sender actor public key
                            ActorAssetRedeemPoint senderActor = assetRedeemPointCommunitySubAppSession.getModuleManager().getLastNotification(senderActorPublicKey);
                            notification = new RedeemAssetCommunityNotificationPainter("New Connection Request", "Was Received From: " + senderActor.getName(), "", "");
                        } else {
                            notification = new RedeemAssetCommunityNotificationPainter("New Connection Request", "A new connection request was received.", "", "");
                        }
                        break;
                    case "CRYPTO-REQUEST":
                        if (assetRedeemPointCommunitySubAppSession.getModuleManager() != null) {
                            //find last notification by sender actor public key
                            ActorAssetRedeemPoint senderActor = assetRedeemPointCommunitySubAppSession.getModuleManager().getLastNotification(senderActorPublicKey);
                            notification = new RedeemAssetCommunityNotificationPainter("CryptoAddress Arrive", "A New CryptoAddress was Received From: " + senderActorPublicKey, "", "");
                        } else {
                            notification = new RedeemAssetCommunityNotificationPainter("CryptoAddress Arrive", "Was Received for: " + senderActorPublicKey, "", "");
                        }
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return notification;
    }

    @Override
    public ResourceSearcher getResourceSearcher() {
        return new RedeemCommunitySearcher();
    }
}
