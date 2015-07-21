package com.bitdubai.fermat_api.layer.dmp_engine.wallet_runtime;


import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Activity;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Fragment;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Wallet;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.dmp_engine.wallet_runtime.exceptions.CantRecordClosedWalletException;
import com.bitdubai.fermat_api.layer.dmp_engine.wallet_runtime.exceptions.CantRecordOpenedWalletException;

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
