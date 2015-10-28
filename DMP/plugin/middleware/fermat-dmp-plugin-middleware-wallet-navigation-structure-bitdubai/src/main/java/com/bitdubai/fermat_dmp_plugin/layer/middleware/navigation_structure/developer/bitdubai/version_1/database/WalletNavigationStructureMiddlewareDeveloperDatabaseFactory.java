package com.bitdubai.fermat_dmp_plugin.layer.middleware.navigation_structure.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.DealsWithPluginIdentity;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
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
import com.bitdubai.fermat_dmp_plugin.layer.middleware.navigation_structure.developer.bitdubai.version_1.exceptions.CantDeliverDatabaseException;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.navigation_structure.developer.bitdubai.version_1.exceptions.CantInitializeWalletNavigationStructureMiddlewareDatabaseException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedPluginExceptionSeverity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Manuel Perez on 12/08/15.
 */
public class WalletNavigationStructureMiddlewareDeveloperDatabaseFactory implements DealsWithPluginDatabaseSystem, DealsWithPluginIdentity {

    /**
     * DealsWithPluginDatabaseSystem Interface member variables.
     */
    PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * DealsWithPluginIdentity Interface member variables.
     */
    UUID pluginId;

    ErrorManager errorManager;

    Database database;

    public WalletNavigationStructureMiddlewareDeveloperDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {

        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;

    }

    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {

        List<DeveloperDatabase> databases = new ArrayList<DeveloperDatabase>();
        try {

            databases.add(developerObjectFactory.getNewDeveloperDatabase("Wallet Navigation Structure", this.pluginId.toString()));

        } catch (Exception exception) {
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_NAVIGATION_STRUCTURE_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);

        }

        return databases;

    }

    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory) {

        List<DeveloperDatabaseTable> tables = new ArrayList<DeveloperDatabaseTable>();

        List<String> projectColumns = new ArrayList<String>();

        try {

            projectColumns.add(WalletNavigationStructureMiddlewareDatabaseConstants.WALLET_NAVIGATION_STRUCTURE_PUBLIC_KEY);
            projectColumns.add(WalletNavigationStructureMiddlewareDatabaseConstants.WALLET_NAVIGATION_STRUCTURE_ACTIVITY);
            projectColumns.add(WalletNavigationStructureMiddlewareDatabaseConstants.WALLET_NAVIGATION_STRUCTURE_START_ACTIVITY);
            projectColumns.add(WalletNavigationStructureMiddlewareDatabaseConstants.WALLET_NAVIGATION_STRUCTURE_LAST_ACTIVITY);

            DeveloperDatabaseTable projectTable = developerObjectFactory.getNewDeveloperDatabaseTable(WalletNavigationStructureMiddlewareDatabaseConstants.WALLET_NAVIGATION_STRUCTURE_TABLE_NAME, projectColumns);
            tables.add(projectTable);

        } catch (Exception exception) {

            FermatException fermatException = new CantDeliverDatabaseException(CantDeliverDatabaseException.DEFAULT_MESSAGE, FermatException.wrapException(exception), "WalletId: " + pluginId.toString(), "Check the cause");
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_NAVIGATION_STRUCTURE_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, fermatException);

        }

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

    public void initializeDatabase() throws CantInitializeWalletNavigationStructureMiddlewareDatabaseException {

        try {

            database = this.pluginDatabaseSystem.openDatabase(pluginId, pluginId.toString());
            database.closeDatabase();

        } catch (CantOpenDatabaseException exception) {

            throw new CantInitializeWalletNavigationStructureMiddlewareDatabaseException(CantOpenDatabaseException.DEFAULT_MESSAGE, exception, "Trying to open the database", "Check the cause");

        } catch (DatabaseNotFoundException exception) {

            WalletNavigationStructureMiddlewareDatabaseFactory walletNavigationStructureMiddlewareDatabaseFactory = new WalletNavigationStructureMiddlewareDatabaseFactory(this.pluginDatabaseSystem);
            try {

                this.database = walletNavigationStructureMiddlewareDatabaseFactory.createDatabase(pluginId, pluginId.toString());
                database.closeDatabase();

            } catch (CantCreateDatabaseException cantCreateDatabaseException) {

                throw new CantInitializeWalletNavigationStructureMiddlewareDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateDatabaseException, "Trying to create the database", "Check the cause");

            }

        } catch (Exception exception) {

            throw new CantInitializeWalletNavigationStructureMiddlewareDatabaseException(CantInitializeWalletNavigationStructureMiddlewareDatabaseException.DEFAULT_MESSAGE, FermatException.wrapException(exception), "", "Check the cause");

        }

    }

    public void setErrorManager(ErrorManager errorManager) {

        this.errorManager = errorManager;

    }

    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    @Override
    public void setPluginId(UUID pluginId) {
        this.pluginId = pluginId;
    }

}
