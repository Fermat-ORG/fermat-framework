package   com.bitdubai.fermat_dmp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.database;

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
 *  The Class  <code>com.bitdubai.fermat_dmp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.database.Crypto AddressesNetworkServiceDatabaseFactory</code>
 * is responsible for creating the tables in the database where it is to keep the information.
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 22/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CryptoAddressesNetworkServiceDatabaseFactory implements DealsWithPluginDatabaseSystem {

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
    public CryptoAddressesNetworkServiceDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem) {
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
    protected Database createDatabase(UUID ownerId, String databaseName) throws CantCreateDatabaseException {
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
             * Create Crypto Address Request table.
             */
            table = databaseFactory.newTableFactory(ownerId, CryptoAddressesNetworkServiceDatabaseConstants.CRYPTO_ADDRESS_REQUEST_TABLE_NAME);

            table.addColumn(CryptoAddressesNetworkServiceDatabaseConstants.CRYPTO_ADDRESS_REQUEST_REQUEST_ID_COLUMN_NAME, DatabaseDataType.STRING, 40, Boolean.TRUE);
            table.addColumn(CryptoAddressesNetworkServiceDatabaseConstants.CRYPTO_ADDRESS_REQUEST_WALLET_PUBLIC_KEY_COLUMN_NAME, DatabaseDataType.STRING, 130, Boolean.FALSE);
            table.addColumn(CryptoAddressesNetworkServiceDatabaseConstants.CRYPTO_ADDRESS_REQUEST_CRYPTO_CURRENCY_COLUMN_NAME, DatabaseDataType.STRING, 130, Boolean.FALSE);
            table.addColumn(CryptoAddressesNetworkServiceDatabaseConstants.CRYPTO_ADDRESS_REQUEST_ACTOR_TYPE_COLUMN_NAME, DatabaseDataType.STRING, 10, Boolean.FALSE);
            table.addColumn(CryptoAddressesNetworkServiceDatabaseConstants.CRYPTO_ADDRESS_REQUEST_ACTOR_PUBLIC_KEY_REQUESTING_COLUMN_NAME, DatabaseDataType.STRING, 130, Boolean.FALSE);
            table.addColumn(CryptoAddressesNetworkServiceDatabaseConstants.CRYPTO_ADDRESS_REQUEST_CRYPTO_ADDRESS_TO_SEND_COLUMN_NAME, DatabaseDataType.STRING, 40, Boolean.FALSE);
            table.addColumn(CryptoAddressesNetworkServiceDatabaseConstants.CRYPTO_ADDRESS_REQUEST_ACTOR_PUBLIC_KEY_ACCEPTING_COLUMN_NAME, DatabaseDataType.STRING, 130, Boolean.FALSE);
            table.addColumn(CryptoAddressesNetworkServiceDatabaseConstants.CRYPTO_ADDRESS_REQUEST_CRYPTO_ADDRESS_TO_RECEIVE_COLUMN_NAME, DatabaseDataType.STRING, 40, Boolean.FALSE);
            table.addColumn(CryptoAddressesNetworkServiceDatabaseConstants.CRYPTO_ADDRESS_REQUEST_STATE_COLUMN_NAME, DatabaseDataType.STRING, 10, Boolean.FALSE);
            table.addColumn(CryptoAddressesNetworkServiceDatabaseConstants.CRYPTO_ADDRESS_REQUEST_RESULT_COLUMN_NAME, DatabaseDataType.STRING, 150, Boolean.FALSE);

            table.addIndex(CryptoAddressesNetworkServiceDatabaseConstants.CRYPTO_ADDRESS_REQUEST_FIRST_KEY_COLUMN);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, table);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
            }
        } catch (InvalidOwnerIdException invalidOwnerId) {
            /**
             * This shouldn't happen here because I was the one who gave the owner id to the database file system,
             * but anyway, if this happens, I can not continue.
             */
            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, invalidOwnerId, "", "There is a problem with the ownerId of the database.");
        }
        return database;
    }

    /**
     * DealsWithPluginDatabaseSystem Interface implementation.
     */
    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }
}