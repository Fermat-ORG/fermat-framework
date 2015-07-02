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

import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import java.security.Key;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Pattern;

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
    static Map<String, LogLevel> newLoggingLevel = new HashMap<String, LogLevel>();

    /**
     * DealsWithDeviceUsers interface member variable
     */
    DeviceUserManager deviceUserManager;

    /**
     * LogManagerForDevelopers member variables
     */
    public static LogLevel logLevel;


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
        logLevel = newLoggingLevel;
    }

    @Override
    public List<String> getClassesFullPath() {
        /**
         * I create the filters and loaders for reflection.
         */
        List<ClassLoader> classLoadersList = new LinkedList<ClassLoader>();
        classLoadersList.add(ClasspathHelper.contextClassLoader());
        classLoadersList.add(ClasspathHelper.staticClassLoader());

        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setScanners(new SubTypesScanner(false), new ResourcesScanner())
                .setUrls(ClasspathHelper.forClassLoader(classLoadersList.toArray(new ClassLoader[0])))
                        /**
                         * I filter by the package name of the plug in Root.
                         */
                .filterInputsBy(new FilterBuilder().include(FilterBuilder.prefix(this.getClass().getName()))));

        Set<Class<?>> classes = reflections.getSubTypesOf(Object.class);
        Iterator<Class<?>> iterator = classes.iterator();

        /**
         * I insert the classes in the List and return it.
         */
        List<String> returnedClasses = new ArrayList<String>();
        while (iterator.hasNext()){
            String fullClass = iterator.next().getName();
            returnedClasses.add(fullClass);
        }
        if (returnedClasses.isEmpty()){
            returnedClasses.add("com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.BitcoinCryptoVaultPluginRoot");
            returnedClasses.add("com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure.BitcoinNetworkConfiguration");
            returnedClasses.add("com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure.CryptoVaultDatabaseFactory");
            returnedClasses.add("com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure.VaultEventListeners");
            returnedClasses.add("com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure.TransactionConfidenceCalculator");
            returnedClasses.add("com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure.CryptoVaultDatabaseConstants");
            returnedClasses.add("com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure.BitcoinCryptoVault");
            returnedClasses.add("com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure.CryptoVaultDatabaseActions");
            returnedClasses.add("com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure.events.TransactionNotificationAgent");
            returnedClasses.add("com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure.developerUtils.DeveloperDatabaseFactory");
        }


        /**
         * I return the values.
         */
        return returnedClasses;
    }


    @Override
    public void setLoggingLevelPerClass(Map<String, LogLevel> newLoggingLevel) {
        /**
         * I will check the current values and update the LogLevel in those which is different
         */

        for (Map.Entry<String, LogLevel> pluginPair : BitcoinCryptoVaultPluginRoot.newLoggingLevel.entrySet()){
            System.out.println("Key: " + pluginPair.getKey() + " LogLevel: " + pluginPair.getValue().toString());
            /**
             * if the incoming value is different from what I already have, then Ill updated it
             */
        if (newLoggingLevel.containsKey(pluginPair.getKey())){


            if (pluginPair.getValue() != newLoggingLevel.get(pluginPair.getKey())){
                System.out.println("New Level to put: " + newLoggingLevel.get(pluginPair.getKey()));
                pluginPair.setValue(newLoggingLevel.get(pluginPair.getKey()));
            }
        }
        }
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
        logLevel = logLevel;
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
        logManager.log(logLevel, "CryptoVault Starting...", "CryptoVault Starting...", "CryptoVault Starting...");

        /**
         * I will initialize the Root map with all the classes in default state of Minimal logging
         */
        try{
            for (String c : getClassesFullPath()){
                BitcoinCryptoVaultPluginRoot.newLoggingLevel.put(c, LogLevel.MINIMAL_LOGGING);
            }
        } catch (Exception e){
            // no big deal if I coudln't fill the class now, we will do it later.
        }


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
            transactionNotificationAgent.setLogManager(logLevel, this.logManager);
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
    public String sendBitcoins(UUID walletId, UUID FermatTrId, CryptoAddress addressTo, long satoshis) throws com.bitdubai.fermat_cry_api.layer.crypto_vault.exceptions.InsufficientMoneyException, InvalidSendToAddressException, CouldNotSendMoneyException {
        return vault.sendBitcoins(FermatTrId, addressTo, satoshis);
    }

    @Override
    public TransactionProtocolManager<CryptoTransaction> getTransactionManager() {
        return vault;
    }

    public static LogLevel getLogLevelByClass(String className){
        try{
            /**
             * sometimes the classname may be passed dinamically with an $moretext
             * I need to ignore whats after this.
             */
            String[] correctedClass = className.split((Pattern.quote("$")));
            return BitcoinCryptoVaultPluginRoot.newLoggingLevel.get(correctedClass[0]);
        } catch (Exception e){
            return LogLevel.MINIMAL_LOGGING;
        }
    }
}
