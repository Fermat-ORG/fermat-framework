package com.bitdubai.fermat_cbp_plugin.layer.identity.crypto_broker.developer.bitdubai.version_1.database;

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
 * The Class  <code>com.bitdubai.fermat_cbp_plugin.layer.identity.crypto_broker.developer.bitdubai.version_1.database.Crypto BrokerIdentityDatabaseFactory</code>
 * is responsible for creating the tables in the database where it is to keep the information.
 * <p/>
 * <p/>
 * Created by Jorge Gonzalez - (jorgeejgonzalez@gmail.com) on 28/09/15.
 * Updated by lnacosta (laion.cj91@gmail.com) on 25/11/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public final class CryptoBrokerIdentityDatabaseFactory {

    private PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * Constructor with parameters to instantiate class.
     */
    public CryptoBrokerIdentityDatabaseFactory(final PluginDatabaseSystem pluginDatabaseSystem) {

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
    protected Database createDatabase(final UUID ownerId,
                                      final String databaseName) throws CantCreateDatabaseException {

        try {

            Database database = this.pluginDatabaseSystem.createDatabase(ownerId, databaseName);

            DatabaseTableFactory table;
            DatabaseFactory databaseFactory = database.getDatabaseFactory();

            /**
             * Create Crypto Broker table.
             */
            table = databaseFactory.newTableFactory(ownerId, CryptoBrokerIdentityDatabaseConstants.CRYPTO_BROKER_TABLE_NAME);

            table.addColumn(CryptoBrokerIdentityDatabaseConstants.CRYPTO_BROKER_PUBLIC_KEY_COLUMN_NAME, DatabaseDataType.TEXT, 130, Boolean.TRUE);
            table.addColumn(CryptoBrokerIdentityDatabaseConstants.CRYPTO_BROKER_ALIAS_COLUMN_NAME, DatabaseDataType.TEXT, 100, Boolean.FALSE);
            table.addColumn(CryptoBrokerIdentityDatabaseConstants.CRYPTO_BROKER_DEVICE_USER_PUBLIC_KEY_COLUMN_NAME, DatabaseDataType.TEXT, 130, Boolean.FALSE);
            table.addColumn(CryptoBrokerIdentityDatabaseConstants.CRYPTO_BROKER_EXPOSURE_LEVEL_COLUMN_NAME, DatabaseDataType.TEXT, 10, Boolean.FALSE);
            table.addColumn(CryptoBrokerIdentityDatabaseConstants.CRYPTO_BROKER_ACCURACY_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0, Boolean.FALSE);
            table.addColumn(CryptoBrokerIdentityDatabaseConstants.CRYPTO_BROKER_FRECUENCY_COLUMN_NAME, DatabaseDataType.STRING, 10, Boolean.FALSE);
            table.addColumn(CryptoBrokerIdentityDatabaseConstants.CRYPTO_BROKER_PAYMENT_CURRENCY_COLUMN_NAME, DatabaseDataType.STRING, 10, Boolean.FALSE);
            table.addColumn(CryptoBrokerIdentityDatabaseConstants.CRYPTO_BROKER_MERCHANDISE_CURRENCY_COLUMN_NAME, DatabaseDataType.STRING, 10, Boolean.FALSE);
            table.addColumn(CryptoBrokerIdentityDatabaseConstants.CRYPTO_BROKER_EXTRA_TEXT_COLUMN_NAME, DatabaseDataType.STRING, 40, Boolean.FALSE);

            table.addIndex(CryptoBrokerIdentityDatabaseConstants.CRYPTO_BROKER_FIRST_KEY_COLUMN);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, table);
            } catch (CantCreateTableException e) {
                throw new CantCreateDatabaseException(e, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
            }

            return database;

        } catch (final CantCreateDatabaseException e) {
            /**
             * I can not handle this situation.
             */
            throw new CantCreateDatabaseException(e, "", "Exception not handled by the plugin, There is a problem and i cannot create the database.");
        } catch (final InvalidOwnerIdException e) {
            /**
             * This shouldn't happen here because I was the one who gave the owner id to the database file system,
             * but anyway, if this happens, I can not continue.
             */
            throw new CantCreateDatabaseException(e, "", "There is a problem with the ownerId of the database.");
        }

    }
}