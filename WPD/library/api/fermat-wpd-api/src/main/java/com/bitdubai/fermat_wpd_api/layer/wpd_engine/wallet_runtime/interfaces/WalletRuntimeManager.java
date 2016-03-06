package com.bitdubai.fermat_wpd_api.layer.wpd_engine.wallet_runtime.interfaces;


import com.bitdubai.fermat_wpd_api.all_definition.WalletNavigationStructure;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatStructure;
import com.bitdubai.fermat_api.layer.engine.runtime.RuntimeManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_engine.wallet_runtime.exceptions.CantRecordInstalledWalletNavigationStructureException;
import com.bitdubai.fermat_wpd_api.layer.wpd_engine.wallet_runtime.exceptions.CantRemoveWalletNavigationStructureException;
import com.bitdubai.fermat_wpd_api.layer.wpd_engine.wallet_runtime.exceptions.WalletRuntimeExceptions;
import com.bitdubai.fermat_api.layer.dmp_network_service.CantCheckResourcesException;

import java.util.UUID;

/**
 * Created by Matias Furszyfer on 23/07/15.
 */
public interface WalletRuntimeManager extends RuntimeManager {


    void recordNavigationStructure(String xmlText, String linkToRepo, String name, UUID skinId, String walletPublicKey) throws CantRecordInstalledWalletNavigationStructureException, CantCheckResourcesException;

    boolean removeNavigationStructure(String publicKey) throws CantRemoveWalletNavigationStructureException;

    WalletNavigationStructure getNavigationStructureFromWallet(String publicKey) throws WalletRuntimeExceptions;




    /**
     *  Get the last wallet in screen
     *
     * @return Wallet in use
     */
    WalletNavigationStructure getLastWallet();


    /**
     *  Search wallet in the wallet installed list
     *
     * @return  The installed Wallet
     */
    WalletNavigationStructure getWallet(String publicKey) throws WalletRuntimeExceptions;


    void recordNAvigationStructure(FermatStructure fermatStructure);
}
