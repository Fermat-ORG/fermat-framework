package com.bitdubai.fermat_dmp_plugin.layer.module.wallet_store.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.event.EventType;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_store.Wallet;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_store.exceptions.CantGetWalletsException;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_store.exceptions.CantRecordInstalledWalletException;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_store.exceptions.CantRecordUninstalledWalletException;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_store.WalletStoreManager;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantLoadFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;

import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.*;
import com.bitdubai.fermat_dmp_plugin.layer.module.wallet_store.developer.bitdubai.version_1.event_handlers.BegunWalletInstallationEventHandler;
import com.bitdubai.fermat_dmp_plugin.layer.module.wallet_store.developer.bitdubai.version_1.event_handlers.WalletUninstalledEventHandler;
import com.bitdubai.fermat_dmp_plugin.layer.module.wallet_store.developer.bitdubai.version_1.exceptions.CantInitializeWalletException;
import com.bitdubai.fermat_dmp_plugin.layer.module.wallet_store.developer.bitdubai.version_1.exceptions.CantRemoveRecordException;
import com.bitdubai.fermat_dmp_plugin.layer.module.wallet_store.developer.bitdubai.version_1.structure.WalletStoreCatalog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author ciencias
 * Created by ciencias on 30.12.14.
 */

/**
 * This plugin manages the information about all wallets published only at the level needed for showing it at the wallet
 * store sub app.
 * * *
 */

public class WalletStoreModulePluginRoot implements DealsWithErrors, DealsWithEvents, DealsWithLogger,DealsWithPluginFileSystem, LogManagerForDevelopers,Plugin, Service, WalletStoreManager{

    /**
     * DealsWithLogger interface member variable
     */
    LogManager logManager;

    static Map<String, LogLevel> newLoggingLevel = new HashMap<String, LogLevel>();

    /**
     * PlatformService Interface member variables.
     */
    ServiceStatus serviceStatus;
    List<EventListener> listenersAdded = new ArrayList<>();

    /**
     * UsesFileSystem Interface member variables.
     */
    PluginFileSystem pluginFileSystem;
    
    /**
     * DealWithEvents Interface member variables.
     */
    EventManager eventManager;

    /**
     * Plugin Interface member variables.
     */
    UUID pluginId;
    private final String WALLET_IDS_FILE_NAME = "walletsIds";
    WalletStoreCatalog catalog;


    public WalletStoreModulePluginRoot(){
        this.serviceStatus = ServiceStatus.CREATED;
    }

    /**
     * PlatformService Interface implementation.
     */

    @Override
    public void start() {
        /**
         * Check if this is the first time this plugin starts. To do so I check if the file containing all the wallets
         * ids managed by this plug-in already exists or not.
         * * *
         */
        PluginTextFile walletIdsFile = null;

            try {

                try {

                    walletIdsFile = this.pluginFileSystem.getTextFile(pluginId, "", WALLET_IDS_FILE_NAME, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);

                } catch (CantCreateFileException cantCreateFileException) {
                    /**
                     * If I can not save this file, then this plugin shouldn't be running at all.
                     */
                    System.err.println("cantCreateFileException: " + cantCreateFileException.getMessage());
                    cantCreateFileException.printStackTrace();

                }
                try {

                    walletIdsFile.loadFromMedia();

                    String stringWalletId = walletIdsFile.getContent();

                    this.catalog = new WalletStoreCatalog(this.pluginId, UUID.fromString(stringWalletId));

                }
                catch (CantLoadFileException cantLoadFileException) {

                    /**
                     * In this situation we might have a corrupted file we can not read. For now the only thing I can do is
                     * to prevent the plug-in from running.
                     *
                     * In the future there should be implemented a method to deal with this situation.
                     * * * *
                     */
                    System.err.println("CantLoadFileException: " + cantLoadFileException.getMessage());
                    cantLoadFileException.printStackTrace();
                }

            } catch (FileNotFoundException fileNotFoundException) {
                /**
                 * If the file did not exist it is not a problem. It only means this is the first time this plugin is running.
                 *
                 * I will create the file now, with an empty content so that when a new wallet is added we wont have to deal
                 * with this file not existing again.
                 * * * * *
                 */

                try{

                    walletIdsFile = pluginFileSystem.createTextFile(pluginId, "", WALLET_IDS_FILE_NAME, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);

                    this.catalog = new WalletStoreCatalog(this.pluginId, UUID.randomUUID());
                    walletIdsFile.setContent(this.catalog.getWalletId().toString());
                }
                catch (CantCreateFileException cantCreateFileException ) {

                    /**
                     * If I can not save this file, then this plugin shouldn't be running at all.
                     */
                    System.err.println("cantCreateFileException: " + cantCreateFileException.getMessage());
                    cantCreateFileException.printStackTrace();
                }
                try {
                    walletIdsFile.persistToMedia();
                }
                catch (CantPersistFileException cantPersistFileException ) {

                    /**
                     * If I can not save this file, then this plugin shouldn't be running at all.
                     */
                    System.err.println("CantPersistFileException: " + cantPersistFileException.getMessage());
                    cantPersistFileException.printStackTrace();
                }
            }

        /**
         * I will initialize the database
         */
        try {

            this.catalog.initialize();

        } catch (CantInitializeWalletException cantInitializeWalletException) {
            System.err.println("CantInitializeWalletException: " + cantInitializeWalletException.getMessage());
            cantInitializeWalletException.printStackTrace();
        }

        /**
         * I will initialize the handling of platform events.
         */

        EventListener eventListener;
        EventHandler eventHandler;

        eventListener = eventManager.getNewListener(EventType.BEGUN_WALLET_INSTALLATION);
        eventHandler = new BegunWalletInstallationEventHandler();
        ((BegunWalletInstallationEventHandler) eventHandler).setWalletStoreManager(this);
        eventListener.setEventHandler(eventHandler);
        eventManager.addListener(eventListener);
        listenersAdded.add(eventListener);

        eventListener = eventManager.getNewListener(EventType.WALLET_UNINSTALLED);
        eventHandler = new WalletUninstalledEventHandler();
        ((WalletUninstalledEventHandler) eventHandler).setWalletStoreManager(this);
        eventListener.setEventHandler(eventHandler);
        eventManager.addListener(eventListener);
        listenersAdded.add(eventListener);

        this.serviceStatus = ServiceStatus.STARTED;
    }

    @Override
    public void pause() {
        this.serviceStatus = ServiceStatus.PAUSED;
    }
    
    @Override
    public void resume() {
        this.serviceStatus = ServiceStatus.STARTED;
    }

    @Override
    public void stop() {

        /**
         * I will remove all the event listeners registered with the event manager.
         */

        for (EventListener eventListener : listenersAdded) {
            eventManager.removeListener(eventListener);
        }

        listenersAdded.clear();

        this.serviceStatus = ServiceStatus.STOPPED;
    }

    @Override
    public ServiceStatus getStatus() {
        return this.serviceStatus;
    }


    /**
     * WalletStore Interface implementation.
     *
     */

    /**
     * Record the installed wallet in the database
     * @param walletId
     * @throws CantRecordInstalledWalletException
     */
    @Override
    public void recordInstalledWallet(UUID walletId) throws CantRecordInstalledWalletException {

        catalog.setWalletId(walletId);
        catalog.setEventManager(this.eventManager);

        catalog.recordInstalledWallet();
    }

    /**
     * Record the uninstalled wallet in the database
     * @param walletId
     * @throws CantRecordUninstalledWalletException
     */
    @Override
    public void recordUninstalledwallet(UUID walletId) throws CantRecordUninstalledWalletException {

        catalog.setWalletId(walletId);
        catalog.setEventManager(this.eventManager);

        try {

            catalog.recordUninstalledWallet();

        } catch (CantRemoveRecordException e) {
            System.err.println("CantRemoveRecord: " + e.getMessage());
            e.printStackTrace();
        }

    }

    /**
     * UsesFileSystem Interface implementation.
     */
    
    @Override
    public void setPluginFileSystem(PluginFileSystem pluginFileSystem) {
        this.pluginFileSystem = pluginFileSystem;
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

    }

    /**
     * DealsWithPluginIdentity methods implementation.
     */

    @Override
    public void setId(UUID pluginId) {
        this.pluginId = pluginId;
    }


    public List<Wallet> getWallets() throws CantGetWalletsException {
       return catalog.getWallets();
    }


    /**
     * DealsWithLogger Interface implementation.
     */

    @Override
    public void setLogManager(LogManager logManager) {
        this.logManager = logManager;
    }

    /**
     * LogManagerForDevelopers Interface implementation.
     */

    @Override
    public List<String> getClassesFullPath() {
        List<String> returnedClasses = new ArrayList<String>();
        returnedClasses.add("com.fermat_dmp_plugin.layer.module.wallet_factory.developer.bitdubai.version_1.WalletFactoryModulePluginRoot");
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.module.wallet_store.developer.bitdubai.version_1.structure.WalletStoreCatalog");
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.module.wallet_store.developer.bitdubai.version_1.structure.WalletStoreDatabaseConstants");
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.module.wallet_store.developer.bitdubai.version_1.structure.WalletStoreDatabaseFactory");

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

        for (Map.Entry<String, LogLevel> pluginPair : newLoggingLevel.entrySet()) {
            /**
             * if this path already exists in the Root.bewLoggingLevel I'll update the value, else, I will put as new
             */
            if (WalletStoreModulePluginRoot.newLoggingLevel.containsKey(pluginPair.getKey())) {
                WalletStoreModulePluginRoot.newLoggingLevel.remove(pluginPair.getKey());
                WalletStoreModulePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            } else {
                WalletStoreModulePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            }
        }

    }

}
