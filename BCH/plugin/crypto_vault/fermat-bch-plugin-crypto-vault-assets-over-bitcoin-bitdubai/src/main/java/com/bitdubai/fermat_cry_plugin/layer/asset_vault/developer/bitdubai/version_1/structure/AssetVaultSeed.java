package com.bitdubai.fermat_cry_plugin.layer.asset_vault.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantLoadFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.BitcoinNetworkConfiguration;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.VaultSeed;
import com.bitdubai.fermat_cry_plugin.layer.asset_vault.developer.bitdubai.version_1.exceptions.CantCreateAssetVaultSeed;
import com.bitdubai.fermat_cry_plugin.layer.asset_vault.developer.bitdubai.version_1.exceptions.CantDeleteExistingVaultSeed;
import com.bitdubai.fermat_cry_plugin.layer.asset_vault.developer.bitdubai.version_1.exceptions.CantLoadExistingVaultSeed;

import org.bitcoinj.core.Wallet;
import org.bitcoinj.wallet.DeterministicSeed;

import java.util.List;
import java.util.UUID;

/**
 * Created by rodrigo on 9/19/15.
 * Defines the seed used to create the master Key of the Hierarchicaly structure in the vault.
 * It will persists this info into disk. The security of this file is handled by the platform itself.
 */
public class AssetVaultSeed implements VaultSeed, DealsWithPluginFileSystem {
    private final String ASSET_VAULT_SEED_FILEPATH = "";
    private final String ASSET_VAULT_SEED_FILENAME = "";

    UUID pluginId;

    /**
     * VaultSeed interface variables
     */
    List<String> mnemonicCode;
    long creationTimeSeconds;
    byte[] seedBytes;

    /**
     * DealsWithPluginFileSystem interface variables
     */
    PluginFileSystem pluginFileSystem;

    /**
     * Constructor
     * @param pluginFileSystem
     */
    public AssetVaultSeed(PluginFileSystem pluginFileSystem, UUID pluginId) {
        this.pluginFileSystem = pluginFileSystem;
        this.pluginId = pluginId;
    }

    /**
     * VaultSeed interface implementation
     */
    @Override
    public List<String> getMnemonicCode() {
        return this.mnemonicCode;
    }

    @Override
    public long getCreationTimeSeconds() {
        return this.creationTimeSeconds;
    }

    @Override
    public byte[] getSeedBytes() {
        return this.seedBytes;
    }

    /**
     * DealsWithPluginFileSystem interface implementation
     */
    @Override
    public void setPluginFileSystem(PluginFileSystem pluginFileSystem) {
        this.pluginFileSystem = pluginFileSystem;
    }

    /**
     * Creates a new Seed and saves it to disk.
     * @throws CantCreateAssetVaultSeed
     */
    public void create() throws CantCreateAssetVaultSeed{
        /**
         * The Wallet class of bitcoinJ has a great entrophy level to generate a random seed.
         */
        Wallet seedWallet = new Wallet(BitcoinNetworkConfiguration.NETWORK_PARAMETERS);
        DeterministicSeed seed = seedWallet.getKeyChainSeed();

        /**
         * I set the class values
         */
        this.mnemonicCode = seed.getMnemonicCode();
        this.creationTimeSeconds = seed.getCreationTimeSeconds();
        this.seedBytes = seed.getSeedBytes();

        /**
         * I save the seed value into the file
         */
        PluginTextFile seedFile = null;
        try {
            seedFile = pluginFileSystem.createTextFile(pluginId, ASSET_VAULT_SEED_FILEPATH, ASSET_VAULT_SEED_FILENAME, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
            seedFile.setContent(XMLParser.parseObject(this));
            seedFile.persistToMedia();
        } catch (CantCreateFileException | CantPersistFileException e) {
            throw new CantCreateAssetVaultSeed(CantCreateAssetVaultSeed.DEFAULT_MESSAGE, e, "seedFile:" + ASSET_VAULT_SEED_FILEPATH + " " + ASSET_VAULT_SEED_FILENAME, "file might already exists.");
        }
    }

    /**
     * Load an existing Seed if it exists
     * @throws CantLoadExistingVaultSeed
     */
    public void load() throws CantLoadExistingVaultSeed{
        /**
         * Before loading the values I will reset them so I can make sure they were correctly loaded.
         */
        this.mnemonicCode = null;
        this.seedBytes = null;
        this.creationTimeSeconds = 0;

        /**
         * I will load the file, If it doesn't exists, then I will throw the error
         */
        PluginTextFile seedFile = null;
        try {
            seedFile = pluginFileSystem.getTextFile(this.pluginId, ASSET_VAULT_SEED_FILEPATH, ASSET_VAULT_SEED_FILENAME, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
            seedFile.loadFromMedia();
        } catch (FileNotFoundException | CantCreateFileException | CantLoadFileException e) {
            throw new CantLoadExistingVaultSeed(CantLoadExistingVaultSeed.DEFAULT_MESSAGE, e, "seedFile:" + ASSET_VAULT_SEED_FILEPATH + " " + ASSET_VAULT_SEED_FILENAME, "File doesn't exists. Try creating a new Seed.");
        }

        /**
         * By loading the XML from file and assigning the values to THIS, I'm re filling all originally saved values.
         */
        String fileContent = seedFile.getContent();
        XMLParser.parseXML(fileContent, this);

        /**
         * I make sure I have values in everything.
         */
        if (this.mnemonicCode == null || this.seedBytes == null || this.creationTimeSeconds == 0)
            throw new CantLoadExistingVaultSeed (CantLoadExistingVaultSeed.DEFAULT_MESSAGE, null, "Class values were not corretly populated after reading from file.", "failed in XMLParser class?");
    }

    /**
     * Validates if the seed exists by checking the file exists.
     * If we have a file, then the seed was already created.
     * @return true if the seed was already created
     */
    public boolean seedExists(){
        try {
            PluginTextFile seedFile = pluginFileSystem.getTextFile(this.pluginId, ASSET_VAULT_SEED_FILEPATH, ASSET_VAULT_SEED_FILEPATH, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
            return true;
        } catch (FileNotFoundException | CantCreateFileException e) {
            return false;
        }
    }

    /**
     * Will delete the existing seed (if any) so it can be recreated later.
     * @throws CantDeleteExistingVaultSeed
     */
    public void delete() throws CantDeleteExistingVaultSeed {
        try {
            pluginFileSystem.deleteTextFile(pluginId, ASSET_VAULT_SEED_FILEPATH, ASSET_VAULT_SEED_FILEPATH, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
        } catch (CantCreateFileException | FileNotFoundException e) {
            throw new CantDeleteExistingVaultSeed(CantDeleteExistingVaultSeed.DEFAULT_MESSAGE, e, "Error trying to delete a seed file.", "file doesn't exists.");
        }
    }
}
