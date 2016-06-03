package com.bitdubai.fermat_ccp_plugin.layer.actor.intra_user.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.DealsWithPluginIdentity;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_ccp_plugin.layer.actor.intra_user.developer.bitdubai.version_1.exceptions.CantInitializeIntraWalletUserActorDatabaseException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by natalia on 13/08/15.
 */
public class IntraWalletUserActorDeveloperDatabaseFactory {//implements DealsWithPluginDatabaseSystem, DealsWithPluginIdentity {

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
    public IntraWalletUserActorDeveloperDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
    }

    /**
     * This method open or creates the database i'll be working with
     *
     * @throws CantInitializeIntraWalletUserActorDatabaseException
     */
    public void initializeDatabase() throws CantInitializeIntraWalletUserActorDatabaseException {
        try {

             /*
              * Open new database connection
              */
            database = this.pluginDatabaseSystem.openDatabase(pluginId, IntraWalletUserActorDatabaseConstants.INTRA_WALLET_USER_DATABASE_NAME);
            database.closeDatabase();

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {

             /*
              * The database exists but cannot be open. I can not handle this situation.
              */
            throw new CantInitializeIntraWalletUserActorDatabaseException(cantOpenDatabaseException.getMessage());

        } catch (DatabaseNotFoundException e) {

             /*
              * The database no exist may be the first time the plugin is running on this device,
              * We need to create the new database
              */
            IntraWalletUserActorDatabaseFactory intraUserIdentityDatabaseFactory = new IntraWalletUserActorDatabaseFactory(pluginDatabaseSystem);

            try {
                  /*
                   * We create the new database
                   */
                database = intraUserIdentityDatabaseFactory.createDatabase(pluginId, IntraWalletUserActorDatabaseConstants.INTRA_WALLET_USER_DATABASE_NAME);
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                  /*
                   * The database cannot be created. I can not handle this situation.
                   */
                throw new CantInitializeIntraWalletUserActorDatabaseException(cantCreateDatabaseException.getMessage());
            }
        }
        catch (Exception e) {

             /*
              * The database exists but cannot be open. I can not handle this situation.
              */
            throw new CantInitializeIntraWalletUserActorDatabaseException(e.getMessage());

        }
    }


    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        /**
         * I only have one database on my plugin. I will return its name.
         */
        List<DeveloperDatabase> databases = new ArrayList<DeveloperDatabase>();
        databases.add(developerObjectFactory.getNewDeveloperDatabase(IntraWalletUserActorDatabaseConstants.INTRA_WALLET_USER_DATABASE_NAME, this.pluginId.toString()));
        return databases;
    }


    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory) {
        List<DeveloperDatabaseTable> tables = new ArrayList<DeveloperDatabaseTable>();

        /**
         * Table Intra User columns.
         */
        List<String> intraUserColumns = new ArrayList<String>();

        intraUserColumns.add(IntraWalletUserActorDatabaseConstants.INTRA_WALLET_USER_PUBLIC_KEY_COLUMN_NAME);
        intraUserColumns.add(IntraWalletUserActorDatabaseConstants.INTRA_WALLET_USER_NAME_COLUMN_NAME);
        intraUserColumns.add(IntraWalletUserActorDatabaseConstants.INTRA_WALLET_USER_LINKED_IDENTITY_PUBLIC_KEY_COLUMN_NAME);
        intraUserColumns.add(IntraWalletUserActorDatabaseConstants.INTRA_WALLET_USER_CONTACT_STATE_COLUMN_NAME);
        intraUserColumns.add(IntraWalletUserActorDatabaseConstants.INTRA_WALLET_USER_REGISTRATION_DATE_COLUMN_NAME);
        intraUserColumns.add(IntraWalletUserActorDatabaseConstants.INTRA_WALLET_USER_MODIFIED_DATE_COLUMN_NAME);
        intraUserColumns.add(IntraWalletUserActorDatabaseConstants.INTRA_WALLET_USER_PHRASE_COLUMN_NAME);


        /**
         * Table Intra User addition.
         */
        DeveloperDatabaseTable intraUserTable = developerObjectFactory.getNewDeveloperDatabaseTable(IntraWalletUserActorDatabaseConstants.INTRA_WALLET_USER_TABLE_NAME, intraUserColumns);
        tables.add(intraUserTable);



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
            for (DatabaseTableRecord row: records){
                List<String> developerRow = new ArrayList<String>();
                /**
                 * for each row in the table list
                 */
                for (DatabaseRecord field : row.getValues()){
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
        } catch (Exception e){
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