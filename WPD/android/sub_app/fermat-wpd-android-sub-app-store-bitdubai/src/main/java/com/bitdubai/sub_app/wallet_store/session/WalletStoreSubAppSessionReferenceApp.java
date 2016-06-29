package com.bitdubai.sub_app.wallet_store.session;

import com.bitdubai.fermat_android_api.layer.definition.wallet.abstracts.AbstractReferenceAppFermatSession;
import com.bitdubai.fermat_api.layer.dmp_module.sub_app_manager.InstalledSubApp;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_store.interfaces.WalletStoreModuleManager;

/**
 * Created by Matias Furszyfer on 2015.07.20..
 */
public class WalletStoreSubAppSessionReferenceApp extends AbstractReferenceAppFermatSession<InstalledSubApp, WalletStoreModuleManager, SubAppResourcesProviderManager> {
    public static final String BASIC_DATA = "catalog item";
    public static final String PREVIEW_IMGS = "preview images";
    public static final String DEVELOPER_NAME = "developer name";
    public static final String SKIN_ID = "skin id";
    public static final String LANGUAGE_ID = "skin id";
    public static final String WALLET_VERSION = "wallet version";
    public static final String WALLET_LIST = "wallet_list";

    public WalletStoreSubAppSessionReferenceApp() {
    }
}
