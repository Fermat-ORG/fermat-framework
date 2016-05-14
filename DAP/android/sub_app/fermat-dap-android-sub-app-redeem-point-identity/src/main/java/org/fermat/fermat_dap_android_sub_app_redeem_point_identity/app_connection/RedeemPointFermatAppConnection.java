package org.fermat.fermat_dap_android_sub_app_redeem_point_identity.app_connection;

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

import org.fermat.fermat_dap_android_sub_app_redeem_point_identity.fragmentFactory.RedeemPointIdentityFragmentFactory;
import org.fermat.fermat_dap_android_sub_app_redeem_point_identity.session.RedeemPointIdentitySubAppSession;

/**
 * Created by Matias Furszyfer on 2015.12.09..
 */
public class RedeemPointFermatAppConnection extends AppConnections{

    public RedeemPointFermatAppConnection(Context activity) {
        super(activity);
    }

    @Override
    public FermatFragmentFactory getFragmentFactory() {
        return new RedeemPointIdentityFragmentFactory();
    }

    @Override
    public PluginVersionReference getPluginVersionReference() {
        return  new PluginVersionReference(
                Platforms.DIGITAL_ASSET_PLATFORM,
                Layers.IDENTITY,
                Plugins.REDEEM_POINT,
                Developers.BITDUBAI,
                new Version()
        );
    }

    @Override
    public AbstractFermatSession getSession() {
        return new RedeemPointIdentitySubAppSession();
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
