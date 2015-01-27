package com.bitdubai.platform.layer._11_module.wallet_manager;

import java.util.List;
import java.util.UUID;

/**
 * Created by ciencias on 25.01.15.
 */
public interface WalletManager {

    public List<WalletManagerWallet> getUserWallets();

    public void loadUserWallets (UUID userId) throws CantLoadWalletsException;

    public void createDefaultWallets (UUID userId) throws CantCreateDefaultWalletsException;
}
