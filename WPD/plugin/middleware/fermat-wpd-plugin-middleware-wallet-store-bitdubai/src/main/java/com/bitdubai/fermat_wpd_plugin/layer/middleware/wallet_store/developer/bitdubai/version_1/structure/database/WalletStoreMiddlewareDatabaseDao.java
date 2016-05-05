package com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_store.developer.bitdubai.version_1.structure.database;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_store.enums.CatalogItems;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_store.enums.InstallationStatus;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTransaction;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_store.developer.bitdubai.version_1.exceptions.CantExecuteDatabaseOperationException;
import com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_store.developer.bitdubai.version_1.exceptions.InconsistentDatabaseResultException;
import com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_store.developer.bitdubai.version_1.structure.*;
import com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_store.developer.bitdubai.version_1.structure.common.DatabaseOperations;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by rodrigo on 7/24/15.
 */
public class WalletStoreMiddlewareDatabaseDao implements DealsWithErrors, DealsWithPluginDatabaseSystem, DealsWithLogger {

    /**
     * WalletStoreMiddlewareDatabaseDao member variables
     */
    UUID pluginId;
    Database database;

    /**
     * DealsWithErrors interface member variables
     */
    ErrorManager errorManager;

    /**
     * DealsWithPluginDatabaseSystem interface member variables
     */
    PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * DealsWithLogger interface member variables
     */
    LogManager logManager;


    /**
     * Constructor
     *
     * @param errorManager
     * @param pluginDatabaseSystem
     * @param logManager
     */
    public WalletStoreMiddlewareDatabaseDao(ErrorManager errorManager, PluginDatabaseSystem pluginDatabaseSystem, LogManager logManager, UUID pluginId) throws CantExecuteDatabaseOperationException {
        this.errorManager = errorManager;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.logManager = logManager;
        this.pluginId = pluginId;

        database = openDatabase();
        database.closeDatabase();
    }

    /**
     * DealsWithErrors interface implementation
     */
    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }

    /**
     * DealsWithPluginDatabaseSystem interface implementation
     */
    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /**
     * DealsWithLogger interface implementation
     */
    @Override
    public void setLogManager(LogManager logManager) {
        this.logManager = logManager;
    }

    public void setPluginId(UUID pluginId) {
        this.pluginId = pluginId;
    }

    private Database openDatabase() throws CantExecuteDatabaseOperationException {
        try {
            return pluginDatabaseSystem.openDatabase(pluginId, com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_store.developer.bitdubai.version_1.structure.database.WalletStoreMiddlewareDatabaseConstants.DATABASE_NAME);
        } catch (CantOpenDatabaseException | DatabaseNotFoundException exception) {
            throw new CantExecuteDatabaseOperationException(exception, null, "Error in database plugin.");
        }
    }

    private DatabaseTable getDatabaseTable(String tableName) {
        DatabaseTable table = database.getTable(tableName);
        return table;
    }

    private Map<DatabaseTable, DatabaseTableRecord> addCatalogItemInformationToDatabaseTableRecords(CatalogItemInformation catalogItemInformation) throws InvalidParameterException {
        Map<DatabaseTable, DatabaseTableRecord> records = new HashMap<DatabaseTable, DatabaseTableRecord>();

        UUID walletId = catalogItemInformation.getCatalogItemId(CatalogItems.WALLET);
        if (walletId != null) {
            DatabaseTableRecord record = getDatabaseTable(com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_store.developer.bitdubai.version_1.structure.database.WalletStoreMiddlewareDatabaseConstants.WALLETSTATUS_TABLE_NAME).getEmptyRecord();
            record.setUUIDValue(com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_store.developer.bitdubai.version_1.structure.database.WalletStoreMiddlewareDatabaseConstants.WALLETSTATUS_ID_COLUMN_NAME, walletId);
            record.setStringValue(com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_store.developer.bitdubai.version_1.structure.database.WalletStoreMiddlewareDatabaseConstants.WALLETSTATUS_INSTALATIONSTATUS_COLUMN_NAME, catalogItemInformation.getInstallationStatus(walletId).getCode());
            records.put(getDatabaseTable(com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_store.developer.bitdubai.version_1.structure.database.WalletStoreMiddlewareDatabaseConstants.WALLETSTATUS_TABLE_NAME), record);
        }

        UUID languageId = catalogItemInformation.getCatalogItemId(CatalogItems.LANGUAGE);
        if (languageId != null) {
            DatabaseTableRecord record = getDatabaseTable(com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_store.developer.bitdubai.version_1.structure.database.WalletStoreMiddlewareDatabaseConstants.LANGUAGESTATUS_TABLE_NAME).getEmptyRecord();
            record.setUUIDValue(com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_store.developer.bitdubai.version_1.structure.database.WalletStoreMiddlewareDatabaseConstants.LANGUAGESTATUS_ID_COLUMN_NAME, languageId);
            record.setStringValue(com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_store.developer.bitdubai.version_1.structure.database.WalletStoreMiddlewareDatabaseConstants.LANGUAGESTATUS_INSTALATIONSTATUS_COLUMN_NAME, catalogItemInformation.getInstallationStatus(languageId).getCode());
            records.put(getDatabaseTable(com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_store.developer.bitdubai.version_1.structure.database.WalletStoreMiddlewareDatabaseConstants.LANGUAGESTATUS_TABLE_NAME), record);
        }

        UUID skinId = catalogItemInformation.getCatalogItemId(CatalogItems.SKIN);
        if (skinId != null) {
            DatabaseTableRecord record = getDatabaseTable(com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_store.developer.bitdubai.version_1.structure.database.WalletStoreMiddlewareDatabaseConstants.SKINSTATUS_TABLE_NAME).getEmptyRecord();
            record.setUUIDValue(com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_store.developer.bitdubai.version_1.structure.database.WalletStoreMiddlewareDatabaseConstants.SKINSTATUS_ID_COLUMN_NAME, skinId);
            record.setStringValue(com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_store.developer.bitdubai.version_1.structure.database.WalletStoreMiddlewareDatabaseConstants.SKINSTATUS_INSTALATIONSTATUS_COLUMN_NAME, catalogItemInformation.getInstallationStatus(skinId).getCode());
            records.put(getDatabaseTable(com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_store.developer.bitdubai.version_1.structure.database.WalletStoreMiddlewareDatabaseConstants.SKINSTATUS_TABLE_NAME), record);
        }

        if (records.isEmpty())
            throw new InvalidParameterException("CatalogItemInformation must contain at least one value.", null, null, null);

        return records;
    }

    private DatabaseTransaction addCatalogItemInformationInTransaction(DatabaseOperations databaseOperation, DatabaseTransaction transaction, CatalogItemInformation catalogItemInformation) throws CantExecuteDatabaseOperationException {
        try {
            for (Map.Entry entry : addCatalogItemInformationToDatabaseTableRecords(catalogItemInformation).entrySet()) {
                switch (databaseOperation) {
                    case INSERT:
                        transaction.addRecordToInsert((DatabaseTable) entry.getKey(), (DatabaseTableRecord) entry.getValue());
                        break;
                    case UPDATE:
                        // I define the filter for the update
                        DatabaseTable table = (DatabaseTable) entry.getKey();
                        DatabaseTableRecord record = (DatabaseTableRecord) entry.getValue();
                        table.addStringFilter(WalletStoreMiddlewareDatabaseConstants.SKINSTATUS_ID_COLUMN_NAME, record.getUUIDValue(WalletStoreMiddlewareDatabaseConstants.SKINSTATUS_ID_COLUMN_NAME).toString(), DatabaseFilterType.EQUAL);
                        transaction.addRecordToUpdate(table, record);
                        break;
                    default:
                        throw new CantExecuteDatabaseOperationException(null, databaseOperation.toString(), "invalid database operation");
                }
            }
        } catch (InvalidParameterException e) {
            throw new CantExecuteDatabaseOperationException(e, null, null);
        }

        return transaction;
    }

    /**
     * Persist the change or insert into the database
     *
     * @param databaseOperation
     * @param catalogItemInformation
     * @throws CantExecuteDatabaseOperationException
     */
    public void persistCatalogItemInformation(DatabaseOperations databaseOperation, CatalogItemInformation catalogItemInformation) throws CantExecuteDatabaseOperationException {
        try {
            database = openDatabase();
            DatabaseTransaction transaction = database.newTransaction();
            transaction = addCatalogItemInformationInTransaction(databaseOperation, transaction, catalogItemInformation);
            database.executeTransaction(transaction);
        } catch (Exception exception) {
            throw new CantExecuteDatabaseOperationException(exception, null, null);
        }
    }

    /**
     * returns the catalogItemInformation object from the database
     *
     * @param catalogItemsIds
     * @return
     */
    public CatalogItemInformation getCatalogItemInformationFromDatabase(Map<CatalogItems, UUID> catalogItemsIds) throws CantExecuteDatabaseOperationException {
        database = openDatabase();
        CatalogItemInformation catalogItemInformation = new CatalogItemInformation();
        for (Map.Entry<CatalogItems, UUID> entry : catalogItemsIds.entrySet()) {
            catalogItemInformation.setCatalogItemId(entry.getKey(), entry.getValue());
            try {
                catalogItemInformation.setInstallationStatus(entry.getValue(), getCatalogItemFromDatabaseTableRecord(entry.getKey(), entry.getValue()));
                database.closeDatabase();
            } catch (Exception e) {
                database.closeDatabase();
                throw new CantExecuteDatabaseOperationException(e, null, null);
            }
        }

        return catalogItemInformation;
    }

    private DatabaseTableRecord getCatalogItemInformationIntoRecord(CatalogItems catalogItem, UUID itemId) throws InconsistentDatabaseResultException, CantExecuteDatabaseOperationException, InvalidParameterException {
        DatabaseTable databaseTable;
        switch (catalogItem) {
            case LANGUAGE:
                databaseTable = getDatabaseTable(WalletStoreMiddlewareDatabaseConstants.LANGUAGESTATUS_TABLE_NAME);
                databaseTable.addStringFilter(WalletStoreMiddlewareDatabaseConstants.LANGUAGESTATUS_ID_COLUMN_NAME, itemId.toString(), DatabaseFilterType.EQUAL);
                break;
            case SKIN:
                databaseTable = getDatabaseTable(WalletStoreMiddlewareDatabaseConstants.SKINSTATUS_TABLE_NAME);
                databaseTable.addStringFilter(WalletStoreMiddlewareDatabaseConstants.SKINSTATUS_ID_COLUMN_NAME, itemId.toString(), DatabaseFilterType.EQUAL);
                break;
            case WALLET:
                databaseTable = getDatabaseTable(WalletStoreMiddlewareDatabaseConstants.WALLETSTATUS_TABLE_NAME);
                databaseTable.addStringFilter(WalletStoreMiddlewareDatabaseConstants.WALLETSTATUS_ID_COLUMN_NAME, itemId.toString(), DatabaseFilterType.EQUAL);
                break;
            default:
                throw new InvalidParameterException("Invalid CatalogItem argument.", null, null, null);
        }
        try {
            databaseTable.loadToMemory();

            if (databaseTable.getRecords().size() != 1)
                throw new InconsistentDatabaseResultException(InconsistentDatabaseResultException.DEFAULT_MESSAGE, null, itemId.toString(), "Returned records: " + databaseTable.getRecords().size());

            return databaseTable.getRecords().get(0);
        } catch (CantLoadTableToMemoryException cantLoadTableToMemoryException) {
            throw new CantExecuteDatabaseOperationException(cantLoadTableToMemoryException, null, null);
        }
    }

    private InstallationStatus getCatalogItemFromDatabaseTableRecord(CatalogItems catalogItem, UUID itemId) throws CantExecuteDatabaseOperationException {
        InstallationStatus installationStatus;
        try {
            DatabaseTableRecord record = getCatalogItemInformationIntoRecord(catalogItem, itemId);
            switch (catalogItem) {
                case WALLET:
                    installationStatus = InstallationStatus.getByCode(record.getStringValue(WalletStoreMiddlewareDatabaseConstants.WALLETSTATUS_INSTALATIONSTATUS_COLUMN_NAME));
                    break;
                case SKIN:
                    installationStatus = InstallationStatus.getByCode(record.getStringValue(WalletStoreMiddlewareDatabaseConstants.SKINSTATUS_INSTALATIONSTATUS_COLUMN_NAME));
                    break;
                case LANGUAGE:
                    installationStatus = InstallationStatus.getByCode(record.getStringValue(WalletStoreMiddlewareDatabaseConstants.LANGUAGESTATUS_INSTALATIONSTATUS_COLUMN_NAME));
                    break;
                default:
                    throw new InvalidParameterException("Invalid CatalogItem argument.", null, null, null);
            }
        } catch (InconsistentDatabaseResultException | CantExecuteDatabaseOperationException | InvalidParameterException exception) {
            throw new CantExecuteDatabaseOperationException(exception, null, null);
        }

        return installationStatus;
    }
}