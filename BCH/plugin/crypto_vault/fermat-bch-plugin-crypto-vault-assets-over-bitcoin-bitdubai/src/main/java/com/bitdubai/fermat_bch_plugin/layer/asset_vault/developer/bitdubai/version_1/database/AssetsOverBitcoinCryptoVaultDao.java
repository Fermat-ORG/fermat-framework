package com.bitdubai.fermat_bch_plugin.layer.asset_vault.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_bch_plugin.layer.asset_vault.developer.bitdubai.version_1.exceptions.CantExecuteDatabaseOperationException;
import com.bitdubai.fermat_bch_plugin.layer.asset_vault.developer.bitdubai.version_1.exceptions.CantInitializeAssetsOverBitcoinCryptoVaultDatabaseException;
import com.bitdubai.fermat_bch_plugin.layer.asset_vault.developer.bitdubai.version_1.structure.HierarchyAccount;

import java.util.ArrayList;
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
public class AssetsOverBitcoinCryptoVaultDao {
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
    public List<HierarchyAccount> getHierarchyAccounts() throws CantExecuteDatabaseOperationException{
        List<HierarchyAccount> hierarchyAccounts = new ArrayList<>();
        DatabaseTable databaseTable = getDatabaseTable(AssetsOverBitcoinCryptoVaultDatabaseConstants.KEY_ACCOUNTS_TABLE_NAME);
        try {
            databaseTable.loadToMemory();
        } catch (CantLoadTableToMemoryException e) {
            throw new CantExecuteDatabaseOperationException(
                    CantExecuteDatabaseOperationException.DEFAULT_MESSAGE,
                    e,
                    "There was an error loading into memory table " +
                            AssetsOverBitcoinCryptoVaultDatabaseConstants.KEY_ACCOUNTS_TABLE_NAME,
                    "A database error.");
        }

        /**
         * Iterate each record and form the HierarchyAccount object.
         */
        for (DatabaseTableRecord record : databaseTable.getRecords()){
            HierarchyAccount hierarchyAccount = new HierarchyAccount(
                    record.getIntegerValue(AssetsOverBitcoinCryptoVaultDatabaseConstants.KEY_ACCOUNTS_ID_COLUMN_NAME),
                    record.getStringValue(AssetsOverBitcoinCryptoVaultDatabaseConstants.KEY_ACCOUNTS_DESCRIPTION_COLUMN_NAME));

            /**
             * Adds the created HierarchyAccount into the list to be returned.
             */
            hierarchyAccounts.add(hierarchyAccount);
        }

        return hierarchyAccounts;
    }
}
