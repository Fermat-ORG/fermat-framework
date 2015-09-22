package com.bitdubai.fermat_bch_plugin.layer.asset_vault.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_bch_api.layer.crypto_network.enums.BlockchainNetworkType;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.interfaces.exceptions.GetNewCryptoAddressException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.vault_seed.VaultSeedGenerator;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.vault_seed.exceptions.CantCreateAssetVaultSeed;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.vault_seed.exceptions.CantLoadExistingVaultSeed;
import com.bitdubai.fermat_bch_plugin.layer.asset_vault.developer.bitdubai.version_1.exceptions.InvalidChainNumberException;
import com.bitdubai.fermat_bch_plugin.layer.asset_vault.developer.bitdubai.version_1.exceptions.VaultKeyHierarchyException;

import org.bitcoinj.wallet.DeterministicSeed;

import java.util.UUID;

/**
 * Created by rodrigo on 9/19/15.
 */
public class AssetCryptoVaultManager implements DealsWithPluginFileSystem, DealsWithPluginDatabaseSystem {
    private final String ASSET_VAULT_SEED_FILEPATH = "AssetVaultSeed";
    private final String ASSET_VAULT_SEED_FILENAME = "Seed";
    UUID pluginId;
    VaultKeyHierarchy vaultKeyHierarchy;

    /**
     * DealsWithPluginFileSystem interface variable and implementation
     */
    PluginFileSystem pluginFileSystem;

    @Override
    public void setPluginFileSystem(PluginFileSystem pluginFileSystem) {
        this.pluginFileSystem = pluginFileSystem;
    }


    /**
     * DealsWithPluginDatabaseSystem interface variable and implementation
     */
    PluginDatabaseSystem pluginDatabaseSystem;
    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /**
     * Constructor
     * @param pluginId
     * @param pluginFileSystem
     */
    public AssetCryptoVaultManager(UUID pluginId, PluginFileSystem pluginFileSystem, PluginDatabaseSystem pluginDatabaseSystem) throws CantCreateAssetVaultSeed, CantLoadExistingVaultSeed, VaultKeyHierarchyException {
        this.pluginId = pluginId;
        this.pluginFileSystem = pluginFileSystem;
        this.pluginDatabaseSystem = pluginDatabaseSystem;

        createKeyHierarchy();
    }

    /**
     * Creates a new Seed or loads and existing one.
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
        vaultKeyHierarchy = new VaultKeyHierarchy(getAssetVaultSeed(), this.pluginDatabaseSystem, this.pluginId);
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
}
