package com.bitdubai.fermat_cbp_plugin.layer.identity.crypto_broker.developer.bitdubai.version_1.database;

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
import com.bitdubai.fermat_cbp_plugin.layer.identity.crypto_broker.developer.bitdubai.version_1.exceptions.CantInitializeCryptoBrokerIdentityDatabaseException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_cbp_plugin.layer.identity.crypto_broker.developer.bitdubai.version_1.database.CryptoBrokerIdentityDeveloperDatabaseFactory</code> have
 * contains the methods that the Developer Database Tools uses to show the information.
 * <p/>
 * <p/>
 * Created by Jorge Gonzalez - (jorgeejgonzalez@gmail.com) on 28/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */

public class CryptoBrokerIdentityDeveloperDatabaseFactory {//implements DealsWithPluginDatabaseSystem, DealsWithPluginIdentity {

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
    public CryptoBrokerIdentityDeveloperDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
    }

    /**
     * This method open or creates the database i'll be working with
     *
     * @throws CantInitializeCryptoBrokerIdentityDatabaseException
     */
    public void initializeDatabase() throws CantInitializeCryptoBrokerIdentityDatabaseException {
        try {

             /*
              * Open new database connection
              */
            database = this.pluginDatabaseSystem.openDatabase(pluginId, CryptoBrokerIdentityDatabaseConstants.DATABASE_NAME);

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {

             /*
              * The database exists but cannot be open. I can not handle this situation.
              */
            throw new CantInitializeCryptoBrokerIdentityDatabaseException(cantOpenDatabaseException.getMessage());

        } catch (DatabaseNotFoundException e) {

             /*
              * The database no exist may be the first time the plugin is running on this device,
              * We need to create the new database
              */
            CryptoBrokerIdentityDatabaseFactory cryptoBrokerIdentityDatabaseFactory = new CryptoBrokerIdentityDatabaseFactory(pluginDatabaseSystem);

            try {
                  /*
                   * We create the new database
                   */
                database = cryptoBrokerIdentityDatabaseFactory.createDatabase(pluginId, CryptoBrokerIdentityDatabaseConstants.DATABASE_NAME);
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                  /*
                   * The database cannot be created. I can not handle this situation.
                   */
                throw new CantInitializeCryptoBrokerIdentityDatabaseException(cantCreateDatabaseException.getMessage());
            }
        }
    }


    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        /**
         * I only have one database on my plugin. I will return its name.
         */
        List<DeveloperDatabase> databases = new ArrayList<DeveloperDatabase>();
        databases.add(developerObjectFactory.getNewDeveloperDatabase(CryptoBrokerIdentityDatabaseConstants.DATABASE_NAME, this.pluginId.toString()));
        return databases;
    }


    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory) {
        List<DeveloperDatabaseTable> tables = new ArrayList<DeveloperDatabaseTable>();

        /**
         * Table Crypto Broker columns.
         */
        List<String> cryptoBrokerColumns = new ArrayList<String>();

        cryptoBrokerColumns.add(CryptoBrokerIdentityDatabaseConstants.CRYPTO_BROKER_PUBLIC_KEY_COLUMN_NAME);
        cryptoBrokerColumns.add(CryptoBrokerIdentityDatabaseConstants.CRYPTO_BROKER_ALIAS_COLUMN_NAME);
        cryptoBrokerColumns.add(CryptoBrokerIdentityDatabaseConstants.CRYPTO_BROKER_DEVICE_USER_PUBLIC_KEY_COLUMN_NAME);
        cryptoBrokerColumns.add(CryptoBrokerIdentityDatabaseConstants.CRYPTO_BROKER_EXPOSURE_LEVEL_COLUMN_NAME);
        cryptoBrokerColumns.add(CryptoBrokerIdentityDatabaseConstants.CRYPTO_BROKER_ACCURACY_COLUMN_NAME);
        cryptoBrokerColumns.add(CryptoBrokerIdentityDatabaseConstants.CRYPTO_BROKER_FRECUENCY_COLUMN_NAME);
        cryptoBrokerColumns.add(CryptoBrokerIdentityDatabaseConstants.CRYPTO_BROKER_PAYMENT_CURRENCY_COLUMN_NAME);
        cryptoBrokerColumns.add(CryptoBrokerIdentityDatabaseConstants.CRYPTO_BROKER_MERCHANDISE_CURRENCY_COLUMN_NAME);
        cryptoBrokerColumns.add(CryptoBrokerIdentityDatabaseConstants.CRYPTO_BROKER_EXTRA_TEXT_COLUMN_NAME);
        /**
         * Table Crypto Broker addition.
         */
        DeveloperDatabaseTable cryptoBrokerTable = developerObjectFactory.getNewDeveloperDatabaseTable(CryptoBrokerIdentityDatabaseConstants.CRYPTO_BROKER_TABLE_NAME, cryptoBrokerColumns);
        tables.add(cryptoBrokerTable);


        return tables;
    }


    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabaseTable developerDatabaseTable) {
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

//    @Override
//    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
//        this.pluginDatabaseSystem = pluginDatabaseSystem;
//    }
//
//    @Override
//    public void setPluginId(UUID pluginId) {
//        this.pluginId = pluginId;
//    }
}
