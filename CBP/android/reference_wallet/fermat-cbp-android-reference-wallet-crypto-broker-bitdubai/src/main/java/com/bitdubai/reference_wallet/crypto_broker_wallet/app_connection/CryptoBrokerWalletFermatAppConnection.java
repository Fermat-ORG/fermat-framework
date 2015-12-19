package com.bitdubai.reference_wallet.crypto_broker_wallet.app_connection;

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
import com.bitdubai.fermat_api.layer.modules.FermatSettings;
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

    ActorIdentity identity;

    public CryptoBrokerWalletFermatAppConnection(Activity activity, ActorIdentity identity) {
        super(activity);
        this.identity = identity;
    }

    @Override
    public FermatFragmentFactory getFragmentFactory() {
        return new CryptoBrokerWalletFragmentFactory();
    }

    @Override
    public PluginVersionReference getPluginVersionReference() {
        return new PluginVersionReference(
                Platforms.CRYPTO_BROKER_PLATFORM,
                Layers.WALLET_MODULE,
                Plugins.CRYPTO_BROKER,
                Developers.BITDUBAI,
                new Version()
        );

    }
    @Override
    protected AbstractFermatSession getSession() {
        return new CryptoBrokerWalletSession();
    }


    @Override
    public NavigationViewPainter getNavigationViewPainter() {
        return new CryptoBrokerNavigationViewPainter(getActivity(), identity);
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
