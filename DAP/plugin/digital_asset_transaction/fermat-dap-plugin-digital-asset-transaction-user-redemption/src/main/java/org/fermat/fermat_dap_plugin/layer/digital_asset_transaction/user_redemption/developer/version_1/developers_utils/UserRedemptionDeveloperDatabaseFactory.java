package org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.user_redemption.developer.version_1.developers_utils;

import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;

import org.fermat.fermat_dap_api.layer.dap_transaction.asset_distribution.exceptions.CantInitializeAssetDistributionTransactionDatabaseException;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.user_redemption.developer.version_1.structure.database.UserRedemptionDatabaseConstants;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.user_redemption.developer.version_1.structure.database.UserRedemptionDatabaseFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 01/11/15.
 */
public class UserRedemptionDeveloperDatabaseFactory {//implements DealsWithPluginDatabaseSystem, DealsWithPluginIdentity {

    /**
     * DealsWithPluginDatabaseSystem Interface member variables.
     */
    PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * DealsWithPluginIdentity Interface member variables.
     */
    UUID pluginId;


    Database database;

    /**
     * Constructor
     *
     * @param pluginDatabaseSystem
     * @param pluginId
     */
    public UserRedemptionDeveloperDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
    }

    /**
     * This method open or creates the database i'll be working with
     *
     * @throws CantInitializeAssetDistributionTransactionDatabaseException
     */
    public void initializeDatabase() throws CantInitializeAssetDistributionTransactionDatabaseException {
        try {

             /*
              * Open new database connection
              */
            database = this.pluginDatabaseSystem.openDatabase(pluginId, pluginId.toString());

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {

             /*
              * The database exists but cannot be open. I can not handle this situation.
              */
            throw new CantInitializeAssetDistributionTransactionDatabaseException(cantOpenDatabaseException.getMessage());

        } catch (DatabaseNotFoundException e) {

             /*
              * The database no exist may be the first time the plugin is running on this device,
              * We need to create the new database
              */
            UserRedemptionDatabaseFactory assetDistributionDatabaseFactory = new UserRedemptionDatabaseFactory(pluginDatabaseSystem);

            try {
                  /*
                   * We create the new database
                   */
                database = assetDistributionDatabaseFactory.createDatabase(pluginId, pluginId.toString());
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                  /*
                   * The database cannot be created. I can not handle this situation.
                   */
                throw new CantInitializeAssetDistributionTransactionDatabaseException(cantCreateDatabaseException.getMessage());
            }
        }
    }


    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        /**
         * I only have one database on my plugin. I will return its name.
         */
        List<DeveloperDatabase> databases = new ArrayList<DeveloperDatabase>();
        databases.add(developerObjectFactory.getNewDeveloperDatabase(UserRedemptionDatabaseConstants.USER_REDEMPTION_DATABASE, this.pluginId.toString()));
        return databases;
    }


    public static List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory) {
        List<DeveloperDatabaseTable> tables = new ArrayList<DeveloperDatabaseTable>();

        /**
         * Table Asset Distribution columns.
         */
        List<String> assetDistributionColumns = new ArrayList<String>();

        assetDistributionColumns.add(UserRedemptionDatabaseConstants.USER_REDEMPTION_GENESIS_TRANSACTION_COLUMN_NAME);
        assetDistributionColumns.add(UserRedemptionDatabaseConstants.USER_REDEMPTION_DIGITAL_ASSET_HASH_COLUMN_NAME);
        assetDistributionColumns.add(UserRedemptionDatabaseConstants.USER_REDEMPTION_ACTOR_REDEEM_POINT_PUBLIC_KEY_COLUMN_NAME);
        assetDistributionColumns.add(UserRedemptionDatabaseConstants.USER_REDEMPTION_DIGITAL_ASSET_STORAGE_LOCAL_PATH_COLUMN_NAME);
        assetDistributionColumns.add(UserRedemptionDatabaseConstants.USER_REDEMPTION_REDEMPTION_STATUS_COLUMN_NAME);
        assetDistributionColumns.add(UserRedemptionDatabaseConstants.USER_REDEMPTION_CRYPTO_STATUS_COLUMN_NAME);
        assetDistributionColumns.add(UserRedemptionDatabaseConstants.USER_REDEMPTION_PROTOCOL_STATUS_COLUMN_NAME);
        assetDistributionColumns.add(UserRedemptionDatabaseConstants.USER_REDEMPTION_ACTOR_REDEEM_POINT_BITCOIN_ADDRESS_COLUMN_NAME);
        assetDistributionColumns.add(UserRedemptionDatabaseConstants.USER_REDEMPTION_REDEMPTION_ID_COLUMN_NAME);
        /**
         * Table Asset Distribution addition.
         */
        DeveloperDatabaseTable assetDistributionTable = developerObjectFactory.getNewDeveloperDatabaseTable(UserRedemptionDatabaseConstants.USER_REDEMPTION_TABLE_NAME, assetDistributionColumns);
        tables.add(assetDistributionTable);

        /**
         * Events Recorder table
         * */
        List<String> eventsRecorderColumns = new ArrayList<String>();

        eventsRecorderColumns.add(UserRedemptionDatabaseConstants.USER_REDEMPTION_EVENTS_RECORDED_ID_COLUMN_NAME);
        eventsRecorderColumns.add(UserRedemptionDatabaseConstants.USER_REDEMPTION_EVENTS_RECORDED_EVENT_COLUMN_NAME);
        eventsRecorderColumns.add(UserRedemptionDatabaseConstants.USER_REDEMPTION_EVENTS_RECORDED_SOURCE_COLUMN_NAME);
        eventsRecorderColumns.add(UserRedemptionDatabaseConstants.USER_REDEMPTION_EVENTS_RECORDED_STATUS_COLUMN_NAME);
        eventsRecorderColumns.add(UserRedemptionDatabaseConstants.USER_REDEMPTION_EVENTS_RECORDED_TIMESTAMP_COLUMN_NAME);

        DeveloperDatabaseTable eventsRecorderTable = developerObjectFactory.getNewDeveloperDatabaseTable(UserRedemptionDatabaseConstants.USER_REDEMPTION_EVENTS_RECORDED_TABLE_NAME, eventsRecorderColumns);
        tables.add(eventsRecorderTable);

        /**
         * Asset Distribution table
         */
        List<String> assetDeliveringColumns = new ArrayList<String>();

        assetDeliveringColumns.add(UserRedemptionDatabaseConstants.USER_REDEMPTION_DELIVERING_TRANSACTION_ID_COLUMN_NAME);
        assetDeliveringColumns.add(UserRedemptionDatabaseConstants.USER_REDEMPTION_DELIVERING_GENESIS_TRANSACTION_COLUMN_NAME);
        assetDeliveringColumns.add(UserRedemptionDatabaseConstants.USER_REDEMPTION_DELIVERING_NETWORK_TYPE_COLUMN_NAME);
        assetDeliveringColumns.add(UserRedemptionDatabaseConstants.USER_REDEMPTION_DELIVERING_ASSET_PUBLICKEY_COLUMN_NAME);
        assetDeliveringColumns.add(UserRedemptionDatabaseConstants.USER_REDEMPTION_DELIVERING_REPO_PUBLICKEY_COLUMN_NAME);
        assetDeliveringColumns.add(UserRedemptionDatabaseConstants.USER_REDEMPTION_DELIVERING_START_TIME_COLUMN_NAME);
        assetDeliveringColumns.add(UserRedemptionDatabaseConstants.USER_REDEMPTION_DELIVERING_TIMEOUT_COLUMN_NAME);
        assetDeliveringColumns.add(UserRedemptionDatabaseConstants.USER_REDEMPTION_DELIVERING_STATE_COLUMN_NAME);
        assetDeliveringColumns.add(UserRedemptionDatabaseConstants.USER_REDEMPTION_DELIVERING_SENT_GENESISTX_COLUMN_NAME);
        assetDeliveringColumns.add(UserRedemptionDatabaseConstants.USER_REDEMPTION_DELIVERING_ATTEMPT_NUMBER_COLUMN_NAME);

        DeveloperDatabaseTable assetDeliveringTable = developerObjectFactory.getNewDeveloperDatabaseTable(UserRedemptionDatabaseConstants.USER_REDEMPTION_DELIVERING_TABLE_NAME, assetDeliveringColumns);
        tables.add(assetDeliveringTable);

        return tables;
    }


    public static List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, Database database, DeveloperDatabaseTable developerDatabaseTable) {
        /**
         * Will get the records for the given table
         */
        List<DeveloperDatabaseTableRecord> returnedRecords = new ArrayList<DeveloperDatabaseTableRecord>();
        /**
         * I load the passed table name from the SQLite database.
         */
        DatabaseTable selectedTable = database.getTable(developerDatabaseTable.getName());
        try {
            selectedTable.loadToMemory();
            List<DatabaseTableRecord> records = selectedTable.getRecords();
            for (DatabaseTableRecord row : records) {
                List<String> developerRow = new ArrayList<String>();
                /**
                 * for each row in the table list
                 */
                for (DatabaseRecord field : row.getValues()) {
                    /**
                     * I get each row and save them into a List<String>
                     */
                    developerRow.add(field.getValue());
                }
                /**
                 * I create the Developer Database record
                 */
                returnedRecords.add(developerObjectFactory.getNewDeveloperDatabaseTableRecord(developerRow));
            }
            /**
             * return the list of DeveloperRecords for the passed table.
             */
        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            /**
             * if there was an error, I will returned an empty list.
             */
            database.closeDatabase();
            return returnedRecords;
        } catch (Exception e) {
            database.closeDatabase();
            return returnedRecords;
        }
        database.closeDatabase();
        return returnedRecords;
    }
}
