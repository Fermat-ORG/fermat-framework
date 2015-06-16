package com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.DealsWithPluginIdentity;
import com.bitdubai.fermat_api.layer.dmp_world.wallet.exceptions.CantStartAgentException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.cry_crypto_network.bitcoin.BitcoinCryptoNetworkManager;
import com.bitdubai.fermat_api.layer.cry_crypto_network.bitcoin.BitcoinManager;
import com.bitdubai.fermat_api.layer.cry_crypto_network.bitcoin.DealsWithBitcoinCryptoNetwork;
import com.bitdubai.fermat_api.layer.cry_crypto_network.bitcoin.exceptions.CantCreateCryptoWalletException;
import com.bitdubai.fermat_api.layer.cry_crypto_vault.CryptoVault;

import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.Wallet;
import org.bitcoinj.store.UnreadableWalletException;
import org.bitcoinj.wallet.DeterministicSeed;

import java.io.File;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by rodrigo on 09/06/15.
 */
public class BitcoinCryptoVault implements BitcoinManager, CryptoVault, DealsWithBitcoinCryptoNetwork, DealsWithErrors, DealsWithPluginIdentity, DealsWithPluginDatabaseSystem, DealsWithPluginFileSystem{

    /**
     * BitcoinCryptoVault member variables
     */
    NetworkParameters networkParameters;
    File vaultFile;
    String vaultFileName;
    VaultEventListeners vaultEventListeners;


    /**
     * CryptoVault interface member variable
     */
    UUID userId;
    Wallet vault;

    /**
     * DealsWithCryptonetwork interface member variable
     */
    BitcoinCryptoNetworkManager bitcoinCryptoNetworkManager;

    /**
     * DealsWithErros interface member variable
     */
    ErrorManager errorManager;

    /**
     * DealsWithPluginIdentity interface member variable
     */
    UUID pluginId;


    /**
     * DealsWithPluginDatabaseSystem interface member variable
     */
    PluginDatabaseSystem pluginDatabaseSystem;
    Database database;

    /**
     * DealsWithPlugInFileSystem interface member variable
     */
    PluginFileSystem pluginFileSystem;



    /**
     * Constructor
     * @param UserId the Id of the user of the platform.
     */
    public BitcoinCryptoVault (UUID UserId) throws CantCreateCryptoWalletException {
        this.userId = UserId;
        this.networkParameters = BitcoinNetworkConfiguration.getNetworkConfiguration();

        this.vaultFileName = userId.toString() + ".vault";
        this.vaultFile = new File(userId.toString(), vaultFileName);

        loadOrCreateVault();
    }

    public  void loadOrCreateVault() throws CantCreateCryptoWalletException {
        if (vaultFile.exists())
            loadExistingVaultFromFile();
        else
            createNewVault();

        configureVault();
    }

    /**
     * creates a new vault.
     * @throws CantCreateCryptoWalletException
     */
    private void createNewVault() throws CantCreateCryptoWalletException {
        vault = new Wallet(networkParameters);
        try {
            pluginFileSystem.createBinaryFile(pluginId, userId.toString(), vaultFileName, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
            /**
             * If I couldn't create it I can't go on
             */
        } catch (CantCreateFileException cantCreateFileException) {
            //todo change fermat-pip-api to add new plugin CryptoVault
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_CRYPTO_NETWORK, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantCreateFileException);
            throw new CantCreateCryptoWalletException();
        }
    }

    /**
     * Loads an existing Vault from file
     * @throws CantCreateCryptoWalletException
     */
    private void loadExistingVaultFromFile() throws CantCreateCryptoWalletException {
        try {
            vault = Wallet.loadFromFile(vaultFile);
            /**s
             * If I couldn't load it I can't go on.
             */
        } catch (UnreadableWalletException unreadableWalletException) {
            //todo change fermat-pip-api to add new plugin CryptoVault
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_CRYPTO_NETWORK, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, unreadableWalletException);
            throw new CantCreateCryptoWalletException();
        }

    }

    /**
     * Will load the Vault from a provided seed. The user will need to provide the mNemonic code,
     * which is a series of prefedined words and the creation time. The vault will need to provide the methods
     * to retrieve this information at soma point.
     * @param mNemonicCode
     * @param CreationTimeInSeconds
     */
    public void loadExistingVaultFromSeed(String mNemonicCode, long CreationTimeInSeconds) throws CantCreateCryptoWalletException {
        try {
            DeterministicSeed seed = new DeterministicSeed(mNemonicCode, null, null, CreationTimeInSeconds);
            vault = Wallet.fromSeed(networkParameters, seed);
            configureVault();
        } catch (UnreadableWalletException e) {
            /**
             * I cannot load the existing vault from the provide seed. I cannot handle this
             */
            //todo change fermat-pip-api to add new plugin CryptoVault
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_CRYPTO_NETWORK, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantCreateCryptoWalletException();
        }

    }

    /**
     * I'm connecting the vault to the bitcoin Agent.
     * @throws CantStartAgentException
     */
    public void connectVault() throws CantStartAgentException {
        bitcoinCryptoNetworkManager.setVault(this);
        bitcoinCryptoNetworkManager.connectToBitcoinNetwork();
    }

    public void disconnectVault(){
        bitcoinCryptoNetworkManager.setVault(this);
        bitcoinCryptoNetworkManager.disconnectFromBitcoinNetwork();
    }

    /**
     * configures internal vault parameters and creates the database that will hold
     * the transactions status.
     * @throws CantCreateCryptoWalletException
     */
    private void configureVault() throws CantCreateCryptoWalletException {
        vault.autosaveToFile(vaultFile, 0, TimeUnit.SECONDS, null);
        createDatabase();
        vaultEventListeners = new VaultEventListeners(database);
        vault.addEventListener(vaultEventListeners);
    }

    /**
     * I create (or load if already exists) the database for this userID
     */
    private void createDatabase() throws CantCreateCryptoWalletException {
        /**
         * I will try to open the database first, if it doesn't exists, then I create it
         */
        try {
            database = pluginDatabaseSystem.openDatabase(pluginId, userId.toString());
        }  catch (DatabaseNotFoundException e) {
            /**
             * The database doesn't exists, lets create it.
             */
            try {
                database = pluginDatabaseSystem.createDatabase(pluginId, userId.toString());
            } catch (CantCreateDatabaseException e1) {
                /**
                 * something went wrong creatig the db, I can't handle this.
                 */
                //todo add in fermat-pip-api correct plug in name CryptoVault
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_CRYPTO_NETWORK, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
                throw new CantCreateCryptoWalletException();
            }
        } catch (CantOpenDatabaseException e) {
            /**
             * the database exists, but I cannot open it! I cannot handle this.
             */
            //todo add in fermat-pip-api correct plug in name CryptoVault
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_CRYPTO_NETWORK, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantCreateCryptoWalletException();
        }
    }

    /**
     * CryptoVault interface implementations
     * @param UserId
     */
    @Override
    public void setUserId(UUID UserId) {
        this.userId = UserId;
    }

    /**
     * CryptoVault interface implementations
     * @return
     */
    @Override
    public UUID getUserId() {
        return this.userId;
    }

    /**
     * CryptoVault interface implementations
     * @return
     */
    @Override
    public Object getWallet() {
        return vault;
    }

    /**
     * DealsWithBitcoinCryptoNetwork interface implementation
     * @param bitcoinCryptoNetworkManager
     */
    @Override
    public void setBitcoinCryptoNetworkManager(BitcoinCryptoNetworkManager bitcoinCryptoNetworkManager) {
        this.bitcoinCryptoNetworkManager = bitcoinCryptoNetworkManager;
    }

    /**
     * DealsWithPluginFileSystem interface implementation
     * @param pluginFileSystem
     */
    @Override
    public void setPluginFileSystem(PluginFileSystem pluginFileSystem) {
        this.pluginFileSystem = pluginFileSystem;
    }

    /**
     * DealsWithPluginIdentity interface implementation
     * @param pluginId
     */
    @Override
    public void setPluginId(UUID pluginId) {
        this.pluginId = pluginId;
    }

    /**
     * DealsWithError interface implementation
     * @param errorManager
     */
    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }

    /**
     * DealsWithPluginDatabaseSystem interface implementation
     * @param pluginDatabaseSystem
     */
    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
       this.pluginDatabaseSystem = pluginDatabaseSystem;
    }
}
