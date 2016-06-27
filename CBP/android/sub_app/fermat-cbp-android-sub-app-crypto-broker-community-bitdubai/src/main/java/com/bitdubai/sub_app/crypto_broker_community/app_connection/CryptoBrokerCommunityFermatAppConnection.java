package com.bitdubai.sub_app.crypto_broker_community.app_connection;

import android.content.Context;

import com.bitdubai.fermat_android_api.engine.FermatFragmentFactory;
import com.bitdubai.fermat_android_api.engine.FooterViewPainter;
import com.bitdubai.fermat_android_api.engine.HeaderViewPainter;
import com.bitdubai.fermat_android_api.engine.NavigationViewPainter;
import com.bitdubai.fermat_android_api.engine.NotificationPainter;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.AppConnections;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Developers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.interfaces.CryptoBrokerCommunitySubAppModuleManager;
import com.bitdubai.sub_app.crypto_broker_community.fragmentFactory.CryptoBrokerCommunityFragmentFactory;
import com.bitdubai.sub_app.crypto_broker_community.common.navigationDrawer.BrokerCommunityNavigationViewPainter;
import com.bitdubai.sub_app.crypto_broker_community.common.notifications.CommunityNotificationPainterBuilder;

/**
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 18/12/2015.
 *
 * @author lnacosta
 * @version 1.0.0
 */
public class CryptoBrokerCommunityFermatAppConnection extends AppConnections<ReferenceAppFermatSession<CryptoBrokerCommunitySubAppModuleManager>> {

    public CryptoBrokerCommunityFermatAppConnection(Context activity) {
        super(activity);
    }

    @Override
    public FermatFragmentFactory getFragmentFactory() {
        return new CryptoBrokerCommunityFragmentFactory();
    }

    @Override
    public PluginVersionReference[] getPluginVersionReference() {
        return  new PluginVersionReference[]{ new PluginVersionReference(
                Platforms.CRYPTO_BROKER_PLATFORM,
                Layers.SUB_APP_MODULE,
                Plugins.CRYPTO_BROKER_COMMUNITY,
                Developers.BITDUBAI,
                new Version()
        )};
    }

    @Override
    public ReferenceAppFermatSession<CryptoBrokerCommunitySubAppModuleManager> getSession() {
        return getFullyLoadedSession();
    }


    @Override
    public NavigationViewPainter getNavigationViewPainter() {
        //TODO: el actorIdentityInformation lo podes obtener del module en un hilo en background y hacer un lindo loader mientras tanto
        return new BrokerCommunityNavigationViewPainter(getContext(), getFullyLoadedSession());
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
    public NotificationPainter getNotificationPainter(final String code) {

        return CommunityNotificationPainterBuilder.getNotification(
                code
        );
    }

}
