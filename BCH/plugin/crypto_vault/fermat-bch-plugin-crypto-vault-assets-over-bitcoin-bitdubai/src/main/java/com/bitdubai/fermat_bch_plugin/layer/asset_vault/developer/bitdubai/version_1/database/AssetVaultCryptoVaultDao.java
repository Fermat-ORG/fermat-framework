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
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
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
        List<DatabaseTableFilterGroup> filterGroups = new ArrayList<>();
        DatabaseTableFilterGroup filterGroup = databaseTable.getNewFilterGroup(filters,filterGroups, DatabaseFilterOperator.AND);
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
        if (database == null)
            try {
                initializeDatabase();
            } catch (CantInitializeAssetVaultCryptoVaultDatabaseException e) {
                throw new CantExecuteDatabaseOperationException(CantExecuteDatabaseOperationException.DEFAULT_MESSAGE, e, "the database could not be initialized. probably failed initial creation.", "database plugin error.");
            }

        DatabaseTableRecord tableRecord = getKeyHierarchyRecord(accountNumber, chainNumber);
        /**
         * I close the database and return the value
         */
        database.closeDatabase();
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
    private DatabaseTableRecord getKeyHierarchyRecord (int accountNumber, int chainNumber) throws CantExecuteDatabaseOperationException, InconsistentDatabaseResultException {
        DatabaseTable databaseTable = loadKeyHierarchyWithFilterGroup(accountNumber, chainNumber);
        if (databaseTable.getRecords().size() == 0){
            /**
             * If there is no matching record, then I will add this accountNumber (the bitcoin network) with chain and position equal to 0
             * I'm doing this because this should happen only on the initial load of the vault. After this, when adding new chains I will add
             * them somewhere else.
             */
            try {
                resetVaultKeyPosition(accountNumber);
            } catch (CantInsertRecordException e) {
                throw new CantExecuteDatabaseOperationException(CantExecuteDatabaseOperationException.DEFAULT_MESSAGE, e, "problem reseting to zero the asset vault branch for the specified account number.", "error trying to insert the record.");
            }
            /**
             * Once added the record, I will re run the method.
             */
            getKeyHierarchyRecord(accountNumber, chainNumber);
        }

        if (databaseTable.getRecords().size() != 1)
            throw new InconsistentDatabaseResultException(InconsistentDatabaseResultException.DEFAULT_MESSAGE, null, "the specified account number:" + accountNumber + " and chainNumber:" + chainNumber + " generated an inconsistency result in the database", "database inconsistency.");

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
        if (database == null)
            try {
                initializeDatabase();
            } catch (CantInitializeAssetVaultCryptoVaultDatabaseException e) {
                throw new CantExecuteDatabaseOperationException(CantExecuteDatabaseOperationException.DEFAULT_MESSAGE, e, "the database could not be initialized. probably failed initial creation.", "database plugin error.");
            }

        DatabaseTableRecord record = getKeyHierarchyRecord(accountNumber, chainNumber);
        int currentValue = record.getIntegerValue(AssetVaultCryptoVaultDatabaseConstants.KEY_HIERARCHY_KEY_POSITION_COLUMN_NAME);
        int newValue = currentValue + 1;

        record.setIntegerValue(AssetVaultCryptoVaultDatabaseConstants.KEY_HIERARCHY_KEY_POSITION_COLUMN_NAME, newValue);
        DatabaseTransaction transaction = database.newTransaction();
        transaction.addRecordToUpdate(getKeyHierarchyDatabaseTable(), record);
        try {
            database.executeTransaction(transaction);
            database.closeDatabase();
        } catch (DatabaseTransactionFailedException e) {
            database.closeDatabase();
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
        if (database == null)
            try {
                initializeDatabase();
            } catch (CantInitializeAssetVaultCryptoVaultDatabaseException e) {
                throw new CantExecuteDatabaseOperationException(CantExecuteDatabaseOperationException.DEFAULT_MESSAGE, e, "the database could not be initialized. probably failed initial creation.", "database plugin error.");
            }

        boolean result = false;
        DatabaseTable databaseTable = loadKeyHierarchyWithFilterGroup(accountNumber, chainNumber);
        if (databaseTable.getRecords().size() != 1)
        {
            /**
             * If the result is invalid, and the chain number is 0 (which belongs to this vault, I just will add it.)
             */
            if (chainNumber == 0){
                try {
                    resetVaultKeyPosition(accountNumber);
                    return true;
                } catch (CantInsertRecordException e) {
                    throw new CantExecuteDatabaseOperationException(CantExecuteDatabaseOperationException.DEFAULT_MESSAGE, e, "there was an error reseting the key position for the asset vault.", "insert failed.");
                }
            }
            /**
             * if the result is invalid and not from account 0, then this is a mistake, I will return false.
             */
            result = false;
        } else
            result = true;

        /**
         * I close the database and return the result.
         */
        database.closeDatabase();
        return result;
    }

    /**
     * Add the records that sets the position of zero to the asset vault branch on the specified network type.
     * @param accountNumber
     * @throws CantExecuteDatabaseOperationException
     */
    private void resetVaultKeyPosition(int accountNumber) throws CantInsertRecordException {
        DatabaseTable table = getKeyHierarchyDatabaseTable();
        DatabaseTableRecord record = table.getEmptyRecord();
        record.setIntegerValue(AssetVaultCryptoVaultDatabaseConstants.KEY_HIERARCHY_ACCOUNT_NUMBER_COLUMN_NAME, accountNumber);
        record.setIntegerValue(AssetVaultCryptoVaultDatabaseConstants.KEY_HIERARCHY_CHAIN_NUMBER_COLUMN_NAME, 0);
        record.setStringValue(AssetVaultCryptoVaultDatabaseConstants.KEY_HIERARCHY_CHAIN_PUBLICKEY_COLUMN_NAME, "AssetVault");
        record.setIntegerValue(AssetVaultCryptoVaultDatabaseConstants.KEY_HIERARCHY_KEY_POSITION_COLUMN_NAME, 0);
        table.insertRecord(record);
    }
}



