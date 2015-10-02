package com.bitdubai.fermat_api.layer.dmp_module.wallet_manager;

import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.CantCreateNewWalletException;

import java.util.List;

/**
 * Created by ciencias on 25.01.15.
 */
public interface WalletManager {

    List<InstalledWallet> getUserWallets() throws CantGetUserWalletException;

    void loadUserWallets (String deviceUserPublicKey) throws CantLoadWalletsException;

    void createDefaultWallets (String deviceUserPublicKey) throws CantCreateDefaultWalletsException;
    
    void enableWallet() throws CantEnableWalletException;

    InstalledWallet getInstalledWallet(String walletPublicKey) throws CantCreateNewWalletException;
}