package com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces;


import com.bitdubai.fermat_android_api.layer.definition.wallet.abstracts.AbstractFermatSession;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.InstalledWallet;
import com.bitdubai.fermat_api.layer.modules.ModuleManager;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.interfaces.CryptoCustomerWalletModuleManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_resources.interfaces.WalletResourcesProviderManager;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWalletManager;

import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces.CryptoBrokerWalletModuleManager;
import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_issuer.interfaces.AssetIssuerWalletSupAppModuleManager;
import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_redeem_point.interfaces.AssetRedeemPointWalletSubAppModule;
import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_user.interfaces.AssetUserWalletSubAppModuleManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.interfaces.WalletSettings;

import java.util.Map;

/**
 * Created by Matias Furszyfer on 2015.07.20..
 */
public interface WalletSessionManager {

    public Map<String,AbstractFermatSession> listOpenWallets();

    public AbstractFermatSession openWalletSession(InstalledWallet installedWallet, CryptoWalletManager cryptoWalletManager, WalletSettings walletSettings, WalletResourcesProviderManager walletResourcesProviderManager, ErrorManager errorManager, CryptoBrokerWalletModuleManager cryptoBrokerWalletModuleManager, CryptoCustomerWalletModuleManager cryptoCustomerWalletModuleManager, AssetIssuerWalletSupAppModuleManager assetIssuerWalletManager, AssetUserWalletSubAppModuleManager assetUserModuleManager, AssetRedeemPointWalletSubAppModule assetRedeemPointWalletSubAppModule,ModuleManager moduleManager);//,EventManager eventManager);
    public boolean closeWalletSession(String publicKey);
    public boolean isWalletOpen(String publicKey);
    public AbstractFermatSession getWalletSession(String publicKey);
}
