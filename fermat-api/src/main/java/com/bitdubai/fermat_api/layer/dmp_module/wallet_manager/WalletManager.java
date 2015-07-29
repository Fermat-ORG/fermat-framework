package com.bitdubai.fermat_api.layer.dmp_module.wallet_manager;

import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.interfaces.InstalledWallet;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.exceptions.CantCreateDefaultWalletsException;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.exceptions.CantEnableWalletException;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.exceptions.CantLoadWalletsException;

import java.util.List;
import java.util.UUID;

/**
 * Created by ciencias on 25.01.15.
 */
public interface WalletManager {

    List<InstalledWallet> getUserWallets();

    void loadUserWallets (String deviceUserPublicKey) throws CantLoadWalletsException;

    void createDefaultWallets (String deviceUserPublicKey) throws CantCreateDefaultWalletsException;
    
    void enableWallet() throws CantEnableWalletException;
}