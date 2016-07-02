package com.bitdubai.fermat_bch_plugin.layer.crypto_vault.fermat.developer.bitdubai.bitdubai.version_1.refactor.interfaces;


import com.bitdubai.fermat_bch_api.layer.crypto_vault.exceptions.CantExecuteDatabaseOperationException;
import com.bitdubai.fermat_bch_plugin.layer.crypto_vault.fermat.developer.bitdubai.bitdubai.version_1.refactor.classes.HierarchyAccount.HierarchyAccount;

import java.util.List;

/**
 * Created by rodrigo on 3/8/16.
 */
public interface CryptoVaultDao {

    /**
     * gets the position for the passed publicKey
     * @param publicKey
     * @return
     * @throws CantExecuteDatabaseOperationException
     */
    int getPublicKeyPosition(String publicKey) throws CantExecuteDatabaseOperationException;

    /**
     * gets all active Hierarchy accounts
     * @return
     * @throws CantExecuteDatabaseOperationException
     */
    List<HierarchyAccount> getHierarchyAccounts() throws CantExecuteDatabaseOperationException;

    /**
     * Gets the amount of keys generated for the specified account id
     * @param accountId
     * @return
     * @throws CantExecuteDatabaseOperationException
     */
    int getCurrentGeneratedKeys(int accountId) throws CantExecuteDatabaseOperationException;
}
