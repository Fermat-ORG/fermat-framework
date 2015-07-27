package com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.structure;

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
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.exceptions.CantInitializeWalletFactoryMiddlewareDatabaseException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.structure.WalletFactoryMiddlewareDeveloperDatabaseFactory</code> have
 * contains the methods that the Developer Database Tools uses to show the information.
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 27/07/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */

public class WalletFactoryMiddlewareDeveloperDatabaseFactory implements DealsWithPluginDatabaseSystem, DealsWithPluginIdentity {

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
    public WalletFactoryMiddlewareDeveloperDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
    }

    /**
     * This method open or creates the database i'll be working with
     *
     * @throws CantInitializeWalletFactoryMiddlewareDatabaseException
     */
    public void initializeDatabase() throws CantInitializeWalletFactoryMiddlewareDatabaseException {
        try {

             /*
              * Open new database connection
              */
            database = this.pluginDatabaseSystem.openDatabase(pluginId, pluginId.toString());

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {

             /*
              * The database exists but cannot be open. I can not handle this situation.
              */
            throw new CantInitializeWalletFactoryMiddlewareDatabaseException(cantOpenDatabaseException.getMessage());

        } catch (DatabaseNotFoundException e) {

             /*
              * The database no exist may be the first time the plugin is running on this device,
              * We need to create the new database
              */
            WalletFactoryMiddlewareDatabaseFactory walletFactoryMiddlewareDatabaseFactory = new WalletFactoryMiddlewareDatabaseFactory(pluginDatabaseSystem);

            try {
                  /*
                   * We create the new database
                   */
                database = walletFactoryMiddlewareDatabaseFactory.createDatabase(pluginId, pluginId.toString());
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                  /*
                   * The database cannot be created. I can not handle this situation.
                   */
                throw new CantInitializeWalletFactoryMiddlewareDatabaseException(cantCreateDatabaseException.getMessage());
            }
        }
    }


    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        /**
         * I only have one database on my plugin. I will return its name.
         */
        List<DeveloperDatabase> databases = new ArrayList<DeveloperDatabase>();
        databases.add(developerObjectFactory.getNewDeveloperDatabase("WalletFactory", this.pluginId.toString()));
        return databases;
    }


    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory) {
        List<DeveloperDatabaseTable> tables = new ArrayList<DeveloperDatabaseTable>();

        /**
         * Table Project columns.
         */
        List<String> projectColumns = new ArrayList<String>();

        projectColumns.add(WalletFactoryMiddlewareDatabaseConstants.PROJECT_ID_COLUMN_NAME);
        projectColumns.add(WalletFactoryMiddlewareDatabaseConstants.PROJECT_DEVELOPER_PUBLIC_KEY_COLUMN_NAME);
        projectColumns.add(WalletFactoryMiddlewareDatabaseConstants.PROJECT_NAME_COLUMN_NAME);
        projectColumns.add(WalletFactoryMiddlewareDatabaseConstants.PROJECT_WALLET_TYPE_COLUMN_NAME);
        /**
         * Table Project addition.
         */
        DeveloperDatabaseTable projectTable = developerObjectFactory.getNewDeveloperDatabaseTable(WalletFactoryMiddlewareDatabaseConstants.PROJECT_TABLE_NAME, projectColumns);
        tables.add(projectTable);

        /**
         * Table Project Proposal columns.
         */
        List<String> projectProposalColumns = new ArrayList<String>();

        projectProposalColumns.add(WalletFactoryMiddlewareDatabaseConstants.PROJECT_PROPOSAL_ID_COLUMN_NAME);
        projectProposalColumns.add(WalletFactoryMiddlewareDatabaseConstants.PROJECT_PROPOSAL_ALIAS_COLUMN_NAME);
        projectProposalColumns.add(WalletFactoryMiddlewareDatabaseConstants.PROJECT_PROPOSAL_FACTORY_PROJECT_STATE_COLUMN_NAME);
        projectProposalColumns.add(WalletFactoryMiddlewareDatabaseConstants.PROJECT_PROPOSAL_PROJECT_ID_COLUMN_NAME);
        /**
         * Table Project Proposal addition.
         */
        DeveloperDatabaseTable projectProposalTable = developerObjectFactory.getNewDeveloperDatabaseTable(WalletFactoryMiddlewareDatabaseConstants.PROJECT_PROPOSAL_TABLE_NAME, projectProposalColumns);
        tables.add(projectProposalTable);

        /**
         * Table Project Skin columns.
         */
        List<String> projectSkinColumns = new ArrayList<String>();

        projectSkinColumns.add(WalletFactoryMiddlewareDatabaseConstants.PROJECT_SKIN_ID_COLUMN_NAME);
        projectSkinColumns.add(WalletFactoryMiddlewareDatabaseConstants.PROJECT_SKIN_NAME_COLUMN_NAME);
        projectSkinColumns.add(WalletFactoryMiddlewareDatabaseConstants.PROJECT_SKIN_DESIGNER_PUBLIC_KEY_COLUMN_NAME);
        projectSkinColumns.add(WalletFactoryMiddlewareDatabaseConstants.PROJECT_SKIN_VERSION_COLUMN_NAME);
        projectSkinColumns.add(WalletFactoryMiddlewareDatabaseConstants.PROJECT_SKIN_PROJECT_PROPOSAL_ID_COLUMN_NAME);
        /**
         * Table Project Skin addition.
         */
        DeveloperDatabaseTable projectSkinTable = developerObjectFactory.getNewDeveloperDatabaseTable(WalletFactoryMiddlewareDatabaseConstants.PROJECT_SKIN_TABLE_NAME, projectSkinColumns);
        tables.add(projectSkinTable);

        /**
         * Table Project Language columns.
         */
        List<String> projectLanguageColumns = new ArrayList<String>();

        projectLanguageColumns.add(WalletFactoryMiddlewareDatabaseConstants.PROJECT_LANGUAGE_ID_COLUMN_NAME);
        projectLanguageColumns.add(WalletFactoryMiddlewareDatabaseConstants.PROJECT_LANGUAGE_NAME_COLUMN_NAME);
        projectLanguageColumns.add(WalletFactoryMiddlewareDatabaseConstants.PROJECT_LANGUAGE_LANGUAGE_TYPE_COLUMN_NAME);
        projectLanguageColumns.add(WalletFactoryMiddlewareDatabaseConstants.PROJECT_LANGUAGE_TRANSLATOR_PUBLIC_KEY_COLUMN_NAME);
        projectLanguageColumns.add(WalletFactoryMiddlewareDatabaseConstants.PROJECT_LANGUAGE_VERSION_COLUMN_NAME);
        projectLanguageColumns.add(WalletFactoryMiddlewareDatabaseConstants.PROJECT_LANGUAGE_PROJECT_PROPOSAL_ID_COLUMN_NAME);
        /**
         * Table Project Language addition.
         */
        DeveloperDatabaseTable projectLanguageTable = developerObjectFactory.getNewDeveloperDatabaseTable(WalletFactoryMiddlewareDatabaseConstants.PROJECT_LANGUAGE_TABLE_NAME, projectLanguageColumns);
        tables.add(projectLanguageTable);



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
        List<String> developerRow = new ArrayList<String>();
        for (DatabaseTableRecord row : records) {
            /**
             * for each row in the table list
             */
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

    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    @Override
    public void setPluginId(UUID pluginId) {
        this.pluginId = pluginId;
    }
}
