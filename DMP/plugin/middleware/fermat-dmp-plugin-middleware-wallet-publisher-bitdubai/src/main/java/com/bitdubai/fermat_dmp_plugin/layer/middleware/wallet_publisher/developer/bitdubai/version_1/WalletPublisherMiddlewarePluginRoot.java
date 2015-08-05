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
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletFactoryManager;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletFactoryProjectProposal;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_publisher.exceptions.CantCheckPublicationException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_publisher.exceptions.CantGetPublishedWalletsInformationException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_publisher.exceptions.CantPublishWalletException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_publisher.interfaces.WalletPublishedInformation;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_publisher.interfaces.WalletPublisherMiddlewareManager;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_store.interfaces.DealsWithWalletStoreMiddleware;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_store.interfaces.WalletStoreManager;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_publisher.developer.bitdubai.version_1.database.WalletPublisherMiddlewareDeveloperDatabaseFactory;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_publisher.developer.bitdubai.version_1.structure.WalletPublisherMiddlewareManagerImpl;
import com.bitdubai.fermat_pip_api.layer.pip_identity.developer.interfaces.DealsWithDeveloperIdentity;
import com.bitdubai.fermat_pip_api.layer.pip_identity.developer.interfaces.DeveloperIdentityManager;
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
public class WalletPublisherMiddlewarePluginRoot implements DealsWithDeveloperIdentity, DealsWithWalletFactory, DealsWithWalletStoreMiddleware, DealsWithErrors,DealsWithLogger, LogManagerForDevelopers, Plugin, Service, WalletPublisherMiddlewareManager, DatabaseManagerForDevelopers {

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
     * Represent the walletFactoryManager
     */
    private WalletFactoryManager walletFactoryManager;

    /**
     * Represent the walletStoreManager
     */
    private WalletStoreManager walletStoreManager;

    /**
     * Represent the developerIdentityManager
     */
    private DeveloperIdentityManager developerIdentityManager;

    /**
     * Represent the plugin id
     */
    private UUID pluginId;

    /**
     * Represent the status of the service
     */
    private ServiceStatus serviceStatus;

    /**
     * Represent the status of the walletPublisherMiddlewareManagerImpl
     */
    private WalletPublisherMiddlewareManagerImpl walletPublisherMiddlewareManagerImpl;

    /**
     * Represent the status of the walletPublisherMiddlewareDeveloperDatabaseFactory
     */
    private WalletPublisherMiddlewareDeveloperDatabaseFactory walletPublisherMiddlewareDeveloperDatabaseFactory;

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
        if (logManager                               == null ||
                errorManager                         == null ||
                    errorManager                     == null ||
                        walletFactoryManager         == null ||
                            developerIdentityManager == null) {

            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Plugin ID: " + pluginId);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("logManager: " + logManager);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("errorManager: " + errorManager);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("walletFactoryManager: " + walletFactoryManager);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("walletStoreManager: " + walletStoreManager);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("walletStoreManager: " + walletStoreManager);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("developerIdentityManager: " + developerIdentityManager);

            String context = contextBuffer.toString();
            String possibleCause = "No all required resource are injected";
            CantStartPluginException pluginStartException = new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, null, context, possibleCause);

            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_PUBLISHER_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, pluginStartException);
            throw pluginStartException;
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
        validateInjectedResources();







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
     * @see DealsWithWalletFactory#setWalletFactoryManager(WalletFactoryManager)
     */
    @Override
    public void setWalletFactoryManager(WalletFactoryManager walletFactoryManager) {
        this.walletFactoryManager = walletFactoryManager;
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
     * @see DealsWithDeveloperIdentity#setDeveloperIdentityManager(DeveloperIdentityManager)
     */
    @Override
    public void setDeveloperIdentityManager(DeveloperIdentityManager developerIdentityManager) {
        this.developerIdentityManager = developerIdentityManager;
    }


    /**
     * (non-Javadoc)
     * @see WalletPublisherMiddlewareManager#showPublishedWallets()
     */
    @Override
    public Map<String, List<WalletPublishedInformation>> showPublishedWallets() throws CantGetPublishedWalletsInformationException {
        return null;
    }

    /**
     * (non-Javadoc)
     * @see WalletPublisherMiddlewareManager#canBePublished(WalletFactoryProjectProposal)
     */
    @Override
    public boolean canBePublished(WalletFactoryProjectProposal walletFactoryProjectProposal) throws CantCheckPublicationException {
        return false;
    }

    /**
     * (non-Javadoc)
     * @see WalletPublisherMiddlewareManager#publishWallet(WalletFactoryProjectProposal)
     */
    @Override
    public void publishWallet(WalletFactoryProjectProposal walletFactoryProjectProposal) throws CantPublishWalletException {

    }

    /**
     * (non-Javadoc)
     * @see WalletPublisherMiddlewareManager#publishSkin(WalletFactoryProjectProposal)
     */
    @Override
    public void publishSkin(WalletFactoryProjectProposal walletFactoryProjectProposal) throws CantPublishWalletException {

    }

    /**
     * (non-Javadoc)
     * @see WalletPublisherMiddlewareManager#publishLanguage(WalletFactoryProjectProposal)
     */
    @Override
    public void publishLanguage(WalletFactoryProjectProposal walletFactoryProjectProposal) throws CantPublishWalletException {

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
}
