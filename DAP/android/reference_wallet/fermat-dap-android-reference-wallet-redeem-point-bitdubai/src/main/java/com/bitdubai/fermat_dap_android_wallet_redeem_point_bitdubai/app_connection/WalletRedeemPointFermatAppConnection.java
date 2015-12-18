package com.bitdubai.fermat_dap_android_wallet_redeem_point_bitdubai.app_connection;

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
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserLoginIdentity;
import com.bitdubai.fermat_dap_android_wallet_redeem_point_bitdubai.common.header.WalletRedeemPointHeaderPainter;
import com.bitdubai.fermat_dap_android_wallet_redeem_point_bitdubai.factory.WalletRedeemPointFragmentFactory;
import com.bitdubai.fermat_dap_android_wallet_redeem_point_bitdubai.navigation_drawer.RedeemPointWalletNavigationViewPainter;
import com.bitdubai.fermat_dap_android_wallet_redeem_point_bitdubai.sessions.RedeemPointSession;
import com.bitdubai.fermat_dap_android_wallet_redeem_point_bitdubai.settings.RedeemPointSettings;
import com.bitdubai.fermat_dap_api.layer.dap_identity.redeem_point.interfaces.RedeemPointIdentity;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.interfaces.FermatSettings;

/**
 * Created by Matias Furszyfer on 2015.12.09..
 */
public class WalletRedeemPointFermatAppConnection extends AppConnections{

    RedeemPointIdentity redeemPointIdentity;

    public WalletRedeemPointFermatAppConnection(Activity activity, RedeemPointIdentity redeemPointIdentity) {
        super(activity);
        this.redeemPointIdentity = redeemPointIdentity;
    }

    @Override
    public FermatFragmentFactory getFragmentFactory() {
        return new WalletRedeemPointFragmentFactory();
    }

    @Override
    public PluginVersionReference getPluginVersionReference() {
        return  new PluginVersionReference(
                Platforms.DIGITAL_ASSET_PLATFORM,
                Layers.WALLET_MODULE,
                Plugins.REDEEM_POINT,
                Developers.BITDUBAI,
                new Version()
            );
    }

    @Override
    public AbstractFermatSession getSession() {
        return new RedeemPointSession();
    }

    @Override
    public FermatSettings getSettings() {
        return new RedeemPointSettings();
    }

    @Override
    public NavigationViewPainter getNavigationViewPainter() {
        return new RedeemPointWalletNavigationViewPainter(getActivity(),redeemPointIdentity);
    }

    @Override
    public HeaderViewPainter getHeaderViewPainter() {
        return new WalletRedeemPointHeaderPainter();
    }

    @Override
    public FooterViewPainter getFooterViewPainter() {
        return null;
    }
}
