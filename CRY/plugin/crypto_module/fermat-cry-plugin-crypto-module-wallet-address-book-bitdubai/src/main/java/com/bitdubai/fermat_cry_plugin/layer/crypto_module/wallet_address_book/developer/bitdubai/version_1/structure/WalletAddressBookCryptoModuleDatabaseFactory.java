package com.bitdubai.fermat_cry_plugin.layer.crypto_module.wallet_address_book.developer.bitdubai.version_1.structure;

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
 * The interface <code>com.bitdubai.fermat_cry_plugin.layer.crypto_module.wallet_address_book.developer.bitdubai.version_1.structure.WalletAddressBookCryptoModuleDatabaseFactory</code>
 * create all the wallet crypto address book tables.
 * <p>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 18/06/15.
 *
 * @version 1.0
 */
public class WalletAddressBookCryptoModuleDatabaseFactory implements DealsWithPluginDatabaseSystem {

    /**
     * DealsWithPluginDatabaseSystem Interface member variables.
     */
    private PluginDatabaseSystem pluginDatabaseSystem;

    /***
     *  Creates the Database identified by the pluginId and the CRYPTO_WALLET_ADDRESS_BOOK_TABLE
     * @param pluginId
     * @return
     * @throws CantCreateDatabaseException
     */
    public Database createDatabase(UUID pluginId) throws CantCreateDatabaseException {
        try {
            Database database = this.pluginDatabaseSystem.createDatabase(pluginId, pluginId.toString());
            DatabaseFactory databaseFactory = database.getDatabaseFactory();
            DatabaseTableFactory cryptoAddressBookTable = createTableCryptoAddressBookTable(pluginId, databaseFactory);
            databaseFactory.createTable(pluginId, cryptoAddressBookTable);
            return database;
        } catch (CantCreateDatabaseException cantCreateDatabaseException) {
            throw cantCreateDatabaseException;
        }  catch (InvalidOwnerIdException invalidOwnerId) {
            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, invalidOwnerId, "", "There is a problem with the ownerId of the database.");
        } catch (CantCreateTableException cantCreateTableException) {
            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
        } catch(Exception exception){
            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, "Unchecked Exception, check the cause");
        }
    }

    /**
     * DealsWithPluginDatabaseSystem Interface implementation.
     */
    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    private DatabaseTableFactory createTableCryptoAddressBookTable(final UUID pluginId, final DatabaseFactory databaseFactory) throws InvalidOwnerIdException {
        DatabaseTableFactory tableFactory = databaseFactory.newTableFactory(pluginId, WalletAddressBookCryptoModuleDatabaseConstants.CRYPTO_WALLET_ADDRESS_BOOK_TABLE_NAME);
        tableFactory.addColumn(WalletAddressBookCryptoModuleDatabaseConstants.CRYPTO_WALLET_ADDRESS_BOOK_TABLE_ID, DatabaseDataType.STRING, 36, true);
        tableFactory.addColumn(WalletAddressBookCryptoModuleDatabaseConstants.CRYPTO_WALLET_ADDRESS_BOOK_TABLE_WALLET_PUBLIC_KEY, DatabaseDataType.STRING, 132, false);
        tableFactory.addColumn(WalletAddressBookCryptoModuleDatabaseConstants.CRYPTO_WALLET_ADDRESS_BOOK_TABLE_WALLET_TYPE, DatabaseDataType.STRING, 3, false);
        tableFactory.addColumn(WalletAddressBookCryptoModuleDatabaseConstants.CRYPTO_WALLET_ADDRESS_BOOK_TABLE_CRYPTO_ADDRESS, DatabaseDataType.STRING, 34, false);
        tableFactory.addColumn(WalletAddressBookCryptoModuleDatabaseConstants.CRYPTO_WALLET_ADDRESS_BOOK_TABLE_CRYPTO_CURRENCY, DatabaseDataType.STRING, 3, false);
        tableFactory.addColumn(WalletAddressBookCryptoModuleDatabaseConstants.CRYPTO_WALLET_ADDRESS_BOOK_TABLE_TIME_STAMP, DatabaseDataType.LONG_INTEGER, 0, false);
        tableFactory.addIndex(WalletAddressBookCryptoModuleDatabaseConstants.CRYPTO_WALLET_ADDRESS_FIRST_KEY_COLUMN_NAME);
        return tableFactory;
    }
}
