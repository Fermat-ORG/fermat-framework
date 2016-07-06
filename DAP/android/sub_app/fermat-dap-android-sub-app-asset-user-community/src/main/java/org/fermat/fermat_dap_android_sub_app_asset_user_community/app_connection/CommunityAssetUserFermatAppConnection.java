package org.fermat.fermat_dap_android_sub_app_asset_user_community.app_connection;

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
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_dap_android_sub_app_asset_user_community_bitdubai.R;

import org.fermat.fermat_dap_android_sub_app_asset_user_community.factory.CommunityUserFragmentFactory;
import org.fermat.fermat_dap_android_sub_app_asset_user_community.navigation_drawer.UserCommunityNavigationViewPainter;
import org.fermat.fermat_dap_android_sub_app_asset_user_community.sessions.AssetUserCommunitySubAppSessionReferenceApp;
import org.fermat.fermat_dap_api.layer.dap_sub_app_module.asset_user_community.interfaces.AssetUserCommunitySubAppModuleManager;

/**
 * Created by Matias Furszyfer on 2015.12.09..
 */
public class CommunityAssetUserFermatAppConnection extends AppConnections<ReferenceAppFermatSession<AssetUserCommunitySubAppModuleManager>> {

    ReferenceAppFermatSession<AssetUserCommunitySubAppModuleManager> assetUserCommunitySubAppSession;

    public CommunityAssetUserFermatAppConnection(Context activity) {
        super(activity);
    }

    @Override
    public FermatFragmentFactory getFragmentFactory() {
        return new CommunityUserFragmentFactory();
    }

    @Override
    public PluginVersionReference[] getPluginVersionReference() {
        return new PluginVersionReference[]{new PluginVersionReference(
                Platforms.DIGITAL_ASSET_PLATFORM,
                Layers.SUB_APP_MODULE,
                Plugins.ASSET_USER_COMMUNITY,
                Developers.BITDUBAI,
                new Version()
        )};
    }

    @Override
    public AbstractReferenceAppFermatSession getSession() {
        return new AssetUserCommunitySubAppSessionReferenceApp();
    }

    @Override
    public NavigationViewPainter getNavigationViewPainter() {
        return new UserCommunityNavigationViewPainter(getContext(), getFullyLoadedSession(), getApplicationManager());
    }

    @Override
    public HeaderViewPainter getHeaderViewPainter() {
        return null;
//        return new UserAssetCommunityHeaderPainter();
    }

    @Override
    public FooterViewPainter getFooterViewPainter() {
        return null;
    }

    @Override
    public NotificationPainter getNotificationPainter(String code) {
        try {
            boolean enabledNotification = true;
            this.assetUserCommunitySubAppSession = this.getFullyLoadedSession();

//            if (assetUserCommunitySubAppSession != null)
//                if (assetUserCommunitySubAppSession.getModuleManager() != null) {
//                    enabledNotification = assetUserCommunitySubAppSession.getModuleManager().loadAndGetSettings(assetUserCommunitySubAppSession.getAppPublicKey()).getNotificationEnabled();
//                }

//            if (assetUserCommunitySubAppSession != null)

            return UserCommunityBuildNotificationPainter.getNotification(assetUserCommunitySubAppSession.getModuleManager(), code, Activities.DAP_ASSET_USER_COMMUNITY_NOTIFICATION_FRAGMENT.getCode());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ResourceSearcher getResourceSearcher() {
        return new UserAssetCommunitySearcher();
    }
}
