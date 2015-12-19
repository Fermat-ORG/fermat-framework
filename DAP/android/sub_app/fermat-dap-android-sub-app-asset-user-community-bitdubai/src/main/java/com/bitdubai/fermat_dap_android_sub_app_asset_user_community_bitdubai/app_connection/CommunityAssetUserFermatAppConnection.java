package com.bitdubai.fermat_dap_android_sub_app_asset_user_community_bitdubai.app_connection;

import android.app.Activity;

import com.bitdubai.fermat_android_api.engine.FermatFragmentFactory;
import com.bitdubai.fermat_android_api.engine.FooterViewPainter;
import com.bitdubai.fermat_android_api.engine.HeaderViewPainter;
import com.bitdubai.fermat_android_api.engine.NavigationViewPainter;
import com.bitdubai.fermat_android_api.layer.definition.wallet.abstracts.AbstractFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.AppConnections;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Developers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_dap_android_sub_app_asset_user_community_bitdubai.factory.CommunityUserFragmentFactory;
import com.bitdubai.fermat_dap_android_sub_app_asset_user_community_bitdubai.sessions.AssetUserCommunitySubAppSession;
import com.bitdubai.fermat_dap_android_sub_app_asset_user_community_bitdubai.settings.Settings;
import com.bitdubai.fermat_api.layer.modules.FermatSettings;

/**
 * Created by Matias Furszyfer on 2015.12.09..
 */
public class CommunityAssetUserFermatAppConnection extends AppConnections{

    public CommunityAssetUserFermatAppConnection(Activity activity) {
        super(activity);
    }

    @Override
    public FermatFragmentFactory getFragmentFactory() {
        return new CommunityUserFragmentFactory();
    }

    @Override
    public PluginVersionReference getPluginVersionReference() {
        return  new PluginVersionReference(
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
    public FermatSettings getSettings() {
        return new Settings();
    }

    @Override
    public NavigationViewPainter getNavigationViewPainter() {
        return null;
    }

    @Override
    public HeaderViewPainter getHeaderViewPainter() {
        return null;
    }

    @Override
    public FooterViewPainter getFooterViewPainter() {
        return null;
    }
}
