package com.bitdubai.fermat_art_android_sub_app_artist_identity_bitdubai.factory.app_connection;



import android.content.Context;

import com.bitdubai.fermat_android_api.engine.FermatFragmentFactory;
import com.bitdubai.fermat_android_api.engine.FooterViewPainter;
import com.bitdubai.fermat_android_api.engine.HeaderViewPainter;
import com.bitdubai.fermat_android_api.engine.NavigationViewPainter;
import com.bitdubai.fermat_android_api.layer.definition.wallet.abstracts.AbstractFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.AppConnections;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_art_android_sub_app_artist_identity_bitdubai.factory.session.ArtistIdentitySubAppSession;

/**
 * Created by Juan Sulbaran sulbaranja@gmail.com on 17/03/16.
 */
public class ArtArtistIdentityAppConnection extends AppConnections {

    public ArtArtistIdentityAppConnection(Context activity) {
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
    public PluginVersionReference getPluginVersionReference() {
        return null;
    }

    @Override
    protected AbstractFermatSession getSession() {
        return null;
    }

    //-----------------------------------------------------------------------------------------------------
    //not final

}
