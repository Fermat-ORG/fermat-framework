package org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_appropiation.developer.version_1.structure.database;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_api.layer.all_definition.util.Validate;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseDataType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateTableException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.InvalidOwnerIdException;

import java.util.UUID;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 06/11/15.
 */
public class AssetAppropriationDatabaseFactory implements DealsWithPluginDatabaseSystem {

    //VARIABLE DECLARATION
    private PluginDatabaseSystem pluginDatabaseSystem;

    //CONSTRUCTORS
    public AssetAppropriationDatabaseFactory() {
    }

    public AssetAppropriationDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem) throws CantSetObjectException {
        this.pluginDatabaseSystem = Validate.verifySetter(pluginDatabaseSystem, "pluginDatabaseSystem is null");
    }


    //PUBLIC METHODS
    public Database createDatabase(UUID ownerId) throws CantCreateDatabaseException {
        Database database;

        /**
         * I will create the database where I am going to store the information of this wallet.
         */
        try {
            database = this.pluginDatabaseSystem.createDatabase(ownerId, AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_DATABASE);
        } catch (CantCreateDatabaseException cantCreateDatabaseException) {
            /**
             * I can not handle this situation.
             */
            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateDatabaseException, "", "Exception not handled by the plugin, There is a problem and i cannot create the database.");
        }

        /**
         * Next, I will add the needed tables.
         */
        try {
            DatabaseFactory databaseFactory = database.getDatabaseFactory();

            /**
             * Create Events recorded database table.
             */
            DatabaseTableFactory eventsRecorderTable = databaseFactory.newTableFactory(ownerId, AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_EVENTS_RECORDED_TABLE_NAME);

            eventsRecorderTable.addColumn(AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_EVENTS_RECORDED_ID_COLUMN_NAME, DatabaseDataType.STRING, 255, Boolean.TRUE);
            eventsRecorderTable.addColumn(AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_EVENTS_RECORDED_EVENT_COLUMN_NAME, DatabaseDataType.STRING, 15, Boolean.FALSE);
            eventsRecorderTable.addColumn(AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_EVENTS_RECORDED_SOURCE_COLUMN_NAME, DatabaseDataType.STRING, 15, Boolean.FALSE);
            eventsRecorderTable.addColumn(AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_EVENTS_RECORDED_STATUS_COLUMN_NAME, DatabaseDataType.STRING, 15, Boolean.FALSE);
            eventsRecorderTable.addColumn(AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_EVENTS_RECORDED_TIMESTAMP_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 100, Boolean.FALSE);

            eventsRecorderTable.addIndex(AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_EVENTS_RECORDED_TABLE_FIRST_KEY_COLUMN);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, eventsRecorderTable);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
            }

            DatabaseTableFactory transactionMetadataTable = databaseFactory.newTableFactory(ownerId, AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_TRANSACTION_METADATA_TABLE_NAME);

            transactionMetadataTable.addColumn(AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_TRANSACTION_METADATA_ID_COLUMN_NAME, DatabaseDataType.STRING, 255, Boolean.TRUE);
            transactionMetadataTable.addColumn(AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_TRANSACTION_METADATA_NETWORK_TYPE, DatabaseDataType.STRING, 255, Boolean.FALSE);
            transactionMetadataTable.addColumn(AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_TRANSACTION_METADATA_STATUS_COLUMN_NAME, DatabaseDataType.STRING, 15, Boolean.FALSE);
            transactionMetadataTable.addColumn(AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_TRANSACTION_METADATA_DA_PUBLIC_KEY_COLUMN_NAME, DatabaseDataType.STRING, 36, Boolean.FALSE);
            transactionMetadataTable.addColumn(AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_TRANSACTION_METADATA_USER_WALLET_KEY_TO_COLUMN_NAME, DatabaseDataType.STRING, 36, Boolean.FALSE);
            transactionMetadataTable.addColumn(AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_TRANSACTION_METADATA_BTC_WALLET_KEY_TO_COLUMN_NAME, DatabaseDataType.STRING, 36, Boolean.FALSE);
            transactionMetadataTable.addColumn(AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_TRANSACTION_METADATA_CRYPTO_ADDRESS_TO_COLUMN_NAME, DatabaseDataType.STRING, 36, Boolean.FALSE);
            transactionMetadataTable.addColumn(AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_TRANSACTION_METADATA_CRYPTO_CURRENCY_TO_COLUMN_NAME, DatabaseDataType.STRING, 36, Boolean.FALSE);
            transactionMetadataTable.addColumn(AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_TRANSACTION_METADATA_START_TIME_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 100, Boolean.FALSE);
            transactionMetadataTable.addColumn(AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_TRANSACTION_METADATA_END_TIME_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 100, Boolean.FALSE);
            transactionMetadataTable.addColumn(AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_TRANSACTION_METADATA_GENESIS_COLUMN_NAME, DatabaseDataType.STRING, 36, Boolean.FALSE);


            transactionMetadataTable.addIndex(AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_TRANSACTION_METADATA_TABLE_FIRST_KEY_COLUMN);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, transactionMetadataTable);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
            }

        } catch (InvalidOwnerIdException e) {
            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, e, "UUID : " + ownerId, "Invalid Owner. Cannot create the table...");
        }
        return database;
    }

    public boolean isDatabaseCreated(UUID ownerId) {
        try {
            this.pluginDatabaseSystem.openDatabase(ownerId, AssetAppropriationDatabaseConstants.ASSET_APPROPRIATION_DATABASE);
        } catch (CantOpenDatabaseException e) {
            return false;
        } catch (DatabaseNotFoundException e) {
            return false;
        }
        return true;
    }
    //PRIVATE METHODS

    //GETTER AND SETTERS
    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    //INNER CLASSES
}
