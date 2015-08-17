/*
 * @#WalletPublisherMiddlewarePluginRoot.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_publisher.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
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
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.DealsWithWalletFactory;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletDescriptorFactoryProjectManager;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_language.interfaces.DealsWithWalletLanguage;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_language.interfaces.WalletLanguageManager;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_publisher.interfaces.WalletPublisherMiddlewareManager;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_publisher.interfaces.WalletPublisherMiddlewarePlugin;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_skin.interfaces.DealsWithWalletSkin;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_skin.interfaces.WalletSkinManager;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_store.interfaces.DealsWithWalletStoreMiddleware;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_store.interfaces.WalletStoreManager;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_publisher.developer.bitdubai.version_1.database.WalletPublisherMiddlewareDatabaseConstants;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_publisher.developer.bitdubai.version_1.database.WalletPublisherMiddlewareDatabaseFactory;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_publisher.developer.bitdubai.version_1.database.WalletPublisherMiddlewareDeveloperDatabaseFactory;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_publisher.developer.bitdubai.version_1.exceptions.CantInitializeWalletPublisherMiddlewareDatabaseException;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_publisher.developer.bitdubai.version_1.structure.WalletPublisherMiddlewareManagerImpl;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_publisher.developer.bitdubai.version_1.WalletPublisherMiddlewarePluginRoot</code> is
 * the responsible to initialize all component to work together, and hold all resources they needed.
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 09/07/15.
 * Update by Roberto Requena - (rart3001@gmail.com) on 04/08/2015
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class WalletPublisherMiddlewarePluginRoot implements DealsWithPluginFileSystem, DealsWithWalletFactory, DealsWithWalletStoreMiddleware, DealsWithErrors,DealsWithLogger, LogManagerForDevelopers, Plugin, Service, WalletPublisherMiddlewarePlugin, DealsWithPluginDatabaseSystem, DatabaseManagerForDevelopers, DealsWithWalletSkin, DealsWithWalletLanguage {

    /**
     * Represent the logManager
     */
    private LogManager logManager;

    /**
     * Represent the newLoggingLevel
     */
    static Map<String, LogLevel> newLoggingLevel = new HashMap<>();

    /**
     * Represent the errorManager
     */
    private ErrorManager errorManager;

    /**
     * Represent the pluginDatabaseSystem
     */
    private PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * Represent the walletDescriptorFactoryProjectManager
     */
    private WalletDescriptorFactoryProjectManager walletDescriptorFactoryProjectManager;

    /**
     * Represent the walletStoreManager
     */
    private WalletStoreManager walletStoreManager;

    /**
     * Represent the walletLanguageManager
     */
    private WalletLanguageManager walletLanguageManager;

    /**
     * Represent the walletSkinManager
     */
    private WalletSkinManager walletSkinManager;

    /**
     * Represent the pluginFileSystem
     */
    private PluginFileSystem pluginFileSystem;

    /**
     * Represent the plugin id
     */
    private UUID pluginId;

    /**
     * Represent the status of the service
     */
    private ServiceStatus serviceStatus;

    /**
     * Represent the walletPublisherMiddlewareManagerImpl
     */
    private WalletPublisherMiddlewareManagerImpl walletPublisherMiddlewareManagerImpl;

    /**
     * Represent the walletPublisherMiddlewareDeveloperDatabaseFactory
     */
    private WalletPublisherMiddlewareDeveloperDatabaseFactory walletPublisherMiddlewareDeveloperDatabaseFactory;

    /**
     * Represent the dataBase
     */
    private Database dataBase;

    /**s
     * Constructor
     */
    public WalletPublisherMiddlewarePluginRoot(){
        super();
        serviceStatus = ServiceStatus.CREATED;
    }

    /**
     * This method validate is all required resource are injected into
     * the plugin root by the platform
     *
     * @throws CantStartPluginException
     */
    private void validateInjectedResources() throws CantStartPluginException {

        /*
         * Validate If all resources are not null
         */
        if (logManager                                == null ||
                errorManager                          == null ||
                errorManager                      == null ||
                walletDescriptorFactoryProjectManager == null ||
                pluginFileSystem  == null ||
                walletLanguageManager == null ||
                walletSkinManager == null ||
                pluginDatabaseSystem == null) {

            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Plugin ID: " + pluginId);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("logManager: " + logManager);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("errorManager: " + errorManager);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("walletDescriptorFactoryProjectManager: " + walletDescriptorFactoryProjectManager);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("walletStoreManager: " + walletStoreManager);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("walletStoreManager: " + walletStoreManager);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("pluginFileSystem: " + pluginFileSystem);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("walletLanguageManager: " + walletLanguageManager);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("walletSkinManager: " + walletSkinManager);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("pluginDatabaseSystem: " + pluginDatabaseSystem);

            String context = contextBuffer.toString();
            String possibleCause = "No all required resource are injected";
            CantStartPluginException pluginStartException = new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, null, context, possibleCause);

            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_PUBLISHER_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, pluginStartException);
            throw pluginStartException;
        }

    }


    /**
     * This method initialize the database
     *
     * @throws CantInitializeWalletPublisherMiddlewareDatabaseException
     */
    private void initializeDb() throws CantInitializeWalletPublisherMiddlewareDatabaseException {

        try {
            /*
             * Open new database connection
             */
            this.dataBase = this.pluginDatabaseSystem.openDatabase(pluginId, WalletPublisherMiddlewareDatabaseConstants.DATA_BASE_NAME);

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {

            /*
             * The database exists but cannot be open. I can not handle this situation.
             */
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_PUBLISHER_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantOpenDatabaseException);
            throw new CantInitializeWalletPublisherMiddlewareDatabaseException(cantOpenDatabaseException.getLocalizedMessage());

        } catch (DatabaseNotFoundException e) {

            /*
             * The database no exist may be the first time the plugin is running on this device,
             * We need to create the new database
             */
            WalletPublisherMiddlewareDatabaseFactory walletPublisherMiddlewareDatabaseFactory = new WalletPublisherMiddlewareDatabaseFactory(pluginDatabaseSystem);

            try {

                /*
                 * We create the new database
                 */
                this.dataBase = walletPublisherMiddlewareDatabaseFactory.createDatabase(pluginId, WalletPublisherMiddlewareDatabaseConstants.DATA_BASE_NAME);

            } catch (CantCreateDatabaseException cantOpenDatabaseException) {

                /*
                 * The database cannot be created. I can not handle this situation.
                 */
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_PUBLISHER_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantOpenDatabaseException);
                throw new CantInitializeWalletPublisherMiddlewareDatabaseException(cantOpenDatabaseException.getLocalizedMessage());

            }
        }

    }

    /**
     * (non-Javadoc)
     * @see Service#start()
     */
    @Override
    public void start() throws CantStartPluginException {

        /*
         * Validate required resources
         */
        //TODO: DESCOMENTAR LA VALIDACION validateInjectedResources();

        try {

            /*
             * Initialize Database
             */
            initializeDb();

            /*
             * Initialize Developer Database Factory
             */
            walletPublisherMiddlewareDeveloperDatabaseFactory = new WalletPublisherMiddlewareDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
            walletPublisherMiddlewareDeveloperDatabaseFactory.initializeDatabase();

            /*
             * Initialize the manager
             */
            walletPublisherMiddlewareManagerImpl = new WalletPublisherMiddlewareManagerImpl(pluginId, pluginFileSystem, dataBase, walletStoreManager, logManager);

        } catch (CantInitializeWalletPublisherMiddlewareDatabaseException exception) {

            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Plugin ID: " + pluginId);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("Database Name: " + WalletPublisherMiddlewareDatabaseConstants.DATA_BASE_NAME);

            String context = contextBuffer.toString();
            String possibleCause = "The Wallet Publisher Database triggered an unexpected problem that wasn't able to solve by itself";
            CantStartPluginException pluginStartException = new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, exception, context, possibleCause);

            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_TEMPLATE_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, pluginStartException);
            throw pluginStartException;
        }


        this.serviceStatus = ServiceStatus.STARTED;
    }

    /**
     * (non-Javadoc)
     * @see Service#pause()
     */
    @Override
    public void pause(){
        this.serviceStatus = ServiceStatus.PAUSED;
    }

    /**
     * (non-Javadoc)
     * @see Service#resume()
     */
    @Override
    public void resume(){
        this.serviceStatus = ServiceStatus.STARTED;
    }

    /**
     * (non-Javadoc)
     * @see Service#stop()
     */
    @Override
    public void stop(){
        this.serviceStatus = ServiceStatus.STOPPED;
    }

    /**
     * (non-Javadoc)
     * @see Service#getStatus()
     */
    @Override
    public ServiceStatus getStatus() {
        return this.serviceStatus;
    }

    /**
     * (non-Javadoc)
     * @see DealsWithErrors#setErrorManager(ErrorManager)
     */
    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }

    /**
     * (non-Javadoc)
     * @see Plugin#setId(UUID)
     */
    @Override
    public void setId(UUID pluginId) {
        this.pluginId = pluginId;
    }

    /**
     * (non-Javadoc)
     * @see DealsWithLogger#setLogManager(LogManager)
     */
    @Override
    public void setLogManager(LogManager logManager) {
        this.logManager = logManager;
    }

    /**
     * (non-Javadoc)
     * @see LogManagerForDevelopers#getClassesFullPath()
     */
    @Override
    public List<String> getClassesFullPath() {
        List<String> returnedClasses = new ArrayList<String>();
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_publisher.developer.bitdubai.version_1.WalletPublisherMiddlewarePluginRoot");
        return returnedClasses;
    }

    /**
     * (non-Javadoc)
     * @see LogManagerForDevelopers#setLoggingLevelPerClass(Map<String, LogLevel>)
     */
    @Override
    public void setLoggingLevelPerClass(Map<String, LogLevel> newLoggingLevel) {

        /*
         * I will check the current values and update the LogLevel in those which is different
         */
        for (Map.Entry<String, LogLevel> pluginPair : newLoggingLevel.entrySet()) {

            /*
             * if this path already exists in the Root.bewLoggingLevel I'll update the value, else, I will put as new
             */
            if (WalletPublisherMiddlewarePluginRoot.newLoggingLevel.containsKey(pluginPair.getKey())) {
                WalletPublisherMiddlewarePluginRoot.newLoggingLevel.remove(pluginPair.getKey());
                WalletPublisherMiddlewarePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            } else {
                WalletPublisherMiddlewarePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            }
        }

    }

    /**
     * (non-Javadoc)
     * @see DealsWithWalletFactory#setWalletDescriptorFactoryProjectManager(WalletDescriptorFactoryProjectManager)
     */
    @Override
    public void setWalletDescriptorFactoryProjectManager(WalletDescriptorFactoryProjectManager walletDescriptorFactoryProjectManager) {
        this.walletDescriptorFactoryProjectManager = walletDescriptorFactoryProjectManager;
    }

    /**
     * (non-Javadoc)
     * @see DealsWithWalletStoreMiddleware#setWalletStoreManager(WalletStoreManager)
     */
    @Override
    public void setWalletStoreManager(WalletStoreManager walletStoreManager) {
        this.walletStoreManager = walletStoreManager;
    }

    /**
     * (non-Javadoc)
     * @see DealsWithWalletLanguage#setWalletLanguageManager(WalletLanguageManager)
     */
    @Override
    public void setWalletLanguageManager(WalletLanguageManager walletLanguageManager) {
        this.walletLanguageManager = walletLanguageManager;
    }

    /**
     * (non-Javadoc)
     * @see DealsWithWalletSkin#setWalletSkinManager(WalletSkinManager)
     */
    @Override
    public void setWalletSkinManager(WalletSkinManager walletSkinManager) {
        this.walletSkinManager = walletSkinManager;
    }

    /**
     * (non-Javadoc)
     * @see DealsWithPluginDatabaseSystem#setPluginDatabaseSystem(PluginDatabaseSystem)
     */
    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /**
     * (non-Javadoc)
     * @see DatabaseManagerForDevelopers#getDatabaseList(DeveloperObjectFactory)
     */
    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        return walletPublisherMiddlewareDeveloperDatabaseFactory.getDatabaseList(developerObjectFactory);
    }

    /**
     * (non-Javadoc)
     * @see DatabaseManagerForDevelopers#getDatabaseTableList(DeveloperObjectFactory, DeveloperDatabase)
     */
    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        return walletPublisherMiddlewareDeveloperDatabaseFactory.getDatabaseTableList(developerObjectFactory);
    }

    /**
     * (non-Javadoc)
     * @see DatabaseManagerForDevelopers#getDatabaseTableContent(DeveloperObjectFactory, DeveloperDatabase, DeveloperDatabaseTable)
     */
    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        return walletPublisherMiddlewareDeveloperDatabaseFactory.getDatabaseTableContent(developerObjectFactory, developerDatabaseTable);
    }

    /**
     * (non-Javadoc)
     * @see DealsWithPluginFileSystem#setPluginFileSystem(PluginFileSystem)
     */
    @Override
    public void setPluginFileSystem(PluginFileSystem pluginFileSystem) {
        this.pluginFileSystem = pluginFileSystem;
    }

    /**
     * (non-Javadoc)
     * @see WalletPublisherMiddlewarePlugin#getWalletPublisherMiddlewareManagerInstance()
     */
    @Override
    public WalletPublisherMiddlewareManager getWalletPublisherMiddlewareManagerInstance() {
        return walletPublisherMiddlewareManagerImpl;
    }
}