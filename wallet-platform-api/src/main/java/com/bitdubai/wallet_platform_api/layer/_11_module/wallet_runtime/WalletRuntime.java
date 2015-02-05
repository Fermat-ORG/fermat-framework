package com.bitdubai.wallet_platform_api.layer._11_module.wallet_runtime;

import java.util.UUID;

/**
 * Created by loui on 05/02/15.
 */
public interface WalletRuntime {
    
    public void recordOpenedWallet (UUID walletId) throws CantRecordOpenedWalletException; 
    
    public void recordClosedWallet (UUID walletId) throws CantRecordClosedWalletException;
    
}
