package com.bitdubai.reference_niche_wallet.loss_protected_wallet.app_connection;

import android.content.Context;
import com.bitdubai.fermat_android_api.engine.*;
import com.bitdubai.fermat_android_api.layer.definition.wallet.abstracts.AbstractFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.AppConnections;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Developers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;

import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.LossProtectedWalletSettings;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.interfaces.LossProtectedWallet;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.header.BitcoinWalletHeaderPainter;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.navigation_drawer.LossProtectedWalletNavigationViewPainter;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.fragment_factory.LossProtectedWalletFragmentFactory;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.session.LossProtectedWalletSession;


/**
 * Created by Matias Furszyfer on 2015.12.09..
 */
public class LossProtectedWalletFermatAppConnection extends AppConnections<LossProtectedWalletSession>{

    private LossProtectedWallet moduleManager = null;
    private LossProtectedWalletSession referenceWalletSession;

    public LossProtectedWalletFermatAppConnection(Context activity) {
        super(activity);
    }

    @Override
    public FermatFragmentFactory getFragmentFactory() {
        return new LossProtectedWalletFragmentFactory();
    }

    @Override
    public PluginVersionReference getPluginVersionReference() {
        return  new PluginVersionReference(
                Platforms.CRYPTO_CURRENCY_PLATFORM,
                Layers.WALLET_MODULE,
                Plugins.CRYPTO_LOSS_PROTECTED_WALLET,
                Developers.BITDUBAI,
                new Version()
            );
    }

    @Override
    public AbstractFermatSession getSession() {
        return new LossProtectedWalletSession();
    }

    @Override
    public NavigationViewPainter getNavigationViewPainter() {


       // return new LossProtectedWalletNavigationView(getActivity(),getActiveIdentity()); -- navigation tool
        return new LossProtectedWalletNavigationViewPainter(getContext(),getActiveIdentity());
    }

    @Override
    public HeaderViewPainter getHeaderViewPainter() {
        return new BitcoinWalletHeaderPainter();
    }

    @Override
    public FooterViewPainter getFooterViewPainter() {
        return null;
    }

    @Override
    public NotificationPainter getNotificationPainter(String code){
     try
        {
            SettingsManager<LossProtectedWalletSettings> settingsManager = null;
            String walletPublicKey = referenceWalletSession.getAppPublicKey();
            boolean enabledNotification = true;
            this.referenceWalletSession = (LossProtectedWalletSession)this.getFullyLoadedSession();
            if(referenceWalletSession!=  null) {

                if (referenceWalletSession.getModuleManager() != null) {
                    moduleManager = referenceWalletSession.getModuleManager().getCryptoWallet();

                    //get enabled notification settings

                    settingsManager = referenceWalletSession.getModuleManager().getSettingsManager();
                    enabledNotification = settingsManager.loadAndGetSettings(walletPublicKey).getNotificationEnabled();
                }


                if (enabledNotification)
                    return LossProtectedWalletBuildNotificationPainter.getNotification(moduleManager, code, referenceWalletSession.getAppPublicKey());
                else
                    return new LossProtectedWalletNotificationPainter("", "", "", "", false,walletPublicKey);

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
}
