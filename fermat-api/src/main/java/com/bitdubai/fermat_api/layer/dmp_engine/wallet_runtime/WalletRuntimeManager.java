package com.bitdubai.fermat_api.layer.dmp_engine.wallet_runtime;


import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Activity;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Fragment;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Wallet;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.dmp_engine.wallet_runtime.exceptions.CantRecordClosedWalletException;
import com.bitdubai.fermat_api.layer.dmp_engine.wallet_runtime.exceptions.CantRecordOpenedWalletException;

import java.util.UUID;

/**
 * Created by Matias Furszyfer on 23/07/15.
 */
public interface WalletRuntimeManager {
    
    public void recordOpenedWallet (UUID walletId) throws CantRecordOpenedWalletException;
    
    public void recordClosedWallet (UUID walletId) throws CantRecordClosedWalletException;

    /**
     *  Get the last wallet in screen
     *
     * @return Wallet in use
     */
    public Wallet getLastWallet ();


    /**
     *  Search wallet in the wallet installed list
     *
     * @param wallets type of Wallet
     * @return  The installed Wallet
     */
    public Wallet getWallet(Wallets wallets);
    
}
