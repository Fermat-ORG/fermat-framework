package com.bitdubai.fermat_dap_plugin.layer.actor.asset.issuer.developer.bitdubai.version_1.database;

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
 * Created by Nerio on 17/09/15.
 */
public class AssetIssuerActorDatabaseFactory implements DealsWithPluginDatabaseSystem {

    /**
     * DealsWithPluginDatabaseSystem Interface member variables.
     */
    private PluginDatabaseSystem pluginDatabaseSystem;


    public AssetIssuerActorDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    public Database createDatabase(UUID ownerId, String databaseName) throws CantCreateDatabaseException {
        Database database;
        /**
         * I will create the database where I am going to store the information of this wallet.
         */
        try{
            database = this.pluginDatabaseSystem.createDatabase(ownerId,databaseName);
        }catch (CantCreateDatabaseException exception){
            /**
             * I can not handle this situation.
             */
            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, exception, "", "Exception not handled by the plugin, There is a problem and i cannot create the database.");
        }

        /**
         * Next, I will add the needed tables.
         */
        try {
            DatabaseTableFactory table;
            DatabaseFactory databaseFactory = database.getDatabaseFactory();

            /**
             * Create Asset Issuer database table.
             */
            table = databaseFactory.newTableFactory(ownerId, AssetIssuerActorDatabaseConstants.ASSET_ISSUER_TABLE_NAME);


            /* Asset Issuer Table primary key.*/
            table.addColumn(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_PUBLIC_KEY_COLUMN_NAME, DatabaseDataType.STRING, 256, Boolean.TRUE);

            table.addColumn(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_LINKED_IDENTITY_PUBLIC_KEY_COLUMN_NAME, DatabaseDataType.STRING, 256, Boolean.FALSE);
            table.addColumn(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_NAME_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_CONNECTION_STATE_COLUMN_NAME, DatabaseDataType.STRING, 10, Boolean.FALSE);
            table.addColumn(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTRATION_DATE_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0, Boolean.FALSE);
            table.addColumn(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_LAST_CONNECTION_DATE_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0, Boolean.FALSE);
            table.addColumn(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_PUBLIC_KEY_EXTENDED_COLUMN_NAME, DatabaseDataType.STRING, 512, Boolean.FALSE);
//            table.addColumn(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_ISSUER_CRYPTO_ADDRESS_COLUMN_NAME, DatabaseDataType.STRING, 256, Boolean.FALSE);
//            table.addColumn(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_ISSUER_CRYPTO_CURRENCY_COLUMN_NAME, DatabaseDataType.STRING, 10, Boolean.FALSE);
            table.addColumn(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_LOCATION_LATITUDE_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_LOCATION_LONGITUDE_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_DESCRIPTION_COLUMN_NAME, DatabaseDataType.STRING, 256, Boolean.FALSE);

            table.addIndex(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_FIRST_KEY_COLUMN);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, table);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
            }

            table = databaseFactory.newTableFactory(ownerId, AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_TABLE_NAME);


            /* Asset Issuer Table primary key.*/
            table.addColumn(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_PUBLIC_KEY_COLUMN_NAME, DatabaseDataType.STRING, 256, Boolean.TRUE);

            table.addColumn(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_LINKED_IDENTITY_PUBLIC_KEY_COLUMN_NAME, DatabaseDataType.STRING, 256, Boolean.FALSE);
            table.addColumn(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_NAME_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_CONNECTION_STATE_COLUMN_NAME, DatabaseDataType.STRING, 10, Boolean.FALSE);
            table.addColumn(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_REGISTRATION_DATE_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0, Boolean.FALSE);
            table.addColumn(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_LAST_CONNECTION_DATE_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0, Boolean.FALSE);
            table.addColumn(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_PUBLIC_KEY_EXTENDED_COLUMN_NAME, DatabaseDataType.STRING, 512, Boolean.FALSE);
//            table.addColumn(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_ISSUER_CRYPTO_ADDRESS_COLUMN_NAME, DatabaseDataType.STRING, 256, Boolean.FALSE);
//            table.addColumn(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_ISSUER_CRYPTO_CURRENCY_COLUMN_NAME, DatabaseDataType.STRING, 10, Boolean.FALSE);
            table.addColumn(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_LOCATION_LATITUDE_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_LOCATION_LONGITUDE_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_DESCRIPTION_COLUMN_NAME, DatabaseDataType.STRING, 256, Boolean.FALSE);

            table.addIndex(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_REGISTERED_FIRST_KEY_COLUMN);


            try {
                //Create the table
                databaseFactory.createTable(ownerId, table);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the registered table.");
            }

            /**
             * Create Asset Issuer relation Redeem Point Associate table.
             */
//            table = databaseFactory.newTableFactory(ownerId, AssetIssuerActorDatabaseConstants.ASSET_ISSUER_RELATION_REDEEM_POINT_TABLE_NAME);
//
//            table.addColumn(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_RELATION_REDEEM_POINT_PUBLIC_KEY_COLUMN_NAME, DatabaseDataType.STRING, 256, Boolean.TRUE);
//            table.addColumn(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_RELATION_REDEEM_POINT_ISSUER_NAME_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
//            table.addColumn(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_RELATION_REDEEM_POINT_NAME_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
//            table.addColumn(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_RELATION_REDEEM_POINT_STATUS_COLUMN_NAME, DatabaseDataType.STRING, 10, Boolean.FALSE);
//            table.addColumn(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_RELATION_REDEEM_POINT_ASSETS_COUNT_COLUMN_NAME, DatabaseDataType.STRING, 10, Boolean.FALSE);
//            table.addColumn(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_RELATION_REDEEM_POINT_REGISTRATION_DATE_ISSUER_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0, Boolean.FALSE);
//            table.addColumn(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_RELATION_REDEEM_POINT_REGISTRATION_DATE_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0, Boolean.FALSE);
//            table.addColumn(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_RELATION_REDEEM_POINT_MODIFIED_DATE_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0, Boolean.FALSE);
//
//            table.addIndex(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_RELATION_REDEEM_POINT_FIRST_KEY_COLUMN);
//
//            try {
//                //Create the table
//                databaseFactory.createTable(ownerId, table);
//            } catch (CantCreateTableException cantCreateTableException) {
//                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
//            }
//
//            /**
//             * Create Asset Issuer relation Asset User table.
//             */
//            table = databaseFactory.newTableFactory(ownerId, AssetIssuerActorDatabaseConstants.ASSET_ISSUER_RELATION_USER_TABLE_NAME);
//
//            table.addColumn(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_RELATION_USER_PUBLIC_KEY_COLUMN_NAME, DatabaseDataType.STRING, 256, Boolean.TRUE);
//            table.addColumn(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_RELATION_USER_ISSUER_NAME_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
//            table.addColumn(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_RELATION_USER_ASSET_ID_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
//            table.addColumn(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_RELATION_USER_ASSET_NAME_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
//            table.addColumn(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_RELATION_USER_ASSET_HASH_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
//            table.addColumn(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_RELATION_USER_ASSET_STATUS_COLUMN_NAME, DatabaseDataType.STRING, 10, Boolean.FALSE);
//            table.addColumn(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_RELATION_USER_ASSET_RESOURCES_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
//            table.addColumn(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_RELATION_USER_ASSET_AMOUNT_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0, Boolean.FALSE);
//            table.addColumn(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_RELATION_USER_ASSET_CURRENCY_COLUMN_NAME, DatabaseDataType.STRING, 10, Boolean.FALSE);
//            table.addColumn(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_RELATION_USER_ASSET_EXPIRATION_DATE_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0, Boolean.FALSE);
//            table.addColumn(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_RELATION_USER_STATUS_COLUMN_NAME, DatabaseDataType.STRING, 10, Boolean.FALSE);
//            table.addColumn(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_RELATION_USER_REGISTRATION_DATE_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0, Boolean.FALSE);
//            table.addColumn(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_RELATION_USER_MODIFIED_DATE_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0, Boolean.FALSE);
//            table.addColumn(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_RELATION_USER_TIMESTAMP_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0, Boolean.FALSE);
//
//            table.addIndex(AssetIssuerActorDatabaseConstants.ASSET_ISSUER_RELATION_USER_FIRST_KEY_COLUMN);
//
//            try {
//                //Create the table
//                databaseFactory.createTable(ownerId, table);
//            } catch (CantCreateTableException cantCreateTableException) {
//                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
//            }

        } catch (InvalidOwnerIdException exception) {
            /**
             * This shouldn't happen here because I was the one who gave the owner id to the database file system,
             * but anyway, if this happens, I can not continue.
             */
            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, exception, "", "There is a problem with the ownerId of the database.");
        }

        return database;
    }

    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }
}
