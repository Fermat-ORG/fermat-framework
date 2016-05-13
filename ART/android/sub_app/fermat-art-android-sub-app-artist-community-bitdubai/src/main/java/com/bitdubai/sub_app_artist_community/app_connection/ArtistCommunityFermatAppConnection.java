package com.bitdubai.sub_app_artist_community.app_connection;

import android.content.Context;

import com.bitdubai.fermat_android_api.engine.FermatFragmentFactory;
import com.bitdubai.fermat_android_api.engine.FooterViewPainter;
import com.bitdubai.fermat_android_api.engine.HeaderViewPainter;
import com.bitdubai.fermat_android_api.engine.NavigationViewPainter;
import com.bitdubai.fermat_android_api.engine.NotificationPainter;
import com.bitdubai.fermat_android_api.layer.definition.wallet.abstracts.AbstractFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.AppConnections;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Developers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.sub_app_artist_community.fragment_factory.ArtistCommunityFragmentFactory;
import com.bitdubai.sub_app_artist_community.navigation_drawer.ArtistCommunityNavigationViewPainter;
import com.bitdubai.sub_app_artist_community.notifications.CommunityNotificationPainterBuilder;
import com.bitdubai.sub_app_artist_community.sessions.ArtistSubAppSession;

/**
 * Created by Gabriel Araujo (gabe_512@hotmail.com) on 07/04/16.
 */
public class ArtistCommunityFermatAppConnection extends AppConnections<ArtistSubAppSession> {


    public ArtistCommunityFermatAppConnection(Context activity) {
        super(activity);
    }

    @Override
    public PluginVersionReference getPluginVersionReference() {
        return  new PluginVersionReference(
                Platforms.ART_PLATFORM,
                Layers.SUB_APP_MODULE,
                Plugins.ARTIST_COMMUNITY_SUB_APP_MODULE,
                Developers.BITDUBAI,
                new Version()
        );
    }

    @Override
    protected AbstractFermatSession getSession() {
        return new ArtistSubAppSession();
    }

    @Override
    public FermatFragmentFactory getFragmentFactory() {
        return new ArtistCommunityFragmentFactory();
    }

    @Override
    public NavigationViewPainter getNavigationViewPainter() {
        return new ArtistCommunityNavigationViewPainter(
                getContext(),
                getActiveIdentity(),
                getFullyLoadedSession());
    }

    @Override
    public NotificationPainter getNotificationPainter(String code) {
        return CommunityNotificationPainterBuilder.getNotification(
                code,
                getFullyLoadedSession()
        );
    }

    @Override
    public HeaderViewPainter getHeaderViewPainter() {
        //return new ArtistCommunityNavigationViewPainter(getContext(), getActiveIdentity(), getFullyLoadedSession());
        return null;
    }

    @Override
    public FooterViewPainter getFooterViewPainter() {
        return null;
    }
}
