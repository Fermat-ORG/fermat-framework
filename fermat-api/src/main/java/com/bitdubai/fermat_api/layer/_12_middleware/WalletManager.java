package com.bitdubai.fermat_api.layer._12_middleware;


import com.bitdubai.fermat_api.layer._12_middleware.wallet.exceptions.CantCreateWalletException;

import java.util.UUID;

/**
 * Created by loui on 16/02/15.
 */
public interface WalletManager {

    public void loadWallet (UUID walletId);

    public void createWallet (UUID walletId) throws CantCreateWalletException;
}
