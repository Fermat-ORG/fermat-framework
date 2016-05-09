package com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces;


import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.InstalledWallet;
import com.bitdubai.fermat_api.layer.modules.interfaces.ModuleManager;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

import java.util.Map;

/**
 * Created by Matias Furszyfer on 2015.07.20..
 */
public interface WalletSessionManager {

    Map<String,FermatSession<InstalledWallet,?> > listOpenWallets();

    FermatSession<InstalledWallet,?> openWalletSession(InstalledWallet installedWallet, ErrorManager errorManager, ModuleManager moduleManager, AppConnections appConnections);    boolean closeWalletSession(String publicKey);
    boolean isWalletOpen(String publicKey);
    FermatSession<InstalledWallet,?>  getWalletSession(String publicKey);
}
