package com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces;


import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.InstalledWallet;
import com.bitdubai.fermat_api.layer.modules.interfaces.ModuleManager;

import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

import java.util.Map;

/**
 * Created by Matias Furszyfer on 2015.07.20..
 */
public interface WalletSessionManager {

    public Map<String,FermatSession<InstalledWallet> > listOpenWallets();

    public FermatSession<InstalledWallet> openWalletSession(InstalledWallet installedWallet,ErrorManager errorManager, ModuleManager moduleManager, AppConnections appConnections);    public boolean closeWalletSession(String publicKey);
    public boolean isWalletOpen(String publicKey);
    public FermatSession<InstalledWallet>  getWalletSession(String publicKey);
}
