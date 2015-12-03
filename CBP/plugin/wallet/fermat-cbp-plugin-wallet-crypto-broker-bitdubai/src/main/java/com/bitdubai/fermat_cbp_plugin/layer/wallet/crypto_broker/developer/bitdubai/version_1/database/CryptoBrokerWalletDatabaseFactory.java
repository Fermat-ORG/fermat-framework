package   com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.FermatException;
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
 *  The Class  <code>com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.database.Crypto BrokerWalletDatabaseFactory</code>
 * is responsible for creating the tables in the database where it is to keep the information.
 * <p/>
 *
 * Created by Yordin Alayn - (y.alayn@gmail.com) on 30/10/15.
 * Modified by Franklin Marcano 01.12.2015
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CryptoBrokerWalletDatabaseFactory implements DealsWithPluginDatabaseSystem {
    /**
     * DealsWithPluginDatabaseSystem Interface member variables.
     */
    private PluginDatabaseSystem pluginDatabaseSystem;
    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    public Database createDatabase(UUID ownerId, UUID walletId) throws CantCreateDatabaseException {
        Database database = null;
        try {
            database = this.pluginDatabaseSystem.createDatabase(ownerId, walletId.toString());
            createStockWalletTable(ownerId, database.getDatabaseFactory());
            createStockalletBalancesTable(ownerId, database.getDatabaseFactory());
            return database;
        } catch(CantCreateTableException exception){
            //catch(CantCreateTableException | CantInsertRecordException exception){
            if(database != null)
                database.closeDatabase();
            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, exception, null, "Check the cause");
        } catch(CantCreateDatabaseException exception){
            throw exception;
        } catch(Exception exception){
            if(database != null)
                database.closeDatabase();
            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

    private void createStockWalletTable(final UUID ownerId, final DatabaseFactory databaseFactory) throws CantCreateTableException{
        try{
            DatabaseTableFactory tableFactory = createStockWalletTableFactory(ownerId, databaseFactory);
            databaseFactory.createTable(tableFactory);
        } catch(InvalidOwnerIdException exception){
            throw new CantCreateTableException(CantCreateTableException.DEFAULT_MESSAGE, exception, null, "The ownerId of the database factory didn't match with the given owner id");
        }
    }

    private void createStockalletBalancesTable(final UUID ownerId, final DatabaseFactory databaseFactory) throws CantCreateTableException{
        try{
            DatabaseTableFactory tableFactory = createStockWalletBalanceTableFactory(ownerId, databaseFactory);
            databaseFactory.createTable(tableFactory);
        }catch(InvalidOwnerIdException exception){
            throw new CantCreateTableException(CantCreateTableException.DEFAULT_MESSAGE, exception, null, "The ownerId of the database factory didn't match with the given owner id");
        }
    }

    private DatabaseTableFactory createStockWalletTableFactory(final UUID ownerId, final DatabaseFactory databaseFactory) throws InvalidOwnerIdException{
        DatabaseTableFactory table = databaseFactory.newTableFactory(ownerId, CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_TABLE_NAME);
        table.addColumn(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_TRANSACTION_ID_COLUMN_NAME, DatabaseDataType.STRING, 36, true);
        table.addColumn(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_AMOUNT_COLUMN_NAME, DatabaseDataType.REAL, 0,false);
        table.addColumn(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_PRICE_REFERENCE_COLUMN_NAME, DatabaseDataType.REAL, 0, false);
        table.addColumn(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_MERCHANDISE_COLUMN_NAME, DatabaseDataType.STRING, 20, false);
        table.addColumn(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_CURRENCY_TYPE_COLUMN_NAME, DatabaseDataType.STRING, 20, false);
        table.addColumn(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_TRANSACTION_TYPE_COLUMN_NAME, DatabaseDataType.STRING, 20, false);
        table.addColumn(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_BALANCE_TYPE_COLUMN_NAME, DatabaseDataType.STRING, 20, false);
        table.addColumn(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_TIMESTAMP_COLUMN_NAME, DatabaseDataType.STRING, 30, false);
        table.addColumn(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_MEMO_COLUMN_NAME, DatabaseDataType.STRING, 200, false);
        table.addColumn(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_ORIGIN_TRANSACTION_COLUMN_NAME, DatabaseDataType.STRING, 20, false);
        table.addColumn(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_RUNNING_BOOK_BALANCE_COLUMN_NAME, DatabaseDataType.REAL, 0, false);
        table.addColumn(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_TRANSACTIONS_RUNNING_AVAILABLE_BALANCE_COLUMN_NAME, DatabaseDataType.REAL, 0, false);


        return table;
    }

    private DatabaseTableFactory createStockWalletBalanceTableFactory(final UUID ownerId, final DatabaseFactory databaseFactory) throws InvalidOwnerIdException{
        DatabaseTableFactory table = databaseFactory.newTableFactory(ownerId, CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_BALANCE_TABLE_NAME);
        table.addColumn(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_BALANCE_MERCHANDISE_COLUMN_NAME, DatabaseDataType.STRING, 20, false);
        table.addColumn(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_BALANCE_CURRENCY_TYPE_COLUMN_NAME, DatabaseDataType.STRING, 20, false);
        table.addColumn(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_BALANCE_AVAILABLE_BALANCE_COLUMN_NAME, DatabaseDataType.REAL, 0, false);
        table.addColumn(CryptoBrokerWalletDatabaseConstants.CRYPTO_BROKER_STOCK_BALANCE_BOOK_BALANCE_COLUMN_NAME, DatabaseDataType.REAL, 0, false);

        return table;
    }
}