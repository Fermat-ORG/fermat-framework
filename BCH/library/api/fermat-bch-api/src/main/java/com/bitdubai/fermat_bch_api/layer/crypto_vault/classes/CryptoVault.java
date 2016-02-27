package com.bitdubai.fermat_bch_api.layer.crypto_vault.classes;

import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.classes.HierarchyAccount.HierarchyAccount;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.classes.vault_seed.VaultSeedGenerator;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.classes.vault_seed.exceptions.CantCreateAssetVaultSeed;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.classes.vault_seed.exceptions.CantLoadExistingVaultSeed;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.classes.vault_seed.exceptions.InvalidSeedException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.exceptions.CantSignTransactionException;

import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.Wallet;
import org.bitcoinj.crypto.MnemonicException;
import org.bitcoinj.wallet.DeterministicSeed;

import java.util.UUID;

/**
 * Created by rodrigo on 2/26/16.
 */
public abstract class CryptoVault {

    /**
     * Platform variables
     */
    PluginFileSystem pluginFileSystem;
    UUID pluginId;
    final String CRYPTO_VAULT_SEED_FILEPATH;
    final String CRYPTO_VAULT_SEED_FILENAME;

    /**
     * Constructor
     * @param CRYPTO_VAULT_SEED_FILEPATH
     * @param CRYPTO_VAULT_SEED_FILENAME
     */
    public CryptoVault(PluginFileSystem pluginFileSystem,
                       UUID pluginId,
                       String CRYPTO_VAULT_SEED_FILEPATH,
                       String CRYPTO_VAULT_SEED_FILENAME) {
        this.pluginFileSystem = pluginFileSystem;
        this.pluginId = pluginId;
        this.CRYPTO_VAULT_SEED_FILEPATH = CRYPTO_VAULT_SEED_FILEPATH;
        this.CRYPTO_VAULT_SEED_FILENAME = CRYPTO_VAULT_SEED_FILENAME;
    }


    private Transaction signTransaction(HierarchyAccount hierarchyAccount, Transaction transactionToSign) throws CantSignTransactionException{
        if (hierarchyAccount == null || transactionToSign == null)
            throw new CantSignTransactionException(CantSignTransactionException.DEFAULT_MESSAGE, null, "SignTransaction parameters can't be null", "null parameters.");

        final NetworkParameters NETWORK_PARAMETERS = transactionToSign.getParams();
        /**
         * Create the bitcoinj wallet from the keys of all accounts
         */
        final Wallet wallet;
        try {
            wallet = Wallet.fromSeed(NETWORK_PARAMETERS, getVaultSeed());
        } catch (InvalidSeedException e) {
            throw new CantSignTransactionException(CantSignTransactionException.DEFAULT_MESSAGE, e, "Unable to create wallet from seed.", "seed issue");
        }

        return null;
    }

    /**
     * Creates a new Seed or loads and existing one for the user logged.
     * @return
     * @throws CantCreateAssetVaultSeed
     * @throws CantLoadExistingVaultSeed
     */
    public DeterministicSeed getVaultSeed()  throws InvalidSeedException{
        try{
            VaultSeedGenerator vaultSeedGenerator = new VaultSeedGenerator(this.pluginFileSystem, this.pluginId, CRYPTO_VAULT_SEED_FILEPATH, CRYPTO_VAULT_SEED_FILENAME);
            if (!vaultSeedGenerator.seedExists()){
                vaultSeedGenerator.create();
                /**
                 * I realod it to make sure I'm using the seed I will start using from now on. Issue #3330
                 */
                vaultSeedGenerator.load();
            } else
                vaultSeedGenerator.load();
            DeterministicSeed seed = new DeterministicSeed(vaultSeedGenerator.getSeedBytes(), vaultSeedGenerator.getMnemonicCode(), vaultSeedGenerator.getCreationTimeSeconds());
            seed.check();
            return seed;
        } catch (CantLoadExistingVaultSeed cantLoadExistingVaultSeed) {
            throw new InvalidSeedException(InvalidSeedException.DEFAULT_MESSAGE, cantLoadExistingVaultSeed, "there was an error trying to load an existing seed.", null);
        } catch (CantCreateAssetVaultSeed cantCreateAssetVaultSeed) {
            throw  new InvalidSeedException(InvalidSeedException.DEFAULT_MESSAGE, cantCreateAssetVaultSeed, "there was an error trying to create a new seed.", null);
        } catch (MnemonicException e) {
            throw  new InvalidSeedException(InvalidSeedException.DEFAULT_MESSAGE, e, "the seed that was generated is not valid.", null);
        }
    }
}
