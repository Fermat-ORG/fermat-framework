package com.bitdubai.sub_app.crypto_customer_community.app_connection;

import android.app.Activity;

import com.bitdubai.fermat_android_api.engine.FermatFragmentFactory;
import com.bitdubai.fermat_android_api.engine.FooterViewPainter;
import com.bitdubai.fermat_android_api.engine.HeaderViewPainter;
import com.bitdubai.fermat_android_api.engine.NavigationViewPainter;
import com.bitdubai.fermat_android_api.engine.NotificationPainter;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.AppConnections;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Developers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.sub_app.crypto_customer_community.fragmentFactory.CryptoCustomerCommunityFragmentFactory;
import com.bitdubai.sub_app.crypto_customer_community.navigationDrawer.CustomerCommunityNavigationViewPainter;
import com.bitdubai.sub_app.crypto_customer_community.notifications.CommunityNotificationPainterBuilder;
import com.bitdubai.sub_app.crypto_customer_community.session.CryptoCustomerCommunitySubAppSession;

/**
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 18/12/2015.
 *
 * @author lnacosta
 * @version 1.0.0
 */
public class CryptoCustomerCommunityFermatAppConnection extends AppConnections<CryptoCustomerCommunitySubAppSession> {

    public CryptoCustomerCommunityFermatAppConnection(Activity activity) {
        super(activity);
    }

    @Override
    public FermatFragmentFactory getFragmentFactory() {
        return new CryptoCustomerCommunityFragmentFactory();
    }

    @Override
    public PluginVersionReference getPluginVersionReference() {
        return  new PluginVersionReference(
                Platforms.CRYPTO_BROKER_PLATFORM,
                Layers.SUB_APP_MODULE,
                Plugins.CRYPTO_CUSTOMER_COMMUNITY,
                Developers.BITDUBAI,
                new Version()
        );
    }

    @Override
    public CryptoCustomerCommunitySubAppSession getSession() {
        return new CryptoCustomerCommunitySubAppSession();
    }


    @Override
    public NavigationViewPainter getNavigationViewPainter() {
        return new CustomerCommunityNavigationViewPainter(getActivity(), getActiveIdentity(),
                getFullyLoadedSession());

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
