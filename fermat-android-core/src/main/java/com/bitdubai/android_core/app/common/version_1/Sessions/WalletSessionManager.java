package com.bitdubai.android_core.app.common.version_1.Sessions;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.WalletSession;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.Wallets;


import java.util.HashSet;
import java.util.Set;

/**
 * Created by Matias Furszyfer on 2015.07.20..
 */
public class WalletSessionManager implements com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.WalletSessionManager{

    private Set<WalletSession> lstWalletSession;

    public WalletSessionManager(){
        lstWalletSession= new HashSet<WalletSession>();
    }

    @Override
    public Set<WalletSession> listOpenWallets() {
        return lstWalletSession;
    }

    @Override
    public boolean openWalletSession(Wallets wallet) {
        return lstWalletSession.add(new com.bitdubai.android_core.app.common.version_1.Sessions.WalletSession(wallet));
    }

    @Override
    public boolean closeWalletSession(Wallets wallet) {
        return lstWalletSession.remove(new com.bitdubai.android_core.app.common.version_1.Sessions.WalletSession(wallet));
    }

    @Override
    public boolean isWalletOpen(Wallets wallet) {
        return lstWalletSession.contains(wallet);
    }
}
