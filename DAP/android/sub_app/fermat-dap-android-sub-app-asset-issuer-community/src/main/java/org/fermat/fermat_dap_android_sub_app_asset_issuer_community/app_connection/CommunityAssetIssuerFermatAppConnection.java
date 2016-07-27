package org.fermat.fermat_dap_android_sub_app_asset_issuer_community.app_connection;

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
import com.bitdubai.fermat_dap_android_sub_app_asset_issuer_community_bitdubai.R;

import org.fermat.fermat_dap_android_sub_app_asset_issuer_community.factory.AssetIssuerCommunityFragmentFactory;
import org.fermat.fermat_dap_android_sub_app_asset_issuer_community.navigation_drawer.IssuerCommunityNavigationViewPainter;
import org.fermat.fermat_dap_android_sub_app_asset_issuer_community.sessions.AssetIssuerCommunitySubAppSessionReferenceApp;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuer;
import org.fermat.fermat_dap_api.layer.dap_sub_app_module.asset_issuer_community.interfaces.AssetIssuerCommunitySubAppModuleManager;

/**
 * Created by Matias Furszyfer on 2015.12.09..
 */
public class CommunityAssetIssuerFermatAppConnection extends AppConnections<ReferenceAppFermatSession<AssetIssuerCommunitySubAppModuleManager>> {

    ReferenceAppFermatSession<AssetIssuerCommunitySubAppModuleManager> assetIssuerCommunitySubAppSession;

    public CommunityAssetIssuerFermatAppConnection(Context activity) {
        super(activity);
    }

    @Override
    public FermatFragmentFactory getFragmentFactory() {
        return new AssetIssuerCommunityFragmentFactory();
    }

    @Override
    public PluginVersionReference[] getPluginVersionReference() {
        return new PluginVersionReference[]{new PluginVersionReference(
                Platforms.DIGITAL_ASSET_PLATFORM,
                Layers.SUB_APP_MODULE,
                Plugins.ASSET_ISSUER_COMMUNITY,
                Developers.BITDUBAI,
                new Version()
        )};
    }

    @Override
    public AbstractReferenceAppFermatSession getSession() {
        return new AssetIssuerCommunitySubAppSessionReferenceApp();
    }

    @Override
    public NavigationViewPainter getNavigationViewPainter() {
        return new IssuerCommunityNavigationViewPainter(getContext(), getFullyLoadedSession(), getApplicationManager());
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

            this.assetIssuerCommunitySubAppSession = this.getFullyLoadedSession();
            if (assetIssuerCommunitySubAppSession != null) {
                if (assetIssuerCommunitySubAppSession.getModuleManager() != null) {
                    enabledNotification = assetIssuerCommunitySubAppSession.getModuleManager().loadAndGetSettings(assetIssuerCommunitySubAppSession.getAppPublicKey()).getNotificationEnabled();
                }
            }

            if (enabledNotification) {
                String[] params = code.split("_");
                String notificationType = params[0];
                String senderActorPublicKey = params[1];

                switch (notificationType) {
                    case "EXTENDED-RECEIVE":
                        if (assetIssuerCommunitySubAppSession.getModuleManager() != null) {
                            //find last notification by sender actor public key
                            ActorAssetIssuer senderActor = assetIssuerCommunitySubAppSession.getModuleManager().getLastNotification(senderActorPublicKey);
                            notification = new IssuerAssetCommunityNotificationPainter("New Extended Key", "Was Received From: " + senderActor.getName(), "", "");
                        } else {
                            notification = new IssuerAssetCommunityNotificationPainter("Extended Key Arrive", "Was Received for: " + senderActorPublicKey, "", "");
                        }
                        break;
                    case "EXTENDED-REQUEST":
                        if (assetIssuerCommunitySubAppSession.getModuleManager() != null) {
                            //find last notification by sender actor public key
                            ActorAssetIssuer senderActor = assetIssuerCommunitySubAppSession.getModuleManager().getLastNotification(senderActorPublicKey);
                            notification = new IssuerAssetCommunityNotificationPainter("New Extended Request", "Was Received From: " + senderActor.getName(), "", "");
                        } else {
                            notification = new IssuerAssetCommunityNotificationPainter("New Extended Request", "A new connection request was received.", "", "");
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
        return new IssuerAssetCommunitySearcher();
    }
}
