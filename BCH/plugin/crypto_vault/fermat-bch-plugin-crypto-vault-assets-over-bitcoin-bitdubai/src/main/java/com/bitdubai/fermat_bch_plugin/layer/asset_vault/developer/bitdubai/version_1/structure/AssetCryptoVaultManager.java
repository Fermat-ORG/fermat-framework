package com.bitdubai.fermat_bch_plugin.layer.asset_vault.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_api.layer.dmp_world.wallet.exceptions.CantStartAgentException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.interfaces.BitcoinNetworkManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.interfaces.exceptions.CantGetGenesisTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.interfaces.exceptions.GetNewCryptoAddressException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.vault_seed.VaultSeedGenerator;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.vault_seed.exceptions.CantCreateAssetVaultSeed;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.vault_seed.exceptions.CantLoadExistingVaultSeed;
import com.bitdubai.fermat_bch_plugin.layer.asset_vault.developer.bitdubai.version_1.exceptions.InvalidSeedException;

import org.bitcoinj.crypto.MnemonicException;
import org.bitcoinj.wallet.DeterministicSeed;
import org.omg.CORBA.DynAnyPackage.Invalid;

import java.util.UUID;

/**
 * Created by rodrigo on 9/19/15.
 */
public class AssetCryptoVaultManager  {
    /**
     * AssetVaultManager variables
     */
    UUID pluginId;


    /**
     * File name information where the seed will be stored
     */
    private final String ASSET_VAULT_SEED_FILEPATH = "AssetVaultSeed";
    private final String ASSET_VAULT_SEED_FILENAME;


    /**
     * platform interfaces definition
     */
    BitcoinNetworkManager bitcoinNetworkManager;
    PluginFileSystem pluginFileSystem;
    PluginDatabaseSystem pluginDatabaseSystem;


    /**
     * Constructor
     * @param pluginId
     * @param pluginFileSystem
     */
    public AssetCryptoVaultManager(UUID pluginId,
                                   PluginFileSystem pluginFileSystem,
                                   PluginDatabaseSystem pluginDatabaseSystem,
                                   String seedFileName,
                                   BitcoinNetworkManager bitcoinNetworkManager) throws InvalidSeedException {

        this.pluginId = pluginId;
        ASSET_VAULT_SEED_FILENAME = seedFileName;
        this.pluginFileSystem = pluginFileSystem;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.bitcoinNetworkManager = bitcoinNetworkManager;

        /**
         * I will let the VaultKeyHierarchyGenerator to start and generate the hierarchy in a new thread
         */
        VaultKeyHierarchyGenerator vaultKeyHierarchyGenerator = new VaultKeyHierarchyGenerator(getAssetVaultSeed(), pluginDatabaseSystem);
        new Thread(vaultKeyHierarchyGenerator).start();
    }

    /**
     * Creates a new Seed or loads and existing one for the user logged.
     * @return
     * @throws CantCreateAssetVaultSeed
     * @throws CantLoadExistingVaultSeed
     */
    private DeterministicSeed getAssetVaultSeed()  throws InvalidSeedException{
        try{
            VaultSeedGenerator vaultSeedGenerator = new VaultSeedGenerator(this.pluginFileSystem, this.pluginId, ASSET_VAULT_SEED_FILEPATH, ASSET_VAULT_SEED_FILENAME);
            if (!vaultSeedGenerator.seedExists())
                vaultSeedGenerator.create();
            else
                vaultSeedGenerator.load();

            DeterministicSeed seed = new DeterministicSeed(vaultSeedGenerator.getSeedBytes(), vaultSeedGenerator.getMnemonicCode(), vaultSeedGenerator.getCreationTimeSeconds());
            seed.check();
            return seed;
        } catch (CantLoadExistingVaultSeed cantLoadExistingVaultSeed) {
            throw new InvalidSeedException(InvalidSeedException.DEFAULT_MESSAGE, cantLoadExistingVaultSeed, "there was an error trying to load an existing seed.", null);
        } catch (CantCreateAssetVaultSeed cantCreateAssetVaultSeed) {
            throw new InvalidSeedException(InvalidSeedException.DEFAULT_MESSAGE, cantCreateAssetVaultSeed, "there was an error trying to create a new seed.", null);
        } catch (MnemonicException e) {
            throw new InvalidSeedException(InvalidSeedException.DEFAULT_MESSAGE, e, "the seed that was generated is not valid.", null);
        }
    }


    /**
     * Will get a new crypto address from the asset vault account.
     * @param blockchainNetworkType
     * @return
     * @throws GetNewCryptoAddressException
     */

    public CryptoAddress getNewAssetVaultCryptoAddress(BlockchainNetworkType blockchainNetworkType) throws GetNewCryptoAddressException {
          return null;
    }


    public CryptoTransaction getGenesisTransaction(String transactionId) throws CantGetGenesisTransactionException {
        return null;
    }


    public long getAvailableBalanceForTransaction(String genesisTransaction) {
        return 0;
    }
}
