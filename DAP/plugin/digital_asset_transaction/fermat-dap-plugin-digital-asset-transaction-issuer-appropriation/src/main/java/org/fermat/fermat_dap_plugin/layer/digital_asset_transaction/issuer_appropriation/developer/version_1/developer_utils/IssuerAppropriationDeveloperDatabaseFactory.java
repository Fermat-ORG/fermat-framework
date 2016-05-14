package org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.issuer_appropriation.developer.version_1.developer_utils;

import com.bitdubai.fermat_api.DealsWithPluginIdentity;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_api.layer.all_definition.util.Validate;
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

import org.fermat.fermat_dap_api.layer.dap_transaction.asset_redemption.exceptions.CantInitializeAssetRedeemPointRedemptionTransactionDatabaseException;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.issuer_appropriation.developer.version_1.structure.database.IssuerAppropriationDatabaseConstants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 06/11/15.
 */
public class IssuerAppropriationDeveloperDatabaseFactory implements DealsWithPluginDatabaseSystem, DealsWithPluginIdentity {

    //VARIABLE DECLARATION
    private PluginDatabaseSystem pluginDatabaseSystem;
    private UUID pluginId;
    private Database database;

    //CONSTRUCTORS

    public IssuerAppropriationDeveloperDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) throws CantSetObjectException {
        this.pluginDatabaseSystem = Validate.verifySetter(pluginDatabaseSystem, "pluginDatabaseSystem is null");
        this.pluginId = Validate.verifySetter(pluginId, "pluginId is null");
    }

    public IssuerAppropriationDeveloperDatabaseFactory() {
    }

    //PUBLIC METHODS


    public void initializeDatabase() throws CantInitializeAssetRedeemPointRedemptionTransactionDatabaseException {
        try {

             /*
              * Open new database connection
              */
            database = pluginDatabaseSystem.openDatabase(pluginId, IssuerAppropriationDatabaseConstants.ISSUER_APPROPRIATION_DATABASE);

        } catch (CantOpenDatabaseException cantOpenDatabaseException) {

             /*
              * The database exists but cannot be open. I can not handle this situation.
              */
            throw new CantInitializeAssetRedeemPointRedemptionTransactionDatabaseException(cantOpenDatabaseException.getMessage());

        } catch (DatabaseNotFoundException e) {

             /*
              * The database no exist may be the first time the plugin is running on this device,
              * We need to create the new database
              */
            org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.issuer_appropriation.developer.version_1.structure.database.IssuerAppropriationDatabaseFactory issuerAppropriationDatabaseFactory = null;
            try {
                issuerAppropriationDatabaseFactory = new org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.issuer_appropriation.developer.version_1.structure.database.IssuerAppropriationDatabaseFactory(pluginDatabaseSystem);
            } catch (CantSetObjectException e1) {
                throw new CantInitializeAssetRedeemPointRedemptionTransactionDatabaseException(e1.getMessage());
            }

            try {
                  /*
                   * We create the new database
                   */
                database = issuerAppropriationDatabaseFactory.createDatabase(pluginId);
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                  /*
                   * The database cannot be created. I can not handle this situation.
                   */
                throw new CantInitializeAssetRedeemPointRedemptionTransactionDatabaseException(cantCreateDatabaseException.getMessage());
            }
        }
    }

    public static List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory) {
        List<DeveloperDatabaseTable> tables = new ArrayList<>();

        /**
         * Events Recorder table
         * */
        List<String> eventsRecorderColumns = new ArrayList<>();

        eventsRecorderColumns.add(IssuerAppropriationDatabaseConstants.ISSUER_APPROPRIATION_EVENTS_RECORDED_ID_COLUMN_NAME);
        eventsRecorderColumns.add(IssuerAppropriationDatabaseConstants.ISSUER_APPROPRIATION_EVENTS_RECORDED_EVENT_COLUMN_NAME);
        eventsRecorderColumns.add(IssuerAppropriationDatabaseConstants.ISSUER_APPROPRIATION_EVENTS_RECORDED_SOURCE_COLUMN_NAME);
        eventsRecorderColumns.add(IssuerAppropriationDatabaseConstants.ISSUER_APPROPRIATION_EVENTS_RECORDED_STATUS_COLUMN_NAME);
        eventsRecorderColumns.add(IssuerAppropriationDatabaseConstants.ISSUER_APPROPRIATION_EVENTS_RECORDED_TIMESTAMP_COLUMN_NAME);

        DeveloperDatabaseTable eventsRecorderTable = developerObjectFactory.getNewDeveloperDatabaseTable(IssuerAppropriationDatabaseConstants.ISSUER_APPROPRIATION_EVENTS_RECORDED_TABLE_NAME, eventsRecorderColumns);
        tables.add(eventsRecorderTable);

        List<String> transactionMetadataColumns = new ArrayList<>();


        transactionMetadataColumns.add(IssuerAppropriationDatabaseConstants.ISSUER_APPROPRIATION_TRANSACTION_METADATA_ID_COLUMN_NAME);
        transactionMetadataColumns.add(IssuerAppropriationDatabaseConstants.ISSUER_APPROPRIATION_TRANSACTION_METADATA_NETWORK_TYPE);
        transactionMetadataColumns.add(IssuerAppropriationDatabaseConstants.ISSUER_APPROPRIATION_TRANSACTION_METADATA_STATUS_COLUMN_NAME);
        transactionMetadataColumns.add(IssuerAppropriationDatabaseConstants.ISSUER_APPROPRIATION_TRANSACTION_METADATA_DA_PUBLIC_KEY_COLUMN_NAME);
        transactionMetadataColumns.add(IssuerAppropriationDatabaseConstants.ISSUER_APPROPRIATION_TRANSACTION_METADATA_USER_WALLET_KEY_TO_COLUMN_NAME);
        transactionMetadataColumns.add(IssuerAppropriationDatabaseConstants.ISSUER_APPROPRIATION_TRANSACTION_METADATA_BTC_WALLET_KEY_TO_COLUMN_NAME);
        transactionMetadataColumns.add(IssuerAppropriationDatabaseConstants.ISSUER_APPROPRIATION_TRANSACTION_METADATA_CRYPTO_ADDRESS_TO_COLUMN_NAME);
        transactionMetadataColumns.add(IssuerAppropriationDatabaseConstants.ISSUER_APPROPRIATION_TRANSACTION_METADATA_CRYPTO_CURRENCY_TO_COLUMN_NAME);
        transactionMetadataColumns.add(IssuerAppropriationDatabaseConstants.ISSUER_APPROPRIATION_TRANSACTION_METADATA_START_TIME_COLUMN_NAME);
        transactionMetadataColumns.add(IssuerAppropriationDatabaseConstants.ISSUER_APPROPRIATION_TRANSACTION_METADATA_END_TIME_COLUMN_NAME);
        transactionMetadataColumns.add(IssuerAppropriationDatabaseConstants.ISSUER_APPROPRIATION_TRANSACTION_METADATA_GENESIS_COLUMN_NAME);

        DeveloperDatabaseTable transactionMetadataTable = developerObjectFactory.getNewDeveloperDatabaseTable(IssuerAppropriationDatabaseConstants.ISSUER_APPROPRIATION_TRANSACTION_METADATA_TABLE_NAME, transactionMetadataColumns);
        tables.add(transactionMetadataTable);


        return tables;
    }

    public static List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory, UUID pluginId) {
        /**
         * I only have one database on my plugin. I will return its name.
         */
        List<DeveloperDatabase> databases = new ArrayList<>();
        databases.add(developerObjectFactory.getNewDeveloperDatabase(IssuerAppropriationDatabaseConstants.ISSUER_APPROPRIATION_DATABASE, pluginId.toString()));
        return databases;
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
            return Collections.EMPTY_LIST;
        } catch (Exception e) {
            return Collections.EMPTY_LIST;
        } finally {
            database.closeDatabase();
        }
        return returnedRecords;
    }
    //PRIVATE METHODS

    //GETTER AND SETTERS
    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    @Override
    public void setPluginId(UUID pluginId) {
        this.pluginId = pluginId;
    }
    //INNER CLASSES
}
