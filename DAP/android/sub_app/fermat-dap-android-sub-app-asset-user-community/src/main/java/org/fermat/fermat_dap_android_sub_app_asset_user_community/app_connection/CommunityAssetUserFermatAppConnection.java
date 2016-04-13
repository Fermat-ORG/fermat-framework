package org.fermat.fermat_dap_android_sub_app_asset_user_community.app_connection;

import android.content.Context;

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
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import org.fermat.fermat_dap_android_sub_app_asset_user_community.factory.CommunityUserFragmentFactory;
import org.fermat.fermat_dap_android_sub_app_asset_user_community.navigation_drawer.UserCommunityNavigationViewPainter;
import org.fermat.fermat_dap_android_sub_app_asset_user_community.sessions.AssetUserCommunitySubAppSession;

import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_user.AssetUserSettings;
import org.fermat.fermat_dap_api.layer.dap_sub_app_module.asset_user_community.interfaces.AssetUserCommunitySubAppModuleManager;

/**
 * Created by Matias Furszyfer on 2015.12.09..
 */
public class CommunityAssetUserFermatAppConnection extends AppConnections<AssetUserCommunitySubAppSession> {

    private AssetUserCommunitySubAppModuleManager manager;
    private AssetUserCommunitySubAppSession assetUserCommunitySubAppSession;

    public CommunityAssetUserFermatAppConnection(Context activity) {
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
        return new UserCommunityNavigationViewPainter(getContext(), getActiveIdentity());
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
            SettingsManager<AssetUserSettings> settingsManager;
            boolean enabledNotification = true;
            this.assetUserCommunitySubAppSession = (AssetUserCommunitySubAppSession) this.getSession();

            if (assetUserCommunitySubAppSession != null)
                if (assetUserCommunitySubAppSession.getModuleManager() != null) {
                    manager = assetUserCommunitySubAppSession.getModuleManager();

                    settingsManager = assetUserCommunitySubAppSession.getModuleManager().getSettingsManager();
                    enabledNotification = settingsManager.loadAndGetSettings(assetUserCommunitySubAppSession.getAppPublicKey()).getNotificationEnabled();
                }

            if (enabledNotification)
                return UserCommunityBuildNotificationPainter.getNotification(manager, code, assetUserCommunitySubAppSession.getAppPublicKey());
            else
                return null;

        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
