package com.bitdubai.sub_app.wallet_manager.session;

import com.bitdubai.fermat_android_api.layer.definition.wallet.abstracts.AbstractReferenceAppFermatSession;
import com.bitdubai.fermat_api.layer.desktop.InstalledDesktop;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.WalletManager;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

/**
 * Created by mati on 2015.11.20..
 */
public class DesktopSessionReferenceApp extends AbstractReferenceAppFermatSession<InstalledDesktop,WalletManager,SubAppResourcesProviderManager> {

    public DesktopSessionReferenceApp() {
    }

    public DesktopSessionReferenceApp(String publicKey, InstalledDesktop fermatApp, ErrorManager errorManager, WalletManager moduleManager, SubAppResourcesProviderManager resourceProviderManager) {
        super(publicKey, fermatApp, errorManager, moduleManager, resourceProviderManager);
    }


}
