package com.bitdubai.fermat_api.layer._16_module.wallet_manager;

import com.bitdubai.fermat_api.layer._16_module.wallet_manager.exceptions.CantCreateDefaultWalletsException;
import com.bitdubai.fermat_api.layer._16_module.wallet_manager.exceptions.CantEnableWalletException;
import com.bitdubai.fermat_api.layer._16_module.wallet_manager.exceptions.CantLoadWalletsException;

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

