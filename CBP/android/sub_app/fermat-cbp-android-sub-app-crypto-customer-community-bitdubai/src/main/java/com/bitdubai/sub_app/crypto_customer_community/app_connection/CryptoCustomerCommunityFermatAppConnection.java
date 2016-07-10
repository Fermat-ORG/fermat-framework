package com.bitdubai.sub_app.crypto_customer_community.app_connection;

import android.content.Context;

import com.bitdubai.fermat_android_api.core.ResourceSearcher;
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
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.FermatBundle;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.NotificationBundleConstants;
import com.bitdubai.fermat_cbp_api.all_definition.constants.CBPBroadcasterConstants;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.interfaces.CryptoCustomerCommunitySubAppModuleManager;
import com.bitdubai.sub_app.crypto_customer_community.R;
import com.bitdubai.sub_app.crypto_customer_community.common.notifications.CommunityNotificationPainter;
import com.bitdubai.sub_app.crypto_customer_community.fragmentFactory.CryptoCustomerCommunityFragmentFactory;


/**
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 18/12/2015.
 *
 * @author lnacosta
 * @version 1.0.0
 */
public class CryptoCustomerCommunityFermatAppConnection extends AppConnections<ReferenceAppFermatSession<CryptoCustomerCommunitySubAppModuleManager>> {

    private CryptoCustomerCommunityResourceSearcher resourceSearcher;


    public CryptoCustomerCommunityFermatAppConnection(Context activity) {
        super(activity);
    }

    @Override
    public FermatFragmentFactory getFragmentFactory() {
        return new CryptoCustomerCommunityFragmentFactory();
    }

    @Override
    public PluginVersionReference[] getPluginVersionReference() {
        return new PluginVersionReference[]{new PluginVersionReference(
                Platforms.CRYPTO_BROKER_PLATFORM,
                Layers.SUB_APP_MODULE,
                Plugins.CRYPTO_CUSTOMER_COMMUNITY,
                Developers.BITDUBAI,
                new Version()
        )};
    }

    @Override
    public ReferenceAppFermatSession<CryptoCustomerCommunitySubAppModuleManager> getSession() {
        return getFullyLoadedSession();
    }


    @Override
    public NavigationViewPainter getNavigationViewPainter() {
        //TODO: Deshabilitado hasta nuevo aviso
        // return new CustomerCommunityNavigationViewPainter(getContext(), getFullyLoadedSession());
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
    public NotificationPainter getNotificationPainter(FermatBundle fermatBundle) {
        int notificationID = fermatBundle.getInt(NotificationBundleConstants.NOTIFICATION_ID);

        switch (notificationID) {
            case CBPBroadcasterConstants.CCC_CONNECTION_REQUEST_RECEIVED:
                return new CommunityNotificationPainter("Crypto Customer Community", "A customer wants to connect with you.",
                        "", R.drawable.cbc_ic_nav_connections);
            default:
                return null;
        }
    }

    @Override
    public ResourceSearcher getResourceSearcher() {
        if (resourceSearcher == null)
            resourceSearcher = new CryptoCustomerCommunityResourceSearcher();
        return resourceSearcher;
    }
}
