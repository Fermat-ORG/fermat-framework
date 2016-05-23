package com.bitdubai.fermat_bch_plugin.layer.crypto_vault.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededPluginReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.developer.DatabaseManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.core.PluginInfo;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.BitcoinNetworkSelector;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantStoreBitcoinTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.interfaces.BitcoinNetworkManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.bitcoin_vault.CryptoVaultManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.classes.transactions.DraftTransaction;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.classes.vault_seed.exceptions.CantLoadExistingVaultSeed;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.exceptions.CantCreateDraftTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.exceptions.CantGetDraftTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.exceptions.CantSignTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.exceptions.CouldNotGenerateTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.exceptions.CouldNotSendMoneyException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.exceptions.CryptoTransactionAlreadySentException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.exceptions.GetNewCryptoAddressException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.exceptions.InsufficientCryptoFundsException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.exceptions.InvalidSendToAddressException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.interfaces.PlatformCryptoVault;
import com.bitdubai.fermat_bch_plugin.layer.crypto_vault.developer.bitdubai.version_1.database.BitcoinCurrencyCryptoVaultDeveloperDatabaseFactory;
import com.bitdubai.fermat_bch_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure.BitcoinCurrencyCryptoVaultManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;
import com.bitdubai.fermat_pip_api.layer.user.device_user.interfaces.DeviceUserManager;

import org.bitcoinj.core.NetworkParameters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Nullable;

/**
 * Created by acostarodrigo on 08/06/15.
 */
@PluginInfo(difficulty = PluginInfo.Dificulty.HIGH, maintainerMail = "acosta_rodrigo@hotmail.com", createdBy = "acostarodrigo", layer = Layers.CRYPTO_VAULT, platform = Platforms.BLOCKCHAINS, plugin = Plugins.BITCOIN_VAULT)
public class CryptoVaultBitcoinCurrencyPluginRoot extends AbstractPlugin implements
        CryptoVaultManager,
        PlatformCryptoVault,
        DatabaseManagerForDevelopers{

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM   , layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER         )
    private ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM   , layer = Layers.PLATFORM_SERVICE, addon = Addons.EVENT_MANAGER         )
    private EventManager eventManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM   , layer = Layers.USER            , addon = Addons.DEVICE_USER         )
    private DeviceUserManager deviceUserManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM)
    private PluginFileSystem pluginFileSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.LOG_MANAGER)
    private LogManager logManager;

    @NeededPluginReference(platform = Platforms.BLOCKCHAINS         , layer = Layers.CRYPTO_NETWORK  , plugin = Plugins.BITCOIN_NETWORK       )
    private BitcoinNetworkManager bitcoinNetworkManager;


    public CryptoVaultBitcoinCurrencyPluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    public static final EventSource EVENT_SOURCE = EventSource.CRYPTO_VAULT;

    /**
     * CryptoVaultBitcoinCurrencyPluginRoot member variables
     */
    BitcoinCurrencyCryptoVaultManager bitcoinCurrencyCryptoVaultManager;
    Database database;

    static Map<String, LogLevel> newLoggingLevel = new HashMap<String, LogLevel>();

    List<FermatEventListener> listenersAdded = new ArrayList<>();


    /**
     * DatabaseManagerForDevelopers interface implementation
     * Returns the list of databases implemented on this plug in.
     */
    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        BitcoinCurrencyCryptoVaultDeveloperDatabaseFactory developerDatabaseFactory = new BitcoinCurrencyCryptoVaultDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
        return developerDatabaseFactory.getDatabaseList(developerObjectFactory);
    }

    /**
     * returns the list of tables for the given database
     * @param developerObjectFactory
     * @param developerDatabase
     * @return
     */
    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        BitcoinCurrencyCryptoVaultDeveloperDatabaseFactory developerDatabaseFactory = new BitcoinCurrencyCryptoVaultDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
        return developerDatabaseFactory.getDatabaseTableList(developerObjectFactory);
    }

    /**
     * returns the list of records for the passed table
     * @param developerObjectFactory
     * @param developerDatabase
     * @param developerDatabaseTable
     * @return
     */
    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        BitcoinCurrencyCryptoVaultDeveloperDatabaseFactory developerDatabaseFactory = new BitcoinCurrencyCryptoVaultDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
        return developerDatabaseFactory.getDatabaseTableContent(developerObjectFactory, developerDatabaseTable);
    }

    /**
     * Determines if the passed CryptoAddress is valid.
     * @param addressTo the address to validate
     * @return true if valid, false if it is not.
     */
    @Override
    public boolean isValidAddress(CryptoAddress addressTo) {
        return  bitcoinCurrencyCryptoVaultManager.isValidAddress(addressTo);
    }

    @Override
    public void start() throws CantStartPluginException {
        /**
         * I'm starting the new Crypto Vault
         */
        try {
            // the DeviceUserLogged
            String deviceUserLoggedPublicKey = deviceUserManager.getLoggedInDeviceUser().getPublicKey();
            System.out.println("Starting new BitcoinCurrency Crypto Vault.");
            bitcoinCurrencyCryptoVaultManager = new BitcoinCurrencyCryptoVaultManager(this.pluginId,
                    this.pluginFileSystem,
                    this.pluginDatabaseSystem,
                    deviceUserLoggedPublicKey,
                    this.bitcoinNetworkManager,
                    this.errorManager);

        } catch (Exception e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantStartPluginException("Error starting plugin Bitcoin Vault");
        }

        /**
         * the service is started.
         */
        this.serviceStatus = ServiceStatus.STARTED;
    }

    /**
     * Service interface implementation
     */
    @Override
    public void stop() {

        /**
         * I will remove all the event listeners registered with the event manager.
         */

        for (FermatEventListener fermatEventListener : listenersAdded) {
            eventManager.removeListener(fermatEventListener);
        }

        listenersAdded.clear();

        /**
         * I will also stop the Notification Agent
         */

        this.serviceStatus = ServiceStatus.STOPPED;
    }


    /**
     * gets a fresh un used crypto Address from the vault
     */
    @Override
    public CryptoAddress getAddress(BlockchainNetworkType blockchainNetworkType) {
        return bitcoinCurrencyCryptoVaultManager.getAddress(blockchainNetworkType);
    }

    @Override
    public synchronized String sendBitcoins(String walletPublicKey, UUID FermatTrId, CryptoAddress addressTo, long satoshis, BlockchainNetworkType blockchainNetworkType) throws InsufficientCryptoFundsException, InvalidSendToAddressException, CouldNotSendMoneyException, CryptoTransactionAlreadySentException {
        return bitcoinCurrencyCryptoVaultManager.sendBitcoins(walletPublicKey, FermatTrId, addressTo, satoshis, null, true, blockchainNetworkType);
    }

    @Override
    public synchronized String sendBitcoins(String walletPublicKey, UUID FermatTrId, CryptoAddress addressTo, long satoshis, String op_Return,  BlockchainNetworkType blockchainNetworkType) throws InsufficientCryptoFundsException, InvalidSendToAddressException, CouldNotSendMoneyException, CryptoTransactionAlreadySentException {
        return bitcoinCurrencyCryptoVaultManager.sendBitcoins(walletPublicKey, FermatTrId, addressTo, satoshis, op_Return, true, blockchainNetworkType);
    }



    /**
     * PlatformCryptoVault interface implementations
     */
    @Override
    public CryptoAddress getCryptoAddress(@Nullable BlockchainNetworkType blockchainNetworkType) throws GetNewCryptoAddressException {
        return getAddress(blockchainNetworkType);
    }

    @Override
    public Platforms getPlatform() {
        return Platforms.CRYPTO_CURRENCY_PLATFORM;
    }

    /**
     * Generates a new Bitcoin Transaction with needed inputs and outputs.
     * Also signs and pass to the Crypto Network the transaction generated.
     * @param walletPublicKey
     * @param fermatTrId
     * @param addressTo
     * @param satoshis
     * @return
     * @throws InsufficientCryptoFundsException
     * @throws InvalidSendToAddressException
     * @throws CryptoTransactionAlreadySentException
     */
    @Override
    public synchronized String generateTransaction(String walletPublicKey, UUID fermatTrId, CryptoAddress addressTo, long satoshis, BlockchainNetworkType blockchainNetworkType) throws InsufficientCryptoFundsException, InvalidSendToAddressException, CryptoTransactionAlreadySentException, CouldNotGenerateTransactionException {
        return generateTransaction(walletPublicKey, fermatTrId, addressTo, satoshis, null, blockchainNetworkType);
    }

    /**
     * Generates a new Bitcoin Transaction with needed inputs and outputs.
     * Also signs and pass to the Crypto Network the transaction generated.
     * @param walletPublicKey
     * @param fermatTrId
     * @param addressTo
     * @param satoshis
     * @return
     * @throws InsufficientCryptoFundsException
     * @throws InvalidSendToAddressException
     * @throws CryptoTransactionAlreadySentException
     */
    @Override
    public synchronized String generateTransaction(String walletPublicKey, UUID fermatTrId, CryptoAddress addressTo, long satoshis, String op_Return,  BlockchainNetworkType blockchainNetworkType) throws InsufficientCryptoFundsException, InvalidSendToAddressException, CryptoTransactionAlreadySentException, CouldNotGenerateTransactionException {
        String trHash=null;
        try {
            trHash = bitcoinCurrencyCryptoVaultManager.sendBitcoins(walletPublicKey, fermatTrId, addressTo, satoshis, op_Return, false, blockchainNetworkType);
        } catch (CouldNotSendMoneyException e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CouldNotGenerateTransactionException(CouldNotGenerateTransactionException.DEFAULT_MESSAGE, e, "Transaction couldn't be stored at the cryptoNetwork", null);
        }
        return trHash;
    }

    /**
     * Gets the Mnemonic code generated for this vault.
     * It can be used to export and import it somewhere else.
     * @return
     * @throws CantLoadExistingVaultSeed
     */
    @Override
    public List<String> getMnemonicCode() throws CantLoadExistingVaultSeed {
        return bitcoinCurrencyCryptoVaultManager.getMnemonicCode();
    }

    @Override
    public void importSeedFromMnemonicCode(List<String> mnemonicCode,long date,@Nullable String userPhrase,BlockchainNetworkType blockchainNetworkType) throws CantLoadExistingVaultSeed {
        //todo: me faltaria validar si la network está activa..
        /**
         * I get the networkParameter
         */
        final NetworkParameters networkParameters = BitcoinNetworkSelector.getNetworkParameter(blockchainNetworkType);
        bitcoinCurrencyCryptoVaultManager.importCryptoFromSeed(networkParameters,mnemonicCode,date,userPhrase);
    }


    /**
     * Signs the owned inputs of the passed Draft transaction
     * @param draftTransaction the transaction to sign
     * @return the signed Transaction
     * @throws CantSignTransactionException
     */
    @Override
    public DraftTransaction signTransaction(DraftTransaction draftTransaction) throws CantSignTransactionException {
        return bitcoinCurrencyCryptoVaultManager.signTransaction(draftTransaction);
    }

    /**
     * Adds more inputs and outputs to a draft transaction
     * @param draftTransaction the incomplete draft transaction
     * @param valueToSend the amount of bitcoins in satoshis to add to the transaction
     * @param addressTo the address to that will receive the bitcoins.
     * @return the draft transaction with the added values.
     * @throws CantCreateDraftTransactionException
     */
    @Override
    public DraftTransaction addInputsToDraftTransaction(DraftTransaction draftTransaction, long valueToSend, CryptoAddress addressTo, BlockchainNetworkType blockchainNetworkType) throws CantCreateDraftTransactionException {
        return bitcoinCurrencyCryptoVaultManager.addInputsToDraftTransaction(draftTransaction, valueToSend, addressTo, blockchainNetworkType);
    }

    /**
     * Returns a stored draft transaction
     * @param blockchainNetworkType
     * @param txHash
     * @return
     * @throws CantGetDraftTransactionException
     */
    @Override
    public DraftTransaction getDraftTransaction(BlockchainNetworkType blockchainNetworkType, String txHash) throws CantGetDraftTransactionException{
        return bitcoinCurrencyCryptoVaultManager.getDraftTransaction(blockchainNetworkType, txHash);
    }

    /**
     * Persists a draft transaction in the vault.
     * @param draftTransaction the draft Transaction to store
     * @throws CantStoreBitcoinTransactionException
     */
    @Override
    public void saveTransaction(DraftTransaction draftTransaction) throws CantStoreBitcoinTransactionException {
        try {
            bitcoinCurrencyCryptoVaultManager.storeDraftTransaction(draftTransaction);
        } catch (Exception e){
            CantStoreBitcoinTransactionException exception = new CantStoreBitcoinTransactionException(
                    CantStoreBitcoinTransactionException.DEFAULT_MESSAGE,
                    e,
                    "There was an error persisting the transaction on the vault",
                    "IO Error");
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
            throw exception;
        }
    }
}