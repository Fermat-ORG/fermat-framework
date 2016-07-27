package org.fermat.fermat_dap_plugin.layer.middleware.asset.issuer.developer.version_1.structure.database;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseDataType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateTableException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.InvalidOwnerIdException;

import java.util.UUID;

/**
 * Created by franklin on 08/09/15.
 */
public class AssetFactoryMiddlewareDatabaseFactory {

    private final PluginDatabaseSystem pluginDatabaseSystem;

    public AssetFactoryMiddlewareDatabaseFactory(final PluginDatabaseSystem pluginDatabaseSystem) {

        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /**
     * Create the database
     *
     * @param ownerId      the owner id
     * @param databaseName the database name
     * @return Database
     * @throws CantCreateDatabaseException
     */
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
            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateDatabaseException, "Asset Factory", "Exception not handled by the plugin, There is a problem and i cannot create the database.");
        }

        /**
         * Next, I will add the needed tables.
         */
        try {
            DatabaseTableFactory table;
            DatabaseFactory databaseFactory = database.getDatabaseFactory();

            /**
             * Create Asset Factory
             */
            table = databaseFactory.newTableFactory(ownerId, AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_TABLE_NAME);

            table.addColumn(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_ID_COLUMN, DatabaseDataType.STRING, 255, Boolean.TRUE);
            table.addColumn(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_ASSET_PUBLIC_KEY_COLUMN, DatabaseDataType.STRING, 255, Boolean.FALSE);
            table.addColumn(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_NAME_COLUMN, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_QUANTITY_COLUMN, DatabaseDataType.LONG_INTEGER, 0, Boolean.FALSE);
            table.addColumn(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_TOTAL_QUANTITY_COLUMN, DatabaseDataType.LONG_INTEGER, 0, Boolean.FALSE);
            table.addColumn(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_AMOUNT_COLUMN, DatabaseDataType.LONG_INTEGER, 0, Boolean.FALSE);
            table.addColumn(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_FEE_COLUMN, DatabaseDataType.LONG_INTEGER, 0, Boolean.FALSE);
            table.addColumn(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_STATE_COLUMN, DatabaseDataType.STRING, 15, Boolean.FALSE);
            table.addColumn(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_DESCRIPTION_COLUMN, DatabaseDataType.STRING, 150, Boolean.FALSE);
            table.addColumn(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_ISSUER_IDENTITY_PUBLIC_KEY_COLUMN, DatabaseDataType.STRING, 255, Boolean.FALSE);
            table.addColumn(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_ISSUER_IDENTITY_ALIAS_COLUMN, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_ISSUER_IDENTITY_SIGNATURE_COLUMN, DatabaseDataType.STRING, 255, Boolean.FALSE);
            table.addColumn(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_CREATION_TIME_COLUMN, DatabaseDataType.STRING, 30, Boolean.FALSE);
            table.addColumn(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_LAST_UPDATE_TIME_COLUMN, DatabaseDataType.STRING, 30, Boolean.FALSE);
            table.addColumn(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_ASSET_BEHAVIOR_COLUMN, DatabaseDataType.STRING, 30, Boolean.FALSE);
            table.addColumn(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_IS_REDEEMABLE, DatabaseDataType.STRING, 5, Boolean.FALSE);
            table.addColumn(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_IS_TRANSFERABLE, DatabaseDataType.STRING, 5, Boolean.FALSE);
            table.addColumn(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_IS_EXCHANGEABLE, DatabaseDataType.STRING, 5, Boolean.FALSE);
            table.addColumn(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_EXPIRATION_DATE, DatabaseDataType.STRING, 30, Boolean.FALSE);
            table.addColumn(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_ASSET_WALLET_PUBLIC_KEY, DatabaseDataType.STRING, 255, Boolean.FALSE);
            table.addColumn(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_NETWORK_TYPE, DatabaseDataType.STRING, 255, Boolean.FALSE);


            table.addIndex(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_FIRST_KEY_COLUMN);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, table);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "Asset Factory", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
            }

            /**
             * Create Asset Factory Resource
             */
            table = databaseFactory.newTableFactory(ownerId, AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_RESOURCE_TABLE_NAME);

            table.addColumn(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_RESOURCE_ID_COLUMN, DatabaseDataType.STRING, 100, Boolean.TRUE);
            table.addColumn(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_RESOURCE_ASSET_PUBLIC_KEY_COLUMN, DatabaseDataType.STRING, 255, Boolean.FALSE);
            table.addColumn(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_RESOURCE_NAME_COLUMN, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_RESOURCE_FILE_NAME_COLUMN, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_RESOURCE_RESOURCE_TYPE_COLUMN, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_RESOURCE_RESOURCE_DENSITY_COLUMN, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_RESOURCE_PATH_COLUMN, DatabaseDataType.STRING, 255, Boolean.FALSE);

            table.addIndex(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_RESOURCE_FIRST_KEY_COLUMN);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, table);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "Asset Factory", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
            }

            /**
             * Create Asset Factory Contract
             */
            table = databaseFactory.newTableFactory(ownerId, AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_CONTRACT_TABLE_NAME);

            table.addColumn(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_CONTRACT_ID_COLUMN, DatabaseDataType.STRING, 100, Boolean.TRUE);
            table.addColumn(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_CONTRACT_ASSET_PUBLIC_KEY_COLUMN, DatabaseDataType.STRING, 255, Boolean.TRUE);
            table.addColumn(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_CONTRACT_NAME_COLUMN, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_CONTRACT_VALUE_COLUMN, DatabaseDataType.STRING, 50, Boolean.FALSE);

            table.addIndex(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_CONTRACT_FIRST_KEY_COLUMN);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, table);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "Asset Factory", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
            }

            /**
             * Create Asset Identity Issuer Contract
             */
            table = databaseFactory.newTableFactory(ownerId, AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_IDENTITY_ISSUER_TABLE_NAME);

            table.addColumn(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_IDENTITY_ID_COLUMN, DatabaseDataType.STRING, 100, Boolean.TRUE);
            table.addColumn(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_IDENTITY_ISSUER_PUBLIC_KEY_COLUMN, DatabaseDataType.STRING, 255, Boolean.TRUE);
            table.addColumn(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_IDENTITY_ISSUER_NAME_COLUMN, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_IDENTITY_ISSUER_ASSET_PUBLIC_KEY_COLUMN, DatabaseDataType.STRING, 255, Boolean.FALSE);
            table.addColumn(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_IDENTITY_ISSUER_SIGNATURE_COLUMN, DatabaseDataType.STRING, 255, Boolean.FALSE);

            table.addIndex(AssetFactoryMiddlewareDatabaseConstant.ASSET_FACTORY_IDENTITY_ISSUER_FIRST_KEY_COLUMN);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, table);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "Asset Factory Identity Issuer", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
            }

        } catch (InvalidOwnerIdException invalidOwnerId) {
            /**
             * This shouldn't happen here because I was the one who gave the owner id to the database file system,
             * but anyway, if this happens, I can not continue.
             */
            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, invalidOwnerId, "Asset Factory", "There is a problem with the ownerId of the database.");
        }
        return database;
    }
}
