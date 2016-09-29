package com.bitdubai.sub_app.intra_user_community.app_connection;

import android.content.Context;

import com.bitdubai.fermat_android_api.core.ResourceSearcher;
import com.bitdubai.fermat_android_api.engine.FermatFragmentFactory;
import com.bitdubai.fermat_android_api.engine.FooterViewPainter;
import com.bitdubai.fermat_android_api.engine.HeaderViewPainter;
import com.bitdubai.fermat_android_api.engine.NavigationViewPainter;
import com.bitdubai.fermat_android_api.engine.NotificationPainter;
import com.bitdubai.fermat_android_api.layer.definition.wallet.abstracts.AbstractReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.AppConnections;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Developers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.FermatBundle;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.NotificationBundleConstants;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserModuleManager;
import com.bitdubai.sub_app.intra_user_community.fragmentFactory.IntraUserFragmentFactory;
import com.bitdubai.sub_app.intra_user_community.navigation_drawer.IntraUserCommunityNavigationViewPainter;

/**
 * Created by Matias Furszyfer on 2015.12.09..
 */
public class CryptoWalletUserCommunityFermatAppConnection extends AppConnections<ReferenceAppFermatSession>{

   private ReferenceAppFermatSession<IntraUserModuleManager> intraUserSubAppSession;
    private IntraUserModuleManager moduleManager;

    public CryptoWalletUserCommunityFermatAppConnection(Context activity) {
        super(activity);

    }

    @Override
    public FermatFragmentFactory getFragmentFactory() {
        return new IntraUserFragmentFactory();
    }

    @Override
    public PluginVersionReference[] getPluginVersionReference() {
        return  new PluginVersionReference[]{ new PluginVersionReference(
                Platforms.CRYPTO_CURRENCY_PLATFORM,
                Layers.SUB_APP_MODULE,
                Plugins.INTRA_WALLET_USER,
                Developers.BITDUBAI,
                new Version()
        )};
    }

    @Override
    public AbstractReferenceAppFermatSession getSession() {
        return (AbstractReferenceAppFermatSession)this.getFullyLoadedSession();
    }

    @Override
    public NavigationViewPainter getNavigationViewPainter() {

        //TODO: el actorIdentityInformation lo podes obtener del module en un hilo en background y hacer un lindo loader mientras tanto
        return new IntraUserCommunityNavigationViewPainter(getContext(),this.getFullyLoadedSession(),getApplicationManager());

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
    public NotificationPainter getNotificationPainter(FermatBundle fermatBundle){
        try
        {
            int notificationID = fermatBundle.getInt(NotificationBundleConstants.NOTIFICATION_ID);
            String involvedActor =  fermatBundle.getString("InvolvedActor");

            this.intraUserSubAppSession = this.getFullyLoadedSession();
            if(intraUserSubAppSession!=  null)
            return CryptoWalletUserCommunityBuildNotification.getNotification(notificationID,involvedActor, Activities.CCP_SUB_APP_INTRA_USER_COMMUNITY_CONNECTION_NOTIFICATIONS.getCode(), getContext());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public ResourceSearcher getResourceSearcher() {
        return new CryptoWalletUserSearcher();
    }
}
