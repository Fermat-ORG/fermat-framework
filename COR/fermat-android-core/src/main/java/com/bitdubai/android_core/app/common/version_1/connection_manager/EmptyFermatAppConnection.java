package com.bitdubai.android_core.app.common.version_1.connection_manager;

import android.content.Context;

import com.bitdubai.fermat_android_api.engine.FermatFragmentFactory;
import com.bitdubai.fermat_android_api.engine.FooterViewPainter;
import com.bitdubai.fermat_android_api.engine.HeaderViewPainter;
import com.bitdubai.fermat_android_api.engine.NavigationViewPainter;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;

/**
 * Created by mati on 2016.06.06..
 */
public class EmptyFermatAppConnection extends com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.AppConnections {
    public EmptyFermatAppConnection(Context activity) {
        super(activity);
    }

    @Override
    public PluginVersionReference[] getPluginVersionReference() {
        return new PluginVersionReference[0];
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
}
