package com.bitdubai.reference_niche_wallet.bitcoin_wallet.app_connection;

import android.app.Activity;

import com.bitdubai.fermat_android_api.engine.FooterViewPainter;
import com.bitdubai.fermat_android_api.engine.HeaderViewPainter;
import com.bitdubai.fermat_android_api.engine.NavigationViewPainter;
import com.bitdubai.fermat_android_api.layer.definition.wallet.abstracts.AbstractFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.AppConnections;
import com.bitdubai.fermat_android_api.engine.FermatFragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.FermatSession;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserLoginIdentity;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.interfaces.FermatSettings;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.header.BitcoinWalletHeaderPainter;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.navigation_drawer.BitcoinWalletNavigationViewPainter;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragment_factory.ReferenceWalletFragmentFactory;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.preference_settings.ReferenceWalletPreferenceSettings;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.session.ReferenceWalletSession;

/**
 * Created by Matias Furszyfer on 2015.12.09..
 */
public class BitcoinWalletFermatAppConnection extends AppConnections{

    IntraUserLoginIdentity intraUserLoginIdentity;

    public BitcoinWalletFermatAppConnection(Activity activity, IntraUserLoginIdentity intraUserLoginIdentity) {
        super(activity);
        this.intraUserLoginIdentity = intraUserLoginIdentity;
    }

    @Override
    public FermatFragmentFactory getFragmentFactory() {
        return new ReferenceWalletFragmentFactory();
    }

    @Override
    public PluginVersionReference getPluginVersionReference() {
        return null;
    }

    @Override
    public AbstractFermatSession getSession() {
        return new ReferenceWalletSession();
    }

    @Override
    public FermatSettings getSettings() {
        return new ReferenceWalletPreferenceSettings();
    }

    @Override
    public NavigationViewPainter getNavigationViewPainter() {
        return new BitcoinWalletNavigationViewPainter(getActivity(),intraUserLoginIdentity);
    }

    @Override
    public HeaderViewPainter getHeaderViewPainter() {
        return new BitcoinWalletHeaderPainter();
    }

    @Override
    public FooterViewPainter getFooterViewPainter() {
        return null;
    }
}
