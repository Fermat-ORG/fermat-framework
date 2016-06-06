package org.fermat.fermat_dap_plugin.layer.actor.redeem.point.developer.version_1.database;

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
public class RedeemPointActorDatabaseFactory implements DealsWithPluginDatabaseSystem {

    /**
     * DealsWithPluginDatabaseSystem Interface member variables.
     */
    private PluginDatabaseSystem pluginDatabaseSystem;


    public RedeemPointActorDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    public Database createDatabase(UUID ownerId, String databaseName) throws CantCreateDatabaseException {
        Database database;
        /**
         * I will create the database where I am going to store the information of this wallet.
         */
        try {
            database = this.pluginDatabaseSystem.createDatabase(ownerId, databaseName);
        } catch (CantCreateDatabaseException exception) {
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
             * Create Redeem Point database table.
             */
            table = databaseFactory.newTableFactory(ownerId, RedeemPointActorDatabaseConstants.REDEEM_POINT_TABLE_NAME);
            //PUBLIC KEYS
            table.addColumn(RedeemPointActorDatabaseConstants.REDEEM_POINT_LINKED_IDENTITY_PUBLIC_KEY_COLUMN_NAME, DatabaseDataType.STRING, 256, Boolean.FALSE);
            table.addColumn(RedeemPointActorDatabaseConstants.REDEEM_POINT_PUBLIC_KEY_COLUMN_NAME, DatabaseDataType.STRING, 256, Boolean.TRUE);

            table.addColumn(RedeemPointActorDatabaseConstants.REDEEM_POINT_NAME_COLUMN_NAME, DatabaseDataType.STRING, 256, Boolean.FALSE);

            table.addColumn(RedeemPointActorDatabaseConstants.REDEEM_POINT_CONNECTION_STATE_COLUMN_NAME, DatabaseDataType.STRING, 10, Boolean.FALSE);

            table.addColumn(RedeemPointActorDatabaseConstants.REDEEM_POINT_REGISTRATION_DATE_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0, Boolean.FALSE);
            table.addColumn(RedeemPointActorDatabaseConstants.REDEEM_POINT_LAST_CONNECTION_DATE_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0, Boolean.FALSE);

            table.addColumn(RedeemPointActorDatabaseConstants.REDEEM_POINT_CONTACT_INFORMATION_COLUMN_NAME, DatabaseDataType.STRING, 256, Boolean.FALSE);

            table.addColumn(RedeemPointActorDatabaseConstants.REDEEM_POINT_HOURS_OF_OPERATION_COLUMN_NAME, DatabaseDataType.STRING, 256, Boolean.FALSE);
            //COLUMNAS DE ADDRESS
            table.addColumn(RedeemPointActorDatabaseConstants.REDEEM_POINT_ADDRESS_COUNTRY_NAME_COLUMN_NAME, DatabaseDataType.STRING, 256, Boolean.FALSE);
            table.addColumn(RedeemPointActorDatabaseConstants.REDEEM_POINT_ADDRESS_POSTAL_CODE_COLUMN_NAME, DatabaseDataType.STRING, 256, Boolean.FALSE);
            table.addColumn(RedeemPointActorDatabaseConstants.REDEEM_POINT_ADDRESS_PROVINCE_NAME_COLUMN_NAME, DatabaseDataType.STRING, 256, Boolean.FALSE);
            table.addColumn(RedeemPointActorDatabaseConstants.REDEEM_POINT_ADDRESS_CITY_NAME_COLUMN_NAME, DatabaseDataType.STRING, 256, Boolean.FALSE);
            table.addColumn(RedeemPointActorDatabaseConstants.REDEEM_POINT_ADDRESS_STREET_NAME_COLUMN_NAME, DatabaseDataType.STRING, 256, Boolean.FALSE);
            table.addColumn(RedeemPointActorDatabaseConstants.REDEEM_POINT_ADDRESS_HOUSE_NUMBER_COLUMN_NAME, DatabaseDataType.STRING, 256, Boolean.FALSE);

            //COLUMAS DE CRYPTOADDRESS
            // table.addColumn(RedeemPointActorDatabaseConstants.REDEEM_POINT_CRYPTO_ADDRESS_COLUMN_NAME, DatabaseDataType.STRING, 256, Boolean.FALSE);
            //table.addColumn(RedeemPointActorDatabaseConstants.REDEEM_POINT_CRYPTO_CURRENCY_COLUMN_NAME, DatabaseDataType.STRING, 10, Boolean.FALSE);
            //COLUMNAS DE LOCATION
            table.addColumn(RedeemPointActorDatabaseConstants.REDEEM_POINT_LOCATION_LATITUDE_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(RedeemPointActorDatabaseConstants.REDEEM_POINT_LOCATION_LONGITUDE_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);

            table.addColumn(RedeemPointActorDatabaseConstants.REDEEM_POINT_ACTOR_TYPE_COLUMN_NAME, DatabaseDataType.STRING, 50, Boolean.FALSE);

            table.addIndex(RedeemPointActorDatabaseConstants.REDEEM_POINT_FIRST_KEY_COLUMN);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, table);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
            }

            /**
             * Create Redeem Point Registered database table.
             */
            table = databaseFactory.newTableFactory(ownerId, RedeemPointActorDatabaseConstants.REDEEM_POINT_REGISTERED_TABLE_NAME);
            //PUBLIC KEYS
            table.addColumn(RedeemPointActorDatabaseConstants.REDEEM_POINT_REGISTERED_LINKED_IDENTITY_PUBLIC_KEY_COLUMN_NAME, DatabaseDataType.STRING, 256, Boolean.FALSE);
            table.addColumn(RedeemPointActorDatabaseConstants.REDEEM_POINT_REGISTERED_PUBLIC_KEY_COLUMN_NAME, DatabaseDataType.STRING, 256, Boolean.TRUE);

            table.addColumn(RedeemPointActorDatabaseConstants.REDEEM_POINT_REGISTERED_NAME_COLUMN_NAME, DatabaseDataType.STRING, 256, Boolean.FALSE);

            table.addColumn(RedeemPointActorDatabaseConstants.REDEEM_POINT_REGISTERED_CONNECTION_STATE_COLUMN_NAME, DatabaseDataType.STRING, 10, Boolean.FALSE);

            table.addColumn(RedeemPointActorDatabaseConstants.REDEEM_POINT_REGISTERED_REGISTRATION_DATE_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0, Boolean.FALSE);
            table.addColumn(RedeemPointActorDatabaseConstants.REDEEM_POINT_REGISTERED_LAST_CONNECTION_DATE_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0, Boolean.FALSE);

            table.addColumn(RedeemPointActorDatabaseConstants.REDEEM_POINT_REGISTERED_CONTACT_INFORMATION_COLUMN_NAME, DatabaseDataType.STRING, 256, Boolean.FALSE);

            table.addColumn(RedeemPointActorDatabaseConstants.REDEEM_POINT_REGISTERED_HOURS_OF_OPERATION_COLUMN_NAME, DatabaseDataType.STRING, 256, Boolean.FALSE);
            //COLUMNAS DE ADDRESS
            table.addColumn(RedeemPointActorDatabaseConstants.REDEEM_POINT_REGISTERED_ADDRESS_COUNTRY_NAME_COLUMN_NAME, DatabaseDataType.STRING, 256, Boolean.FALSE);
            table.addColumn(RedeemPointActorDatabaseConstants.REDEEM_POINT_REGISTERED_ADDRESS_POSTAL_CODE_COLUMN_NAME, DatabaseDataType.STRING, 256, Boolean.FALSE);
            table.addColumn(RedeemPointActorDatabaseConstants.REDEEM_POINT_REGISTERED_ADDRESS_PROVINCE_NAME_COLUMN_NAME, DatabaseDataType.STRING, 256, Boolean.FALSE);
            table.addColumn(RedeemPointActorDatabaseConstants.REDEEM_POINT_REGISTERED_ADDRESS_CITY_NAME_COLUMN_NAME, DatabaseDataType.STRING, 256, Boolean.FALSE);
            table.addColumn(RedeemPointActorDatabaseConstants.REDEEM_POINT_REGISTERED_ADDRESS_STREET_NAME_COLUMN_NAME, DatabaseDataType.STRING, 256, Boolean.FALSE);
            table.addColumn(RedeemPointActorDatabaseConstants.REDEEM_POINT_REGISTERED_ADDRESS_HOUSE_NUMBER_COLUMN_NAME, DatabaseDataType.STRING, 256, Boolean.FALSE);

            //COLUMAS DE CRYPTOADDRESS
            //table.addColumn(RedeemPointActorDatabaseConstants.REDEEM_POINT_REGISTERED_CRYPTO_ADDRESS_COLUMN_NAME, DatabaseDataType.STRING, 256, Boolean.FALSE);
            //table.addColumn(RedeemPointActorDatabaseConstants.REDEEM_POINT_REGISTERED_CRYPTO_CURRENCY_COLUMN_NAME, DatabaseDataType.STRING, 10, Boolean.FALSE);
            //COLUMNAS DE LOCATION
            table.addColumn(RedeemPointActorDatabaseConstants.REDEEM_POINT_REGISTERED_LOCATION_LATITUDE_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(RedeemPointActorDatabaseConstants.REDEEM_POINT_REGISTERED_LOCATION_LONGITUDE_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);

            table.addColumn(RedeemPointActorDatabaseConstants.REDEEM_POINT_REGISTERED_ACTOR_TYPE_COLUMN_NAME, DatabaseDataType.STRING, 50, Boolean.FALSE);

            table.addIndex(RedeemPointActorDatabaseConstants.REDEEM_POINT_REGISTERED_FIRST_KEY_COLUMN);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, table);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
            }

            table = databaseFactory.newTableFactory(ownerId, RedeemPointActorDatabaseConstants.REGISTERED_ASSET_ISSUERS_TABLE_NAME);
            //PUBLIC KEYS
            table.addColumn(RedeemPointActorDatabaseConstants.REGISTERED_ASSET_ISSUERS_REDEEM_POINT_PUBLICKEY_COLUMN, DatabaseDataType.STRING, 256, Boolean.TRUE);
            table.addColumn(RedeemPointActorDatabaseConstants.REGISTERED_ASSET_ISSUERS_ISSUER_PUBLICKEY_COLUMN, DatabaseDataType.STRING, 256, Boolean.FALSE);
            table.addIndex(RedeemPointActorDatabaseConstants.REGISTERED_ASSET_ISSUERS_FIRST_KEY_COLUMN);
            try {
                //Create the table
                databaseFactory.createTable(ownerId, table);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
            }

            /**
             * Create Redeem Point Crypto database table.
             */
            table = databaseFactory.newTableFactory(ownerId, RedeemPointActorDatabaseConstants.REDEEM_POINT_CRYPTO_TABLE_NAME);

            table.addColumn(RedeemPointActorDatabaseConstants.REDEEM_POINT_CRYPTO_PUBLIC_KEY_COLUMN_NAME, DatabaseDataType.STRING, 256, Boolean.TRUE);
            table.addColumn(RedeemPointActorDatabaseConstants.REDEEM_POINT_CRYPTO_CRYPTO_ADDRESS_COLUMN_NAME, DatabaseDataType.STRING, 256, Boolean.FALSE);
            table.addColumn(RedeemPointActorDatabaseConstants.REDEEM_POINT_CRYPTO_CRYPTO_CURRENCY_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(RedeemPointActorDatabaseConstants.REDEEM_POINT_CRYPTO_NETWORK_TYPE_COLUMN_NAME, DatabaseDataType.STRING, 50, Boolean.FALSE);

            table.addIndex(RedeemPointActorDatabaseConstants.REDEEM_POINT_CRYPTO_FIRST_KEY_COLUMN);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, table);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
            }

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
