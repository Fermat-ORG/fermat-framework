package com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.developer.DatabaseManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.exceptions.CantGetWalletContactRegistryException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.interfaces.WalletContactsManager;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.interfaces.WalletContactsRegistry;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.exceptions.CantInitializeWalletContactsDatabaseException;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.structure.WalletContactsMiddlewareDeveloperDatabaseFactory;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.structure.WalletContactsMiddlewareRegistry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * Created by loui on 17/02/15.
 * <p/>
 * This plugin manages list of contacts.
 * <p/>
 * A contact list is associated with one or more wallets. This is useful if a user want to share contacts between
 * wallets. A single wallet can be part of more tha one list also.
 * <p/>
 * * * * * *
 */

public class WalletContactsMiddlewarePluginRoot implements DatabaseManagerForDevelopers, DealsWithErrors, DealsWithPluginDatabaseSystem, DealsWithLogger, LogManagerForDevelopers, Plugin, Service, WalletContactsManager {

    /**
     * DatabaseManagerForDevelopers interface implementation
     * Returns the list of databases implemented on this plug in.
     */
    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        List<DeveloperDatabase> developerDatabaseList = null;
        try {
            WalletContactsMiddlewareDeveloperDatabaseFactory dbFactory = new WalletContactsMiddlewareDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
            developerDatabaseList = dbFactory.getDatabaseList(developerObjectFactory);
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_CONTACTS_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, FermatException.wrapException(e));
        }
        return developerDatabaseList;
    }

    /**
     * returns the list of tables for the given database
     *
     * @param developerObjectFactory
     * @param developerDatabase
     * @return
     */
    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        List<DeveloperDatabaseTable> developerDatabaseTableList = null;
        try {
            WalletContactsMiddlewareDeveloperDatabaseFactory dbFactory = new WalletContactsMiddlewareDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
            developerDatabaseTableList = dbFactory.getDatabaseTableList(developerObjectFactory);
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_CONTACTS_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, FermatException.wrapException(e));
        }
        return developerDatabaseTableList;
    }

    /**
     * returns the list of records for the passed table
     *
     * @param developerObjectFactory
     * @param developerDatabase
     * @param developerDatabaseTable
     * @return
     */
    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        List<DeveloperDatabaseTableRecord> developerDatabaseTableRecordList = null;
        try {
            WalletContactsMiddlewareDeveloperDatabaseFactory dbFactory = new WalletContactsMiddlewareDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
            dbFactory.initializeDatabase();
            developerDatabaseTableRecordList = dbFactory.getDatabaseTableContent(developerObjectFactory, developerDatabaseTable);
        } catch (CantInitializeWalletContactsDatabaseException we) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_CONTACTS_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, we);
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_CONTACTS_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, FermatException.wrapException(e));
        }
        return developerDatabaseTableRecordList;
    }

    /**
     * DealWithErrors Interface member variables.
     */
    ErrorManager errorManager;

    /**
     * DealsWithPluginDatabaseSystem Interface member variables.
     */
    PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * Plugin Interface member variables.
     */
    UUID pluginId;

    /**
     * DealsWithLogger interface member variable
     */
    LogManager logManager;
    static Map<String, LogLevel> newLoggingLevel = new HashMap<String, LogLevel>();

    /**
     * Service Interface member variables.
     */
    ServiceStatus serviceStatus = ServiceStatus.CREATED;


    /**
     * WalletContactsManager Interface implementation.
     */
    public WalletContactsRegistry getWalletContactsRegistry() throws CantGetWalletContactRegistryException {
        logManager.log(WalletContactsMiddlewarePluginRoot.getLogLevelByClass(this.getClass().getName()), "WalletContacts Registry Initializing...", null, null);

        /**
         * I created instance of WalletCryptoAddressBookRegistry
         */
        WalletContactsMiddlewareRegistry walletContactsRegistry = new WalletContactsMiddlewareRegistry();
        try {
            walletContactsRegistry.setErrorManager(this.errorManager);
            walletContactsRegistry.setPluginDatabaseSystem(this.pluginDatabaseSystem);
            walletContactsRegistry.setPluginId(this.pluginId);

            walletContactsRegistry.initialize();
        } catch (CantInitializeWalletContactsDatabaseException exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_USER_ADDRESS_BOOK_CRYPTO, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
            throw new CantGetWalletContactRegistryException(CantGetWalletContactRegistryException.DEFAULT_MESSAGE, exception);
        } catch (Exception exception){
            throw new CantGetWalletContactRegistryException(CantGetWalletContactRegistryException.DEFAULT_MESSAGE, FermatException.wrapException(exception));
        }
        logManager.log(WalletContactsMiddlewarePluginRoot.getLogLevelByClass(this.getClass().getName()), "WalletContacts Registry created Successfully...", null, null);

        return walletContactsRegistry;
    }


    /**
     * Service Interface implementation.
     */

    @Override
    public void start() {
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
    }

    @Override
    public ServiceStatus getStatus() {
        return this.serviceStatus;
    }

    /**
     * Plugin interface implementation
     * @param uuid
     */
    @Override
    public void setId(UUID uuid) {
        this.pluginId = uuid;
    }

    /**
     * DealsWithLogger interface implementations
     */
    @Override
    public void setLogManager(LogManager logManager) {
        this.logManager = logManager;
    }

    @Override
    public List<String> getClassesFullPath() {
        List<String> returnedClasses = new ArrayList<String>();
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.WalletContactsMiddlewarePluginRoot");
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.structure.WalletContactsMiddlewareDatabaseFactory");
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.structure.WalletContactsMiddlewareDatabaseConstants");
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.structure.WalletContactsMiddlewareRegistry");
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.structure.WalletContactsMiddlewareRecord");
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.structure.WalletContactsMiddlewareDeveloperDatabaseFactory");
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.structure.WalletContactsMiddlewareDao");

        return returnedClasses;
    }

    @Override
    public void setLoggingLevelPerClass(Map<String, LogLevel> newLoggingLevel) {
        /**
         * I will check the current values and update the LogLevel in those which is different
         */

        try {
            for (Map.Entry<String, LogLevel> pluginPair : newLoggingLevel.entrySet()) {
                /**
                 * if this path already exists in the Root.bewLoggingLevel I'll update the value, else, I will put as new
                 */
                if (WalletContactsMiddlewarePluginRoot.newLoggingLevel.containsKey(pluginPair.getKey())) {
                    WalletContactsMiddlewarePluginRoot.newLoggingLevel.remove(pluginPair.getKey());
                    WalletContactsMiddlewarePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
                } else {
                    WalletContactsMiddlewarePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
                }
            }
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_CONTACTS_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, FermatException.wrapException(e));
        }
    }

    /**
     * Static method to get the logging level from any class under root.
     * @param className
     * @return
     */

    public static LogLevel getLogLevelByClass(String className){
        try{
            String[] correctedClass = className.split(Pattern.quote("$"));
            return WalletContactsMiddlewarePluginRoot.newLoggingLevel.get(correctedClass[0]);
        } catch (Exception e){
            return LogLevel.MODERATE_LOGGING;
        }
    }

    /**
     * DealWithErrors Interface implementation.
     */
    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }

    /**
     * DealWithPluginDatabaseSystem Interface implementation.
     */
    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }
}
