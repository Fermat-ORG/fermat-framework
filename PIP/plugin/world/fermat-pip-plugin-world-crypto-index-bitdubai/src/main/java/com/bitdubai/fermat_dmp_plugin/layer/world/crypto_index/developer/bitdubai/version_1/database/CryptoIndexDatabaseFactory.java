package com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.database;

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
 * Created by francisco on 09/09/15.
 */
public class CryptoIndexDatabaseFactory {
    /**
     * DealsWithPluginDatabaseSystem Interface member variables.
     */
    private PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * Constructor with parameters to instantiate class
     * .
     *
     * @param pluginDatabaseSystem DealsWithPluginDatabaseSystem
     */
    public CryptoIndexDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /**
     * @param ownerId
     * @param databaseName
     * @return
     * @throws CantCreateDatabaseException
     */
    public Database createDatabase(UUID ownerId, String databaseName) throws CantCreateDatabaseException {
        Database database = null;
        try {
            database = this.pluginDatabaseSystem.createDatabase(ownerId, databaseName);
            DatabaseTableFactory table;
            DatabaseFactory databaseFactory = database.getDatabaseFactory();

            table = databaseFactory.newTableFactory(ownerId, CryptoIndexDatabaseConstants.CRYPTO_INDEX_TABLE_NAME);

            table.addColumn(CryptoIndexDatabaseConstants.CRYPTO_INDEX_PRIMARY_KEY_COLUMN_NAME, DatabaseDataType.INTEGER, 0, Boolean.TRUE);
            table.addColumn(CryptoIndexDatabaseConstants.CRYPTO_INDEX_CRYPTO_CURRENCY_COLUMN_NAME, DatabaseDataType.STRING, 0, Boolean.FALSE);
            table.addColumn(CryptoIndexDatabaseConstants.CRYPTO_INDEX_FIAT_CURRENCY_COLUMN_NAME, DatabaseDataType.STRING, 0, Boolean.FALSE);
            table.addColumn(CryptoIndexDatabaseConstants.CRYPTO_INDEX_EXCHANGE_RATE_COLUMN_NAME, DatabaseDataType.MONEY, 0, Boolean.FALSE);
            table.addColumn(CryptoIndexDatabaseConstants.CRYPTO_INDEX_TIME_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0, Boolean.FALSE);

            table.addIndex(CryptoIndexDatabaseConstants.CRYPTO_INDEX_FIRST_KEY_COLUMN);
            databaseFactory.createTable(ownerId, table);

            return database;

        } catch (CantCreateTableException cantCreateTableException) {

            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "Crypto Index", "Exception not handled by the plugin, There is a problem and i cannot create the table.");

        } catch (InvalidOwnerIdException e) {

        }

        return database;
    }
}
