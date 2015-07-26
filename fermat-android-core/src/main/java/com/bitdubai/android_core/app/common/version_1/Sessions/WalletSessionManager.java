package com.bitdubai.android_core.app.common.version_1.Sessions;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.WalletSession;

import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.interfaces.CryptoWalletManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Matias Furszyfer on 2015.07.20..
 */

public class WalletSessionManager implements com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.WalletSessionManager{

    private Map<Wallets,WalletSession> lstWalletSession;

    public WalletSessionManager(){
        lstWalletSession= new HashMap<Wallets,WalletSession>();
    }

    @Override
    public Map<Wallets,WalletSession> listOpenWallets() {
        return lstWalletSession;
    }


    @Override
    public WalletSession openWalletSession(Wallets walletType,CryptoWalletManager cryptoWalletManager,ErrorManager errorManager) {
        WalletSession walletSession= new com.bitdubai.reference_niche_wallet.bitcoin_wallet.session.WalletSession(walletType,cryptoWalletManager,errorManager);
        lstWalletSession.put(walletType,walletSession);
        return walletSession;
    }

    @Override
    public boolean closeWalletSession(Wallets wallet) {
        try {
            lstWalletSession.remove(new com.bitdubai.reference_niche_wallet.bitcoin_wallet.session.WalletSession(wallet));
        }catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean isWalletOpen(Wallets wallet) {
        return lstWalletSession.containsKey(wallet);
    }
}
