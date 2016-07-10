package com.bitdubai.sub_app.wallet_manager.app_connection;

import android.content.Context;
import com.bitdubai.fermat_android_api.engine.*;
import com.bitdubai.fermat_android_api.layer.definition.wallet.abstracts.AbstractReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.AppConnections;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Developers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.WalletManager;
import com.bitdubai.sub_app.wallet_manager.fragment_factory.DesktopFragmentFactory;
import com.bitdubai.sub_app.wallet_manager.session.DesktopSessionReferenceApp;

/**
 * Created by Matias Furszyfer on 2015.12.09..
 */
public class DesktopFermatAppConnection extends AppConnections<ReferenceAppFermatSession<WalletManager>> {

    public DesktopFermatAppConnection(Context activity) {
        super(activity);
    }

    @Override
    public FermatFragmentFactory getFragmentFactory() {
        return new DesktopFragmentFactory();
    }

    @Override
    public PluginVersionReference[] getPluginVersionReference() {
        return  new PluginVersionReference[]{ new PluginVersionReference(
                Platforms.CRYPTO_CURRENCY_PLATFORM,
                Layers.DESKTOP_MODULE,
                Plugins.WALLET_MANAGER,
                Developers.BITDUBAI,
                new Version()
        )};
    }

    @Override
    public AbstractReferenceAppFermatSession getSession() {
        return new DesktopSessionReferenceApp();
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
    public NotificationPainter getNotificationPainter(String code){
        return null;
    }
}
