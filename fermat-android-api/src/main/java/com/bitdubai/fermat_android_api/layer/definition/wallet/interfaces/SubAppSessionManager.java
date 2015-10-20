package com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces;

import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;

import com.bitdubai.fermat_api.layer.dmp_module.intra_user.interfaces.IntraUserModuleManager;
import com.bitdubai.fermat_api.layer.modules.ModuleManager;
import com.bitdubai.fermat_cbp_api.layer.cbp_sub_app_module.crypto_broker_identity.interfaces.CryptoBrokerIdentityModuleManager;
import com.bitdubai.fermat_cbp_api.layer.cbp_sub_app_module.crypto_customer_identity.interfaces.CryptoCustomerIdentityModuleManager;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_wallet_user.interfaces.IntraWalletUserManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_factory.interfaces.WalletFactoryManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_publisher.interfaces.WalletPublisherModuleManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_store.interfaces.WalletStoreModuleManager;
import com.bitdubai.fermat_dap_api.layer.dap_module.asset_factory.interfaces.AssetFactoryModuleManager;
import com.bitdubai.fermat_pip_api.layer.pip_module.developer.interfaces.ToolManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;

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
