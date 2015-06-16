package com.bitdubai.fermat_cry_plugin.layer.crypto_module.wallet_address_book.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseDataType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateTableException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.InvalidOwnerId;
import java.util.UUID;

/**
 * Created by armando on 27/05/15.
 */
class WalletAddressBookDataBaseFactory implements DealsWithPluginDatabaseSystem
{
    /**
     * DealsWithPluginDatabaseSystem Interface member variables.
     */
    private PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * DealsWithPluginDatabaseSystem Interface implementation.
     */
    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    Database createDatabase(UUID walletId) throws CantCreateDatabaseException {

        Database database;

        /**
         * I will create the database where I am going to store the information
         of this wallet.
         */
        try {

            database = this.pluginDatabaseSystem.createDatabase(walletId,WalletAddressBookDataBaseConstants.CRYPTO_WALLET_ADDRESS_BOOK_TABLE_NAME);

        }
        catch (CantCreateDatabaseException cantCreateDatabaseException){

            /**
             * I can not handle this situation.
             */

            throw new CantCreateDatabaseException();
        }


        /**
         * Next, I will add the needed tables.
         */
        try {

            DatabaseTableFactory table;

            /**
             * First the Address book table.
             */
            table = ((DatabaseFactory) database).newTableFactory(walletId,WalletAddressBookDataBaseConstants.CRYPTO_WALLET_ADDRESS_BOOK_TABLE_NAME);

            table.addColumn(WalletAddressBookDataBaseConstants.CRYPTO_WALLET_ADDRESS_BOOK_TABLE_ID, DatabaseDataType.STRING, 36,true);

            table.addColumn(WalletAddressBookDataBaseConstants.CRYPTO_WALLET_ADDRESS_BOOK_TABLE_ID_WALLET, DatabaseDataType.STRING, 36,false);

            table.addColumn(WalletAddressBookDataBaseConstants.CRYPTO_WALLET_ADDRESS_BOOK_TABLE_CRYPTO_ADDRESS, DatabaseDataType.STRING,33,false);

            table.addColumn(WalletAddressBookDataBaseConstants.CRYPTO_WALLET_ADDRESS_BOOK_TABLE_TIME_STAMP, DatabaseDataType.LONG_INTEGER,0,false);

            table.addIndex(WalletAddressBookDataBaseConstants.CRYPTO_WALLET_ADDRESSES_FIRST_KEY_COLUMN_NAME);

            try {
                ((DatabaseFactory) database).createTable(walletId, table);
            }
            catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException();
            }


        }
        catch (InvalidOwnerId invalidOwnerId) {
            /**
             * This shouldn't happen here because I was the one who gave the
             owner id to the database file system,
             * but anyway, if this happens, I can not continue.
             * * *
             */
            throw new CantCreateDatabaseException();
        }

        return database;
    }
}
