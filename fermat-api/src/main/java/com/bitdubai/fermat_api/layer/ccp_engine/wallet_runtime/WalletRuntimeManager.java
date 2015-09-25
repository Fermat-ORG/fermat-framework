package com.bitdubai.fermat_api.layer.ccp_engine.wallet_runtime;


import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Activity;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Fragment;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Wallet;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.WalletNavigationStructure;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.ccp_engine.wallet_runtime.exceptions.CantRecordClosedWalletException;
import com.bitdubai.fermat_api.layer.ccp_engine.wallet_runtime.exceptions.CantRecordInstalledWalletNavigationStructureException;
import com.bitdubai.fermat_api.layer.ccp_engine.wallet_runtime.exceptions.CantRecordOpenedWalletException;
import com.bitdubai.fermat_api.layer.ccp_engine.wallet_runtime.exceptions.CantRemoveWalletNavigationStructureException;
import com.bitdubai.fermat_api.layer.ccp_engine.wallet_runtime.exceptions.WalletRuntimeExceptions;
import com.bitdubai.fermat_api.layer.ccp_network_service.CantCheckResourcesException;

import java.util.UUID;

/**
 * Created by Matias Furszyfer on 23/07/15.
 */
public interface WalletRuntimeManager {


    public void recordNavigationStructure(String xmlText,String linkToRepo,String name,UUID skinId,String walletPublicKey) throws CantRecordInstalledWalletNavigationStructureException, CantCheckResourcesException;

    public boolean removeNavigationStructure(String publicKey) throws CantRemoveWalletNavigationStructureException;

    public WalletNavigationStructure getNavigationStructureFromWallet(String publicKey);




    /**
     *  Get the last wallet in screen
     *
     * @return Wallet in use
     */
    public WalletNavigationStructure getLastWallet ();


    /**
     *  Search wallet in the wallet installed list
     *
     * @return  The installed Wallet
     */
    public WalletNavigationStructure getWallet(String publicKey) throws WalletRuntimeExceptions;


}
