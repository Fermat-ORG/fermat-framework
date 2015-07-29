package com.bitdubai.fermat_dmp_plugin.layer.module.wallet_manager.developer.bitdubai.version_1;


import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.DealsWithPluginIdentity;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.enums.DeviceDirectory;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletCategory;
import com.bitdubai.fermat_api.layer.all_definition.event.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.event.EventType;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletManager;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces.DealsWithBitcoinWallet;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.interfaces.InstalledLanguage;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.interfaces.InstalledSkin;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.interfaces.InstalledWallet;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.Wallet;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.WalletManager;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.exceptions.CantCreateDefaultWalletsException;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.exceptions.CantLoadWalletsException;
import com.bitdubai.fermat_api.layer.all_definition.event.PlatformEvent;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.exceptions.CantPersistWalletException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
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
import com.bitdubai.fermat_dmp_plugin.layer.module.wallet_manager.developer.bitdubai.version_1.structure.WalletManagerModuleInstalledWallet;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;

import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.*;
import com.bitdubai.fermat_dmp_plugin.layer.module.wallet_manager.developer.bitdubai.version_1.structure.WalletManagerWallet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by ciencias on 21.01.15.
 */

public class WalletManagerModulePluginRoot implements Service, WalletManager, DealsWithBitcoinWallet, DealsWithEvents, DealsWithErrors, DealsWithLogger, DealsWithPluginDatabaseSystem, DealsWithPluginFileSystem, LogManagerForDevelopers, Plugin {


    /**
     * WalletManager Interface member variables.
     */
    String deviceUserPublicKey = "";
    UUID walletId = UUID.fromString("25428311-deb3-4064-93b2-69093e859871");

    List<InstalledWallet> userWallets;

    private Map<String, UUID> walletIds = new HashMap<>();

    /**
     * DealsWithBitcoinWallet Interface member variables.
     */

    BitcoinWalletManager bitcoinWalletManager;

    /**
     * DealsWithLogger interface member variable
     */
    LogManager logManager;

    static Map<String, LogLevel> newLoggingLevel = new HashMap<String, LogLevel>();

    /**
     * Service Interface member variables.
     */
    ServiceStatus serviceStatus = ServiceStatus.CREATED;
    List<EventListener> listenersAdded = new ArrayList<>();

    /**
     * DealsWithPluginDatabaseSystem Interface member variables.
     */

    PluginDatabaseSystem pluginDatabaseSystem;


    /**
     * DealsWithPluginFileSystem Interface member variables.
     */
    PluginFileSystem pluginFileSystem;

    /**
     * DealWithEvents Interface member variables.
     */
    EventManager eventManager;


    /**
     * DealsWithErrors Interface member variables.
     */
    ErrorManager errorManager;


    /**
     * Plugin Interface member variables.
     */
    UUID pluginId;


    public WalletManagerModulePluginRoot() {
        userWallets = new ArrayList<>();
        this.serviceStatus = ServiceStatus.CREATED;
    }


    /**
     * PlatformService Interface implementation.
     */

    @Override
    public void start() throws CantStartPluginException {

        /**
         * I will check if wallet if created, if not I execute Bitcoinmanager create
         *
         */

        boolean existWallet = false;
        try {
            //load user's wallets ids
            this.loadUserWallets(deviceUserPublicKey);

            Iterator iterator = walletIds.entrySet().iterator();

            while (iterator.hasNext()) {
                Map.Entry mapEntry = (Map.Entry) iterator.next();
                if (mapEntry.getValue().toString().equals(walletId.toString()))
                    existWallet = true;
            }

            if (!existWallet) {
                //Create new Bitcoin Wallet

                try {

                    ((DealsWithPluginFileSystem) bitcoinWalletManager).setPluginFileSystem(this.pluginFileSystem);
                    ((DealsWithErrors) bitcoinWalletManager).setErrorManager(this.errorManager);
                    ((DealsWithPluginDatabaseSystem) bitcoinWalletManager).setPluginDatabaseSystem(this.pluginDatabaseSystem);

                    bitcoinWalletManager.createWallet(walletId);

                    //Save wallet id on file

                    try {
                        this.persistWallet(walletId);
                    } catch (CantPersistWalletException cantPersistWalletException) {
                        throw new CantStartPluginException(cantPersistWalletException, Plugins.BITDUBAI_WALLET_MANAGER_MODULE);

                    }

                } catch (com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.exceptions.CantCreateWalletException cantCreateWalletException) {
                    throw new CantStartPluginException(cantCreateWalletException, Plugins.BITDUBAI_WALLET_MANAGER_MODULE);

                }
            }

        } catch (CantLoadWalletsException cantLoadWalletsException) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_MANAGER_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantLoadWalletsException);
            throw new CantStartPluginException();
        }


        /**
         * I will initialize the handling of com.bitdubai.platform events.
         */

        EventListener eventListener;
        EventHandler eventHandler;

       /* eventListener = eventManager.getNewListener(EventType.DEVICE_USER_CREATED);
        eventHandler = new UserCreatedEventHandler();
        ((UserCreatedEventHandler) eventHandler).setWalletManager(this);
        eventListener.setEventHandler(eventHandler);
        eventManager.addListener(eventListener);
        listenersAdded.add(eventListener);

        eventListener = eventManager.getNewListener(EventType.DEVICE_USER_LOGGED_IN);
        eventHandler = new UserLoggedInEventHandler();
        ((UserLoggedInEventHandler) eventHandler).setWalletManager(this);
        eventListener.setEventHandler(eventHandler);
        eventManager.addListener(eventListener);
        listenersAdded.add(eventListener);

        eventListener = eventManager.getNewListener(EventType.WALLET_RESOURCES_INSTALLED);
        eventHandler = new WalletResourcesInstalledEventHandler();
        ((WalletResourcesInstalledEventHandler) eventHandler).setWalletManager(this);
        eventListener.setEventHandler(eventHandler);
        eventManager.addListener(eventListener);
        listenersAdded.add(eventListener);

        eventListener = eventManager.getNewListener(EventType.NAVIGATION_STRUCTURE_UPDATED);
        eventHandler = new NavigationStructureUpdatedEventHandler();
        ((NavigationStructureUpdatedEventHandler) eventHandler).setWalletManager(this);
        eventListener.setEventHandler(eventHandler);
        eventManager.addListener(eventListener);
        listenersAdded.add(eventListener);

        eventListener = eventManager.getNewListener(EventType.WALLET_CREATED);
        eventHandler = new WalletCreatedEventHandler();
        ((WalletCreatedEventHandler) eventHandler).setWalletManager(this);
        eventListener.setEventHandler(eventHandler);
        eventManager.addListener(eventListener);
        listenersAdded.add(eventListener);*/


        this.serviceStatus = ServiceStatus.STARTED;
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {

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
    }

    @Override
    public ServiceStatus getStatus() {
        return this.serviceStatus;
    }


    public void enableWallet() {


    }


    private void finishedWalletInstallation() {
        PlatformEvent platformEvent = eventManager.getNewEvent(EventType.FINISHED_WALLET_INSTALLATION);
        platformEvent.setSource(EventSource.MODULE_WALLET_MANAGER_PLUGIN);
        eventManager.raiseEvent(platformEvent);

    }

    /**
     * WalletManager Interface implementation.
     */

    public List<InstalledWallet> getUserWallets() {
        // Harcoded para testear el circuito más arriba
        InstalledWallet installedWallet= new WalletManagerModuleInstalledWallet(WalletCategory.REFERENCE_WALLET,
                new ArrayList<InstalledSkin>(),
                new ArrayList<InstalledLanguage>(),
                "reference_wallet_icon",
                "Bitcoin Reference Wallet",
                "reference_wallet",
                "wallet_platform_identifier",
                new Version(1,0,0)
        );

        List<InstalledWallet> lstInstalledWallet = new ArrayList<InstalledWallet>();
        lstInstalledWallet.add(installedWallet);
        return lstInstalledWallet;
    }

    public void loadUserWallets(String deviceUserPublicKey) throws CantLoadWalletsException {

        this.deviceUserPublicKey = deviceUserPublicKey;
        /**
         *I check if the file containing all the wallets  ids managed by this plug-in already exists or not.
         * and load wallets ids for user
         *
         */
        PluginTextFile walletIdsFile;

        try {

            try {
                walletIdsFile = pluginFileSystem.getTextFile(pluginId, "", DeviceDirectory.LOCAL_WALLETS.getName(), FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
            } catch (CantCreateFileException cantCreateFileException) {

                /**
                 * If I can not save this file, then this plugin shouldn't be running at all.
                 */
                System.err.println("cantCreateFileException: " + cantCreateFileException.getMessage());
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_WALLET_BASIC_WALLET, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantCreateFileException);

                throw new CantLoadWalletsException();
            }
            try {
                walletIdsFile.loadFromMedia();

                /*
                 * TODO: This can't stay in a file. A user id will be assign to many walletIds
                 */
                /**
                 * Now I read the content of the file and place it in memory.
                 */
                String[] stringWalletIds = walletIdsFile.getContent().split(";", -1);

                for (String stringWalletId : stringWalletIds) {

                    if (!stringWalletId.equals("")) {
                        /**
                         * Each record in the file has to values: the first is the external id of the wallet, and the
                         * second is the internal id of the wallet.
                         * * *
                         */
                        String[] idPair = stringWalletId.split(",", -1);

                        //put wallets of this user
                        if (idPair[0].equals(deviceUserPublicKey))
                            walletIds.put(idPair[0], UUID.fromString(idPair[1]));

                        /**
                         * Great, now the wallet list is in memory.
                         */
                    }
                }
            } catch (CantLoadFileException cantLoadFileException) {

                /**
                 * In this situation we might have a corrupted file we can not read. For now the only thing I can do is
                 * to prevent the plug-in from running.
                 *
                 * In the future there should be implemented a method to deal with this situation.
                 * * * *
                 */

                //errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_WALLET_BASIC_WALLET, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantLoadFileException);

                throw new CantLoadWalletsException();
            }
        } catch (FileNotFoundException fileNotFoundException) {
            /**
             * If the file did not exist it is not a problem. It only means this is the first time this plugin is running.
             *
             * I will create the file now, with an empty content so that when a new wallet is added we wont have to deal
             * with this file not existing again.
             * * * * *
             */

            try {

                walletIdsFile = pluginFileSystem.createTextFile(pluginId, "", DeviceDirectory.LOCAL_WALLETS.getName(), FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
            } catch (CantCreateFileException cantCreateFileException) {

                /**
                 * If I can not save this file, then this plugin shouldn't be running at all.
                 */
                System.err.println("cantCreateFileException: " + cantCreateFileException.getMessage());
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_WALLET_BASIC_WALLET, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantCreateFileException);
                throw new CantLoadWalletsException();
            }
            try {
                walletIdsFile.persistToMedia();
            } catch (CantPersistFileException cantPersistFileException) {

                /**
                 * If I can not save this file, then this plugin shouldn't be running at all.
                 */
                System.err.println("CantPersistFileException: " + cantPersistFileException.getMessage());
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_WALLET_BASIC_WALLET, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantPersistFileException);
                throw new CantLoadWalletsException();
            }
        }
    }

    public void persistWallet(UUID walletId) throws CantPersistWalletException {
        /**
         * Now I will add this wallet to the list of wallets managed by the plugin.
         */
        walletIds.put(deviceUserPublicKey, walletId);

        PluginTextFile walletIdsFile = null;

        try {
            walletIdsFile = pluginFileSystem.createTextFile(pluginId, "", DeviceDirectory.LOCAL_WALLETS.getName(), FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
        } catch (CantCreateFileException cantCreateFileException) {

            /**
             * If I can not save this file, then this plugin shouldn't be running at all.
             */
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_MANAGER_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantCreateFileException);

            throw new CantPersistWalletException();
        }

        /**
         * I will generate the file content.
         */
        StringBuilder stringBuilder = new StringBuilder(walletIds.size() * 72);

        Iterator iterator = walletIds.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry pair = (Map.Entry) iterator.next();
            stringBuilder.append(pair.getKey().toString() + "," + pair.getValue().toString() + ";");
            iterator.remove();
        }


        /**
         * Now I set the content.
         */
        walletIdsFile.setContent(stringBuilder.toString());

        try {
            walletIdsFile.persistToMedia();
        } catch (CantPersistFileException cantPersistFileException) {
            /**
             * If I can not save the id of the new wallet created, then this method fails.
             */
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_MANAGER_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantPersistFileException);
            throw new CantPersistWalletException();
        }

    }

    @Override
    public void createDefaultWallets(String deviceUserPublicKey) throws CantCreateDefaultWalletsException {

        /**
         * By now I will create only a new wallet, In the future there will be more than one default wallets.
         */

        Wallet wallet = new WalletManagerWallet();

        ((DealsWithPluginFileSystem) wallet).setPluginFileSystem(pluginFileSystem);
        ((DealsWithEvents) wallet).setEventManager(eventManager);
        ((DealsWithPluginIdentity) wallet).setPluginId(pluginId);

        //try {
            //aquí al crear la wallet se le deben pasar todos los parametros de esta
            //wallet.createWallet("public_key_hardcoded");
        //} catch (CantCreateWalletException cantCreateWalletException) {
            /**
             * Well, if it is not possible to create a wallet, then we have a problem that I can not handle...
             */
        //    System.err.println("CantCreateWalletException: " + cantCreateWalletException.getMessage());
        //    cantCreateWalletException.printStackTrace();

        //    throw new CantCreateDefaultWalletsException();
        //}

    }

    /**
     * DealsWithBitcoinWallet Interface implementation.
     */

    @Override
    public void setBitcoinWalletManager(BitcoinWalletManager bitcoinWalletManager) {
        this.bitcoinWalletManager = bitcoinWalletManager;
    }


    /**
     * DealsWithPluginDatabaseSystem Interface implementation.
     */

    @Override

    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }


    /**
     * DealsWithPluginFileSystem Interface implementation.
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
     * DealWithErrors Interface implementation.
     */
    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }

    /**
     * DealsWithPluginIdentity methods implementation.
     */

    @Override
    public void setId(UUID pluginId) {
        this.pluginId = pluginId;
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
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.module.wallet_manager.developer.bitdubai.version_1.WalletManagerModulePluginRoot");
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.module.wallet_manager.developer.bitdubai.version_1.structure.WalletManagerWallet");


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
            if (WalletManagerModulePluginRoot.newLoggingLevel.containsKey(pluginPair.getKey())) {
                WalletManagerModulePluginRoot.newLoggingLevel.remove(pluginPair.getKey());
                WalletManagerModulePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            } else {
                WalletManagerModulePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            }
        }

    }

}

