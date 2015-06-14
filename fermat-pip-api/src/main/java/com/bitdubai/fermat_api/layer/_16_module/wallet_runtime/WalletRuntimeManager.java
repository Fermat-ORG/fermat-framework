package com.bitdubai.fermat_api.layer._16_module.wallet_runtime;

import com.bitdubai.fermat_api.layer._15_middleware.app_runtime.Activity;
import com.bitdubai.fermat_api.layer._15_middleware.app_runtime.Fragment;
import com.bitdubai.fermat_api.layer._15_middleware.app_runtime.Wallet;
import com.bitdubai.fermat_api.layer._15_middleware.app_runtime.enums.Activities;
import com.bitdubai.fermat_api.layer._16_module.wallet_runtime.exceptions.CantRecordClosedWalletException;
import com.bitdubai.fermat_api.layer._16_module.wallet_runtime.exceptions.CantRecordOpenedWalletException;

import java.util.UUID;

/**
 * Created by loui on 05/02/15.
 */
public interface WalletRuntimeManager {
    
    public void recordOpenedWallet (UUID walletId) throws CantRecordOpenedWalletException;
    
    public void recordClosedWallet (UUID walletId) throws CantRecordClosedWalletException;

    public Wallet getLastWallet ();

    public Activity getLasActivity ();

    public Fragment getLastFragment ();

    public Activity getActivity(Activities app);
    
}
