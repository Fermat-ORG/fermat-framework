package com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.developer.DatabaseManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.TransactionProtocolManager;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_api.layer.dmp_world.wallet.exceptions.CantStartAgentException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.DealsWithEvents;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.EventListener;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.EventManager;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_api.layer.pip_user.device_user.DealsWithDeviceUsers;
import com.bitdubai.fermat_api.layer.pip_user.device_user.DeviceUserManager;
import com.bitdubai.fermat_cry_api.layer.crypto_network.bitcoin.BitcoinCryptoNetworkManager;
import com.bitdubai.fermat_cry_api.layer.crypto_network.bitcoin.DealsWithBitcoinCryptoNetwork;
import com.bitdubai.fermat_cry_api.layer.crypto_network.bitcoin.exceptions.CantConnectToBitcoinNetwork;
import com.bitdubai.fermat_cry_api.layer.crypto_network.bitcoin.exceptions.CantCreateCryptoWalletException;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.CryptoVaultManager;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.exceptions.CouldNotSendMoneyException;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.exceptions.InvalidSendToAddressException;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.exceptions.VaultNotConnectedToNetworkException;
import com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure.BitcoinCryptoVault;
import com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure.CryptoVaultDatabaseFactory;
import com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure.events.TransactionNotificationAgent;
import com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure.developerUtils.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by loui on 08/06/15.
 */
public class BitcoinCryptoVaultPluginRoot implements CryptoVaultManager, DatabaseManagerForDevelopers, DealsWithBitcoinCryptoNetwork, DealsWithEvents, DealsWithErrors, DealsWithPluginDatabaseSystem, DealsWithDeviceUsers, DealsWithLogger, DealsWithPluginFileSystem, LogManagerForDevelopers, Plugin, Service {

    /**
     * BitcoinCryptoVaultPluginRoot member variables
     */
    BitcoinCryptoVault vault;
    UUID userId;
    TransactionNotificationAgent transactionNotificationAgent;

    /**
     * DealsWithBitcoinCryptoNetwork interface member variable
     */
    BitcoinCryptoNetworkManager bitcoinCryptoNetworkManager;

    /**
     * DealWithEvents Interface member variables.
     */
    EventManager eventManager;

    /**
     * Plugin Interface member variables.
     */
    UUID pluginId;

    /**
     * DealsWithErrors interface member variable
     */
    ErrorManager errorManager;

    /**
     * DealsWithPluginDatabaseSystem interface member variable
     */
    PluginDatabaseSystem pluginDatabaseSystem;
    Database database;

    /**
     * DealsWithLogger interface member variable
     */
    LogManager logManager;

    /**
     * DealsWithDeviceUsers interface member variable
     */
    DeviceUserManager deviceUserManager;

    /**
     * LogManagerForDevelopers member variables
     */
    LogLevel logLevel;


    /**
     * DealsWithPluginFileSystem interface member variable
     */
    PluginFileSystem pluginFileSystem;


    /**
     * Service Interface member variables.
     */
    ServiceStatus serviceStatus = ServiceStatus.CREATED;
    List<EventListener> listenersAdded = new ArrayList<>();


    /**
     * DealsWithBitcoinCryptoNetwork interface implementation
     * @param bitcoinCryptoNetworkManager
     */
    @Override
    public void setBitcoinCryptoNetworkManager(BitcoinCryptoNetworkManager bitcoinCryptoNetworkManager) {
        this.bitcoinCryptoNetworkManager = bitcoinCryptoNetworkManager;
    }

    @Override
    public LogLevel getLoggingLevel() {
        return logLevel;
    }

    @Override
    public void changeLoggingLevel(LogLevel newLoggingLevel) {
        this.logLevel = newLoggingLevel;
    }

    /**
     * DatabaseManagerForDevelopers interface implementation
     * Returns the list of databases implemented on this plug in.
     */
    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        DeveloperDatabaseFactory dbFactory = new DeveloperDatabaseFactory(userId.toString(), pluginId.toString());
        return dbFactory.getDatabaseList(developerObjectFactory);

    }

    /**
     * returns the list of tables for the given database
     * @param developerObjectFactory
     * @param developerDatabase
     * @return
     */
    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        return DeveloperDatabaseFactory.getDatabaseTableList(developerObjectFactory);
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
        return DeveloperDatabaseFactory.getDatabaseTableContent(developerObjectFactory, database, developerDatabaseTable);
    }

    @Override
    public void setLogManager(LogLevel logLevel, LogManager logManager) {
        this.logLevel = logLevel;
        this.logManager = logManager;
    }

    /**
     * Service interface implementation
     * @return
     */
    @Override
    public ServiceStatus getStatus() {
        return this.serviceStatus;
    }


    /**
     * DealWithEvents Interface implementation.
     */

    @Override
    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    /**
     *DealWithErrors Interface implementation.
     */
    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }

    /**
     * DealsWithPlugIndatabaseSystem interace implementation
     * @param pluginDatabaseSystem
     */
    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /**
     * Plugin method implementation.
     */

    @Override
    public void setId(UUID pluginId) {
        this.pluginId = pluginId;
    }


    @Override
    public void setDeviceUserManager(DeviceUserManager deviceUserManager) {
        this.deviceUserManager = deviceUserManager;
    }

    /**
     * DealsWithPluginFileSystem interface implementation
     * @param pluginFileSystem
     */
    @Override
    public void setPluginFileSystem(PluginFileSystem pluginFileSystem) {
        this.pluginFileSystem = pluginFileSystem;
    }


    @Override
    public void start() throws CantStartPluginException {
        System.out.println("CryptoVault Starting...");
        logManager.log(LogLevel.MODERATE_LOGGING, "CryptoVault Starting...", "CryptoVault Starting...", "CryptoVault Starting...");


        /**
         * I get the userId from the deviceUserManager
         */
        //userId = deviceUserManager.getLoggedInUser().getId();
        userId = UUID.fromString("4c4322c7-8c73-4633-956d-96991f413e93"); //todo fix deviceUser Implementation
        //userId = UUID.randomUUID();


        /**
         * I will try to open the database first, if it doesn't exists, then I create it
         */
        try {

            database = pluginDatabaseSystem.openDatabase(pluginId, userId.toString());

        } catch (CantOpenDatabaseException e) {
            /**
             * The database could not be opened, let try to create it instead.
             */
            try {
                CryptoVaultDatabaseFactory cryptoVaultDatabaseFactory = new CryptoVaultDatabaseFactory();
                cryptoVaultDatabaseFactory.setPluginDatabaseSystem(pluginDatabaseSystem);
                cryptoVaultDatabaseFactory.setErrorManager(errorManager);

                database = cryptoVaultDatabaseFactory.createDatabase(pluginId, userId.toString());
            } catch (CantCreateDatabaseException e1) {
                /**
                 * something went wrong creating the db, I can't handle this.
                 */
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_CRYPTO_VAULT, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
                throw new CantStartPluginException(e1, Plugins.BITDUBAI_BITCOIN_CRYPTO_VAULT);
            }

        } catch (DatabaseNotFoundException e) {
            /**
             * The database doesn't exists, lets create it.
             */
            try {
                CryptoVaultDatabaseFactory cryptoVaultDatabaseFactory = new CryptoVaultDatabaseFactory();
                cryptoVaultDatabaseFactory.setPluginDatabaseSystem(pluginDatabaseSystem);
                cryptoVaultDatabaseFactory.setErrorManager(errorManager);

                database = cryptoVaultDatabaseFactory.createDatabase(pluginId, userId.toString());
            } catch (CantCreateDatabaseException e1) {
                /**
                 * something went wrong creatig the db, I can't handle this.
                 */
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_CRYPTO_VAULT, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
                throw new CantStartPluginException(e1, Plugins.BITDUBAI_BITCOIN_CRYPTO_VAULT);
            }
        }

            /**
             * I will start the loading creation of the wallet from the user Id
             */
            try {
                vault = new BitcoinCryptoVault(this.userId);
                vault.setErrorManager(errorManager);
                vault.setPluginDatabaseSystem(pluginDatabaseSystem);
                vault.setDatabase(this.database);
                vault.setPluginFileSystem(pluginFileSystem);
                vault.setBitcoinCryptoNetworkManager(bitcoinCryptoNetworkManager);
                vault.setPluginId(pluginId);

                vault.loadOrCreateVault();

                System.out.println("CryptoVault - Valid receive address for the vault is: " + vault.getAddress().getAddress());


                /**
                 * Once the vault is loaded or created, I will connect it to Bitcoin network to recieve pending transactions
                 */

                try {
                    vault.connectVault();
                } catch (CantConnectToBitcoinNetwork cantConnectToBitcoinNetwork) {
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_CRYPTO_VAULT, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantConnectToBitcoinNetwork);

                }


            } catch (CantCreateCryptoWalletException cantCreateCryptoWalletException ) {
                /**
                 * If I couldnt create the Vault, I cant go on.
                 */
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_CRYPTO_VAULT, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantCreateCryptoWalletException );
                throw new CantStartPluginException(cantCreateCryptoWalletException, Plugins.BITDUBAI_BITCOIN_CRYPTO_VAULT);
            }


            /**
             * now I will start the TransactionNotificationAgent to monitor
             */
            transactionNotificationAgent = new TransactionNotificationAgent(eventManager, pluginDatabaseSystem, errorManager, pluginId, userId);
            try {
                transactionNotificationAgent.start();
            } catch (CantStartAgentException cantStartAgentException ) {
                /**
                 * If I couldn't start the agent, I still will continue with the vault
                 */
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_CRYPTO_VAULT, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantStartAgentException );
            }


            /**
             * the service is started.
             */
            this.serviceStatus = ServiceStatus.STARTED;
            System.out.println("CryptoVault started.");
    }

    /**
     * Service interface implementation
     */
    @Override
    public void pause() {

        this.serviceStatus = ServiceStatus.PAUSED;
    }

    /**
     * Service interface implementation
     */
    @Override
    public void resume() {
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

        for (EventListener eventListener : listenersAdded) {
            eventManager.removeListener(eventListener);
        }

        listenersAdded.clear();

        /**
         * I will also stop the Notification Agent
         */
        transactionNotificationAgent.stop();

        this.serviceStatus = ServiceStatus.STOPPED;
    }

    /**
     * CryptoVaultManager interface implementation
     */
    @Override
    public void connectToBitcoin() throws VaultNotConnectedToNetworkException {
        try {
            vault.connectVault();
        } catch (CantConnectToBitcoinNetwork cantConnectToBitcoinNetwork) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_CRYPTO_VAULT,UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantConnectToBitcoinNetwork);
            throw new VaultNotConnectedToNetworkException();
        }
    }

    /**
     * CryptoVaultManager interface implementation
     */
    @Override
    public void disconnectFromBitcoin() {
        vault.disconnectVault();

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

    @Override
    public void sendBitcoins(UUID walletId, UUID FermatTrId, CryptoAddress addressTo, long satothis) throws com.bitdubai.fermat_cry_api.layer.crypto_vault.exceptions.InsufficientMoneyException, InvalidSendToAddressException, CouldNotSendMoneyException {
        vault.sendBitcoins(FermatTrId, addressTo, satothis);
    }

    @Override
    public TransactionProtocolManager<CryptoTransaction> getTransactionManager() {
        return vault;
    }
}
