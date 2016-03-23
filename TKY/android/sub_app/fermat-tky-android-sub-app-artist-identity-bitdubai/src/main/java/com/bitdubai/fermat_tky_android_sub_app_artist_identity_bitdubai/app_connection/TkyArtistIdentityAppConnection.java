package com.bitdubai.fermat_tky_android_sub_app_artist_identity_bitdubai.app_connection;

import android.content.Context;

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
import com.bitdubai.fermat_tky_android_sub_app_artist_identity_bitdubai.session.TkyIdentitySubAppSession;

/**
 * Created by Juan Sulbaran sulbaranja@gmail.com on 21/03/16.
 */
public class TkyArtistIdentityAppConnection extends AppConnections {

    public TkyArtistIdentityAppConnection(Context activity) {
        super(activity);
    }

    @Override
    public FermatFragmentFactory getFragmentFactory() {
        return null;
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
    public PluginVersionReference getPluginVersionReference() { return  new PluginVersionReference(
            Platforms.CRYPTO_CURRENCY_PLATFORM,
            Layers.SUB_APP_MODULE,
            Plugins.INTRA_IDENTITY_USER,
            Developers.BITDUBAI,
            new Version()
    );
    }

    @Override
    protected AbstractFermatSession getSession() {
        return new TkyIdentitySubAppSession();
    }

    //-----------------------------------------------------------------------------------------------------
    //not final

}
