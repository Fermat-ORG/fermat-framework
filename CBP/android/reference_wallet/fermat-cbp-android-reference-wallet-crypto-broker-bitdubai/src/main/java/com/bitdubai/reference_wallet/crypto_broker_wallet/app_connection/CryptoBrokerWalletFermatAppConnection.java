package com.bitdubai.reference_wallet.crypto_broker_wallet.app_connection;

import android.app.Activity;

import com.bitdubai.fermat_android_api.engine.FermatFragmentFactory;
import com.bitdubai.fermat_android_api.engine.FooterViewPainter;
import com.bitdubai.fermat_android_api.engine.HeaderViewPainter;
import com.bitdubai.fermat_android_api.engine.NavigationViewPainter;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.AppConnections;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.FermatSession;
import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.interfaces.FermatSettings;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.footer.CryptoBrokerWalletFooterPainter;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.header.CryptoBrokerWalletHeaderPainter;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.navigationDrawer.CryptoBrokerNavigationViewPainter;
import com.bitdubai.reference_wallet.crypto_broker_wallet.fragmentFactory.CryptoBrokerWalletFragmentFactory;
import com.bitdubai.reference_wallet.crypto_broker_wallet.preference_settings.CryptoBrokerWalletPreferenceSettings;
import com.bitdubai.reference_wallet.crypto_broker_wallet.session.CryptoBrokerWalletSession;

/**
 * Created by Nelson Ramirez
 *
 * @since 2015.12.17
 */
public class CryptoBrokerWalletFermatAppConnection extends AppConnections {

    Activity activity;
    ActorIdentity identity;

    public CryptoBrokerWalletFermatAppConnection(Activity activity, ActorIdentity identity) {
        this.activity = activity;
        this.identity = identity;
    }

    @Override
    public FermatFragmentFactory getFragmentFactory() {
        return new CryptoBrokerWalletFragmentFactory();
    }

    @Override
    public FermatSession getSession() {
        return new CryptoBrokerWalletSession();
    }

    @Override
    public FermatSettings getSettings() {
        return new CryptoBrokerWalletPreferenceSettings();
    }

    @Override
    public NavigationViewPainter getNavigationViewPainter() {
        return new CryptoBrokerNavigationViewPainter(activity, null);
    }

    @Override
    public HeaderViewPainter getHeaderViewPainter() {
        return new CryptoBrokerWalletHeaderPainter();
    }

    @Override
    public FooterViewPainter getFooterViewPainter() {
        return new CryptoBrokerWalletFooterPainter();
    }
}
