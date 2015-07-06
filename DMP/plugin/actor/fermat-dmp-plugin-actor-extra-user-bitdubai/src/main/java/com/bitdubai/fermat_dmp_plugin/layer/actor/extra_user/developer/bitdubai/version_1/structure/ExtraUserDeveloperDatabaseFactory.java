package com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPlatformDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PlatformDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_api.layer.pip_user.extra_user.exceptions.CantInitializeExtraUserRegistryException;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class <code>com.bitdubai.fermat_cry_plugin.layer.crypto_module.actor_address_book.developer.bitdubai.version_1.structure.ActorAddressBookCryptoModuleDeveloperDatabaseFactory</code> have
 * <p/>
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 29/06/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */

public class ExtraUserDeveloperDatabaseFactory implements DealsWithErrors, DealsWithPlatformDatabaseSystem {

    /**
     * DealsWithErrors Interface member variables.
     */
    ErrorManager errorManager;

    /**
     * DealsWithPlatformDatabaseSystem Interface member variables.
     */
    PlatformDatabaseSystem platformDatabaseSystem;

    Database database;

    /**
     * Constructor
     *
     * @param errorManager
     * @param platformDatabaseSystem
     */
    public ExtraUserDeveloperDatabaseFactory(ErrorManager errorManager, PlatformDatabaseSystem platformDatabaseSystem) {
        this.errorManager = errorManager;
        this.platformDatabaseSystem = platformDatabaseSystem;
    }

    /**
     * This method open or creates the database i'll be working with
     *
     * @throws CantInitializeExtraUserRegistryException
     */
    public void initializeDatabase() throws CantInitializeExtraUserRegistryException {

        /**
         * I will try to open the users' database..
         */
        try {

            this.database = this.platformDatabaseSystem.openDatabase("ExtraUser");
        } catch (DatabaseNotFoundException databaseNotFoundException) {

            ExtraUserDatabaseFactory databaseFactory = new ExtraUserDatabaseFactory();
            databaseFactory.setPlatformDatabaseSystem(this.platformDatabaseSystem);
            databaseFactory.setErrorManager(this.errorManager);

            /**
             * I will create the database where I am going to store the information of this wallet.
             */

            try {

                this.database = databaseFactory.createDatabase();

            } catch (CantCreateDatabaseException cantCreateDatabaseException) {

                /**
                 * The database cannot be created. I can not handle this situation.
                 */
                throw new CantInitializeExtraUserRegistryException();
            }
        } catch (CantOpenDatabaseException cantOpenDatabaseException) {

            /**
             * The database exists but cannot be open. I can not handle this situation.
             */

            throw new CantInitializeExtraUserRegistryException();
        }
    }


    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        /**
         * I only have one database on my plugin. I will return its name.
         */
        List<DeveloperDatabase> databases = new ArrayList<>();
        databases.add(developerObjectFactory.getNewDeveloperDatabase("Extra User", "ExtraUser"));
        return databases;
    }


    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory) {
        List<DeveloperDatabaseTable> tables = new ArrayList<DeveloperDatabaseTable>();

        /**
         * extraUserTable columns
         */
        List<String> extraUserTableColumns = new ArrayList<String>();
        extraUserTableColumns.add(ExtraUserDatabaseConstants.EXTRA_USER_TABLE_ID_COLUMN_NAME);
        extraUserTableColumns.add(ExtraUserDatabaseConstants.EXTRA_USER_TABLE_NAME_COLUMN_NAME);
        extraUserTableColumns.add(ExtraUserDatabaseConstants.EXTRA_USER_TABLE_TIME_STAMP_COLUMN_NAME);

        /**
         * extraUser table
         */
        DeveloperDatabaseTable extraUserTable = developerObjectFactory.getNewDeveloperDatabaseTable(ExtraUserDatabaseConstants.EXTRA_USER_TABLE_NAME, extraUserTableColumns);
        tables.add(extraUserTable);

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
        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            /**
             * if there was an error, I will returned an empty list.
             */
            return returnedRecords;
        }

        List<DatabaseTableRecord> records = selectedTable.getRecords();
        for (DatabaseTableRecord row : records) {
            /**
             * for each row in the table list
             */
            List<String> developerRow = new ArrayList<String>();
            for (DatabaseRecord field : row.getValues()) {
                /**
                 * I get each row and save them into a List<String>
                 */
                developerRow.add(field.getValue().toString());
            }
            /**
             * I create the Developer Database record
             */
            returnedRecords.add(developerObjectFactory.getNewDeveloperDatabaseTableRecord(developerRow));
        }

        /**
         * return the list of DeveloperRecords for the passed table.
         */
        return returnedRecords;
    }

    /**
     * DealsWithErrors Interface implementation.
     */

    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }

    /**
     * DealsWithPlatformDatabaseSystem interface implementation.
     */
    @Override
    public void setPlatformDatabaseSystem(PlatformDatabaseSystem platformDatabaseSystem) {
        this.platformDatabaseSystem = platformDatabaseSystem;
    }
}
