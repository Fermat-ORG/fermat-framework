package com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces;

import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;

import com.bitdubai.fermat_api.layer.modules.ModuleManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;

import java.util.Map;

/**
 * Created by mati on 2015.07.20..
 */
public interface SubAppSessionManager {


    public SubAppsSession openSubAppSession(SubApps subApps, ErrorManager errorManager,ModuleManager moduleManager);
//                                            WalletFactoryManager walletFactoryManager,
//                                            ToolManager toolManager,
//                                            WalletStoreModuleManager walletStoreModuleManager,
//                                            WalletPublisherModuleManager walletPublisherManager,
//                                            IntraUserModuleManager intraUserModuleManager,
//                                            AssetFactoryModuleManager assetFactoryModuleManager,
//                                            CryptoBrokerIdentityModuleManager cryptoBrokerIdentityModuleManager,
//                                            CryptoCustomerIdentityModuleManager cryptoCustomerIdentityModuleManager,
//                                            IntraWalletUserManager intraWalletUserManager);

    public boolean closeSubAppSession(SubApps subApps);
    public Map<SubApps,SubAppsSession> listOpenSubApps();
    public boolean isSubAppOpen(SubApps subApps);
    public SubAppsSession getSubAppsSession(SubApps subAppType);

}
