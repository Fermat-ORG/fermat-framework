package com.bitdubai.fermat_cry_plugin.layer.crypto_module.actor_address_book.developer.bitdubai.version_1;

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
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.exceptions.CantGetActorAddressBookRegistryException;
import com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.interfaces.ActorAddressBookManager;
import com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.interfaces.ActorAddressBookRegistry;
import com.bitdubai.fermat_cry_plugin.layer.crypto_module.actor_address_book.developer.bitdubai.version_1.exceptions.CantInitializeActorAddressBookCryptoModuleException;
import com.bitdubai.fermat_cry_plugin.layer.crypto_module.actor_address_book.developer.bitdubai.version_1.structure.ActorAddressBookCryptoModuleDeveloperDatabaseFactory;
import com.bitdubai.fermat_cry_plugin.layer.crypto_module.actor_address_book.developer.bitdubai.version_1.structure.ActorAddressBookCryptoModuleRegistry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * Created by loui on 20/02/15.asd
 */

/**
 * This Plug-in has the responsibility to manage the relationship between users and crypto addresses.
 *
 * * * * * *
 */

public class ActorAddressBookCryptoModulePluginRoot implements ActorAddressBookManager, DatabaseManagerForDevelopers, DealsWithErrors, DealsWithLogger, DealsWithPluginDatabaseSystem, LogManagerForDevelopers, Plugin, Service {

    /**
     * DatabaseManagerForDevelopers interface implementation
     * Returns the list of databases implemented on this plug in.
     */
    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        List<DeveloperDatabase> developerDatabaseList = new ArrayList<>();
        try {
            ActorAddressBookCryptoModuleDeveloperDatabaseFactory dbFactory = new ActorAddressBookCryptoModuleDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
            developerDatabaseList = dbFactory.getDatabaseList(developerObjectFactory);
        } catch (Exception e) {
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_USER_ADDRESS_BOOK_CRYPTO, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
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
        List<DeveloperDatabaseTable> developerDatabaseTableList = new ArrayList<>();
        try {
            ActorAddressBookCryptoModuleDeveloperDatabaseFactory dbFactory = new ActorAddressBookCryptoModuleDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
            developerDatabaseTableList = dbFactory.getDatabaseTableList(developerObjectFactory);
        } catch (Exception e){
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_USER_ADDRESS_BOOK_CRYPTO, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
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
        List<DeveloperDatabaseTableRecord> developerDatabaseTableRecordList = new ArrayList<>();
        try {
            ActorAddressBookCryptoModuleDeveloperDatabaseFactory dbFactory = new ActorAddressBookCryptoModuleDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
            dbFactory.initializeDatabase();
            developerDatabaseTableRecordList = dbFactory.getDatabaseTableContent(developerObjectFactory, developerDatabaseTable);
        } catch (Exception e) {
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_USER_ADDRESS_BOOK_CRYPTO, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }
        return developerDatabaseTableRecordList;
    }
    /**
     * DealsWithErrors Interface member variables.
     */
    ErrorManager errorManager;

    /**
     * DealsWithPluginDatabaseSystem Interface member variables.
     */
    PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * DealsWithLogger interface member variable
     */
    LogManager logManager;
    static Map<String, LogLevel> newLoggingLevel = new HashMap<String, LogLevel>();

    /**
     * Plugin Interface member variables.
     */
    UUID pluginId;

    /**
     * Service Interface member variables.
     */
    ServiceStatus serviceStatus = ServiceStatus.CREATED;


    /**
     * ActorAddressBookManager Interface implementation.
     */
    @Override
    public ActorAddressBookRegistry getActorAddressBookRegistry() throws CantGetActorAddressBookRegistryException {
        logManager.log(ActorAddressBookCryptoModulePluginRoot.getLogLevelByClass(this.getClass().getName()), "Getting ActorAddressBookRegistry...", null, null);

        /**
         * I created instance of ActorCryptoAddressBookRegistry
         */
        ActorAddressBookCryptoModuleRegistry actorCryptoAddressBookRegistry = new ActorAddressBookCryptoModuleRegistry();

        actorCryptoAddressBookRegistry.setErrorManager(this.errorManager);
        actorCryptoAddressBookRegistry.setPluginDatabaseSystem(this.pluginDatabaseSystem);
        actorCryptoAddressBookRegistry.setPluginId(this.pluginId);

        try {
            actorCryptoAddressBookRegistry.initialize();
            logManager.log(ActorAddressBookCryptoModulePluginRoot.getLogLevelByClass(this.getClass().getName()), "Initializing ActorAddressBookRegistry successfully...", null, null);

            return actorCryptoAddressBookRegistry;
        } catch (CantInitializeActorAddressBookCryptoModuleException cantInitializeActorCryptoAddressBookException) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_USER_ADDRESS_BOOK_CRYPTO, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantInitializeActorCryptoAddressBookException);
            throw new CantGetActorAddressBookRegistryException(CantGetActorAddressBookRegistryException.DEFAULT_MESSAGE, cantInitializeActorCryptoAddressBookException, "", "Error initializing database");
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_USER_ADDRESS_BOOK_CRYPTO, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantGetActorAddressBookRegistryException(CantGetActorAddressBookRegistryException.DEFAULT_MESSAGE, e);
        }
    }


    @Override
    public List<String> getClassesFullPath() {
        List<String> returnedClasses = new ArrayList<String>();
        returnedClasses.add("com.bitdubai.fermat_cry_plugin.layer.crypto_module.actor_address_book.developer.bitdubai.version_1.ActorAddressBookCryptoModulePluginRoot");
        returnedClasses.add("com.bitdubai.fermat_cry_plugin.layer.crypto_module.actor_address_book.developer.bitdubai.version_1.exceptions.CantInitializeActorAddressBookCryptoModuleException");
        returnedClasses.add("com.bitdubai.fermat_cry_plugin.layer.crypto_module.actor_address_book.developer.bitdubai.version_1.structure.ActorAddressBookCryptoModuleDao");
        returnedClasses.add("com.bitdubai.fermat_cry_plugin.layer.crypto_module.actor_address_book.developer.bitdubai.version_1.structure.ActorAddressBookCryptoModuleRecord");
        returnedClasses.add("com.bitdubai.fermat_cry_plugin.layer.crypto_module.actor_address_book.developer.bitdubai.version_1.structure.ActorAddressBookCryptoModuleRegistry");
        returnedClasses.add("com.bitdubai.fermat_cry_plugin.layer.crypto_module.actor_address_book.developer.bitdubai.version_1.structure.ActorAddressBookCryptoModuleDatabaseConstants");
        returnedClasses.add("com.bitdubai.fermat_cry_plugin.layer.crypto_module.actor_address_book.developer.bitdubai.version_1.structure.ActorAddressBookCryptoModuleDeveloperDatabaseFactory");
        returnedClasses.add("com.bitdubai.fermat_cry_plugin.layer.crypto_module.actor_address_book.developer.bitdubai.version_1.structure.ActorAddressBookCryptoModuleDatabaseFactory");
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
            if (ActorAddressBookCryptoModulePluginRoot.newLoggingLevel.containsKey(pluginPair.getKey())) {
                ActorAddressBookCryptoModulePluginRoot.newLoggingLevel.remove(pluginPair.getKey());
                ActorAddressBookCryptoModulePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            } else {
                ActorAddressBookCryptoModulePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            }
        }

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
            return ActorAddressBookCryptoModulePluginRoot.newLoggingLevel.get(correctedClass[0]);
        } catch (Exception e){
            /**
             * If I couldn't get the correct loggin level, then I will set it to minimal.
             */
            return DEFAULT_LOG_LEVEL;
        }
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
     *DealWithErrors Interface implementation.
     */
    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }

    /**
     * DealsWithPluginDatabaseSystem interface implementation.
     */
    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /**
     * DealsWithPluginIdentity methods implementation.
     */
    @Override
    public void setId(UUID pluginId) {
        this.pluginId = pluginId;
    }


    @Override
    public void setLogManager(LogManager logManager) {
        this.logManager = logManager;
    }
}