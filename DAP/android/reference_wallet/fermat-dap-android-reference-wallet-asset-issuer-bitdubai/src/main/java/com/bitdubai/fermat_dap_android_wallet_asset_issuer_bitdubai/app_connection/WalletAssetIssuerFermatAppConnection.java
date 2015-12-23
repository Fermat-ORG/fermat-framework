package com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.app_connection;

import android.app.Activity;

import com.bitdubai.fermat_android_api.engine.FooterViewPainter;
import com.bitdubai.fermat_android_api.engine.HeaderViewPainter;
import com.bitdubai.fermat_android_api.engine.NavigationViewPainter;
import com.bitdubai.fermat_android_api.layer.definition.wallet.abstracts.AbstractFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.AppConnections;
import com.bitdubai.fermat_android_api.engine.FermatFragmentFactory;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Developers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.common.header.WalletAssetIssuerHeaderPainter;
import com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.factory.IssuerWalletFragmentFactory;
import com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.common.navigation_drawer.IssuerWalletNavigationViewPainter;
import com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.sessions.AssetIssuerSession;
import com.bitdubai.fermat_dap_api.layer.dap_identity.asset_issuer.interfaces.IdentityAssetIssuer;

/**
 * Created by Matias Furszyfer on 2015.12.09..
 */
public class WalletAssetIssuerFermatAppConnection extends AppConnections {

    IdentityAssetIssuer identityAssetIssuer;

    public WalletAssetIssuerFermatAppConnection(Activity activity) {
        super(activity);
        this.identityAssetIssuer = identityAssetIssuer;
    }

    @Override
    public FermatFragmentFactory getFragmentFactory() {
        return new IssuerWalletFragmentFactory();
    }

    @Override
    public PluginVersionReference getPluginVersionReference() {
        return  new PluginVersionReference(
                Platforms.DIGITAL_ASSET_PLATFORM,
                Layers.WALLET_MODULE,
                Plugins.ASSET_ISSUER,
                Developers.BITDUBAI,
                new Version()
            );
    }

    @Override
    public AbstractFermatSession getSession() {
        return new AssetIssuerSession();
    }

    @Override
    public NavigationViewPainter getNavigationViewPainter() {
        return new IssuerWalletNavigationViewPainter(getActivity(), identityAssetIssuer);
    }

    @Override
    public HeaderViewPainter getHeaderViewPainter() {
        return new WalletAssetIssuerHeaderPainter();
    }

    @Override
    public FooterViewPainter getFooterViewPainter() {
        return null;
    }
}
