package com.bitdubai.fermat_dap_plugin.digital_asset_transaction.redeem_point_redemption.bitdubai.version_1.structure.database;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseDataType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateTableException;
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
    public Database createDatabase(UUID ownerId, String databaseName) throws CantCreateDatabaseException {
        Database database;

        /**
         * I will create the database where I am going to store the information of this wallet.
         */
        try {
            database = this.pluginDatabaseSystem.createDatabase(ownerId, databaseName);
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
            DatabaseTableFactory table;
            DatabaseFactory databaseFactory = database.getDatabaseFactory();

            /**
             * Create Asset Reception table.
             */
            table = databaseFactory.newTableFactory(ownerId, AssetRedeemPointRedemptionDatabaseConstants.ASSET_RPR_EVENTS_RECORDED_TABLE_NAME);
            DatabaseTableFactory eventsRecorderTable = databaseFactory.newTableFactory(ownerId, AssetRedeemPointRedemptionDatabaseConstants.ASSET_RPR_EVENTS_RECORDED_TABLE_NAME);

            eventsRecorderTable.addColumn(AssetRedeemPointRedemptionDatabaseConstants.ASSET_RPR_EVENTS_RECORDED_ID_COLUMN_NAME, DatabaseDataType.STRING, 36, Boolean.TRUE);
            eventsRecorderTable.addColumn(AssetRedeemPointRedemptionDatabaseConstants.ASSET_RPR_EVENTS_RECORDED_EVENT_COLUMN_NAME, DatabaseDataType.STRING, 10, Boolean.FALSE);
            eventsRecorderTable.addColumn(AssetRedeemPointRedemptionDatabaseConstants.ASSET_RPR_EVENTS_RECORDED_SOURCE_COLUMN_NAME, DatabaseDataType.STRING, 10, Boolean.FALSE);
            eventsRecorderTable.addColumn(AssetRedeemPointRedemptionDatabaseConstants.ASSET_RPR_EVENTS_RECORDED_STATUS_COLUMN_NAME, DatabaseDataType.STRING, 10, Boolean.FALSE);
            eventsRecorderTable.addColumn(AssetRedeemPointRedemptionDatabaseConstants.ASSET_RPR_EVENTS_RECORDED_TIMESTAMP_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 100, Boolean.FALSE);

            eventsRecorderTable.addIndex(AssetRedeemPointRedemptionDatabaseConstants.ASSET_RPR_EVENTS_RECORDED_TABLE_FIRST_KEY_COLUMN);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, table);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
            }
        } catch (InvalidOwnerIdException e) {
            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, e, "UUID : " + ownerId, "Invalid Owner. Cannot create the table...");
        }
        return database;
    }

    //GETTER AND SETTERS
    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }
}
