package com.bitdubai.fermat_bch_plugin.layer.asset_vault.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTransaction;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseTransactionFailedException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.classes.HierarchyAccount.HierarchyAccountType;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.exceptions.CantExecuteDatabaseOperationException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.interfaces.CryptoVaultDao;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.interfaces.VaultKeyMaintenanceParameters;
import com.bitdubai.fermat_bch_plugin.layer.asset_vault.developer.bitdubai.version_1.exceptions.CantInitializeAssetsOverBitcoinCryptoVaultDatabaseException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.classes.HierarchyAccount.HierarchyAccount;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.UnexpectedResultReturnedFromDatabaseException;

import org.bitcoinj.core.ECKey;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_bch_plugin.layer.cryptovault.assetsoverbitcoin.developer.bitdubai.version_1.database.AssetsOverBitcoinCryptoVaultDao</code>
 * Class used to initialize the database and access and modify the saved data.<p/>
 * <p/>
 *
 * Created by Rodrigo Acosta - (acosta_rodrigo@hotmail.com) on 06/10/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class AssetsOverBitcoinCryptoVaultDao implements CryptoVaultDao {
    /**
     * Database instance of the plugin.
     */
    Database database;

    /**
     * Platform variables
     */
    PluginDatabaseSystem pluginDatabaseSystem;
    UUID pluginId;

    /**
     * Constructor
     * @param pluginDatabaseSystem the database object
     * @param pluginId the pluginId used to access the database
     */
    public AssetsOverBitcoinCryptoVaultDao(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) throws CantInitializeAssetsOverBitcoinCryptoVaultDatabaseException {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;

        /**
         * initialize the database
         */
        initializeDatabase();
    }

    /**
     * Initializes the database on the plugin. If it doesn't exists it will create it. If it exists, it will load it.
     * @throws CantInitializeAssetsOverBitcoinCryptoVaultDatabaseException
     */
    private void initializeDatabase() throws CantInitializeAssetsOverBitcoinCryptoVaultDatabaseException {
        try {
             /*
              * Open new database connection
              */
            database = this.pluginDatabaseSystem.openDatabase(pluginId, pluginId.toString());

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {
             /*
              * The database exists but cannot be open. I can not handle this situation.
              */
            throw new CantInitializeAssetsOverBitcoinCryptoVaultDatabaseException(cantOpenDatabaseException.getMessage());

        } catch (DatabaseNotFoundException e) {
             /*
              * The database no exist may be the first time the plugin is running on this device,
              * We need to create the new database
              */
            AssetsOverBitcoinCryptoVaultDatabaseFactory assetsOverBitcoinCryptoVaultDatabaseFactory = new AssetsOverBitcoinCryptoVaultDatabaseFactory(pluginDatabaseSystem);
            try {
                  /*
                   * We create the new database
                   */
                database = assetsOverBitcoinCryptoVaultDatabaseFactory.createDatabase(pluginId, pluginId.toString());
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                  /*
                   * The database cannot be created. I can not handle this situation.
                   */
                throw new CantInitializeAssetsOverBitcoinCryptoVaultDatabaseException(cantCreateDatabaseException.getMessage());
            }
        }
    }


    private DatabaseTable getDatabaseTable(String tableName){
        return database.getTable(tableName);
    }

    /**
     * Inserts a new HierarchyAccount into the database
     * @param hierarchyAccount
     * @throws CantExecuteDatabaseOperationException
     * @throws CantInsertRecordException
     */
    public void addNewHierarchyAccount(HierarchyAccount hierarchyAccount) throws CantExecuteDatabaseOperationException {
        DatabaseTable databaseTable = getDatabaseTable(AssetsOverBitcoinCryptoVaultDatabaseConstants.KEY_ACCOUNTS_TABLE_NAME);
        DatabaseTableRecord record = databaseTable.getEmptyRecord();

        record.setIntegerValue(AssetsOverBitcoinCryptoVaultDatabaseConstants.KEY_ACCOUNTS_ID_COLUMN_NAME, hierarchyAccount.getId());
        record.setStringValue(AssetsOverBitcoinCryptoVaultDatabaseConstants.KEY_ACCOUNTS_DESCRIPTION_COLUMN_NAME, hierarchyAccount.getDescription());
        record.setStringValue(AssetsOverBitcoinCryptoVaultDatabaseConstants.KEY_ACCOUNTS_TYPE_COLUMN_NAME, hierarchyAccount.getHierarchyAccountType().getCode());

        try {
            databaseTable.insertRecord(record);
        } catch (CantInsertRecordException e) {
            /**
             * if there was an error inserting the object, I will prepare the error message and throw it.
             */
            StringBuilder outputMessage = new StringBuilder("There was an error inserting a new HierarchyAccount value.");
            outputMessage.append(System.lineSeparator());
            outputMessage.append("HierarchyAccount value:");
            outputMessage.append(System.lineSeparator());
            outputMessage.append(XMLParser.parseObject(hierarchyAccount));

            throw new CantExecuteDatabaseOperationException(CantExecuteDatabaseOperationException.DEFAULT_MESSAGE, e, outputMessage.toString(), "A database error.");
        }
    }

    /**
     * Gets all the HierarchyAccounts records stored in the database
     * @return the list of HierarchyAccounts objects
     * @throws CantExecuteDatabaseOperationException
     */
    @Override
    public List<HierarchyAccount> getHierarchyAccounts() throws CantExecuteDatabaseOperationException {
        List<HierarchyAccount> hierarchyAccounts = new ArrayList<>();
        DatabaseTable databaseTable = getDatabaseTable(AssetsOverBitcoinCryptoVaultDatabaseConstants.KEY_ACCOUNTS_TABLE_NAME);
        try {
            databaseTable.loadToMemory();
        } catch (CantLoadTableToMemoryException e) {
            throwLoadToMemoryException(e, databaseTable.getTableName());
        }

        /**
         * Iterate each record and form the HierarchyAccount object.
         */
        for (DatabaseTableRecord record : databaseTable.getRecords()){
            HierarchyAccount hierarchyAccount = null;
            try {
                hierarchyAccount = new HierarchyAccount(
                        record.getIntegerValue(AssetsOverBitcoinCryptoVaultDatabaseConstants.KEY_ACCOUNTS_ID_COLUMN_NAME),
                        record.getStringValue(AssetsOverBitcoinCryptoVaultDatabaseConstants.KEY_ACCOUNTS_DESCRIPTION_COLUMN_NAME),
                        HierarchyAccountType.getByCode(record.getStringValue(AssetsOverBitcoinCryptoVaultDatabaseConstants.KEY_ACCOUNTS_TYPE_COLUMN_NAME)));
            } catch (InvalidParameterException e) {
                throw new CantExecuteDatabaseOperationException(CantExecuteDatabaseOperationException.DEFAULT_MESSAGE, e, "Invalid Account Type: " + record.getStringValue(AssetsOverBitcoinCryptoVaultDatabaseConstants.KEY_ACCOUNTS_TYPE_COLUMN_NAME), null );
            }

            /**
             * Adds the created HierarchyAccount into the list to be returned.
             */
            hierarchyAccounts.add(hierarchyAccount);
        }

        return hierarchyAccounts;
    }

    /**
     * Inserts (if new) or updates Key Maitainer statistics in the database.
     * this information is usefull to troubleshoot maintainer issues
     * @param hierarchyAccountId the account we have just maintained.
     * @param date the current execution date and time
     * @param currentGeneratedKeys the current amount of generated keys already
     * @param currentUsedKeys the current amount of used keys
     * @param currentThreshold the current threshold that will indicate if new keys are generated.
     * @throws CantExecuteDatabaseOperationException
     */
    public void updateMaintainerStatistics(int hierarchyAccountId,
                                            String date,
                                            int currentGeneratedKeys,
                                            int currentUsedKeys,
                                            int currentThreshold)
            throws CantExecuteDatabaseOperationException{

        DatabaseTable databaseTable = getDatabaseTable(AssetsOverBitcoinCryptoVaultDatabaseConstants.KEY_MAINTENANCE_MONITOR_TABLE_NAME);

        /**
         * I will filter the table using the account id
         */
        databaseTable.addStringFilter(AssetsOverBitcoinCryptoVaultDatabaseConstants.KEY_MAINTENANCE_MONITOR_ACCOUNT_ID_COLUMN_NAME, String.valueOf(hierarchyAccountId), DatabaseFilterType.EQUAL);
        try {
            databaseTable.loadToMemory();
        } catch (CantLoadTableToMemoryException e) {
            throwLoadToMemoryException(e, databaseTable.getTableName());
        }

        /**
         * If there are no matching record I will insert it, or update them if they already exists.
         */

        if (databaseTable.getRecords().size() == 0){
            insertNewMaintainerStatistics(hierarchyAccountId, date, currentGeneratedKeys, currentUsedKeys, currentThreshold);
        } else {
            DatabaseTableRecord record = databaseTable.getRecords().get(0);
            updateExistingMaintainerStatistics(record, date, currentGeneratedKeys, currentUsedKeys, currentThreshold);
        }

    }

    /**
     * Inserts into the database new statistics from the key maintainer
     * @param hierarchyAccountId
     * @param date
     * @param currentGeneratedKeys
     * @param currentUsedKeys
     * @param currentThreshold
     * @throws CantExecuteDatabaseOperationException
     */
    private void insertNewMaintainerStatistics(int hierarchyAccountId,
                                               String date,
                                               int currentGeneratedKeys,
                                               int currentUsedKeys,
                                               int currentThreshold)
            throws CantExecuteDatabaseOperationException{
        DatabaseTable databaseTable = getDatabaseTable(AssetsOverBitcoinCryptoVaultDatabaseConstants.KEY_MAINTENANCE_MONITOR_TABLE_NAME);

        DatabaseTableRecord databaseTableRecord = databaseTable.getEmptyRecord();
        databaseTableRecord.setIntegerValue(AssetsOverBitcoinCryptoVaultDatabaseConstants.KEY_MAINTENANCE_MONITOR_EXECUTION_NUMBER_COLUMN_NAME, 1);
        databaseTableRecord.setIntegerValue(AssetsOverBitcoinCryptoVaultDatabaseConstants.KEY_MAINTENANCE_MONITOR_ACCOUNT_ID_COLUMN_NAME, hierarchyAccountId);
        databaseTableRecord.setStringValue(AssetsOverBitcoinCryptoVaultDatabaseConstants.KEY_MAINTENANCE_MONITOR_EXECUTION_DATE_COLUMN_NAME, date);
        databaseTableRecord.setIntegerValue(AssetsOverBitcoinCryptoVaultDatabaseConstants.KEY_MAINTENANCE_MONITOR_GENERATED_KEYS_COLUMN_NAME, currentGeneratedKeys);
        databaseTableRecord.setIntegerValue(AssetsOverBitcoinCryptoVaultDatabaseConstants.KEY_MAINTENANCE_MONITOR_USED_KEYS_COLUMN_NAME, currentUsedKeys);
        databaseTableRecord.setIntegerValue(AssetsOverBitcoinCryptoVaultDatabaseConstants.KEY_MAINTENANCE_MONITOR_THRESHOLD_COLUMN_NAME, currentThreshold);

        try {
            databaseTable.insertRecord(databaseTableRecord);
        } catch (CantInsertRecordException e) {
            /**
             * I will create the output meessage
             */
            StringBuilder outputMessage = new StringBuilder("there was an error inserting a new record into " );
            outputMessage.append(AssetsOverBitcoinCryptoVaultDatabaseConstants.KEY_MAINTENANCE_MONITOR_TABLE_NAME);
            outputMessage.append(System.lineSeparator());
            outputMessage.append("The record to insert is: ");
            outputMessage.append(XMLParser.parseObject(databaseTableRecord));

            throw new CantExecuteDatabaseOperationException(
                    CantExecuteDatabaseOperationException.DEFAULT_MESSAGE,
                    e,
                    outputMessage.toString(),
                    "Database issue");
        }
    }

    /**
     * Updates existing key maintainer statistics.
     * @param date
     * @param currentGeneratedKeys
     * @param currentUsedKeys
     * @param currentThreshold
     * @throws CantExecuteDatabaseOperationException
     */
    private void updateExistingMaintainerStatistics(DatabaseTableRecord recordToUpdate,
                                                    String date,
                                                    int currentGeneratedKeys,
                                                    int currentUsedKeys,
                                                    int currentThreshold)
            throws CantExecuteDatabaseOperationException{
        int previousExecNumber = recordToUpdate.getIntegerValue(AssetsOverBitcoinCryptoVaultDatabaseConstants.KEY_MAINTENANCE_MONITOR_EXECUTION_NUMBER_COLUMN_NAME);
        recordToUpdate.setIntegerValue(AssetsOverBitcoinCryptoVaultDatabaseConstants.KEY_MAINTENANCE_MONITOR_EXECUTION_NUMBER_COLUMN_NAME, previousExecNumber + 1);
        recordToUpdate.setStringValue(AssetsOverBitcoinCryptoVaultDatabaseConstants.KEY_MAINTENANCE_MONITOR_EXECUTION_DATE_COLUMN_NAME, date);
        recordToUpdate.setIntegerValue(AssetsOverBitcoinCryptoVaultDatabaseConstants.KEY_MAINTENANCE_MONITOR_GENERATED_KEYS_COLUMN_NAME, currentGeneratedKeys);
        recordToUpdate.setIntegerValue(AssetsOverBitcoinCryptoVaultDatabaseConstants.KEY_MAINTENANCE_MONITOR_USED_KEYS_COLUMN_NAME, currentUsedKeys);
        recordToUpdate.setIntegerValue(AssetsOverBitcoinCryptoVaultDatabaseConstants.KEY_MAINTENANCE_MONITOR_THRESHOLD_COLUMN_NAME, currentThreshold);

        try {
            getDatabaseTable(AssetsOverBitcoinCryptoVaultDatabaseConstants.KEY_MAINTENANCE_MONITOR_TABLE_NAME).updateRecord(recordToUpdate);
        } catch (CantUpdateRecordException e) {
            /**
             * I will create the output meessage
             */
            StringBuilder outputMessage = new StringBuilder("there was an error updating an existing record from " );
            outputMessage.append(AssetsOverBitcoinCryptoVaultDatabaseConstants.KEY_MAINTENANCE_MONITOR_TABLE_NAME);
            outputMessage.append(System.lineSeparator());
            outputMessage.append("The record to insert is: ");
            outputMessage.append(XMLParser.parseObject(recordToUpdate));

            throw new CantExecuteDatabaseOperationException(
                    CantExecuteDatabaseOperationException.DEFAULT_MESSAGE,
                    e,
                    outputMessage.toString(),
                    "Database issue");
        }
    }

    /**
     * Sets the new value of how many keys have been generated by the Key Maintainer
     * @param accountId the account id
     * @param value the amount of keys generated. This value accumulates to the one that existed.
     * @throws CantExecuteDatabaseOperationException
     */
    public void setGeneratedKeysValue(int accountId, int value) throws CantExecuteDatabaseOperationException{
        DatabaseTable databaseTable = getDatabaseTable(AssetsOverBitcoinCryptoVaultDatabaseConstants.KEY_MAINTENANCE_TABLE_NAME);

        /**
         * first I see if we already have records for this account by setting a filter and getting the values
         */
        databaseTable.addStringFilter(AssetsOverBitcoinCryptoVaultDatabaseConstants.KEY_MAINTENANCE_ACCOUNT_ID_COLUMN_NAME, String.valueOf(accountId), DatabaseFilterType.EQUAL);
        try {
            databaseTable.loadToMemory();
        } catch (CantLoadTableToMemoryException e) {
            throwLoadToMemoryException(e, databaseTable.getTableName());
        }

        /**
         * I will insert or update the record
         */
        DatabaseTableRecord record = null;
        try{
            if (databaseTable.getRecords().size() == 0){
                //insert
                record = databaseTable.getEmptyRecord();
                record.setIntegerValue(AssetsOverBitcoinCryptoVaultDatabaseConstants.KEY_MAINTENANCE_ACCOUNT_ID_COLUMN_NAME, accountId);
                record.setIntegerValue(AssetsOverBitcoinCryptoVaultDatabaseConstants.KEY_MAINTENANCE_GENERATED_KEYS_COLUMN_NAME, value);
                record.setIntegerValue(AssetsOverBitcoinCryptoVaultDatabaseConstants.KEY_MAINTENANCE_USED_KEYS_COLUMN_NAME, 0);
                databaseTable.insertRecord(record);
            } else {
                //update
                record = databaseTable.getRecords().get(0);
                record.setIntegerValue(AssetsOverBitcoinCryptoVaultDatabaseConstants.KEY_MAINTENANCE_GENERATED_KEYS_COLUMN_NAME, value);
                databaseTable.updateRecord(record);
            }
        } catch (CantInsertRecordException | CantUpdateRecordException e) {
            StringBuilder outputMessage = new StringBuilder("There was an error inserting or updating the following table: ");
            outputMessage.append(databaseTable.getTableName());
            outputMessage.append(System.lineSeparator());
            outputMessage.append("The record is:");
            outputMessage.append(System.lineSeparator());
            outputMessage.append(XMLParser.parseObject(record));
        }
    }


    /**
     * Gets the current ammount of generated keys from the database for the specified account
     * @param accountId
     * @return
     * @throws CantExecuteDatabaseOperationException
     */
    public int getCurrentGeneratedKeys(int accountId) throws CantExecuteDatabaseOperationException{
        DatabaseTable databaseTable = getDatabaseTable(AssetsOverBitcoinCryptoVaultDatabaseConstants.KEY_MAINTENANCE_TABLE_NAME);
        databaseTable.addStringFilter(AssetsOverBitcoinCryptoVaultDatabaseConstants.KEY_MAINTENANCE_ACCOUNT_ID_COLUMN_NAME, String.valueOf(accountId), DatabaseFilterType.EQUAL);
        try {
            databaseTable.loadToMemory();
        } catch (CantLoadTableToMemoryException e) {
            throwLoadToMemoryException(e, databaseTable.getTableName());
        }

        if (databaseTable.getRecords().size() == 0)
            return 1;
        else
            return databaseTable.getRecords().get(0).getIntegerValue(AssetsOverBitcoinCryptoVaultDatabaseConstants.KEY_MAINTENANCE_GENERATED_KEYS_COLUMN_NAME);
    }

    /**
     * Gets the current ammount of used Keys from the database for the specified account
     * @param accountId
     * @return
     * @throws CantExecuteDatabaseOperationException
     */
    public int getCurrentUsedKeys(int accountId) throws CantExecuteDatabaseOperationException{
        DatabaseTable databaseTable = getDatabaseTable(AssetsOverBitcoinCryptoVaultDatabaseConstants.KEY_MAINTENANCE_TABLE_NAME);
        databaseTable.addStringFilter(AssetsOverBitcoinCryptoVaultDatabaseConstants.KEY_MAINTENANCE_ACCOUNT_ID_COLUMN_NAME, String.valueOf(accountId), DatabaseFilterType.EQUAL);
        try {
            databaseTable.loadToMemory();
        } catch (CantLoadTableToMemoryException e) {
            throwLoadToMemoryException(e, databaseTable.getTableName());
        }

        if (databaseTable.getRecords().size() == 0)
            return 1;
        else
            return databaseTable.getRecords().get(0).getIntegerValue(AssetsOverBitcoinCryptoVaultDatabaseConstants.KEY_MAINTENANCE_USED_KEYS_COLUMN_NAME);
    }

    /**
     * Sets the new depth of the current Used keys value
     * @param accountId
     * @param newValue
     * @throws CantExecuteDatabaseOperationException
     */
    public void setNewCurrentUsedKeyValue(int accountId, int newValue) throws CantExecuteDatabaseOperationException{
        DatabaseTable databaseTable = getDatabaseTable(AssetsOverBitcoinCryptoVaultDatabaseConstants.KEY_MAINTENANCE_TABLE_NAME);

        /**
         * I will check to see if I already have a value for this account so i can updated it.
         */
        databaseTable.addStringFilter(AssetsOverBitcoinCryptoVaultDatabaseConstants.KEY_MAINTENANCE_ACCOUNT_ID_COLUMN_NAME, String.valueOf(accountId), DatabaseFilterType.EQUAL);
        try {
            databaseTable.loadToMemory();
        } catch (CantLoadTableToMemoryException e) {
            throwLoadToMemoryException(e, databaseTable.getTableName());
        }

        DatabaseTableRecord record = null;
        try{
            if (databaseTable.getRecords().size() == 0){
                // I will insert the new value
                record = databaseTable.getEmptyRecord();
                record.setIntegerValue(AssetsOverBitcoinCryptoVaultDatabaseConstants.KEY_MAINTENANCE_ACCOUNT_ID_COLUMN_NAME, accountId);
                record.setIntegerValue(AssetsOverBitcoinCryptoVaultDatabaseConstants.KEY_MAINTENANCE_GENERATED_KEYS_COLUMN_NAME, 0);
                record.setIntegerValue(AssetsOverBitcoinCryptoVaultDatabaseConstants.KEY_MAINTENANCE_USED_KEYS_COLUMN_NAME, newValue);

                databaseTable.insertRecord(record);
            } else {
                // I will update the existing value
                record = databaseTable.getRecords().get(0);
                record.setIntegerValue(AssetsOverBitcoinCryptoVaultDatabaseConstants.KEY_MAINTENANCE_USED_KEYS_COLUMN_NAME, newValue);
                databaseTable.updateRecord(record);
            }
        } catch (CantInsertRecordException | CantUpdateRecordException e) {
            StringBuilder outputMessage = new StringBuilder("There was an error inserting or updating the key depth value in the database.");
            outputMessage.append(System.lineSeparator());
            outputMessage.append("The record is:");
            outputMessage.append(System.lineSeparator());
            outputMessage.append(XMLParser.parseObject(record));

            throw new CantExecuteDatabaseOperationException(CantExecuteDatabaseOperationException.DEFAULT_MESSAGE, e, outputMessage.toString(), "database issue");
        }
    }

    /**
     * Sets the active network type that the Bitcoin Network will need to listen too.
     * Network types are MainNet, TestNet and RegTest
     * @param blockchainNetworkType
     * @throws CantExecuteDatabaseOperationException
     */
    public void setActiveNetworkType(BlockchainNetworkType blockchainNetworkType) throws CantExecuteDatabaseOperationException{
        DatabaseTable databaseTable = getDatabaseTable(AssetsOverBitcoinCryptoVaultDatabaseConstants.ACTIVE_NETWORKS_TABLE_NAME);

        /**
         * I will check to see if I already have a value for this account so i can updated it.
         */
        databaseTable.addStringFilter(AssetsOverBitcoinCryptoVaultDatabaseConstants.ACTIVE_NETWORKS_NETWORKTYPE_COLUMN_NAME, blockchainNetworkType.getCode(), DatabaseFilterType.EQUAL);
        try {
            databaseTable.loadToMemory();
        } catch (CantLoadTableToMemoryException e) {
            throwLoadToMemoryException(e, databaseTable.getTableName());
        }
        DatabaseTableRecord record = null;
        String date = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        try{
            if (databaseTable.getRecords().size() == 0){
                // I will insert the new value
                record = databaseTable.getEmptyRecord();
                record.setStringValue(AssetsOverBitcoinCryptoVaultDatabaseConstants.ACTIVE_NETWORKS_NETWORKTYPE_COLUMN_NAME, blockchainNetworkType.getCode());
                record.setStringValue(AssetsOverBitcoinCryptoVaultDatabaseConstants.ACTIVE_NETWORKS_ACTIVATION_DATE_COLUMN_NAME, date);
                databaseTable.insertRecord(record);
            } else {
                // I will update the existing value
                record = databaseTable.getRecords().get(0);
                record.setStringValue(AssetsOverBitcoinCryptoVaultDatabaseConstants.ACTIVE_NETWORKS_ACTIVATION_DATE_COLUMN_NAME, date);
                databaseTable.updateRecord(record);
            }
        } catch (CantInsertRecordException | CantUpdateRecordException e) {
            StringBuilder outputMessage = new StringBuilder("There was an error inserting or updating the network type in the database.");
            outputMessage.append(System.lineSeparator());
            outputMessage.append("The record is:");
            outputMessage.append(System.lineSeparator());
            outputMessage.append(XMLParser.parseObject(record));

            throw new CantExecuteDatabaseOperationException(CantExecuteDatabaseOperationException.DEFAULT_MESSAGE, e, outputMessage.toString(), "database issue");
        }
    }

    /**
     * gets the list of Active network Types activated in the platform
     * @return
     * @throws CantExecuteDatabaseOperationException
     */
    public List<BlockchainNetworkType> getActiveNetworkTypes() throws CantExecuteDatabaseOperationException{
        List<BlockchainNetworkType> networkTypes = new ArrayList<>();
        DatabaseTable databaseTable = getDatabaseTable(AssetsOverBitcoinCryptoVaultDatabaseConstants.ACTIVE_NETWORKS_TABLE_NAME);

        /**
         * I will check to see if I already have a value for this account so i can updated it.
         */
        try {
            databaseTable.loadToMemory();
        } catch (CantLoadTableToMemoryException e) {
            throwLoadToMemoryException(e, databaseTable.getTableName());}

        /**
         * I will add all the saved values into the list to return
         */
        for (DatabaseTableRecord record : databaseTable.getRecords()){
            networkTypes.add(BlockchainNetworkType.getByCode(record.getStringValue(AssetsOverBitcoinCryptoVaultDatabaseConstants.ACTIVE_NETWORKS_NETWORKTYPE_COLUMN_NAME)));
        }

        /**
         * If there are no records saved yet, because no one request an address to set the network
         * I will manually save the default value
         */
        if (networkTypes.size() == 0){
            this.setActiveNetworkType(BlockchainNetworkType.getDefaultBlockchainNetworkType());
            networkTypes.add(BlockchainNetworkType.getDefaultBlockchainNetworkType());
        }

        return networkTypes;
    }

    /**
     * Insert new public keys into the detailed monitor table
     * @param accountId
     * @param keys
     * @throws CantExecuteDatabaseOperationException
     */
    public void updateDetailMaintainerStats(int accountId, List<ECKey> keys, int currentGeneratedKeys) throws CantExecuteDatabaseOperationException {
        /**
         * If we are not allowed to save detailed information then we will exit.
         */
        if (!VaultKeyMaintenanceParameters.STORE_DETAILED_KEY_INFORMATION)
            return;

        DatabaseTable databaseTable = database.getTable(AssetsOverBitcoinCryptoVaultDatabaseConstants.KEY_MAINTENANCE_DETAIL_TABLE_NAME);
        DatabaseTransaction transaction = database.newTransaction();



        /**
         * I will insert each key. Since I don't want to repeat inserting keys, I will only insert the keys
         * which position is after currentGeneratedKeys value
         */
        int i=1;
        for (ECKey key : keys){
            if (i >= currentGeneratedKeys){
                DatabaseTableRecord record = databaseTable.getEmptyRecord();
                record.setIntegerValue(AssetsOverBitcoinCryptoVaultDatabaseConstants.KEY_MAINTENANCE_DETAIL_ACCOUNT_ID_COLUMN_NAME, accountId);
                record.setIntegerValue(AssetsOverBitcoinCryptoVaultDatabaseConstants.KEY_MAINTENANCE_DETAIL_KEY_DEPTH_COLUMN_NAME, i);
                record.setStringValue(AssetsOverBitcoinCryptoVaultDatabaseConstants.KEY_MAINTENANCE_DETAIL_PUBLIC_KEY_COLUMN_NAME, key.getPublicKeyAsHex());
                transaction.addRecordToInsert(databaseTable, record);
            }
            i++;
        }

        /**
         * once I collected all records, I will insert them in a single transaction
         */
        try {
            database.executeTransaction(transaction);
        } catch (DatabaseTransactionFailedException e) {
            throw new CantExecuteDatabaseOperationException(CantExecuteDatabaseOperationException.DEFAULT_MESSAGE, e, "error inserting records in transaction.", null);
        }
    }

    /**
     * Updates the Key detailed table for this account and key, with the passed addres
     * @param hierarchyAccountId
     * @param ecKey
     * @param cryptoAddress
     * @param blockchainNetworkType
     */
    public void updateKeyDetailedStatsWithNewAddress(int hierarchyAccountId, ECKey ecKey, CryptoAddress cryptoAddress, BlockchainNetworkType blockchainNetworkType) throws CantExecuteDatabaseOperationException, UnexpectedResultReturnedFromDatabaseException {
        /**
         * If we are not allowed to save detailed information then we will exit
         */
        if (!VaultKeyMaintenanceParameters.STORE_DETAILED_KEY_INFORMATION)
            return;

        DatabaseTable databaseTable = database.getTable(AssetsOverBitcoinCryptoVaultDatabaseConstants.KEY_MAINTENANCE_DETAIL_TABLE_NAME);
        databaseTable.addStringFilter(AssetsOverBitcoinCryptoVaultDatabaseConstants.KEY_MAINTENANCE_DETAIL_ACCOUNT_ID_COLUMN_NAME, String.valueOf(hierarchyAccountId), DatabaseFilterType.EQUAL);
        databaseTable.addStringFilter(AssetsOverBitcoinCryptoVaultDatabaseConstants.KEY_MAINTENANCE_DETAIL_PUBLIC_KEY_COLUMN_NAME, ecKey.getPublicKeyAsHex(), DatabaseFilterType.EQUAL);

        try {
            databaseTable.loadToMemory();
        } catch (CantLoadTableToMemoryException e) {
            throwLoadToMemoryException(e, databaseTable.getTableName());
        }

        if (databaseTable.getRecords().size() == 0){
            StringBuilder output = new StringBuilder("The key " + ecKey.toString());
            output.append(System.lineSeparator());
            output.append("which generated the address " + cryptoAddress.getAddress());
            output.append(System.lineSeparator());
            output.append("is not a key derived from the vault.");

            throw new UnexpectedResultReturnedFromDatabaseException(null, output.toString(), "Vault derivation miss match");
        }

        DatabaseTableRecord record = databaseTable.getRecords().get(0);
        record.setStringValue(AssetsOverBitcoinCryptoVaultDatabaseConstants.KEY_MAINTENANCE_DETAIL_ADDRESS_COLUMN_NAME, cryptoAddress.getAddress());
        record.setStringValue(AssetsOverBitcoinCryptoVaultDatabaseConstants.KEY_MAINTENANCE_DETAIL_BLOCKCHAIN_NETWORK_TYPE_COLUMN_NAME, blockchainNetworkType.getCode());
        try {
            databaseTable.updateRecord(record);
        } catch (CantUpdateRecordException e) {
            throw new CantExecuteDatabaseOperationException(CantExecuteDatabaseOperationException.DEFAULT_MESSAGE, e, "error updating record", "database issue");
        }
    }

    /**
     * Gets the next It to be used to create a new HierarchyAccountId
     * @return
     */
    public int getNextAvailableHierarchyAccountId() throws CantExecuteDatabaseOperationException{
        DatabaseTable databaseTable = database.getTable(AssetsOverBitcoinCryptoVaultDatabaseConstants.KEY_ACCOUNTS_TABLE_NAME);
        try {
            databaseTable.loadToMemory();
        } catch (CantLoadTableToMemoryException e) {
            throwLoadToMemoryException(e, databaseTable.getTableName());
        }

        /**
         * returns the next available Id to be used.
         */
        List<DatabaseTableRecord> databaseTableRecords =  databaseTable.getRecords();
        if (databaseTableRecords.isEmpty())
            return 0;
        else
        {
            int hierarchyAccountId = 0;
            for (DatabaseTableRecord record : databaseTableRecords){
                if (record.getIntegerValue(AssetsOverBitcoinCryptoVaultDatabaseConstants.KEY_ACCOUNTS_ID_COLUMN_NAME) > hierarchyAccountId)
                    hierarchyAccountId = record.getIntegerValue(AssetsOverBitcoinCryptoVaultDatabaseConstants.KEY_ACCOUNTS_ID_COLUMN_NAME);
            }

            return hierarchyAccountId + 1;
        }
    }


    /**
     * searches for the public key and notifies if is exists.
     * @param redeemPointPublicKey
     * @return
     */
    public boolean isExistingRedeemPoint(String redeemPointPublicKey) throws CantExecuteDatabaseOperationException{
        DatabaseTable databaseTable = database.getTable(AssetsOverBitcoinCryptoVaultDatabaseConstants.KEY_ACCOUNTS_TABLE_NAME);
        databaseTable.addStringFilter(AssetsOverBitcoinCryptoVaultDatabaseConstants.KEY_ACCOUNTS_DESCRIPTION_COLUMN_NAME, redeemPointPublicKey, DatabaseFilterType.EQUAL);

        try {
            databaseTable.loadToMemory();
        } catch (CantLoadTableToMemoryException e) {
            throwLoadToMemoryException(e, databaseTable.getTableName());
        }

        return !databaseTable.getRecords().isEmpty();
    }

    /**
     * sets and thorws the error when we coundl't load a table into memory
     * @param e
     * @param tableName
     * @throws CantExecuteDatabaseOperationException
     */
    private void throwLoadToMemoryException(Exception e, String tableName) throws CantExecuteDatabaseOperationException{
        String outputMessage = "There was an error loading into memory table " + tableName + ".";
        throw new CantExecuteDatabaseOperationException(CantExecuteDatabaseOperationException.DEFAULT_MESSAGE, e, outputMessage, "Database error.");
    }

    /**
     * Will load an existing Hierarchy Account
     * @param publicKey
     * @return
     */
    public HierarchyAccount getHierarchyAccount(String publicKey) throws CantExecuteDatabaseOperationException {
        DatabaseTable databaseTable = database.getTable(AssetsOverBitcoinCryptoVaultDatabaseConstants.KEY_ACCOUNTS_TABLE_NAME);
        databaseTable.addStringFilter(AssetsOverBitcoinCryptoVaultDatabaseConstants.KEY_ACCOUNTS_DESCRIPTION_COLUMN_NAME, publicKey, DatabaseFilterType.EQUAL);

        try {
            databaseTable.loadToMemory();
        } catch (CantLoadTableToMemoryException e) {
            throwLoadToMemoryException(e, databaseTable.getTableName());
        }

        if (databaseTable.getRecords().size() != 1)
            throw new CantExecuteDatabaseOperationException(CantExecuteDatabaseOperationException.DEFAULT_MESSAGE, null, "inconsistent data found in Key_Accounts table.", "more than one Hierarchy Account stored.");

        DatabaseTableRecord record = databaseTable.getRecords().get(0);


        /**
         * Gets the records and forms the HierarchyAccount class.
         */
        int id = record.getIntegerValue(AssetsOverBitcoinCryptoVaultDatabaseConstants.KEY_ACCOUNTS_ID_COLUMN_NAME);
        HierarchyAccountType hierarchyAccountType=null;
        try {
            hierarchyAccountType = HierarchyAccountType.getByCode(record.getStringValue(AssetsOverBitcoinCryptoVaultDatabaseConstants.KEY_ACCOUNTS_TYPE_COLUMN_NAME));
        } catch (InvalidParameterException e) {
            hierarchyAccountType = HierarchyAccountType.REDEEMPOINT_ACCOUNT;
        }

        HierarchyAccount hierarchyAccount = new HierarchyAccount(id, publicKey, hierarchyAccountType);
        return hierarchyAccount;
    }

    @Override
    public int getPublicKeyPosition(String address) throws CantExecuteDatabaseOperationException {
        DatabaseTable databaseTable = database.getTable(AssetsOverBitcoinCryptoVaultDatabaseConstants.KEY_MAINTENANCE_DETAIL_TABLE_NAME);
        databaseTable.addStringFilter(AssetsOverBitcoinCryptoVaultDatabaseConstants.KEY_MAINTENANCE_DETAIL_ADDRESS_COLUMN_NAME, address, DatabaseFilterType.EQUAL);

        try {
            databaseTable.loadToMemory();
        } catch (CantLoadTableToMemoryException e) {
            throwLoadToMemoryException(e, databaseTable.getTableName());
        }

        List<DatabaseTableRecord> databaseTableRecords = databaseTable.getRecords();
        if (databaseTableRecords.size() != 0){
            return databaseTableRecords.get(0).getIntegerValue(AssetsOverBitcoinCryptoVaultDatabaseConstants.KEY_MAINTENANCE_DETAIL_KEY_DEPTH_COLUMN_NAME);
        } else
            return 0;
    }
}
