package com.bitdubai.wallet_platform_api.layer._13_module.wallet_manager;

import java.util.List;
import java.util.UUID;

/**
 * Created by ciencias on 25.01.15.
 */
public interface WalletManager {

    public List<Wallet> getUserWallets();

    public void loadUserWallets (UUID userId) throws CantLoadWalletsException;

    public void createDefaultWallets (UUID userId) throws CantCreateDefaultWalletsException;
    
    public void enableWallet() throws CantEnableWalletException;
}

