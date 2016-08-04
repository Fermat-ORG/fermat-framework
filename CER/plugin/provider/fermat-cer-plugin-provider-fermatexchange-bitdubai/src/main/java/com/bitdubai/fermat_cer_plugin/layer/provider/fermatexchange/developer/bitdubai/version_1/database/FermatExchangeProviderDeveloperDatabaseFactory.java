package com.bitdubai.fermat_cer_plugin.layer.provider.fermatexchange.developer.bitdubai.version_1.database;

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
import com.bitdubai.fermat_cer_plugin.layer.provider.fermatexchange.developer.bitdubai.version_1.exceptions.CantInitializeFermatExchangeProviderDatabaseException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_cer_plugin.layer.provider.fermatexchange.developer.bitdubai.version_1.database.FermatExchangeProviderDeveloperDatabaseFactory</code> have
 * contains the methods that the Developer Database Tools uses to show the information.
 * <p/>
 * <p/>
 * Created by Alejandro Bicelis - (abicelis@gmail.com) on 23/05/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */

public class FermatExchangeProviderDeveloperDatabaseFactory implements DealsWithPluginDatabaseSystem, DealsWithPluginIdentity {

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
    public FermatExchangeProviderDeveloperDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
    }

    /**
     * This method open or creates the database i'll be working with
     *
     * @throws CantInitializeFermatExchangeProviderDatabaseException
     */
    public void initializeDatabase() throws CantInitializeFermatExchangeProviderDatabaseException {
        try {

             /*
              * Open new database connection
              */
            database = this.pluginDatabaseSystem.openDatabase(pluginId, pluginId.toString());

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {

             /*
              * The database exists but cannot be open. I can not handle this situation.
              */
            throw new CantInitializeFermatExchangeProviderDatabaseException(cantOpenDatabaseException.getMessage());

        } catch (DatabaseNotFoundException e) {

             /*
              * The database no exist may be the first time the plugin is running on this device,
              * We need to create the new database
              */
            FermatExchangeProviderDatabaseFactory fermatExchangeProviderDatabaseFactory = new FermatExchangeProviderDatabaseFactory(pluginDatabaseSystem);

            try {
                  /*
                   * We create the new database
                   */
                database = fermatExchangeProviderDatabaseFactory.createDatabase(pluginId, pluginId.toString());
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                  /*
                   * The database cannot be created. I can not handle this situation.
                   */
                throw new CantInitializeFermatExchangeProviderDatabaseException(cantCreateDatabaseException.getMessage());
            }
        }
    }


    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        /**
         * I only have one database on my plugin. I will return its name.
         */
        List<DeveloperDatabase> databases = new ArrayList<DeveloperDatabase>();
        databases.add(developerObjectFactory.getNewDeveloperDatabase("FermatExchange", this.pluginId.toString()));
        return databases;
    }


    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory) {
        List<DeveloperDatabaseTable> tables = new ArrayList<DeveloperDatabaseTable>();

        /**
         * Table Provider Info columns.
         */
        List<String> providerInfoColumns = new ArrayList<String>();

        providerInfoColumns.add(FermatExchangeProviderDatabaseConstants.PROVIDER_INFO_ID_COLUMN_NAME);
        providerInfoColumns.add(FermatExchangeProviderDatabaseConstants.PROVIDER_INFO_NAME_COLUMN_NAME);
        /**
         * Table Provider Info addition.
         */
        DeveloperDatabaseTable providerInfoTable = developerObjectFactory.getNewDeveloperDatabaseTable(FermatExchangeProviderDatabaseConstants.PROVIDER_INFO_TABLE_NAME, providerInfoColumns);
        tables.add(providerInfoTable);

        /**
         * Table Current Exchange Rates columns.
         */
        List<String> currentExchangeRatesColumns = new ArrayList<String>();

        currentExchangeRatesColumns.add(FermatExchangeProviderDatabaseConstants.CURRENT_EXCHANGE_RATES_ID_COLUMN_NAME);
        currentExchangeRatesColumns.add(FermatExchangeProviderDatabaseConstants.CURRENT_EXCHANGE_RATES_FROM_CURRENCY_COLUMN_NAME);
        currentExchangeRatesColumns.add(FermatExchangeProviderDatabaseConstants.CURRENT_EXCHANGE_RATES_TO_CURRENCY_COLUMN_NAME);
        currentExchangeRatesColumns.add(FermatExchangeProviderDatabaseConstants.CURRENT_EXCHANGE_RATES_SALE_PRICE_COLUMN_NAME);
        currentExchangeRatesColumns.add(FermatExchangeProviderDatabaseConstants.CURRENT_EXCHANGE_RATES_PURCHASE_PRICE_COLUMN_NAME);
        currentExchangeRatesColumns.add(FermatExchangeProviderDatabaseConstants.CURRENT_EXCHANGE_RATES_TIMESTAMP_COLUMN_NAME);
        /**
         * Table Current Exchange Rates addition.
         */
        DeveloperDatabaseTable currentExchangeRatesTable = developerObjectFactory.getNewDeveloperDatabaseTable(FermatExchangeProviderDatabaseConstants.CURRENT_EXCHANGE_RATES_TABLE_NAME, currentExchangeRatesColumns);
        tables.add(currentExchangeRatesTable);

        /**
         * Table Daily Exchange Rates columns.
         */
        List<String> dailyExchangeRatesColumns = new ArrayList<String>();

        dailyExchangeRatesColumns.add(FermatExchangeProviderDatabaseConstants.DAILY_EXCHANGE_RATES_ID_COLUMN_NAME);
        dailyExchangeRatesColumns.add(FermatExchangeProviderDatabaseConstants.DAILY_EXCHANGE_RATES_FROM_CURRENCY_COLUMN_NAME);
        dailyExchangeRatesColumns.add(FermatExchangeProviderDatabaseConstants.DAILY_EXCHANGE_RATES_TO_CURRENCY_COLUMN_NAME);
        dailyExchangeRatesColumns.add(FermatExchangeProviderDatabaseConstants.DAILY_EXCHANGE_RATES_SALE_PRICE_COLUMN_NAME);
        dailyExchangeRatesColumns.add(FermatExchangeProviderDatabaseConstants.DAILY_EXCHANGE_RATES_PURCHASE_PRICE_COLUMN_NAME);
        dailyExchangeRatesColumns.add(FermatExchangeProviderDatabaseConstants.DAILY_EXCHANGE_RATES_TIMESTAMP_COLUMN_NAME);
        /**
         * Table Daily Exchange Rates addition.
         */
        DeveloperDatabaseTable dailyExchangeRatesTable = developerObjectFactory.getNewDeveloperDatabaseTable(FermatExchangeProviderDatabaseConstants.DAILY_EXCHANGE_RATES_TABLE_NAME, dailyExchangeRatesColumns);
        tables.add(dailyExchangeRatesTable);


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
            List<String> developerRow = new ArrayList<String>();
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
