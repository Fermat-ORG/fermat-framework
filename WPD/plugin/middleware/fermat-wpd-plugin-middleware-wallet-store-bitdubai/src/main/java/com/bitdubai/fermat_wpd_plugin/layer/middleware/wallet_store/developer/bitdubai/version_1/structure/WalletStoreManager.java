package com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_store.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_store.enums.CatalogItems;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_store.enums.InstallationStatus;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_store.exceptions.CantGetItemInformationException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_store.exceptions.CantSetInstallationStatusException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_store.developer.bitdubai.version_1.exceptions.CantExecuteDatabaseOperationException;
import com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_store.developer.bitdubai.version_1.structure.common.DatabaseOperations;
import com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_store.developer.bitdubai.version_1.structure.database.WalletStoreMiddlewareDatabaseDao;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by rodrigo on 7/27/15.
 */
public class WalletStoreManager implements DealsWithErrors,DealsWithLogger,DealsWithPluginDatabaseSystem {

    /**
     * WalletStoreManager member variables
     */
    UUID pluginId;

    /**
     * DealsWithErrors Interface member variables.
     */
    ErrorManager errorManager;

    /**
     * DealsWithLogger interface member variable
     */
    LogManager logManager;

    /**
     * constructor
     * @param pluginId
     * @param errorManager
     * @param logManager
     * @param pluginDatabaseSystem
     */
    public WalletStoreManager(UUID pluginId, ErrorManager errorManager, LogManager logManager, PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginId = pluginId;
        this.errorManager = errorManager;
        this.logManager = logManager;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /**
     * DealsWithPluginDatabaseSystem interface member variables
     */
    PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * DealsWithErrors interface implementation
     * @param errorManager
     */
    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }

    /**
     * DealsWithLogger interface implementation
     * @param logManager
     */
    @Override
    public void setLogManager(LogManager logManager) {
        this.logManager = logManager;
    }

    /**
     * DealsWithPluginDatabaseSystem interface implementation
     * @param pluginDatabaseSystem
     */
    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    private WalletStoreMiddlewareDatabaseDao getDatabaseDao() throws CantExecuteDatabaseOperationException {
        WalletStoreMiddlewareDatabaseDao databaseDao = new WalletStoreMiddlewareDatabaseDao(errorManager, pluginDatabaseSystem, logManager, pluginId);
        return databaseDao;
    }

    /**
     * Gets the installation status of the passed catalog item and id
     * @param catalogItemType
     * @param itemId
     * @return
     * @throws CantGetItemInformationException
     */
    public InstallationStatus getInstallationStatus(CatalogItems catalogItemType, UUID itemId) throws CantGetItemInformationException {
        Map<CatalogItems, UUID> mapIds = new HashMap<>();
        mapIds.put(catalogItemType, itemId);
        try {
            CatalogItemInformation catalogItemInformation = getDatabaseDao().getCatalogItemInformationFromDatabase(mapIds);
            return catalogItemInformation.getInstallationStatus(itemId);
        } catch (Exception e) {
            throw new CantGetItemInformationException(CantGetItemInformationException.DEFAULT_MESSAGE, e, null, null);
        }
    }

    /**
     * Sets the catalog Item Information for the passed item
     * @param catalogItemType
     * @param itemId
     * @param installationStatus
     * @throws CantSetInstallationStatusException
     */
    public void setCatalogItemInformation(CatalogItems catalogItemType, UUID itemId, InstallationStatus installationStatus) throws CantSetInstallationStatusException {
        try {
            CatalogItemInformation catalogItemInformation = new CatalogItemInformation();
            catalogItemInformation.setCatalogItemId(catalogItemType, itemId);
            catalogItemInformation.setInstallationStatus(itemId, installationStatus);

            WalletStoreMiddlewareDatabaseDao databaseDao = getDatabaseDao();

            Map<CatalogItems, UUID> items = new HashMap<CatalogItems, UUID>();
            items.put(catalogItemType, itemId);
            try {
                databaseDao.getCatalogItemInformationFromDatabase(items);
                databaseDao.persistCatalogItemInformation(DatabaseOperations.UPDATE, catalogItemInformation);
            } catch (CantExecuteDatabaseOperationException e) {
                databaseDao.persistCatalogItemInformation(DatabaseOperations.INSERT, catalogItemInformation);
            }
        } catch (Exception e) {
            throw new CantSetInstallationStatusException(CantSetInstallationStatusException.DEFAULT_MESSAGE, e, null, null);
        }
    }
}
