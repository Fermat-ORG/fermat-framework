package com.bitdubai.fermat_bch_plugin.layer.crypto_vault.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededPluginReference;
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
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.TransactionProtocolManager;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoStatus;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.interfaces.BitcoinNetworkManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.exceptions.GetNewCryptoAddressException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.interfaces.PlatformCryptoVault;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.bitcoin_vault.CryptoVaultManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.exceptions.InsufficientCryptoFundsException;
import com.bitdubai.fermat_bch_plugin.layer.crypto_vault.developer.bitdubai.version_1.database.BitcoinCurrencyCryptoVaultDeveloperDatabaseFactory;
import com.bitdubai.fermat_bch_plugin.layer.crypto_vault.developer.bitdubai.version_1.exceptions.InvalidSeedException;
import com.bitdubai.fermat_bch_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure.BitcoinCurrencyCryptoVaultManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;
import com.bitdubai.fermat_pip_api.layer.user.device_user.exceptions.CantGetLoggedInDeviceUserException;
import com.bitdubai.fermat_pip_api.layer.user.device_user.interfaces.DeviceUserManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.exceptions.CouldNotGetCryptoStatusException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.exceptions.CouldNotSendMoneyException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.exceptions.CryptoTransactionAlreadySentException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.exceptions.InvalidSendToAddressException;
import com.bitdubai.fermat_bch_plugin.layer.crypto_vault.developer.bitdubai.version_1.exceptions.CantExecuteQueryException;
import com.bitdubai.fermat_bch_plugin.layer.crypto_vault.developer.bitdubai.version_1.exceptions.UnexpectedResultReturnedFromDatabaseException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

import javax.annotation.Nullable;

/**
 * Created by loui on 08/06/15.
 */
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
     * Determines if the passsed CryptoAddress is valid.
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
                    this.bitcoinNetworkManager);

        } catch (InvalidSeedException e) {
            e.printStackTrace();
        } catch (CantGetLoggedInDeviceUserException e) {
            e.printStackTrace();
        }

        /**
         * the service is started.
         */
        this.serviceStatus = ServiceStatus.STARTED;
        logManager.log(CryptoVaultBitcoinCurrencyPluginRoot.getLogLevelByClass(this.getClass().getName()), "PlatformCryptoVault started.", null, null);
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
     * CryptoVaultManager interface implementation
     */
    @Override
    public CryptoAddress getAddress() {
        return vault.getAddress();
    }

    /**
     * CryptoVaultManager interface implementation
     */
    @Override
    public List<CryptoAddress> getAddresses(int amount) {
        List<CryptoAddress> addresses = new ArrayList<CryptoAddress>();
        for (int i=0; i < amount; i++){
            addresses.add(getAddress());
        }
        return addresses;
    }

    // changed wallet id from UUID to Strubg representing a public key
    // Ezequiel Postan August 15th 2015
    @Override
    public String sendBitcoins(String walletPublicKey, UUID FermatTrId, CryptoAddress addressTo, long satoshis) throws InsufficientCryptoFundsException, InvalidSendToAddressException, CouldNotSendMoneyException, CryptoTransactionAlreadySentException {
        return vault.sendBitcoins(FermatTrId, addressTo, satoshis, null);
    }

    @Override
    public String sendBitcoins(String walletPublicKey, UUID FermatTrId, CryptoAddress addressTo, long satoshis, String op_Return) throws InsufficientCryptoFundsException, InvalidSendToAddressException, CouldNotSendMoneyException, CryptoTransactionAlreadySentException {
        return vault.sendBitcoins(FermatTrId, addressTo, satoshis, op_Return);
    }

    @Override
    public TransactionProtocolManager<CryptoTransaction> getTransactionManager() {
        return vault;
    }


    /**
     * Static method to get the logging level from any class under root.
     * @param className
     * @return
     */
    public static LogLevel getLogLevelByClass(String className){
        try{
            /**
             * sometimes the classname may be passed dinamically with an $moretext
             * I need to ignore whats after this.
             */
            String[] correctedClass = className.split((Pattern.quote("$")));
            return CryptoVaultBitcoinCurrencyPluginRoot.newLoggingLevel.get(correctedClass[0]);
        } catch (Exception e){
            /**
             * If I couldn't get the correct loggin level, then I will set it to minimal.
             */
            return DEFAULT_LOG_LEVEL;
        }
    }


    @Override
    public CryptoStatus getCryptoStatus(String txHash) throws CouldNotGetCryptoStatusException {
        try {
            return vault.getCryptoStatus(txHash);
        } catch (CantExecuteQueryException e) {
            throw new CouldNotGetCryptoStatusException("There was an error accesing the database to get the CryptoStatus.", e, "TransactionId: " + txHash, "An error in the database plugin.");
        } catch (UnexpectedResultReturnedFromDatabaseException e) {
            throw new CouldNotGetCryptoStatusException("There was an error getting the CryptoStatus of the transaction.", e, "TransactionId: " + txHash, "Duplicated transaction Id in the database.");
        }
    }

    /**
     * PlatformCryptoVault interface implementations
     */
    @Override
    public CryptoAddress getCryptoAddress(@Nullable BlockchainNetworkType blockchainNetworkType) throws GetNewCryptoAddressException {
        return getAddress();
    }

    @Override
    public Platforms getPlatform() {
        return Platforms.CRYPTO_CURRENCY_PLATFORM;
    }
}