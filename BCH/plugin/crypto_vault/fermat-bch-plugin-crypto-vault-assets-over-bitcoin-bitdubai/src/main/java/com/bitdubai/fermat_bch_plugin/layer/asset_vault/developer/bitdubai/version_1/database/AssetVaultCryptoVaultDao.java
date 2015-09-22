package com.bitdubai.fermat_bch_plugin.layer.asset_vault.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterOperator;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilter;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilterGroup;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTransaction;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseTransactionFailedException;
import com.bitdubai.fermat_bch_plugin.layer.asset_vault.developer.bitdubai.version_1.exceptions.CantExecuteDatabaseOperationException;
import com.bitdubai.fermat_bch_plugin.layer.asset_vault.developer.bitdubai.version_1.exceptions.CantInitializeAssetVaultCryptoVaultDatabaseException;
import com.bitdubai.fermat_bch_plugin.layer.asset_vault.developer.bitdubai.version_1.exceptions.InconsistentDatabaseResultException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by rodrigo on 9/21/15.
 */
public class AssetVaultCryptoVaultDao implements DealsWithPluginDatabaseSystem {
    Database database;
    UUID pluginId;

    /**
     * DealsWithPluginDatabaseSystem interface variable and implementation
     */
    PluginDatabaseSystem pluginDatabaseSystem;
    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }


    /**
     * Constructor
     * @param pluginId
     * @param pluginDatabaseSystem
     */
    public AssetVaultCryptoVaultDao(UUID pluginId, PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginId = pluginId;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /**
     * Will load or create the database
     * @throws CantInitializeAssetVaultCryptoVaultDatabaseException
     */
    private void initializeDatabase() throws CantInitializeAssetVaultCryptoVaultDatabaseException {
        try {
             /*
              * Open new database connection
              */
            database = this.pluginDatabaseSystem.openDatabase(pluginId, AssetVaultCryptoVaultDatabaseConstants.DATABASE_NAME);

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {

             /*
              * The database exists but cannot be open. I can not handle this situation.
              */
            throw new CantInitializeAssetVaultCryptoVaultDatabaseException(cantOpenDatabaseException.getMessage());

        } catch (DatabaseNotFoundException e) {

             /*
              * The database no exist may be the first time the plugin is running on this device,
              * We need to create the new database
              */
            AssetVaultCryptoVaultDatabaseFactory assetVaultCryptoVaultDatabaseFactory = new AssetVaultCryptoVaultDatabaseFactory(pluginDatabaseSystem);

            try {
                  /*
                   * We create the new database
                   */
                database = assetVaultCryptoVaultDatabaseFactory.createDatabase(pluginId, AssetVaultCryptoVaultDatabaseConstants.DATABASE_NAME);
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                  /*
                   * The database cannot be created. I can not handle this situation.
                   */
                throw new CantInitializeAssetVaultCryptoVaultDatabaseException(cantCreateDatabaseException.getMessage());
            }
        }

    }

    /**
     * gets the Key Hierarchy table
     * @return
     */
    private DatabaseTable getKeyHierarchyDatabaseTable() {
        return database.getTable(AssetVaultCryptoVaultDatabaseConstants.KEY_HIERARCHY_TABLE_NAME);
    }

    /**
     * Since I'm using this filter a lot, I'm creating the filter group that will be used in most methods.
     * The filter is AccountNumber = "x" and ChainNumber = "y"
     * @param accountNumber
     * @param chainNumber
     * @return
     */
    private DatabaseTableFilterGroup getFilterGroup(int accountNumber, int chainNumber){
        DatabaseTable databaseTable = getKeyHierarchyDatabaseTable();
        List<DatabaseTableFilter> filters = new ArrayList<>();
        filters.add(databaseTable.getNewFilter(AssetVaultCryptoVaultDatabaseConstants.KEY_HIERARCHY_ACCOUNT_NUMBER_COLUMN_NAME, DatabaseFilterType.EQUAL, String.valueOf(accountNumber)));
        filters.add(databaseTable.getNewFilter(AssetVaultCryptoVaultDatabaseConstants.KEY_HIERARCHY_CHAIN_NUMBER_COLUMN_NAME, DatabaseFilterType.EQUAL, String.valueOf(chainNumber)));

        DatabaseTableFilterGroup filterGroup = databaseTable.getNewFilterGroup(filters,null, DatabaseFilterOperator.AND);
        return filterGroup;
    }

    /**
     * Loads into memory the KeyHierarchy table with the specified filter.
     * @param accountNumber
     * @param chainNumber
     * @return
     * @throws CantExecuteDatabaseOperationException
     */
    private DatabaseTable loadKeyHierarchyWithFilterGroup(int accountNumber, int chainNumber) throws CantExecuteDatabaseOperationException {
        try {
            initializeDatabase();
        } catch (CantInitializeAssetVaultCryptoVaultDatabaseException e) {
            throw new CantExecuteDatabaseOperationException(CantExecuteDatabaseOperationException.DEFAULT_MESSAGE, e, "Database could not be initialized. Tried to load and create it.", "Database plugin issue");
        }
        DatabaseTable databaseTable = getKeyHierarchyDatabaseTable();
        databaseTable.setFilterGroup(getFilterGroup(accountNumber, chainNumber));
        try {
            databaseTable.loadToMemory();
        } catch (CantLoadTableToMemoryException e) {
            throw new CantExecuteDatabaseOperationException(CantExecuteDatabaseOperationException.DEFAULT_MESSAGE, e, "Couldn't load KeyHierarchy table into memory with the specified filter.", "Database plugin issue");
        }

        return databaseTable;
    }

    /**
     * Will get the available value to use from the key chain for the specified account and chain number.
     * @param accountNumber
     * @param chainNumber
     * @return
     * @throws CantExecuteDatabaseOperationException
     * @throws InconsistentDatabaseResultException
     */
    public int getAvailableKeyPosition(int accountNumber, int chainNumber) throws CantExecuteDatabaseOperationException, InconsistentDatabaseResultException {
        DatabaseTableRecord tableRecord = getDatabaseTableRecord(accountNumber, chainNumber);
        return tableRecord.getIntegerValue(AssetVaultCryptoVaultDatabaseConstants.KEY_HIERARCHY_KEY_POSITION_COLUMN_NAME);
    }

    /**
     * Gets the records that was loaded with the specified filter.
     * @param accountNumber
     * @param chainNumber
     * @return
     * @throws CantExecuteDatabaseOperationException
     * @throws InconsistentDatabaseResultException if more than one records existed with the specified filter.
     */
    private DatabaseTableRecord getDatabaseTableRecord (int accountNumber, int chainNumber) throws CantExecuteDatabaseOperationException, InconsistentDatabaseResultException {
        DatabaseTable databaseTable = loadKeyHierarchyWithFilterGroup(accountNumber, chainNumber);
        if (databaseTable.getRecords().size() != 1)
            throw new InconsistentDatabaseResultException(InconsistentDatabaseResultException.DEFAULT_MESSAGE, null, "Filter Account Number: " + accountNumber + " Chain Number: " + chainNumber, "inconsistent data in database.");

        DatabaseTableRecord tableRecord = databaseTable.getRecords().get(0);
        return tableRecord;
    }

    /**
     * Increase the counter of the position of the available key for the specified account and chain numbers.
     * @param accountNumber
     * @param chainNumber
     * @return
     * @throws InconsistentDatabaseResultException
     * @throws CantExecuteDatabaseOperationException
     */
    public void setNewAvailableKeyPosition(int accountNumber, int chainNumber) throws InconsistentDatabaseResultException, CantExecuteDatabaseOperationException {
        DatabaseTableRecord record = getDatabaseTableRecord(accountNumber, chainNumber);
        int currentValue = record.getIntegerValue(AssetVaultCryptoVaultDatabaseConstants.KEY_HIERARCHY_KEY_POSITION_COLUMN_NAME);
        int newValue = currentValue + 1;

        record.setIntegerValue(AssetVaultCryptoVaultDatabaseConstants.KEY_HIERARCHY_KEY_POSITION_COLUMN_NAME, newValue);
        DatabaseTransaction transaction = database.newTransaction();
        transaction.addRecordToUpdate(getKeyHierarchyDatabaseTable(), record);
        try {
            database.executeTransaction(transaction);
        } catch (DatabaseTransactionFailedException e) {
            throw new CantExecuteDatabaseOperationException(CantExecuteDatabaseOperationException.DEFAULT_MESSAGE, e, "error executing update transaction to increase the key position in the chain.", null);
        }
    }

    /**
     * For the specified account number and Actor public Key, I get the chainNumber that can be used to form the hierarchy path.
     * @param accountNumber
     * @param chainPublicKey
     * @return
     */
    public int getChainNumber(int accountNumber, String chainPublicKey){
        return 0;
    }

    /**
     * Verifies the passed account and chain numbers exists,
     * @param accountNumber
     * @param chainNumber
     * @return
     */
    public boolean isValidChainNumber(int accountNumber, int chainNumber) throws CantExecuteDatabaseOperationException {
        DatabaseTable databaseTable = loadKeyHierarchyWithFilterGroup(accountNumber, chainNumber);
        boolean result = false;

        if (databaseTable.getRecords().size() != 1)
            result = false;
        else
            result = true;

        database.closeDatabase();
        return result;
    }


}



