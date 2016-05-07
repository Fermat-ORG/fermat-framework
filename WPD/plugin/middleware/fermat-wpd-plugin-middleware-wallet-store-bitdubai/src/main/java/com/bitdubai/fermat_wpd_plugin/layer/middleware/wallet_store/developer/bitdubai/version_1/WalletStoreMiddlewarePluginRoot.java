package com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_store.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.developer.DatabaseManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_store.enums.CatalogItems;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_store.enums.InstallationStatus;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_store.exceptions.CantGetItemInformationException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_store.exceptions.CantSetInstallationStatusException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_store.interfaces.WalletStoreManager;
import com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_store.developer.bitdubai.version_1.structure.database.WalletStoreMiddlewareDatabaseConstants;
import com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_store.developer.bitdubai.version_1.structure.database.WalletStoreMiddlewareDatabaseFactory;
import com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_store.developer.bitdubai.version_1.structure.developerUtils.DeveloperDatabaseFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * Wallet Store - MiddleWare
 * This plugin controls and maintain the installation status of catalog items like wallets, language packages and skins.
 * When a new version is available or a catalog item is available, because it was found on a peer or installed on this device, we
 * keep the status of the installation on this session.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 09/07/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class WalletStoreMiddlewarePluginRoot extends AbstractPlugin implements
        DatabaseManagerForDevelopers,
        LogManagerForDevelopers,
        WalletStoreManager {

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM   , layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER         )
    private ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.LOG_MANAGER    )
    private LogManager logManager;


    /**
     * WalletStoreMiddlewarePluginRoot member variables
     */
    Database database;

    static Map<String, LogLevel> newLoggingLevel = new HashMap<>();

    public WalletStoreMiddlewarePluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    /**
     * DatabaseManagerForDevelopers interface implementation
     */
    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        DeveloperDatabaseFactory developerDatabaseFactory = new DeveloperDatabaseFactory(pluginId.toString());
        return developerDatabaseFactory.getDatabaseList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        return DeveloperDatabaseFactory.getDatabaseTableList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        return DeveloperDatabaseFactory.getDatabaseTableContent(developerObjectFactory, database, developerDatabaseTable);
    }

    /**
     * Service Interface implementation
     */

    @Override
    public void start() throws CantStartPluginException {
        /**
         * I will try to open the database first, if it doesn't exists, then I create it
         */
        try {
            database = pluginDatabaseSystem.openDatabase(pluginId, WalletStoreMiddlewareDatabaseConstants.DATABASE_NAME);
        } catch (CantOpenDatabaseException cantOpenDatabaseException) {
            try {
                createWalletStoreMiddlewareDatabase();
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WPD_WALLET_STORE_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantCreateDatabaseException);
                throw new CantStartPluginException();
            } catch (Exception exception) {
                throw new CantStartPluginException("Cannot start WalletStoreNetworkService plugin.", FermatException.wrapException(exception), null, null);
            }
        } catch (DatabaseNotFoundException databaseNotFoundException) {
            /**
             * The database doesn't exists, lets create it.
             */
            try {
                createWalletStoreMiddlewareDatabase();
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WPD_WALLET_STORE_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantCreateDatabaseException);
                throw new CantStartPluginException();
            }
        } catch (Exception exception) {
            throw new CantStartPluginException("Cannot start WalletStoreNetworkService plugin.", FermatException.wrapException(exception), null, null);
        }

        this.serviceStatus = ServiceStatus.STARTED;
    }

    private void createWalletStoreMiddlewareDatabase() throws CantCreateDatabaseException {
        WalletStoreMiddlewareDatabaseFactory databaseFactory = new WalletStoreMiddlewareDatabaseFactory(this.pluginDatabaseSystem);
        database = databaseFactory.createDatabase(pluginId, WalletStoreMiddlewareDatabaseConstants.DATABASE_NAME);
    }

    /**
     * LogManagerForDevelopers Interface implementation.
     */

    @Override
    public List<String> getClassesFullPath() {
        List<String> returnedClasses = new ArrayList<String>();
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_store.developer.bitdubai.version_1.WalletStoreMiddlewarePluginRoot");
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_store.developer.bitdubai.version_1.exceptions.CantExecuteDatabaseOperationException");
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_store.developer.bitdubai.version_1.exceptions.InconsistentDatabaseResultException");
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_store.developer.bitdubai.version_1.structure.common.DatabaseOperations");
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_store.developer.bitdubai.version_1.structure.database.WalletStoreMiddlewareDatabaseConstants");
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_store.developer.bitdubai.version_1.structure.database.WalletStoreMiddlewareDatabaseDao");
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_store.developer.bitdubai.version_1.structure.database.WalletStoreMiddlewareDatabaseFactory");
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_store.developer.bitdubai.version_1.structure.developerUtils.DeveloperDatabaseFactory");
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_store.developer.bitdubai.version_1.structure.CatalogItemInformation");
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_store.developer.bitdubai.version_1.structure.WalletStoreManager");
        /**
         * I return the values.
         */
        return returnedClasses;
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
            return WalletStoreMiddlewarePluginRoot.newLoggingLevel.get(correctedClass[0]);
        } catch (Exception exception) {
            System.err.println("CantGetLogLevelByClass: " + exception.getMessage());
            return LogLevel.MINIMAL_LOGGING;
        }
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
            if (WalletStoreMiddlewarePluginRoot.newLoggingLevel.containsKey(pluginPair.getKey())) {
                WalletStoreMiddlewarePluginRoot.newLoggingLevel.remove(pluginPair.getKey());
                WalletStoreMiddlewarePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            } else {
                WalletStoreMiddlewarePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            }
        }

    }

    private com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_store.developer.bitdubai.version_1.structure.WalletStoreManager getWalletStoreManager() {
        com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_store.developer.bitdubai.version_1.structure.WalletStoreManager walletStoreManager;
        walletStoreManager = new com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_store.developer.bitdubai.version_1.structure.WalletStoreManager(pluginId, errorManager, logManager, pluginDatabaseSystem);
        return walletStoreManager;
    }

    @Override
    public InstallationStatus getInstallationStatus(CatalogItems catalogItemType, UUID itemId) throws CantGetItemInformationException {
        return getWalletStoreManager().getInstallationStatus(catalogItemType, itemId);
    }

    @Override
    public void setInstallationStatus(CatalogItems catalogItemType, UUID itemId, InstallationStatus installationStatus) throws CantSetInstallationStatusException {
        getWalletStoreManager().setCatalogItemInformation(catalogItemType, itemId, installationStatus);
    }
}
