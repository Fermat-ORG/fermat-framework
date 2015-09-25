package   com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.database;

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
 *  The Class  <code>com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_addresses.developer.bitdubai.version_1.database.Crypto AddressesNetworkServiceDatabaseFactory</code>
 * is responsible for creating the tables in the database where it is to keep the information.
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 22/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CryptoAddressesNetworkServiceDatabaseFactory  {

    private final PluginDatabaseSystem pluginDatabaseSystem;

    public CryptoAddressesNetworkServiceDatabaseFactory(final PluginDatabaseSystem pluginDatabaseSystem) {

        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    protected Database createDatabase(UUID   ownerId     ,
                                      String databaseName) throws CantCreateDatabaseException {

        try {

            Database database = this.pluginDatabaseSystem.createDatabase(
                    ownerId     ,
                    databaseName
            );

            DatabaseTableFactory table;
            DatabaseFactory databaseFactory = database.getDatabaseFactory();

            /**
             * Create Crypto Address Request table.
             */
            table = databaseFactory.newTableFactory(ownerId, CryptoAddressesNetworkServiceDatabaseConstants.CRYPTO_ADDRESS_REQUEST_TABLE_NAME);

            table.addColumn(CryptoAddressesNetworkServiceDatabaseConstants.CRYPTO_ADDRESS_REQUEST_REQUEST_ID_COLUMN_NAME                    , DatabaseDataType.STRING,  40, Boolean.TRUE );
            table.addColumn(CryptoAddressesNetworkServiceDatabaseConstants.CRYPTO_ADDRESS_REQUEST_WALLET_PUBLIC_KEY_COLUMN_NAME             , DatabaseDataType.STRING, 130, Boolean.FALSE);
            table.addColumn(CryptoAddressesNetworkServiceDatabaseConstants.CRYPTO_ADDRESS_REQUEST_IDENTITY_TYPE_REQUESTING_COLUMN_NAME      , DatabaseDataType.STRING,  10, Boolean.FALSE);
            table.addColumn(CryptoAddressesNetworkServiceDatabaseConstants.CRYPTO_ADDRESS_REQUEST_IDENTITY_TYPE_ACCEPTING_COLUMN_NAME       , DatabaseDataType.STRING,  10, Boolean.FALSE);
            table.addColumn(CryptoAddressesNetworkServiceDatabaseConstants.CRYPTO_ADDRESS_REQUEST_IDENTITY_PUBLIC_KEY_REQUESTING_COLUMN_NAME, DatabaseDataType.STRING, 130, Boolean.FALSE);
            table.addColumn(CryptoAddressesNetworkServiceDatabaseConstants.CRYPTO_ADDRESS_REQUEST_IDENTITY_PUBLIC_KEY_ACCEPTING_COLUMN_NAME , DatabaseDataType.STRING, 130, Boolean.FALSE);
            table.addColumn(CryptoAddressesNetworkServiceDatabaseConstants.CRYPTO_ADDRESS_REQUEST_CRYPTO_ADDRESS_FROM_REQUEST_COLUMN_NAME   , DatabaseDataType.STRING, 130, Boolean.FALSE);
            table.addColumn(CryptoAddressesNetworkServiceDatabaseConstants.CRYPTO_ADDRESS_REQUEST_CRYPTO_ADDRESS_FROM_RESPONSE_COLUMN_NAME  , DatabaseDataType.STRING, 130, Boolean.FALSE);
            table.addColumn(CryptoAddressesNetworkServiceDatabaseConstants.CRYPTO_ADDRESS_REQUEST_CRYPTO_CURRENCY_COLUMN_NAME               , DatabaseDataType.STRING,  10, Boolean.FALSE);
            table.addColumn(CryptoAddressesNetworkServiceDatabaseConstants.CRYPTO_ADDRESS_REQUEST_BLOCKCHAIN_NETWORK_TYPE_COLUMN_NAME       , DatabaseDataType.STRING,  10, Boolean.FALSE);
            table.addColumn(CryptoAddressesNetworkServiceDatabaseConstants.CRYPTO_ADDRESS_REQUEST_STATE_COLUMN_NAME                         , DatabaseDataType.STRING,  10, Boolean.FALSE);

            table.addIndex(CryptoAddressesNetworkServiceDatabaseConstants.CRYPTO_ADDRESS_REQUEST_FIRST_KEY_COLUMN);

            try {

                databaseFactory.createTable(ownerId, table);
            } catch (CantCreateTableException cantCreateTableException) {

                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
            }

            return database;

        } catch (CantCreateDatabaseException cantCreateDatabaseException) {

            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateDatabaseException, "", "Exception not handled by the plugin, There is a problem and i cannot create the database.");
        } catch (InvalidOwnerIdException invalidOwnerId) {

            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, invalidOwnerId, "", "There is a problem with the ownerId of the database.");
        }
    }
}