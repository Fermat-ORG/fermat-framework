package com.bitdubai.sub_app.wallet_manager.session;

import com.bitdubai.fermat_android_api.layer.definition.wallet.abstracts.AbstractFermatSession;
import com.bitdubai.fermat_api.layer.desktop.InstalledDesktop;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.WalletManager;
import com.bitdubai.fermat_pip_api.layer.pip_network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

/**
 * Created by mati on 2015.11.20..
 */
public class DesktopSession extends AbstractFermatSession<InstalledDesktop,WalletManager,SubAppResourcesProviderManager> {


    public DesktopSession(String publicKey, InstalledDesktop fermatApp, ErrorManager errorManager, WalletManager moduleManager, SubAppResourcesProviderManager resourceProviderManager) {
        super(publicKey, fermatApp, errorManager, moduleManager, resourceProviderManager);
    }


}
