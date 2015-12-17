package com.bitdubai.sub_app.crypto_broker_community.app_connection;

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
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.interfaces.FermatSettings;
import com.bitdubai.sub_app.crypto_broker_community.fragmentFactory.CryptoBrokerCommunityFragmentFactory;
import com.bitdubai.sub_app.crypto_broker_community.preference_settings.CryptoBrokerCommunityPreferenceSettings;
import com.bitdubai.sub_app.crypto_broker_community.session.CryptoBrokerCommunitySubAppSession;

/**
 * Created by Matias Furszyfer on 2015.12.09..
 */
public class CryptoBrokerCommunityFermatAppConnection extends AppConnections{

    public CryptoBrokerCommunityFermatAppConnection(Activity activity) {
        super(activity);
    }

    @Override
    public FermatFragmentFactory getFragmentFactory() {
        return new CryptoBrokerCommunityFragmentFactory();
    }

    @Override
    public PluginVersionReference getPluginVersionReference() {
        return  new PluginVersionReference(
                Platforms.CRYPTO_CURRENCY_PLATFORM,
                Layers.SUB_APP_MODULE,
                Plugins.INTRA_WALLET_USER,
                Developers.BITDUBAI,
                new Version()
        );
    }

    @Override
    public AbstractFermatSession getSession() {
        return new CryptoBrokerCommunitySubAppSession();
    }

    @Override
    public FermatSettings getSettings() {
        return new CryptoBrokerCommunityPreferenceSettings();
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
