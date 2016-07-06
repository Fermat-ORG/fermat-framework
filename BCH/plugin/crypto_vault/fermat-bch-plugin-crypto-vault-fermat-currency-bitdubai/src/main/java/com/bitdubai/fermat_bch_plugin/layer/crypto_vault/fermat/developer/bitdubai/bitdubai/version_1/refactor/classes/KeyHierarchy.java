package com.bitdubai.fermat_bch_plugin.layer.crypto_vault.fermat.developer.bitdubai.bitdubai.version_1.refactor.classes;

import com.bitdubai.fermat_bch_api.layer.crypto_vault.exceptions.CantExecuteDatabaseOperationException;
import com.bitdubai.fermat_bch_plugin.layer.crypto_vault.fermat.developer.bitdubai.bitdubai.version_1.refactor.interfaces.CryptoVaultDao;


import org.bitcoinj.core.Address;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.crypto.ChildNumber;
import org.bitcoinj.crypto.DeterministicHierarchy;
import org.bitcoinj.crypto.DeterministicKey;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rodrigo on 3/8/16.
 */
public abstract  class KeyHierarchy extends DeterministicHierarchy {
    /**
     * Super constructor
     * @param rootKey
     */
    public KeyHierarchy(DeterministicKey rootKey) {
        super(rootKey);
    }

    /**
     * gets the DAO object to access the database
     * @return
     */
    public abstract CryptoVaultDao getCryptoVaultDao();

    /**
     * gets the private key of the corresponding public Key
     * @param address
     * @return
     */
    public ECKey getPrivateKey (Address address){
        /**
         * I will try to get the position from the database just to save resources and avoid deriving the keys
         */
        int keyPosition;
        final NetworkParameters NETWORK_PARAMETERS = address.getParameters();
        try {
            keyPosition = getCryptoVaultDao().getPublicKeyPosition(address.toString());
        } catch (CantExecuteDatabaseOperationException e) {
            keyPosition = 0;
        }

        /**
         * If I didn't get the key position, then I will derive all of them and search it manually
         */
        if (keyPosition == 0) {
            try {
                for (com.bitdubai.fermat_bch_plugin.layer.crypto_vault.fermat.developer.bitdubai.bitdubai.version_1.refactor.classes.HierarchyAccount.HierarchyAccount hierarchyAccount : getCryptoVaultDao().getHierarchyAccounts()) {
                    for (ECKey privateKey : getDerivedPrivateKeys(hierarchyAccount)){
                        // If there is a match with the pub key, I will return the private key
                        if (privateKey.toAddress(NETWORK_PARAMETERS).equals(address))
                            return privateKey;
                    }
                }
            } catch (CantExecuteDatabaseOperationException e) {
                //null if there was a database error
                return null;
            }
        } else {
            /**
             * I found it and I know the position, then I will derive it and return it.
             */
            com.bitdubai.fermat_bch_plugin.layer.crypto_vault.fermat.developer.bitdubai.bitdubai.version_1.refactor.classes.HierarchyAccount.HierarchyAccount hierarchyAccount = new com.bitdubai.fermat_bch_plugin.layer.crypto_vault.fermat.developer.bitdubai.bitdubai.version_1.refactor.classes.HierarchyAccount.HierarchyAccount(0, "Master Account", com.bitdubai.fermat_bch_plugin.layer.crypto_vault.fermat.developer.bitdubai.bitdubai.version_1.refactor.classes.HierarchyAccount.HierarchyAccountType.MASTER_ACCOUNT);
            ECKey privateKey = this.derivePrivateKey(keyPosition, hierarchyAccount);
            return privateKey;
        }

        // I couldn't find a match
       return null;
    }

    /**
     * gets the Deterministic Hierarchy for the specified account
     * @param hierarchyAccount
     * @return
     */
    public abstract DeterministicHierarchy getKeyHierarchyFromAccount(com.bitdubai.fermat_bch_plugin.layer.crypto_vault.fermat.developer.bitdubai.bitdubai.version_1.refactor.classes.HierarchyAccount.HierarchyAccount hierarchyAccount);

    /**
     * Gets all the already derived keys from the hierarchy on this account.
     * @param hierarchyAccount
     * @return
     */
    public List<ECKey> getDerivedPrivateKeys(com.bitdubai.fermat_bch_plugin.layer.crypto_vault.fermat.developer.bitdubai.bitdubai.version_1.refactor.classes.HierarchyAccount.HierarchyAccount hierarchyAccount){
        DeterministicHierarchy keyHierarchy = getKeyHierarchyFromAccount(hierarchyAccount);
        List<ECKey> childKeys = new ArrayList<>();

        /**
         * I will get how many keys are already generated for this account.
         */
        int generatedKeys;
        try {
            generatedKeys  = this.getCryptoVaultDao().getCurrentGeneratedKeys(hierarchyAccount.getId());
        } catch (CantExecuteDatabaseOperationException e) {
            generatedKeys = 200;
        }
        for (int i = 0; i < generatedKeys; i++) {
            // I derive the key at position i
            DeterministicKey derivedKey = keyHierarchy.deriveChild(keyHierarchy.getRootKey().getPath(), true, false, new ChildNumber(i, false));
            // I add this key to the ECKey list
            childKeys.add(ECKey.fromPrivate(derivedKey.getPrivKey()));
        }

        return childKeys;
    }

    private ECKey derivePrivateKey(int position, com.bitdubai.fermat_bch_plugin.layer.crypto_vault.fermat.developer.bitdubai.bitdubai.version_1.refactor.classes.HierarchyAccount.HierarchyAccount hierarchyAccount){
        DeterministicHierarchy keyHierarchy = getKeyHierarchyFromAccount(hierarchyAccount);
        // I derive the key at position
        DeterministicKey derivedKey = keyHierarchy.deriveChild(keyHierarchy.getRootKey().getPath(), true, false, new ChildNumber(position, false));
        return ECKey.fromPrivate(derivedKey.getPrivKey());
    }
}
