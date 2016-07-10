package com.bitdubai.sub_app.art_fan_identity.app_connection;

import android.content.Context;

import com.bitdubai.fermat_android_api.engine.FermatFragmentFactory;
import com.bitdubai.fermat_android_api.engine.FooterViewPainter;
import com.bitdubai.fermat_android_api.engine.HeaderViewPainter;
import com.bitdubai.fermat_android_api.engine.NavigationViewPainter;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.AppConnections;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Developers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.sub_app.art_fan_identity.fragment_factory.ArtFanUserIdentityFragmentFactory;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 08/04/16.
 */
public class ArtFanUserFermatAppConnection extends AppConnections {

    public ArtFanUserFermatAppConnection(Context activity) {
        super(activity);
    }

    @Override
    public PluginVersionReference[] getPluginVersionReference() {
        return  new PluginVersionReference[]{
                    new PluginVersionReference(
                        Platforms.ART_PLATFORM,
                        Layers.SUB_APP_MODULE,
                        Plugins.ART_FAN_SUB_APP_MODULE,
                        Developers.BITDUBAI,
                        new Version()
                    )
        };
    }

    @Override
    public FermatFragmentFactory getFragmentFactory() {
        return new ArtFanUserIdentityFragmentFactory();
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
