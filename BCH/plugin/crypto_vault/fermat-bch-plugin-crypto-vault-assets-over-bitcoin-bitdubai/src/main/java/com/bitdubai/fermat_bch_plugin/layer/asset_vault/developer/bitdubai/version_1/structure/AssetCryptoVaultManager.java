package com.bitdubai.fermat_bch_plugin.layer.asset_vault.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantMonitorBitcoinNetworkException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.interfaces.BitcoinNetworkManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.interfaces.AssetVaultManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.interfaces.exceptions.CantGetGenesisTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.interfaces.exceptions.GetNewCryptoAddressException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.vault_seed.VaultSeedGenerator;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.vault_seed.exceptions.CantCreateAssetVaultSeed;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.vault_seed.exceptions.CantLoadExistingVaultSeed;
import com.bitdubai.fermat_bch_plugin.layer.asset_vault.developer.bitdubai.version_1.exceptions.VaultKeyHierarchyException;

import org.bitcoinj.wallet.DeterministicSeed;

import java.util.UUID;

/**
 * Created by rodrigo on 9/19/15.
 */
public class AssetCryptoVaultManager  {
    /**
     * AssetVaultManager variables
     */
    UUID pluginId;
    VaultKeyHierarchy vaultKeyHierarchy;
    DeterministicSeed seed;

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
                                   String deviceUserLoggerPublicKey,
                                   BitcoinNetworkManager bitcoinNetworkManager) throws CantCreateAssetVaultSeed, CantLoadExistingVaultSeed, VaultKeyHierarchyException, CantMonitorBitcoinNetworkException {
        //this will be used to set the owner of the files
        this.pluginId = pluginId;
        // I'm defining the filename to be the publick key of the device used logged. I will be saving the blockchain under this directory name
        ASSET_VAULT_SEED_FILENAME = deviceUserLoggerPublicKey;
        this.pluginFileSystem = pluginFileSystem;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.bitcoinNetworkManager = bitcoinNetworkManager;

        createKeyHierarchy();

        /**
         * Once the KeyHierarchy is created, I will request the Bitcoin Network to monitor the network using the seed I created.
         */
        // Todo I should check if I have already delivered address in more than one network type to generate more than one monitoring agent
        //bitcoinNetworkManager.monitorNetworkFromSeed(BlockchainNetworkType.DEFAULT, seed);
        //bitcoinNetworkManager.monitorNetworkFromWatchingKey(BlockchainNetworkType.DEFAULT, vaultKeyHierarchy.getWatchingKey());
    }

    /**
     * Creates a new Seed or loads and existing one for the user logged.
     * @return
     * @throws CantCreateAssetVaultSeed
     * @throws CantLoadExistingVaultSeed
     */
    private DeterministicSeed getAssetVaultSeed() throws CantCreateAssetVaultSeed, CantLoadExistingVaultSeed {
        VaultSeedGenerator vaultSeedGenerator = new VaultSeedGenerator(this.pluginFileSystem, this.pluginId, ASSET_VAULT_SEED_FILEPATH, ASSET_VAULT_SEED_FILENAME);
        if (!vaultSeedGenerator.seedExists())
            vaultSeedGenerator.create();
        else
            vaultSeedGenerator.load();

        DeterministicSeed seed = new DeterministicSeed(vaultSeedGenerator.getSeedBytes(), vaultSeedGenerator.getMnemonicCode(), vaultSeedGenerator.getCreationTimeSeconds());
        return seed;
    }

    /**
     * Creates the key hierarchy using the seed we load from disk.
     * @throws CantLoadExistingVaultSeed
     * @throws CantCreateAssetVaultSeed
     * @throws VaultKeyHierarchyException
     */
    private void createKeyHierarchy() throws CantLoadExistingVaultSeed, CantCreateAssetVaultSeed, VaultKeyHierarchyException {
        this.seed = getAssetVaultSeed();
        vaultKeyHierarchy = new VaultKeyHierarchy(seed, this.pluginDatabaseSystem, this.pluginId);
    }


    /**
     * Will get a new crypto address from the asset vault account.
     * @param blockchainNetworkType
     * @return
     * @throws GetNewCryptoAddressException
     */

    public CryptoAddress getNewAssetVaultCryptoAddress(BlockchainNetworkType blockchainNetworkType) throws GetNewCryptoAddressException {
            return vaultKeyHierarchy.getNewCryptoAddressFromChain(blockchainNetworkType, 0);
    }


    public CryptoTransaction getGenesisTransaction(String transactionId) throws CantGetGenesisTransactionException {
        return null;
    }


    public long getAvailableBalanceForTransaction(String genesisTransaction) {
        return 0;
    }
}
