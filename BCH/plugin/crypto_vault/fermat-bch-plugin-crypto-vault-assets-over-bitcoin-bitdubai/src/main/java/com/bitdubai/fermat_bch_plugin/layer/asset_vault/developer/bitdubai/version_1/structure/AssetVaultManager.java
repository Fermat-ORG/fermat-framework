package com.bitdubai.fermat_bch_plugin.layer.asset_vault.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.vault_seed.VaultSeedGenerator;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.vault_seed.exceptions.CantCreateAssetVaultSeed;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.vault_seed.exceptions.CantLoadExistingVaultSeed;

import org.bitcoinj.wallet.DeterministicSeed;

import java.util.UUID;

/**
 * Created by rodrigo on 9/19/15.
 */
public class AssetVaultManager implements DealsWithPluginFileSystem {
    private final String ASSET_VAULT_SEED_FILEPATH = "AssetVaultSeed";
    private final String ASSET_VAULT_SEED_FILENAME = "Seed";
    UUID pluginId;

    /**
     * DealsWithPluginFileSystem interface variable and implementation
     */
    PluginFileSystem pluginFileSystem;

    @Override
    public void setPluginFileSystem(PluginFileSystem pluginFileSystem) {
        this.pluginFileSystem = pluginFileSystem;
    }

    /**
     * Constructor
     * @param pluginId
     * @param pluginFileSystem
     */
    public AssetVaultManager(UUID pluginId, PluginFileSystem pluginFileSystem) {
        this.pluginId = pluginId;
        this.pluginFileSystem = pluginFileSystem;
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
}
