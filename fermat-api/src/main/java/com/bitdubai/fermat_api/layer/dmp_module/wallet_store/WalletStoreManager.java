package com.bitdubai.fermat_api.layer.dmp_module.wallet_store;

import com.bitdubai.fermat_api.layer.dmp_module.wallet_store.exceptions.DEPRECATED_CantRecordInstalledWalletException;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_store.exceptions.DEPRECATED_CantRecordUninstalledWalletException;

import java.util.UUID;

/**
 * Created by loui on 05/02/15.
 */
public interface WalletStoreManager extends Catalog{
    
    public void recordInstalledWallet (UUID walletId) throws DEPRECATED_CantRecordInstalledWalletException;
    
    public void recordUninstalledwallet (UUID walletId) throws DEPRECATED_CantRecordUninstalledWalletException;
    
    
}
