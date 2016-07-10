package com.bitdubai.sub_app.intra_user_identity.app_connection;

import android.content.Context;

import com.bitdubai.fermat_android_api.core.ResourceSearcher;
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
import com.bitdubai.sub_app.intra_user_identity.R;
import com.bitdubai.sub_app.intra_user_identity.fragment_factory.IntraUserIdentityFragmentFactory;
import com.bitdubai.sub_app.intra_user_identity.session.IntraUserIdentitySubAppSessionReferenceApp;

/**
 * Created by Matias Furszyfer on 2015.12.09..
 */
public class CryptoWalletUserFermatAppConnection extends AppConnections{

    public CryptoWalletUserFermatAppConnection(Context activity) {
        super(activity);
    }

    @Override
    public FermatFragmentFactory getFragmentFactory() {
        return new IntraUserIdentityFragmentFactory();
    }

    @Override
    public PluginVersionReference[] getPluginVersionReference() {
        return  new PluginVersionReference[]{ new PluginVersionReference(
                Platforms.CRYPTO_CURRENCY_PLATFORM,
                Layers.SUB_APP_MODULE,
                Plugins.INTRA_IDENTITY_USER,
                Developers.BITDUBAI,
                new Version()
        )};
    }

    @Override
    public AbstractReferenceAppFermatSession getSession() {
        return new IntraUserIdentitySubAppSessionReferenceApp();
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

    @Override
    public ResourceSearcher getResourceSearcher() {
        return new CryptoWalletUserIdentitySearcher();
    }
}
