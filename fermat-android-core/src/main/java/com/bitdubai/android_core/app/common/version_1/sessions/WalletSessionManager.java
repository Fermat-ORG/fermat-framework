package com.bitdubai.android_core.app.common.version_1.sessions;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.AppConnections;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.FermatSession;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.InstalledWallet;
import com.bitdubai.fermat_api.layer.modules.interfaces.ModuleManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Matias Furszyfer on 2015.07.20..
 */

public class WalletSessionManager implements com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.WalletSessionManager {

    private Map<String, FermatSession<InstalledWallet,?>> lstWalletSession;

    public WalletSessionManager() {
        lstWalletSession = new HashMap<>();
    }

    @Override
    public Map<String, FermatSession<InstalledWallet,?> > listOpenWallets() {
        return lstWalletSession;
    }


    @Override
    public FermatSession<InstalledWallet,?> openWalletSession(InstalledWallet installedWallet,ErrorManager errorManager, ModuleManager moduleManager, AppConnections appConnections) {
        FermatSession<InstalledWallet,?> walletSession  = appConnections.buildSession(installedWallet,moduleManager,errorManager);
        lstWalletSession.put(installedWallet.getAppPublicKey(), walletSession);
        return walletSession;
    }


    @Override
    public boolean closeWalletSession(String publicKey) {
        try {
            lstWalletSession.remove(publicKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean isWalletOpen(String publicKey) {
        return lstWalletSession.containsKey(publicKey);
    }

    @Override
    public FermatSession<InstalledWallet,?> getWalletSession(String publicKey) {
        return lstWalletSession.get(publicKey);
    }

}
