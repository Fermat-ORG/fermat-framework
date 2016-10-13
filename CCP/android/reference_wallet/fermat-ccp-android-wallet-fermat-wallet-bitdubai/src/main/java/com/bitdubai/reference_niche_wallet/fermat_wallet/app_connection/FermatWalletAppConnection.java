package com.bitdubai.reference_niche_wallet.fermat_wallet.app_connection;

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
import com.bitdubai.fermat_ccp_api.layer.wallet_module.fermat_wallet.interfaces.FermatWallet;
import com.bitdubai.reference_niche_wallet.fermat_wallet.common.header.FermatWalletHeaderPainter;
import com.bitdubai.reference_niche_wallet.fermat_wallet.common.navigation_drawer.FermatWalletNavigationViewPainter;
import com.bitdubai.reference_niche_wallet.fermat_wallet.fragment_factory.ReferenceWalletFragmentFactory;
import com.bitdubai.reference_niche_wallet.fermat_wallet.session.FermatWalletSessionReferenceApp;


/**
 * Created by Matias Furszyfer on 2015.12.09..
 */

public class FermatWalletAppConnection extends AppConnections<ReferenceAppFermatSession<FermatWallet>>{

    private FermatWallet moduleManager = null;
    private ReferenceAppFermatSession<FermatWallet> referenceWalletSession;


    public FermatWalletAppConnection(Context activity) {
        super(activity);
    }

    @Override
    public FermatFragmentFactory getFragmentFactory() {
        return new ReferenceWalletFragmentFactory();
    }

    @Override
    public PluginVersionReference[] getPluginVersionReference() {
        return  new PluginVersionReference[]{ new PluginVersionReference(
                Platforms.CRYPTO_CURRENCY_PLATFORM,
                Layers.WALLET_MODULE,
                Plugins.CRYPTO_FERMAT_WALLET,
                Developers.BITDUBAI,
                new Version()
            )};
    }

    @Override

    public AbstractReferenceAppFermatSession getSession() {
        return new FermatWalletSessionReferenceApp();
    }

    @Override
    public NavigationViewPainter getNavigationViewPainter() {

        //TODO: el actorIdentityInformation lo podes obtener del module en un hilo en background y hacer un lindo loader mientras tanto

        return new FermatWalletNavigationViewPainter(getContext(),this.getFullyLoadedSession(),getApplicationManager());

    }

    @Override
    public HeaderViewPainter getHeaderViewPainter() {
        return new FermatWalletHeaderPainter();
    }

    @Override
    public FooterViewPainter getFooterViewPainter() {
        return null;
    }

    @Override
    public NotificationPainter getNotificationPainter(String code){
     try
        {
           boolean enabledNotification = true;
            this.referenceWalletSession = this.getFullyLoadedSession();
            if(referenceWalletSession!=  null) {
                if (referenceWalletSession.getModuleManager() != null) {
                    moduleManager = referenceWalletSession.getModuleManager();
                    enabledNotification = referenceWalletSession.getModuleManager().loadAndGetSettings(referenceWalletSession.getAppPublicKey()).getNotificationEnabled();
                }


                if (enabledNotification)
                    return FermatWalletNotificationPainter.getNotification(moduleManager, code, referenceWalletSession.getAppPublicKey(),Activities.CWP_WALLET_RUNTIME_WALLET_FERMAT_WALLET_BITDUBAI_VERSION_1_MAIN.getCode());
                else
                    return new FermatWalletBuildNotificationPainter("", "", "", "", false,Activities.CWP_WALLET_RUNTIME_WALLET_FERMAT_WALLET_BITDUBAI_VERSION_1_MAIN.getCode());

            }
            else
                return null;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ResourceSearcher getResourceSearcher() {
        return new FermatWalletSearcher();
    }
}
