package com.bitdubai.reference_wallet.crypto_customer_wallet.app_connection;

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
import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.interfaces.FermatSettings;
import com.bitdubai.reference_wallet.crypto_customer_wallet.common.header.CryptoCustomerWalletHeaderPainter;
import com.bitdubai.reference_wallet.crypto_customer_wallet.common.navigationDrawer.CustomerNavigationViewPainter;
import com.bitdubai.reference_wallet.crypto_customer_wallet.fragmentFactory.CryptoCustomerWalletFragmentFactory;
import com.bitdubai.reference_wallet.crypto_customer_wallet.preference_settings.CryptoCustomerWalletPreferenceSettings;
import com.bitdubai.reference_wallet.crypto_customer_wallet.session.CryptoCustomerWalletSession;

/**
 * Created by Nelson Ramirez
 *
 * @since 2015.12.17
 */
public class CryptoCustomerWalletFermatAppConnection extends AppConnections {

    ActorIdentity identity;

    public CryptoCustomerWalletFermatAppConnection(Activity activity, ActorIdentity identity) {
        super(activity);
        this.identity = identity;
    }

    @Override
    public FermatFragmentFactory getFragmentFactory() {
        return new CryptoCustomerWalletFragmentFactory();
    }

    @Override
    public PluginVersionReference getPluginVersionReference() {
        return new PluginVersionReference(
                Platforms.CRYPTO_BROKER_PLATFORM,
                Layers.WALLET_MODULE,
                Plugins.CRYPTO_CUSTOMER,
                Developers.BITDUBAI,
                new Version()
        );
    }

    @Override
    protected AbstractFermatSession getSession() {
        return new CryptoCustomerWalletSession();
    }


    @Override
    public NavigationViewPainter getNavigationViewPainter() {
        return new CustomerNavigationViewPainter(getActivity(), identity);
    }

    @Override
    public HeaderViewPainter getHeaderViewPainter() {
        return new CryptoCustomerWalletHeaderPainter();
    }

    @Override
    public FooterViewPainter getFooterViewPainter() {
        return null;
    }
}
