package com.bitdubai.sub_app.wallet_store.app_connection;

import android.content.Context;
import com.bitdubai.fermat_android_api.engine.FermatFragmentFactory;
import com.bitdubai.fermat_android_api.engine.FooterViewPainter;
import com.bitdubai.fermat_android_api.engine.HeaderViewPainter;
import com.bitdubai.fermat_android_api.engine.NavigationViewPainter;
import com.bitdubai.fermat_android_api.layer.definition.wallet.abstracts.AbstractReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.AppConnections;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Developers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.sub_app.wallet_store.fragmentFactory.WalletStoreFragmentFactory;
import com.bitdubai.sub_app.wallet_store.session.WalletStoreSubAppSessionReferenceApp;

/**
 * Created by Nelson Ramirez
 *
 * @since 2015.12.17
 */
public class WalletStoreFermatAppConnection extends AppConnections<WalletStoreSubAppSessionReferenceApp> {

    public WalletStoreFermatAppConnection(Context activity) {
        super(activity);
    }

    @Override
    public FermatFragmentFactory getFragmentFactory() {
        return new WalletStoreFragmentFactory();
    }

    @Override
    public PluginVersionReference[] getPluginVersionReference() {
        return new PluginVersionReference[]{ new PluginVersionReference(
                Platforms.WALLET_PRODUCTION_AND_DISTRIBUTION,
                Layers.SUB_APP_MODULE,
                Plugins.WALLET_STORE,
                Developers.BITDUBAI,
                new Version()
        )};
    }

    @Override
    public AbstractReferenceAppFermatSession getSession() {
        return new WalletStoreSubAppSessionReferenceApp();
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
