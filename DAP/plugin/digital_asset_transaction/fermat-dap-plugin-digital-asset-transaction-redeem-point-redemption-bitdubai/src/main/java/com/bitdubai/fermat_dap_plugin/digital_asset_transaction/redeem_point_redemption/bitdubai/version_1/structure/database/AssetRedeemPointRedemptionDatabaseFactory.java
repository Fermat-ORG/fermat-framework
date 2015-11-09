package com.bitdubai.fermat_dap_plugin.digital_asset_transaction.redeem_point_redemption.bitdubai.version_1.structure.database;

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
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 23/10/15.
 */
public class AssetRedeemPointRedemptionDatabaseFactory implements DealsWithPluginDatabaseSystem {

    //VARIABLE DECLARATION
    private PluginDatabaseSystem pluginDatabaseSystem;


    //CONSTRUCTORS
    public AssetRedeemPointRedemptionDatabaseFactory() {
    }

    public AssetRedeemPointRedemptionDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    //PUBLIC METHODS
    public Database createDatabase(UUID ownerId) throws CantCreateDatabaseException {
        Database database;

        /**
         * I will create the database where I am going to store the information of this wallet.
         */
        try {
            database = this.pluginDatabaseSystem.createDatabase(ownerId, AssetRedeemPointRedemptionDatabaseConstants.ASSET_RPR_DATABASE);
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
             * Create Events Recorder database table.
             */
            DatabaseTableFactory eventsRecorderTable = databaseFactory.newTableFactory(ownerId, AssetRedeemPointRedemptionDatabaseConstants.ASSET_RPR_EVENTS_RECORDED_TABLE_NAME);

            eventsRecorderTable.addColumn(AssetRedeemPointRedemptionDatabaseConstants.ASSET_RPR_EVENTS_RECORDED_ID_COLUMN_NAME, DatabaseDataType.STRING, 36, Boolean.TRUE);
            eventsRecorderTable.addColumn(AssetRedeemPointRedemptionDatabaseConstants.ASSET_RPR_EVENTS_RECORDED_EVENT_COLUMN_NAME, DatabaseDataType.STRING, 15, Boolean.FALSE);
            eventsRecorderTable.addColumn(AssetRedeemPointRedemptionDatabaseConstants.ASSET_RPR_EVENTS_RECORDED_SOURCE_COLUMN_NAME, DatabaseDataType.STRING, 15, Boolean.FALSE);
            eventsRecorderTable.addColumn(AssetRedeemPointRedemptionDatabaseConstants.ASSET_RPR_EVENTS_RECORDED_STATUS_COLUMN_NAME, DatabaseDataType.STRING, 15, Boolean.FALSE);
            eventsRecorderTable.addColumn(AssetRedeemPointRedemptionDatabaseConstants.ASSET_RPR_EVENTS_RECORDED_TIMESTAMP_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 100, Boolean.FALSE);

            eventsRecorderTable.addIndex(AssetRedeemPointRedemptionDatabaseConstants.ASSET_RPR_EVENTS_RECORDED_TABLE_FIRST_KEY_COLUMN);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, eventsRecorderTable);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
            }

            /**
             * Create Transaction Metadata database table.
             */
            DatabaseTableFactory metadataTable = databaseFactory.newTableFactory(ownerId, AssetRedeemPointRedemptionDatabaseConstants.ASSET_RPR_METADATA_TABLE_NAME);

            metadataTable.addColumn(AssetRedeemPointRedemptionDatabaseConstants.ASSET_RPR_METADATA_TABLE_FIRST_KEY_COLUMN, DatabaseDataType.STRING, 36, Boolean.TRUE);
            metadataTable.addColumn(AssetRedeemPointRedemptionDatabaseConstants.ASSET_RPR_METADATA_TRANSACTION_STATUS_COLUMN_NAME, DatabaseDataType.STRING, 15, Boolean.FALSE);
            metadataTable.addColumn(AssetRedeemPointRedemptionDatabaseConstants.ASSET_RPR_METADATA_TRANSACTION_CRYPTO_STATUS_COLUMN_NAME, DatabaseDataType.STRING, 15, Boolean.FALSE);
            metadataTable.addColumn(AssetRedeemPointRedemptionDatabaseConstants.ASSET_RPR_METADATA_SENDER_KEY_COLUMN_NAME, DatabaseDataType.STRING, 36, Boolean.FALSE);
            metadataTable.addColumn(AssetRedeemPointRedemptionDatabaseConstants.ASSET_RPR_METADATA_RECEIVER_KEY_COLUMN_NAME, DatabaseDataType.STRING, 36, Boolean.FALSE);
            metadataTable.addColumn(AssetRedeemPointRedemptionDatabaseConstants.ASSET_RPR_METADATA_TIMESTAMP_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 100, Boolean.FALSE);


            metadataTable.addIndex(AssetRedeemPointRedemptionDatabaseConstants.ASSET_RPR_METADATA_TABLE_FIRST_KEY_COLUMN);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, metadataTable);
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
            this.pluginDatabaseSystem.openDatabase(ownerId, AssetRedeemPointRedemptionDatabaseConstants.ASSET_RPR_DATABASE);
        } catch (CantOpenDatabaseException e) {
            return false;
        } catch (DatabaseNotFoundException e) {
            return false;
        }
        return true;
    }

    //GETTER AND SETTERS
    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }
}
