package com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_store.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_store.enums.CatalogItems;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTransaction;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_store.developer.bitdubai.version_1.exceptions.CantExecuteDatabaseOperationException;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_store.developer.bitdubai.version_1.structure.common.DatabaseOperations;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

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
            return pluginDatabaseSystem.openDatabase(pluginId, WalletStoreMiddlewareDatabaseConstants.DATABASE_NAME);
        } catch (CantOpenDatabaseException  | DatabaseNotFoundException exception) {
            throw  new CantExecuteDatabaseOperationException(exception, null, "Error in database plugin.");
        }
    }

    private DatabaseTable getDatabaseTable(String tableName){
        DatabaseTable table = database.getTable(tableName);
        return table;
    }

    private Map<DatabaseTable, DatabaseTableRecord> addCatalogItemInformationToDatabaseTableRecords (CatalogItemInformation catalogItemInformation) throws InvalidParameterException {
        Map<DatabaseTable, DatabaseTableRecord> records = new HashMap<DatabaseTable, DatabaseTableRecord>();

        UUID walletId = catalogItemInformation.getCatalogItemId(CatalogItems.WALLET);
        if (walletId != null){
            DatabaseTableRecord record = getDatabaseTable(WalletStoreMiddlewareDatabaseConstants.WALLETSTATUS_TABLE_NAME).getEmptyRecord();
            record.setUUIDValue(WalletStoreMiddlewareDatabaseConstants.WALLETSTATUS_ID_COLUMN_NAME, walletId);
            record.setStringValue(WalletStoreMiddlewareDatabaseConstants.WALLETSTATUS_INSTALATIONSTATUS_COLUMN_NAME, catalogItemInformation.getInstallationStatus(walletId).getCode());
            records.put(getDatabaseTable(WalletStoreMiddlewareDatabaseConstants.WALLETSTATUS_TABLE_NAME), record);
        }

        UUID languageId = catalogItemInformation.getCatalogItemId(CatalogItems.LANGUAGE);
        if (languageId != null){
            DatabaseTableRecord record = getDatabaseTable(WalletStoreMiddlewareDatabaseConstants.LANGUAGESTATUS_TABLE_NAME).getEmptyRecord();
            record.setUUIDValue(WalletStoreMiddlewareDatabaseConstants.LANGUAGESTATUS_ID_COLUMN_NAME, languageId);
            record.setStringValue(WalletStoreMiddlewareDatabaseConstants.LANGUAGESTATUS_INSTALATIONSTATUS_COLUMN_NAME, catalogItemInformation.getInstallationStatus(languageId).getCode());
            records.put(getDatabaseTable(WalletStoreMiddlewareDatabaseConstants.LANGUAGESTATUS_TABLE_NAME), record);
        }

        UUID skinId = catalogItemInformation.getCatalogItemId(CatalogItems.SKIN);
        if (skinId != null){
            DatabaseTableRecord record = getDatabaseTable(WalletStoreMiddlewareDatabaseConstants.SKINSTATUS_TABLE_NAME).getEmptyRecord();
            record.setUUIDValue(WalletStoreMiddlewareDatabaseConstants.SKINSTATUS_ID_COLUMN_NAME, skinId);
            record.setStringValue(WalletStoreMiddlewareDatabaseConstants.SKINSTATUS_INSTALATIONSTATUS_COLUMN_NAME, catalogItemInformation.getInstallationStatus(skinId).getCode());
            records.put(getDatabaseTable(WalletStoreMiddlewareDatabaseConstants.SKINSTATUS_TABLE_NAME), record);
        }

        if (records.isEmpty())
            throw new InvalidParameterException("CatalogItemInformation must contain at least one value.", null, null, null);

        return records;
    }

    private DatabaseTransaction addCatalogItemInformationInTransaction (DatabaseOperations databaseOperation, DatabaseTransaction transaction, CatalogItemInformation catalogItemInformation) throws CantExecuteDatabaseOperationException {
        try{
            for (Map.Entry entry : addCatalogItemInformationToDatabaseTableRecords(catalogItemInformation).entrySet()){
                switch (databaseOperation){
                    case INSERT:
                        transaction.addRecordToInsert((DatabaseTable) entry.getKey(), (DatabaseTableRecord) entry.getValue());
                        break;
                    case UPDATE:
                        transaction.addRecordToUpdate((DatabaseTable) entry.getKey(), (DatabaseTableRecord) entry.getValue());
                        break;
                    default:
                        throw new CantExecuteDatabaseOperationException(null,databaseOperation.toString(),"invalid database operation");
                }
            }
        } catch (InvalidParameterException e) {
            throw new CantExecuteDatabaseOperationException(e,null,null);
        }

        return transaction;
    }

    /**
     * Persist the change or insert into the database
     * @param databaseOperation
     * @param catalogItemInformation
     * @throws CantExecuteDatabaseOperationException
     */
    public void persistCatalogItemInformation (DatabaseOperations databaseOperation, CatalogItemInformation catalogItemInformation) throws CantExecuteDatabaseOperationException {
        try{
            database = openDatabase();
            DatabaseTransaction transaction = database.newTransaction();
            transaction = addCatalogItemInformationInTransaction(databaseOperation, transaction, catalogItemInformation);
            database.executeTransaction(transaction);
        } catch (Exception exception){
            throw new CantExecuteDatabaseOperationException(exception, null, null);
        }
    }

    /**
     * returns the catalogItemInformation object from the database
     * @param catalogItemsIds
     * @return
     */
    public CatalogItemInformation getCatalogItemInformationFromDatabase (Map<CatalogItems, UUID> catalogItemsIds){

        return null;
    }
}
