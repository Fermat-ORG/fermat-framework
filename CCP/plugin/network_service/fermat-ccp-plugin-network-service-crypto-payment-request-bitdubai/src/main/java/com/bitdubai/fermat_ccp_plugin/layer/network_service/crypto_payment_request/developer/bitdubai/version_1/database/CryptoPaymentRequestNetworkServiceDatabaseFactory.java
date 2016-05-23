package   com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_payment_request.developer.bitdubai.version_1.database;

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
 *  The Class  <code>com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_payment_request.developer.bitdubai.version_1.database.CryptoPaymentRequestNetworkServiceDatabaseFactory</code>
 * is responsible for creating the tables in the database where it is to keep the information.
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 01/10/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CryptoPaymentRequestNetworkServiceDatabaseFactory {

    private final PluginDatabaseSystem pluginDatabaseSystem;

    public CryptoPaymentRequestNetworkServiceDatabaseFactory(final PluginDatabaseSystem pluginDatabaseSystem) {

        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    public Database createDatabase(final UUID   ownerId     ,
                                      final String databaseName) throws CantCreateDatabaseException {

        try {

            Database database = this.pluginDatabaseSystem.createDatabase(ownerId, databaseName);

            DatabaseTableFactory table;
            DatabaseFactory databaseFactory = database.getDatabaseFactory();

            /**
             * Create Crypto Address Request table.
             */
            table = databaseFactory.newTableFactory(ownerId, CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_TABLE_NAME);

            table.addColumn(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_REQUEST_ID_COLUMN_NAME         , DatabaseDataType.STRING      ,  40, Boolean.TRUE );
            table.addColumn(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_IDENTITY_PUBLIC_KEY_COLUMN_NAME, DatabaseDataType.STRING      , 130, Boolean.FALSE);
            table.addColumn(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_IDENTITY_TYPE_COLUMN_NAME      , DatabaseDataType.STRING      ,  10, Boolean.FALSE);
            table.addColumn(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_ACTOR_PUBLIC_KEY_COLUMN_NAME   , DatabaseDataType.STRING      , 130, Boolean.FALSE);
            table.addColumn(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_ACTOR_TYPE_COLUMN_NAME         , DatabaseDataType.STRING      ,  10, Boolean.FALSE);
            table.addColumn(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_DESCRIPTION_COLUMN_NAME        , DatabaseDataType.STRING      , 200, Boolean.FALSE);
            table.addColumn(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_CRYPTO_ADDRESS_COLUMN_NAME     , DatabaseDataType.STRING      , 130, Boolean.FALSE);
            table.addColumn(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_CRYPTO_CURRENCY_COLUMN_NAME    , DatabaseDataType.STRING      ,  10, Boolean.FALSE);
            table.addColumn(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_AMOUNT_COLUMN_NAME             , DatabaseDataType.LONG_INTEGER,   0, Boolean.FALSE);
            table.addColumn(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_START_TIME_STAMP_COLUMN_NAME   , DatabaseDataType.LONG_INTEGER,   0, Boolean.FALSE);
            table.addColumn(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_DIRECTION_COLUMN_NAME          , DatabaseDataType.STRING      ,  10, Boolean.FALSE);
            table.addColumn(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_ACTION_COLUMN_NAME             , DatabaseDataType.STRING      ,  10, Boolean.FALSE);
            table.addColumn(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_PROTOCOL_STATE_COLUMN_NAME     , DatabaseDataType.STRING      ,  10, Boolean.FALSE);
            table.addColumn(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_NETWORK_TYPE_COLUMN_NAME       , DatabaseDataType.STRING      ,  10, Boolean.FALSE);
            table.addColumn(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_WALLET_REFERENCE_TYPE_COLUMN_NAME, DatabaseDataType.STRING      ,  10, Boolean.FALSE);
            table.addColumn(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_SENT_COUNT_COLUMN_NAME,          DatabaseDataType.INTEGER      ,  0, Boolean.FALSE);
            table.addColumn(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_MESSAGE_TYPE_COLUMN_NAME,        DatabaseDataType.STRING       ,  3, Boolean.FALSE);
            table.addColumn(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_WALLET_PUBLIC_KEY_COLUMN_NAME,        DatabaseDataType.STRING       , 10, Boolean.FALSE);

            table.addIndex(CryptoPaymentRequestNetworkServiceDatabaseConstants.CRYPTO_PAYMENT_REQUEST_FIRST_KEY_COLUMN);

            try {
                //Create the table
                databaseFactory.createTable(ownerId, table);

            } catch (CantCreateTableException cantCreateTableException) {

                throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
            }

            return database;

        } catch (InvalidOwnerIdException invalidOwnerId) {

            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, invalidOwnerId, "", "There is a problem with the ownerId of the database.");
        } catch (CantCreateDatabaseException cantCreateDatabaseException) {

            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateDatabaseException, "", "Exception not handled by the plugin, There is a problem and i cannot create the database.");
        }
    }

}