package com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_manager.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.all_definition.developer.DatabaseManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Languages;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletCategory;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletType;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.enums.ScreenSize;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.CantCreateNewWalletException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.InstalledLanguage;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.InstalledSkin;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_pip_api.layer.pip_user.device_user.interfaces.DeviceUserManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.enums.EventType;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.events.WalletInstalledEvent;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.DealsWithEvents;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.exceptions.CantFindProcessException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.exceptions.CantGetInstalledWalletException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.exceptions.CantInstallLanguageException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.exceptions.CantInstallSkinException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.exceptions.CantListWalletsException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.exceptions.CantRemoveWalletException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.exceptions.CantRenameWalletException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.exceptions.CantUninstallLanguageException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.exceptions.CantUninstallSkinException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.exceptions.CantUninstallWalletException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.InstalledWallet;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.WalletInstallationProcess;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.WalletManagerManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_resources.exceptions.WalletResourcesInstalationException;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_resources.exceptions.WalletResourcesUnninstallException;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_resources.interfaces.DealsWithWalletResources;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_resources.interfaces.WalletResourcesInstalationManager;
import com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_manager.developer.bitdubai.version_1.developerUtils.WalletManagerMiddlewareDeveloperDatabaseFactory;
import com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_manager.developer.bitdubai.version_1.exceptions.CantDeleteWalletLanguageException;
import com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_manager.developer.bitdubai.version_1.exceptions.CantDeleteWalletSkinException;
import com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_manager.developer.bitdubai.version_1.exceptions.CantDeliverDatabaseException;
import com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_manager.developer.bitdubai.version_1.exceptions.CantExecuteDatabaseOperationException;
import com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_manager.developer.bitdubai.version_1.exceptions.CantGetInstalledWalletsException;
import com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_manager.developer.bitdubai.version_1.exceptions.CantPersistWalletLanguageException;
import com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_manager.developer.bitdubai.version_1.exceptions.CantPersistWalletSkinException;
import com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_manager.developer.bitdubai.version_1.exceptions.CantUpdateWalletNameException;
import com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_manager.developer.bitdubai.version_1.structure.WalletManagerMiddlewareInstallationProcess;
import com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_manager.developer.bitdubai.version_1.structure.WalletManagerMiddlewareInstalledWallet;
import com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_manager.developer.bitdubai.version_1.structure.database.WalletManagerMiddlewareDao;
import com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_manager.developer.bitdubai.version_1.structure.database.WalletManagerMiddlewareDatabaseConstants;
import com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_manager.developer.bitdubai.version_1.structure.database.WalletManagerMiddlewareDatabaseFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * That plugin produces the specific installation of the wallet on the user device
 * It is responsible for binding all parties to the new Reference Niche Wallet | Niche Wallet is available for use.
 *
 * It also allows other administrative tasks on the wallets installed ( for example uninstall, change skin and language, etc. )
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 09/07/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class WalletManagerMiddlewarePluginRoot implements DatabaseManagerForDevelopers,DealsWithErrors,DealsWithEvents,DealsWithLogger,DealsWithWalletResources,DealsWithPluginDatabaseSystem,LogManagerForDevelopers, Plugin, Service, WalletManagerManager {

    String walletPublicKey = "reference_wallet";

    /**
     * DealsWithDeviceUser member variables
     */
    DeviceUserManager deviceUserManager;


    /**
     * WalletManagerMiddlewarePluginRoot member variables
     */
    Database database;

    private List<InstalledWallet> installedWallets = null;


    /**
     * DealsWithErrors Interface member variables.
     */
    ErrorManager errorManager;


    /**
     * DealsWithLogger interface member variable
     */
    LogManager logManager;
    static Map<String, LogLevel> newLoggingLevel = new HashMap<String, LogLevel>();

    /**
     * DealsWithEvents interface member variable
     */
    EventManager eventManager;


    /**
     * DealsWithWalletResources interface member variable
     */
    WalletResourcesInstalationManager walletResources;


    /**
     * DealsWithPluginDatabaseSystem Interface member variables.
     */
    PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * Plugin Interface member variables.
     */
    UUID pluginId;

    /**
     * Service Interface member variables.
     */
    ServiceStatus serviceStatus = ServiceStatus.CREATED;

    /**
     * This method let the client create a new wallet of a type already intalled by the user.
     * Clone Wallet
     *
     */
    public void createNewWallet(UUID walletIdInTheDevice, String newName) throws CantCreateNewWalletException{

        try
        {
            /**
             * I'll first get de installed wallet with walletIdInTheDevice params (public key)
             */
            WalletManagerMiddlewareDao walletManagerDao = new WalletManagerMiddlewareDao(this.pluginDatabaseSystem,pluginId);

            InstalledWallet installedWallet = walletManagerDao.getInstalledWallet(walletIdInTheDevice);
            /**
             * Call the wallet resource to install new wallet
             */
            // TODO: Le tendria que pasar la wallet public key
            walletResources.installCompleteWallet(installedWallet.getWalletCategory().getCode(), installedWallet.getWalletType().getCode(), installedWallet.getWalletDeveloperName(), installedWallet.getWalletScreenSize(),installedWallet.getSkinsId().get(0).getAlias(), installedWallet.getLanguagesId().get(0).getLanguage().value(), installedWallet.getWalletNavigationStructureVersion(), installedWallet.getWalletPublicKey());

            /**
             * I create a new clone wallet on database
             */
            ECCKeyPair keyPair = new ECCKeyPair();

            walletManagerDao.persistWallet(keyPair.getPublicKey(),keyPair.getPrivateKey(),installedWallet.getWalletDeviceUserPublicKey(),installedWallet.getWalletCategory(),newName, installedWallet.getWalletIcon(), installedWallet.getWalletPlatformIdentifier(), installedWallet.getWalletCatalogId(), installedWallet.getWalletVersion(), installedWallet.getWalletDeveloperName(), installedWallet.getWalletScreenSize(),installedWallet.getWalletNavigationStructureVersion());

            /**
             * I persist skin and language on database
             */
            walletManagerDao.persistWalletSkin(installedWallet.getWalletCatalogId(),installedWallet.getSkinsId().get(0).getId(),installedWallet.getSkinsId().get(0).getAlias(),installedWallet.getSkinsId().get(0).getPreview(),installedWallet.getSkinsId().get(0).getVersion());
            walletManagerDao.persistWalletLanguage(installedWallet.getWalletCatalogId(),installedWallet.getLanguagesId().get(0).getId(),installedWallet.getLanguagesId().get(0).getLanguage(),installedWallet.getLanguagesId().get(0).getLabel(),installedWallet.getLanguagesId().get(0).getVersion());

        }
        catch (CantPersistWalletSkinException ex)
        {
            throw new CantCreateNewWalletException("ERROR INSTALLING WALLET",ex, "Error Save Skin on DB ", "");
        }
        catch (CantPersistWalletLanguageException ex)
        {
            throw new CantCreateNewWalletException("ERROR INSTALLING WALLET",ex, "Error Save Skin on DB ", "");
        }
        catch (WalletResourcesInstalationException ex)
        {
            throw new CantCreateNewWalletException("ERROR INSTALLING WALLET",ex, "Error Save Skin on DB ", "");
        }
        catch (CantGetInstalledWalletsException e){
            throw new CantCreateNewWalletException("CAN'T INSTALL WALLET Language",e, null, null);
        }
        catch (CantExecuteDatabaseOperationException e){
            throw new CantCreateNewWalletException("CAN'T INSTALL WALLET Language",e, null, null);
        }
        catch (Exception exception){
            throw new CantCreateNewWalletException("CAN'T INSTALL WALLET Language",FermatException.wrapException(exception), "Unexpected Exception", null);
        }

    }

    /*
     * DatabaseManagerForDevelopers interface methods implementation
     */
    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {

        WalletManagerMiddlewareDeveloperDatabaseFactory dbFactory = new WalletManagerMiddlewareDeveloperDatabaseFactory(pluginId.toString());
        return dbFactory.getDatabaseList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        return WalletManagerMiddlewareDeveloperDatabaseFactory.getDatabaseTableList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        Database database;
        try {
            database = this.pluginDatabaseSystem.openDatabase(pluginId, WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_TABLE_TABLE_NAME);
            return WalletManagerMiddlewareDeveloperDatabaseFactory.getDatabaseTableContent(developerObjectFactory, database, developerDatabaseTable);
        }catch (CantOpenDatabaseException cantOpenDatabaseException){
            /**
             * The database exists but cannot be open. I can not handle this situation.
             */
            FermatException e = new CantDeliverDatabaseException("I can't open database",cantOpenDatabaseException,"WalletId: " + developerDatabase.getName(),"");
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WPD_WALLET_MANAGER_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
        }
        catch (DatabaseNotFoundException databaseNotFoundException) {
            FermatException e = new CantDeliverDatabaseException("Database does not exists",databaseNotFoundException,"WalletId: " + developerDatabase.getName(),"");
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WPD_WALLET_MANAGER_MIDDLEWARE,UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
        } catch(Exception exception){
            FermatException e = new CantDeliverDatabaseException("Unexpected Exception",exception,"WalletId: " + developerDatabase.getName(),"");
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WPD_WALLET_MANAGER_MIDDLEWARE,UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
        }
        // If we are here the database could not be opened, so we return an empty list
        return new ArrayList<>();
    }

    @Override
    public void start() throws CantStartPluginException {

        /**
         * I will try to open the database first, if it doesn't exists, then I create it
         */
        try {

            database = pluginDatabaseSystem.openDatabase(pluginId, WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_DATABASE);

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {
            try {
                createWalletManagerMiddlewareDatabase();
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WPD_WALLET_MANAGER_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantCreateDatabaseException);
                throw new CantStartPluginException();
            } catch (Exception exception){
                throw new CantStartPluginException("Cannot start WalletManagerMiddleware plugin.", FermatException.wrapException(exception), null, null);
            }
        } catch (DatabaseNotFoundException databaseNotFoundException) {
            /**
             * The database doesn't exists, lets create it.
             */
            try {
                createWalletManagerMiddlewareDatabase();
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WPD_WALLET_MANAGER_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantCreateDatabaseException);
                throw new CantStartPluginException();
            }
        } catch (Exception exception){
            throw new CantStartPluginException("Cannot start WalletManagerMiddleware plugin.", FermatException.wrapException(exception), "Unexpected Exception", "Check the casue");
        }

        this.serviceStatus = ServiceStatus.STARTED;
        //TODO:delete this line
        //testMethod();

    }
    @Override
    public void pause(){
        this.serviceStatus = ServiceStatus.PAUSED;
    }

    @Override
    public void resume(){
        this.serviceStatus = ServiceStatus.STARTED;
    }

    @Override
    public void stop(){
        this.serviceStatus = ServiceStatus.STOPPED;
    }

    @Override
    public ServiceStatus getStatus() {
        return this.serviceStatus;
    }

    /**
     * DealWithErrors Interface implementation. 
     */
    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }

    /**
     * Plugin methods implementation.
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
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_manager.developer.bitdubai.version_1.WalletManagerMiddlewarePluginRoot");
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_manager.developer.bitdubai.version_1.structure.WalletManagerMiddlewareInstalledWallet");
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_manager.developer.bitdubai.version_1.structure.WalletManagerMiddlewareInstalledSkin");
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_manager.developer.bitdubai.version_1.structure.WalletManagerMiddlewareInstalledLanguage");
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_manager.developer.bitdubai.version_1.structure.WalletManagerMiddlewareInstallationProcess");
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_manager.developer.bitdubai.version_1.structure.WalletManagerMiddlewareInstallationInformation");
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_manager.developer.bitdubai.version_1.structure.database.WalletManagerMiddlewareDatabaseFactory");
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_manager.developer.bitdubai.version_1.structure.database.WalletManagerMiddlewareDatabaseConstants");
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_manager.developer.bitdubai.version_1.structure.database.WalletManagerMiddlewareDao");

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
            if (WalletManagerMiddlewarePluginRoot.newLoggingLevel.containsKey(pluginPair.getKey())) {
                WalletManagerMiddlewarePluginRoot.newLoggingLevel.remove(pluginPair.getKey());
                WalletManagerMiddlewarePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            } else {
                WalletManagerMiddlewarePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            }
        }

    }

    /*
     * WalletManagerManager interface methods implementation
     */

    /**
     * This method returns the list of installed wallets in the device
     *
     */
    public List<InstalledWallet> getInstalledWallets() throws CantListWalletsException{

        try{

            WalletManagerMiddlewareDao walletManagerDao = new WalletManagerMiddlewareDao(this.pluginDatabaseSystem,pluginId);

            installedWallets = walletManagerDao.getInstalledWallets();
        }
        catch (CantGetInstalledWalletsException e){
            throw new CantListWalletsException("CAN'T INSTALL REQUESTED Language",e, null, null);
        }
        catch (CantExecuteDatabaseOperationException e){
            throw new CantListWalletsException("CAN'T INSTALL REQUESTED Language",e, null, null);
        }
        catch (Exception exception){
            throw new CantListWalletsException("CAN'T INSTALL REQUESTED Language",FermatException.wrapException(exception), null, null);
        }
        //Voy a harcodear esto para tener la reference wallet instalada
        //return installedWallets;


        // Harcoded para testear el circuito más arriba
        InstalledWallet installedWallet= new WalletManagerMiddlewareInstalledWallet(

                WalletCategory.REFERENCE_WALLET,
                new ArrayList<InstalledSkin>(),
                new ArrayList<InstalledLanguage>(),
                "reference_wallet_icon",
                "bitDubai bitcoin Wallet",
                "reference_wallet",
                "wallet_platform_identifier",
                new Version(1,0,0),
                WalletType.REFERENCE,
                "medium",
                "1.0.0",
                null,
                "bitDubai",
                ""
        );

        List<InstalledWallet> lstInstalledWallet = new ArrayList<InstalledWallet>();
        lstInstalledWallet.add(installedWallet);
        return lstInstalledWallet;

    }

    /**

     * This method starts the process of installing a new language for an specific wallet
     *
     */
    public void installLanguage(UUID walletCatalogueId, UUID languageId, Languages language, String label, Version version) throws CantInstallLanguageException{

        try{

            /**
             * Get installed wallet info
             */
            WalletManagerMiddlewareDao walletManagerDao = new WalletManagerMiddlewareDao(this.pluginDatabaseSystem,pluginId);

            InstalledWallet installedWallet = walletManagerDao.getInstalledWalletByCatalogueId(walletCatalogueId);

            /**
             * Call Wallet Resource to install Language
             */
            walletResources.installLanguageForWallet(installedWallet.getWalletCategory().getCode(), installedWallet.getWalletType().getCode(), installedWallet.getWalletDeveloperName(),installedWallet.getWalletScreenSize(), languageId, language.value(),installedWallet.getWalletPublicKey());

            /**
             * Save language in the Data Base
             */
            walletManagerDao.persistWalletLanguage(walletCatalogueId, languageId, language, label, version);

        }
        catch (WalletResourcesInstalationException e){
            throw new CantInstallLanguageException("CAN'T INSTALL WALLET Language",e, null, null);
        }
        catch (CantPersistWalletLanguageException e){
            throw new CantInstallLanguageException("CAN'T INSTALL REQUESTED Language",e, null, null);
        }
        catch (CantExecuteDatabaseOperationException e){
            throw new CantInstallLanguageException("CAN'T INSTALL REQUESTED Language",e, null, null);
        }
        catch (Exception exception){
            throw new CantInstallLanguageException("CAN'T INSTALL REQUESTED Language",FermatException.wrapException(exception), null, null);
        }
    }

    /**
     * This method starts the process of installing a new skin for an specific wallet
     *
     * @param walletCatalogueId the identifier of the wallet we want to install the skin to
     * @param skinId the identifier of the skin
     * @param alias the alias (name) of the skin
     * @param Preview the name of the preview image of the skin
     * @param version the version of the skin
     * @throws CantInstallSkinException
     */
    public void installSkin(UUID walletCatalogueId, UUID skinId, String alias, String Preview, Version version) throws CantInstallSkinException{
        try{

            /**
             * Get installed wallet info
             */
            WalletManagerMiddlewareDao walletManagerDao = new WalletManagerMiddlewareDao(this.pluginDatabaseSystem,pluginId);

            InstalledWallet installedWallet = walletManagerDao.getInstalledWalletByCatalogueId(walletCatalogueId);

            /**
             * Call Wallet Resource to install Skin
             */

            walletResources.installSkinForWallet(installedWallet.getWalletCategory().getCode(), installedWallet.getWalletType().getCode(), installedWallet.getWalletDeveloperName(), installedWallet.getWalletScreenSize(), alias, installedWallet.getWalletNavigationStructureVersion(), installedWallet.getWalletPublicKey());

            /**
             * Save Skin in the Data Base
              */

            walletManagerDao.persistWalletSkin(walletCatalogueId, skinId, alias, Preview, version);


        }
        catch (WalletResourcesInstalationException e){
            throw new CantInstallSkinException("CAN'T INSTALL WALLET SKIN",e, null, null);
        }
        catch (CantPersistWalletSkinException e){
            throw new CantInstallSkinException("CAN'T INSTALL REQUESTED ON_REVISION",e, null, null);
        }
        catch (CantExecuteDatabaseOperationException e){
            throw new CantInstallSkinException("CAN'T INSTALL REQUESTED ON_REVISION",e, null, null);
        }
        catch (Exception exception){
            throw new CantInstallSkinException("CAN'T INSTALL REQUESTED ON_REVISION",FermatException.wrapException(exception), null, null);
        }
     }

    /**
     *
     * This method returns the interface responsible of the installation process of a niche wallet
     *
     * @param walletCategory The category of the wallet to install
     * @param walletPlatformIdentifier an string that encodes the wallet identifier in the platform
     *                                 We are usign the term platform to identify the software installed
     *                                 in the device and not the network.
     * @return an interface to manage the installation of a new wallet
     * @throws CantFindProcessException
     */
    public WalletInstallationProcess installWallet(WalletCategory walletCategory, String walletPlatformIdentifier) throws CantFindProcessException{
        try {
            Logger LOG = Logger.getGlobal();
            LOG.info("MAP_WALLET_CATEGORY:"+walletCategory);
            LOG.info("MAP_WPI:"+walletPlatformIdentifier);
            eventManager.raiseEvent( new WalletInstalledEvent(EventType.WALLET_INSTALLED));
            LOG.info("MAP_EVENT:"+eventManager.toString());
            return new WalletManagerMiddlewareInstallationProcess(this.walletResources,walletCategory,walletPlatformIdentifier,this.pluginDatabaseSystem,pluginId);

        } catch (Exception e){
            throw new CantFindProcessException("CAN'T FIND PROCESS",FermatException.wrapException(e), null, null);
        }
     }



    /**
     * This method starts the process of uninstalling a new language for an specific wallet
     *
     * @param walletCatalogueId the identifier of the wallet where we want to uninstall the language
     * @param languageId the identifier of the language to uninstall
     */
    public void uninstallLanguage(UUID walletCatalogueId, UUID languageId) throws CantUninstallLanguageException{

        try{

            WalletManagerMiddlewareDao walletMangerDao = new WalletManagerMiddlewareDao(this.pluginDatabaseSystem,pluginId);
            /**
             * Get installed wallet info
             */
            InstalledWallet installedWallet = walletMangerDao.getInstalledWalletByCatalogueId(walletCatalogueId);

            /**
             * Get language information
             */
            InstalledLanguage installedLanguage =  walletMangerDao.getInstalledLanguage(languageId.toString());

            /**
             * Call Wallet Resource to uninstall Skin
             */
           walletResources.uninstallLanguageForWallet(installedWallet.getSkinsId().get(0).getId(), installedWallet.getWalletPublicKey(),installedLanguage.getLanguage().name());

            walletMangerDao.deleteWalletLanguage(walletCatalogueId, languageId);


        } catch (WalletResourcesUnninstallException e){
            throw new CantUninstallLanguageException("CAN'T UNINSTALL WALLET LANGUAGE",e, null, null);

        } catch (CantDeleteWalletLanguageException e){
            throw new CantUninstallLanguageException("CAN'T UNINSTALL REQUESTED PUBLISHED",e, null, null);
        }
        catch (CantExecuteDatabaseOperationException e){
            throw new CantUninstallLanguageException("CAN'T UNINSTALL REQUESTED PUBLISHED",e, null, null);
        }
        catch (Exception exception){
            throw new CantUninstallLanguageException("CAN'T UNINSTALL REQUESTED PUBLISHED",FermatException.wrapException(exception), null, null);
        }
    }

    /**
     * This method starts the process of uninstalling a new skin for an specific wallet
     *
     * @param walletCatalogueId the identifier of the wallet in which we want to uninstall the language
     * @param skinId the identifier of the skin
     */
    public void uninstallSkin(UUID walletCatalogueId, UUID skinId) throws CantUninstallSkinException{
        try{

            /**
             * Get installed wallet information
             */
            WalletManagerMiddlewareDao walletMangerDao = new WalletManagerMiddlewareDao(this.pluginDatabaseSystem,pluginId);


            InstalledWallet installedWallet = walletMangerDao.getInstalledWalletByCatalogueId(walletCatalogueId);

            /**
             * Get skin information
             */
            InstalledSkin installedSkin =  walletMangerDao.getInstalledSkin(skinId.toString());

            /**
             * Conected with Wallet Resource to uninstall resources
             */
           walletResources.uninstallSkinForWallet( skinId,installedWallet.getWalletPublicKey());
            /**
             * I delete skin from database
             */
            walletMangerDao.deleteWalletSkin(walletCatalogueId, skinId);

        } catch (WalletResourcesUnninstallException e){
            throw new CantUninstallSkinException("CAN'T UNINSTALL WALLET SKIN",e, null, null);

        } catch (CantDeleteWalletSkinException e){
            throw new CantUninstallSkinException("CAN'T UNINSTALL REQUESTED ON_REVISION",e, null, null);
        }
        catch (CantExecuteDatabaseOperationException e){
            throw new CantUninstallSkinException("CAN'T UNINSTALL REQUESTED ON_REVISION",e, null, null);
        }
        catch (Exception exception){
            throw new CantUninstallSkinException("CAN'T UNINSTALL REQUESTED ON_REVISION",FermatException.wrapException(exception), null, null);
        }
    }

    /**
     * This method starts the uninstalling process of a walled
     *
     * @param walletIdInThisDevice the id of the wallet to uninstall
     */
    public void uninstallWallet(UUID walletIdInThisDevice) throws CantUninstallWalletException{

        try
        {

            /**
             * Get installed wallet information
             */
            WalletManagerMiddlewareDao walletMangerDao = new WalletManagerMiddlewareDao(this.pluginDatabaseSystem,pluginId);

            InstalledWallet installedWallet = walletMangerDao.getInstalledWallet(walletIdInThisDevice);
            /**
             * Conected with Wallet Resource to unistalld resources
             */
            //TODO: Falta que reciba el Public key de la wallet y la lista de skins y language instalados

            walletResources.uninstallCompleteWallet(installedWallet.getWalletCategory().getCode(), installedWallet.getWalletType().getCode(), installedWallet.getWalletDeveloperName(), null, null, installedWallet.getWalletScreenSize(), installedWallet.getWalletNavigationStructureVersion(), true, installedWallet.getWalletPublicKey());

            /**
             * Delete wallet for DataBase
             */
            walletMangerDao.deleteWallet(walletIdInThisDevice);

        }
        catch (CantDeleteWalletSkinException e){
            throw new CantUninstallWalletException("CAN'T REMOVE WALLET",e, null, null);
        }
        catch (CantExecuteDatabaseOperationException e){
            throw new CantUninstallWalletException("CAN'T REMOVE WALLET ",e, null, null);
        }
        catch (Exception exception){
            throw new CantUninstallWalletException("CAN'T REMOVE WALLET ",FermatException.wrapException(exception), null, null);
        }
    }

    /**
     * This method removes a wallet created by a user. <p>
     * Note that this won't uninstall the wallet type. It is used to delete a copy of a wallet created
     * using the <code>createWallet</code> method.
     *
     * @param walletIdInTheDevice the identifier of the wallet to delete
     * @throws CantRemoveWalletException
     */
    public void removeWallet(UUID walletIdInTheDevice) throws CantRemoveWalletException{
        try
        {
            WalletManagerMiddlewareDao walletDao = new WalletManagerMiddlewareDao(this.pluginDatabaseSystem,pluginId);

            walletDao.deleteWallet(walletIdInTheDevice);

        } catch (CantDeleteWalletSkinException e){
            throw new CantRemoveWalletException("CAN'T REMOVE REQUESTED",e, null, null);
        }
        catch (CantExecuteDatabaseOperationException e){
            throw new CantRemoveWalletException("CAN'T REMOVE REQUESTED ",e, null, null);
        }
        catch (Exception exception){
            throw new CantRemoveWalletException("CAN'T REMOVE REQUESTED ",FermatException.wrapException(exception), null, null);
        }
    }

    /**
     * This method let us change the name (alias) of a given wallet.
     *
     * @param walletIdInTheDevice the identifier of the wallet to rename
     * @param newName the new name for the wallet
     * @throws CantRenameWalletException
     */
    public void renameWallet(UUID walletIdInTheDevice, String newName) throws CantRenameWalletException{
        try
        {
            WalletManagerMiddlewareDao walletDao = new WalletManagerMiddlewareDao(this.pluginDatabaseSystem,pluginId);
            walletDao.changeWalletName(walletIdInTheDevice, newName);

        } catch (CantUpdateWalletNameException e){
            throw new CantRenameWalletException("CAN'T RENAME REQUESTED ALIAS",e, null, null);
        }
        catch (CantExecuteDatabaseOperationException e){
            throw new CantRenameWalletException("CAN'T RENAME REQUESTED ALIAS",e, null, null);
        }
        catch (Exception exception){
            throw new CantRenameWalletException("CAN'T RENAME REQUESTED ALIAS",FermatException.wrapException(exception), null, null);
        }

    }

    /**
     * get Installed wallet
     *
     * @return
     */
    @Override
    public InstalledWallet getInstalledWallet(String walletPublicKey) throws CantCreateNewWalletException {

        try{
            WalletManagerMiddlewareDao walletManagerMiddlewareDao = new WalletManagerMiddlewareDao(pluginDatabaseSystem,pluginId);
            return walletManagerMiddlewareDao.getInstalledWallet(walletPublicKey);

        } catch (CantGetInstalledWalletsException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WPD_WALLET_MANAGER_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantCreateNewWalletException("CAN'T INSTALL WALLET Language",e, null, null);
        } catch (CantExecuteDatabaseOperationException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WPD_WALLET_MANAGER_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantCreateNewWalletException("CAN'T INSTALL WALLET Language",e, null, null);
        }


    }


    // TODO DO THIS RIGHT.
    @Override
    public InstalledWallet getDefaultWallet(CryptoCurrency cryptoCurrency,
                                            Actors actorType,
                                            BlockchainNetworkType blockchainNetworkType) throws CantGetInstalledWalletException {

        try {
            List<InstalledWallet> installedWalletList = this.getInstalledWallets();
            if (installedWalletList.isEmpty())
                return null;
            else
                return installedWalletList.get(0);
        } catch (CantListWalletsException e) {
            throw new CantGetInstalledWalletException(e, null, null);
        }
    }

    /*
     * DealsWithPluginDatabaseSystem interface methods implementation
     */

    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /*
     * DealsWithEvents interface methods implementation
     */

    @Override
    public void setEventManager(EventManager eventManager) {
        this.eventManager=eventManager;
    }


    /*
    * DealsWithWalletResources interface methods implementation
    */
    @Override
    public void setWalletResourcesManager(WalletResourcesInstalationManager walletResources) {
        this.walletResources = walletResources;


    }
    private void createWalletManagerMiddlewareDatabase() throws CantCreateDatabaseException {
        WalletManagerMiddlewareDatabaseFactory databaseFactory = new WalletManagerMiddlewareDatabaseFactory(this.pluginDatabaseSystem);
        database = databaseFactory.createDatabase(pluginId, WalletManagerMiddlewareDatabaseConstants.WALLET_MANAGER_WALLETS_DATABASE);
    }

    //TODO:Delete this method
    private void testMethod(){

        UUID testUUID=UUID.randomUUID();
        Version testVersion=new Version(1,1,0);
        Logger LOG = Logger.getGlobal();
        try {
            //createNewWallet(testUUID,"testWallet");
            WalletCategory wc=WalletCategory.NICHE_WALLET;
            String walletPlatformIdentifier="testID";
            WalletInstallationProcess wIP=installWallet(wc, walletPlatformIdentifier);
            wIP.startInstallation(WalletType.NICHE,"testWallet","123456","654321","098765",null,testUUID,testVersion, ScreenSize.MEDIUM.toString(),testUUID,testVersion,"Skin",null,testUUID,testVersion,Languages.LATIN_AMERICAN_SPANISH, "es","MAP","1.0.0");
            LOG.info("Rastro Atómico:"+wIP.getInstallationProgress().getCode());
        } catch (Exception e) {
            LOG.info("TEST ERROR:"+e.getMessage());
            e.printStackTrace();
        }

    }

}
