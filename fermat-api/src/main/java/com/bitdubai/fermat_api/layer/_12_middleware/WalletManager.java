package com.bitdubai.fermat_api.layer._12_middleware;


import com.bitdubai.fermat_api.layer._12_middleware.wallet.Wallet;
import com.bitdubai.fermat_api.layer._12_middleware.wallet.exceptions.CantCreateWalletException;
import com.bitdubai.fermat_api.layer._12_middleware.wallet.exceptions.CantLoadWalletException;

import java.util.UUID;

/**
 * Created by loui on 16/02/15.
 */
public interface WalletManager {

    public void loadWallet (UUID walletId) throws CantLoadWalletException;

    public void createWallet (UUID walletId) throws CantCreateWalletException;
    
    public Wallet getCurrentWallet();
}
