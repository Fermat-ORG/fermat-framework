package com.bitdubai.fermat_bch_api.layer.crypto_vault.classes.vault_seed;

import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantLoadFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.BlockchainNetworkSelector;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.BitcoinNetworkConfiguration;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.classes.vault_seed.exceptions.CantCreateAssetVaultSeed;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.classes.vault_seed.exceptions.CantDeleteExistingVaultSeed;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.classes.vault_seed.exceptions.CantLoadExistingVaultSeed;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.exceptions.CantImportSeedException;
import com.google.common.base.Splitter;

import org.apache.commons.codec.binary.Hex;
import org.bitcoinj.core.Wallet;
import org.bitcoinj.crypto.MnemonicCode;
import org.bitcoinj.crypto.MnemonicException;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.store.UnreadableWalletException;
import org.bitcoinj.wallet.DeterministicSeed;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * Created by rodrigo on 9/19/15.
 * Creates and Stores in file the Seed used in the vault to create the Master Key that will be the root of the
 * Hierarchical Deterministic tree of keys.
 */
public class VaultSeedGenerator implements VaultSeed, DealsWithPluginFileSystem {
    String filePath;
    String fileName;
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
    public VaultSeedGenerator(PluginFileSystem pluginFileSystem, UUID pluginId, String filePath, String fileName) {
        this.pluginFileSystem = pluginFileSystem;
        this.pluginId = pluginId;
        this.filePath = filePath;
        this.fileName = fileName;
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
     * @throws com.bitdubai.fermat_bch_api.layer.crypto_vault.classes.vault_seed.exceptions.CantCreateAssetVaultSeed
     */
    public void create() throws CantCreateAssetVaultSeed {
        /**
         * The Wallet class of bitcoinJ has a great entrophy level to generate a random seed.
         */
        Wallet seedWallet = new Wallet(MainNetParams.get());
        DeterministicSeed seed = new DeterministicSeed(seedWallet.getKeyChainSeed().getMnemonicCode(), null, "", seedWallet.getKeyChainSeed().getCreationTimeSeconds());

        /**
         * I set the class values
         */
        this.mnemonicCode = seed.getMnemonicCode();
        this.creationTimeSeconds = seed.getCreationTimeSeconds();
        this.seedBytes = seed.getSeedBytes();


        storeSeedInFile(this.fileName);
    }

    private void storeSeedInFile(String seedFileName) throws CantCreateAssetVaultSeed {
        /**
         * I save the seed value into the file
         */
        PluginTextFile seedFile = null;
        try {
            seedFile = pluginFileSystem.createTextFile(pluginId, this.filePath,  seedFileName, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
            seedFile.setContent(generateFileContent());
            seedFile.persistToMedia();
        } catch (CantCreateFileException | CantPersistFileException e) {
            throw new CantCreateAssetVaultSeed(CantCreateAssetVaultSeed.DEFAULT_MESSAGE, e, "seedFile:" + this.filePath + " " + this.fileName, "file might already exists.");
        }
    }

    /**
     * validates if the passed seed is valid or not.
     * @param seed
     * @return
     */
    private boolean isSeedValid(DeterministicSeed seed){
        try {
            seed.check();
            return true;
        } catch (MnemonicException e) {
            return false;
        }
    }

    private String generateFileContent() {
        StringBuilder fileContent = new StringBuilder();
        fileContent.append(this.mnemonicCode.toString());
        fileContent.append(System.lineSeparator());
        fileContent.append(this.creationTimeSeconds);
        fileContent.append(System.lineSeparator());
        fileContent.append(this.seedBytes.toString());
        return fileContent.toString();
    }

    /**
     * Load an existing Seed if it exists
     * @throws CantLoadExistingVaultSeed
     */
    public void load(String fileName) throws CantLoadExistingVaultSeed{
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
            seedFile = pluginFileSystem.getTextFile(this.pluginId, this.filePath, fileName, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
            seedFile.loadFromMedia();
        } catch (FileNotFoundException | CantCreateFileException | CantLoadFileException e) {
            throw new CantLoadExistingVaultSeed(CantLoadExistingVaultSeed.DEFAULT_MESSAGE, e, "seedFile:" + filePath+ " " + fileName, "File doesn't exists. Try creating a new Seed.");
        }

        /**
         * I will load the file content into the variables
         */
        loadSeedFileContent(seedFile.getContent());

        /**
         * I make sure I have values in everything.
         */
        if (this.mnemonicCode == null || this.seedBytes == null || this.creationTimeSeconds == 0)
            throw new CantLoadExistingVaultSeed (CantLoadExistingVaultSeed.DEFAULT_MESSAGE, null, "Class values were not corretly populated after reading from file.", "failed in XMLParser class?");
    }

    private void loadSeedFileContent(String fileContent) throws CantLoadExistingVaultSeed {
        try{
            /**
             * I split the content into lines, there should only be 3
             */
            String[] lines = fileContent.split("\\r?\\n");
            //First line is the mnemonic Code, I remove the [] added by the toString.
            lines[0] = lines[0].replace("[", "");
            lines[0] = lines[0].replace("]", "");
            this.mnemonicCode = Arrays.asList(lines[0].split("\\s*,\\s*"));
            //second line is the creation time in seconds
            this.creationTimeSeconds = Long.parseLong(lines[1]);
            //third line are the seedBytes
            this.seedBytes = lines[2].getBytes();
        } catch (Exception e){
            throw new CantLoadExistingVaultSeed(CantLoadExistingVaultSeed.DEFAULT_MESSAGE, e, "There was an error trying to load the content from the Seed File.", "Corrupted File.");
        }

    }

    public void importSeed(String mnemonicCode, long seedCreationTimeSeconds) throws CantImportSeedException{
        importSeed(getmNemonicAsList(mnemonicCode), seedCreationTimeSeconds);
    }

    /**
     * Gets the mnemonic code as a list of strings.
     * @param mnemonicCode
     * @return
     */
    public static List<String> getmNemonicAsList(String mnemonicCode){
        return Splitter.on(" ").splitToList(mnemonicCode);
    }

    /**
     * Gets the mnemonic code as a phrase in a single string.
     * @param mnemonicCode
     * @return
     */
    public static String getmNemonicAsString(List<String> mnemonicCode){
        StringBuilder phrase = new StringBuilder();
        for (String word : mnemonicCode){
            phrase.append(word);
            phrase.append(" ");
        }

        //remove the last space
        return phrase.substring(0, phrase.length() - 1);
    }


    /**
     * Imports a new seed. Importing means changing the main seed for the new one, and change the actual to be an historic seed
     * @param mnemonicCode
     * @param seedCreationTimeSeconds
     * @throws CantImportSeedException
     */
    public void importSeed(List<String> mnemonicCode, long seedCreationTimeSeconds) throws CantImportSeedException{
        DeterministicSeed importedSeed = null;
        importedSeed = new DeterministicSeed(mnemonicCode, null, "", seedCreationTimeSeconds);

        if (!isSeedValid(importedSeed))
            throw new CantImportSeedException(null, "Importing new seed from " + mnemonicCode, "incorrect seed format");


        /**
         * I set the seed values of the class
         */
        this.mnemonicCode = importedSeed.getMnemonicCode();
        this.creationTimeSeconds = importedSeed.getCreationTimeSeconds();
        this.seedBytes = importedSeed.getSeedBytes();

        /**
         * and Store the new seed.
         */
        try {
            String newSeedFileName = this.fileName + "_" + this.getNextSeedFileOrder();
            storeSeedInFile(newSeedFileName);
        } catch (CantCreateAssetVaultSeed cantCreateAssetVaultSeed) {
            throw new CantImportSeedException(cantCreateAssetVaultSeed, "unable to save new seed into disk.", "IO Error");
        }
    }

    public void importSeed(List<String> mnemonicCode, long seedCreationTimeSeconds, byte[] bytes) throws CantImportSeedException{
        DeterministicSeed importedSeed = null;
        importedSeed = new DeterministicSeed(mnemonicCode, bytes, "", seedCreationTimeSeconds);

        if (!isSeedValid(importedSeed))
            throw new CantImportSeedException(null, "Importing new seed from " + mnemonicCode, "incorrect seed format");


        /**
         * I set the seed values of the class
         */
        this.mnemonicCode = importedSeed.getMnemonicCode();
        this.creationTimeSeconds = importedSeed.getCreationTimeSeconds();
        this.seedBytes = importedSeed.getSeedBytes();

        /**
         * and Store the new seed.
         */
        try {
            String newSeedFileName = this.fileName + "_" + this.getNextSeedFileOrder();
            storeSeedInFile(newSeedFileName);
        } catch (CantCreateAssetVaultSeed cantCreateAssetVaultSeed) {
            throw new CantImportSeedException(cantCreateAssetVaultSeed, "unable to save new seed into disk.", "IO Error");
        }
    }


    private void archiveNewSeed() throws CantCreateAssetVaultSeed {
        int seedOrder = getNextSeedFileOrder();
        try {
            this.load(this.fileName);
        } catch (CantLoadExistingVaultSeed cantLoadExistingVaultSeed) {
            throw new CantCreateAssetVaultSeed(CantCreateAssetVaultSeed.DEFAULT_MESSAGE, cantLoadExistingVaultSeed, "unable to replace seed with ned one", "IO error");
        }
        storeSeedInFile(this.fileName + "_" + seedOrder);
    }

    /**
     * searchs for next available number on archived seeds files.
     * @return
     */
    private int getNextSeedFileOrder() {
        int i = 1;

        while(true){
            String historicSeedFileName = this.fileName + "_" + i;
            try {
                pluginFileSystem.getTextFile(this.pluginId, this.filePath, historicSeedFileName, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
            } catch (Exception e) {
                return i;
            }

            i++;
        }
    }

    /**
     * Validates if the seed exists by checking the file exists.
     * If we have a file, then the seed was already created.
     * @return true if the seed was already created
     */
    public boolean seedExists(){
        try {
            PluginTextFile seedFile = pluginFileSystem.getTextFile(this.pluginId, filePath, fileName, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
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
            pluginFileSystem.deleteTextFile(pluginId, filePath, fileName, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
        } catch (CantCreateFileException | FileNotFoundException e) {
            throw new CantDeleteExistingVaultSeed(CantDeleteExistingVaultSeed.DEFAULT_MESSAGE, e, "Error trying to delete a seed file.", "file doesn't exists.");
        }
    }

    /**
     * iterates from stored files on disk to form and return previously saved deterministicSeeds.
     * @return
     */
    public List<DeterministicSeed> getImportedSeeds() {
        List<DeterministicSeed> importedSeedsList = new ArrayList<>();
        int i = 1;
        while (true){
            try {
                load(this.fileName + "_" + i);
                DeterministicSeed importedSeed = new DeterministicSeed(this.mnemonicCode, null, "", this.creationTimeSeconds);
                importedSeedsList.add(importedSeed);

                i++;
            } catch (CantLoadExistingVaultSeed cantLoadExistingVaultSeed) {
                return importedSeedsList;
            }
        }


    }
}
