package com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces;



import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.interfaces.InstalledWallet;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_resources.WalletResourcesProviderManager;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.interfaces.CryptoWalletManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;

import java.util.Map;
import java.util.Set;

/**
 * Created by Matias Furszyfer on 2015.07.20..
 */
public interface WalletSessionManager {

    public Map<String,WalletSession> listOpenWallets();
    public WalletSession openWalletSession(InstalledWallet installedWallet,CryptoWalletManager cryptoWalletManager,WalletResourcesProviderManager walletResourcesProviderManager,ErrorManager errorManager);//,EventManager eventManager);
    public boolean closeWalletSession(String publicKey);
    public boolean isWalletOpen(String publicKey);
    public WalletSession getWalletSession(String publicKey);
}
