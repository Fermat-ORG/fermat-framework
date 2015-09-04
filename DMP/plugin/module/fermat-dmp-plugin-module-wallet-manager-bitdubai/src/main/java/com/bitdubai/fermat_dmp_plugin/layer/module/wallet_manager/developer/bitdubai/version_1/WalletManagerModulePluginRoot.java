package com.bitdubai.fermat_dmp_plugin.layer.module.wallet_manager.developer.bitdubai.version_1;


import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.enums.DeviceDirectory;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;

import com.bitdubai.fermat_api.layer.all_definition.enums.WalletCategory;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletType;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.basic_wallet_common_exceptions.CantCreateWalletException;

import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletManager;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces.DealsWithBitcoinWallet;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.exceptions.CantListWalletsException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.interfaces.DealsWithWalletManager;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.interfaces.InstalledLanguage;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.interfaces.InstalledSkin;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.interfaces.WalletManagerManager;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.WalletManager;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.exceptions.CantCreateDefaultWalletsException;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.exceptions.CantEnableWalletException;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.exceptions.CantLoadWalletsException;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.exceptions.NewWalletCreationFailedException;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.exceptions.WalletRemovalFailedException;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.exceptions.WalletRenameFailedException;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.exceptions.WalletsListFailedToLoadException;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.interfaces.InstalledWallet;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.exceptions.CantPersistWalletException;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.interfaces.WalletManagerModule;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by ciencias on 21.01.15.
 */

/**
 * This plug serves as an interface between the SubApp and WalletManager Middleware.
 * It allows you to retrieve information from the wallets installed and create clones.
 * <p/>
 * <p/>
 * Created by ciencias on 21.01.15.
 * Modified by Natalia on 04/08/2015
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class WalletManagerModulePluginRoot implements DealsWithBitcoinWallet, com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.DealsWithEvents, DealsWithErrors, DealsWithLogger, DealsWithPluginDatabaseSystem, DealsWithPluginFileSystem, DealsWithWalletManager, LogManagerForDevelopers, Plugin, Service, WalletManagerModule, WalletManager {


    /**
     * WalletManager Interface member variables.
     */
    String deviceUserPublicKey = "";
    String walletId = "25428311-deb3-4064-93b2-69093e859871";

    List<InstalledWallet> userWallets;

    private Map<String, String> walletIds = new HashMap<>();

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
    List<com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventListener> listenersAdded = new ArrayList<>();

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
    com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventManager eventManager;


    /**
     * DealsWithErrors Interface member variables.
     */
    ErrorManager errorManager;


    /**
     * DealsWithWalletManager Interface member variables.
     */
    WalletManagerManager walletMiddlewareManager;

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
        //TODO: Verificar si este bloque de codigo es necesario que quede aca
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

                } catch (CantCreateWalletException cantCreateWalletException) {
                    throw new CantStartPluginException(cantCreateWalletException, Plugins.BITDUBAI_WALLET_MANAGER_MODULE);

                }
            }

        } catch (Exception cantLoadWalletsException) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_MANAGER_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantLoadWalletsException);
            throw new CantStartPluginException();
        }


        /**
         * I will initialize the handling of com.bitdubai.platform events.
         */

        com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventListener eventListener;
        com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventHandler eventHandler;

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
        this.serviceStatus = ServiceStatus.PAUSED;
    }

    @Override
    public void resume() {
        this.serviceStatus = ServiceStatus.STARTED;
    }

    @Override
    public void stop() {
        this.serviceStatus = ServiceStatus.STOPPED;
        /**
         * I will remove all the event listeners registered with the event manager.
         */

        for (com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventListener eventListener : listenersAdded) {
            eventManager.removeListener(eventListener);
        }

        listenersAdded.clear();
    }

    @Override
    public ServiceStatus getStatus() {
        return this.serviceStatus;
    }


    /**
     * WalletManager Interface implementation.
     */

    /**
     * This method let the client create a new wallet of a type already intalled by the user.
     *
     * @param walletIdInTheDevice The identifier of the wallet to copy
     * @param newName             the name to give to the wallet
     * @throws NewWalletCreationFailedException
     */
    public void createNewWallet(UUID walletIdInTheDevice, String newName) throws NewWalletCreationFailedException {
        try {
            walletMiddlewareManager.createNewWallet(walletIdInTheDevice, newName);
        } catch (com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.exceptions.CantCreateNewWalletException e) {
            throw new NewWalletCreationFailedException("CAN'T CREATE NEW WALLET", e, "", "");
        } catch (Exception e) {
            throw new NewWalletCreationFailedException("CAN'T CREATE NEW WALLET", FermatException.wrapException(e), "", "");
        }
    }

    /**
     * This method returns the list of installed wallets in the device
     *
     * @return A list with the installed wallets information
     * @throws WalletsListFailedToLoadException
     */
    public List<InstalledWallet> getInstalledWallets() throws WalletsListFailedToLoadException {
        List<InstalledWallet> lstInstalledWallet = new ArrayList<InstalledWallet>();

        try {

            List<com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.interfaces.InstalledWallet> installetMiddlewareWallets = walletMiddlewareManager.getInstalledWallets();
            for (com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.interfaces.InstalledWallet wallet : installetMiddlewareWallets) {

                InstalledWallet installedWallet = new WalletManagerModuleInstalledWallet(wallet.getWalletCategory(), wallet.getWalletType(),
                        wallet.getSkinsId(),
                        wallet.getLanguagesId(),
                        wallet.getWalletIcon(),
                        wallet.getWalletName(),
                        wallet.getWalletPublicKey(),
                        wallet.getWalletPlatformIdentifier(),
                        wallet.getWalletVersion()
                );

                lstInstalledWallet.add(installedWallet);
            }


        } catch (CantListWalletsException e) {
            throw new WalletsListFailedToLoadException("CAN'T GET THE INSTALLED WALLETS", e, "", "");
        } catch (Exception e) {
            throw new WalletsListFailedToLoadException("CAN'T GET THE INSTALLED WALLETS", FermatException.wrapException(e), "", "");
        }

        return lstInstalledWallet;
    }

    /**
     * This method removes a wallet created by a user. <p>
     * Note that this won't uninstall the wallet type. It is used to delete a copy of a wallet created
     * using the <code>createWallet</code> method.
     *
     * @param walletIdInTheDevice the identifier of the wallet to delete
     * @throws WalletRemovalFailedException
     */
    public void removeWallet(UUID walletIdInTheDevice) throws WalletRemovalFailedException {
        try {
            walletMiddlewareManager.removeWallet(walletIdInTheDevice);
        } catch (com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.exceptions.CantRemoveWalletException e) {
            throw new WalletRemovalFailedException("CAN'T CREATE NEW WALLET", e, "", "");
        } catch (Exception e) {
            throw new WalletRemovalFailedException("CAN'T CREATE NEW WALLET", FermatException.wrapException(e), "", "");
        }
    }

    /**
     * This method let us change the name (alias) of a given wallet.
     *
     * @param walletIdInTheDevice the identifier of the wallet to rename
     * @param newName             the new name for the wallet
     * @throws WalletRenameFailedException
     */
    public void renameWallet(UUID walletIdInTheDevice, String newName) throws WalletRenameFailedException {
        try {
            walletMiddlewareManager.renameWallet(walletIdInTheDevice, newName);
        } catch (com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.exceptions.CantRenameWalletException e) {
            throw new WalletRenameFailedException("CAN'T RENAME WALLET", e, "", "");
        } catch (Exception e) {
            throw new WalletRenameFailedException("CAN'T RENAME WALLET", FermatException.wrapException(e), "", "");
        }
    }


    /**
     * DealsWithBitcoinWallet Interface implementation.
     */

    @Override
    public void setBitcoinWalletManager(BitcoinWalletManager bitcoinWalletManager) {
        this.bitcoinWalletManager = bitcoinWalletManager;
    }


    public void persistWallet(String walletId) throws CantPersistWalletException {
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
    public void setEventManager(com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventManager eventManager) {
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
     * DealsWithWalletManager methods implementation.
     */
    @Override
    public void setWalletManagerManager(WalletManagerManager walletManagerManager) {
        this.walletMiddlewareManager = walletManagerManager;
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

    //TODO: revisar si esta interface Wallet Manager se va a usar (Natalia)

    /**
     * WalletManager Interface implementation.
     */


    //TODO: Analizar este metodo deberia reemplazarce por el metodo getInstalledWallets de la interface WalletManagerModule que ya esta implementado (Natalia)
    public List<InstalledWallet> getUserWallets() {
        // Harcoded para testear el circuito más arriba
        InstalledWallet installedWallet = new WalletManagerModuleInstalledWallet(WalletCategory.REFERENCE_WALLET,
                WalletType.REFERENCE,
                new ArrayList<InstalledSkin>(),
                new ArrayList<InstalledLanguage>(),
                "reference_wallet_icon",
                "Bitcoin Reference Wallet",
                "reference_wallet",
                "wallet_platform_identifier",
                new Version(1, 0, 0)
        );

        List<InstalledWallet> lstInstalledWallet = new ArrayList<InstalledWallet>();
        lstInstalledWallet.add(installedWallet);
        return lstInstalledWallet;
    }

    @Override
    public void createDefaultWallets(String deviceUserPublicKey) throws CantCreateDefaultWalletsException {

        /**
         * By now I will create only a new wallet, In the future there will be more than one default wallets.
         */

        //  Wallet wallet = new WalletManagerWallet();

        //  ((DealsWithPluginFileSystem) wallet).setPluginFileSystem(pluginFileSystem);
        // ((DealsWithEvents) wallet).setEventManager(eventManager);
        //((DealsWithPluginIdentity) wallet).setPluginId(pluginId);

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

    @Override
    public void enableWallet() throws CantEnableWalletException {

    }

    /**
     * @param deviceUserPublicKey
     * @throws CantLoadWalletsException
     */
    @Override
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
                            walletIds.put(idPair[0], idPair[1]);

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

}

