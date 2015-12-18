package com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.app_connection;

import android.app.Activity;

import com.bitdubai.fermat_android_api.engine.FooterViewPainter;
import com.bitdubai.fermat_android_api.engine.HeaderViewPainter;
import com.bitdubai.fermat_android_api.engine.NavigationViewPainter;
import com.bitdubai.fermat_android_api.layer.definition.wallet.abstracts.AbstractFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.AppConnections;
import com.bitdubai.fermat_android_api.engine.FermatFragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.FermatSession;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Developers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.common.header.WalletAssetUserHeaderPainter;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.factory.WalletAssetUserFragmentFactory;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.navigation_drawer.UserWalletNavigationViewPainter;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.sessions.AssetUserSession;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.settings.AssetUserSettings;
import com.bitdubai.fermat_dap_api.layer.dap_identity.asset_user.interfaces.IdentityAssetUser;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.interfaces.FermatSettings;

/**
 * Created by Matias Furszyfer on 2015.12.09..
 */
public class WalletAssetUserFermatAppConnection extends AppConnections{

    IdentityAssetUser identityAssetUser;

    public WalletAssetUserFermatAppConnection(Activity activity, IdentityAssetUser identityAssetUser) {
        super(activity);
        this.identityAssetUser = identityAssetUser;
    }

    @Override
    public FermatFragmentFactory getFragmentFactory() {
        return new WalletAssetUserFragmentFactory();
    }

    @Override
    public PluginVersionReference getPluginVersionReference() {
        return  new PluginVersionReference(
                Platforms.DIGITAL_ASSET_PLATFORM,
                Layers.WALLET_MODULE,
                Plugins.ASSET_USER,
                Developers.BITDUBAI,
                new Version()
            );
    }

    @Override
    public AbstractFermatSession getSession() {
        return new AssetUserSession();
    }

    @Override
    public FermatSettings getSettings() {
        return new AssetUserSettings();
    }

    @Override
    public NavigationViewPainter getNavigationViewPainter() {
        return new UserWalletNavigationViewPainter(getActivity(),identityAssetUser);
    }

    @Override
    public HeaderViewPainter getHeaderViewPainter() {
        return new WalletAssetUserHeaderPainter();
    }

    @Override
    public FooterViewPainter getFooterViewPainter() {
        return null;
    }
}
