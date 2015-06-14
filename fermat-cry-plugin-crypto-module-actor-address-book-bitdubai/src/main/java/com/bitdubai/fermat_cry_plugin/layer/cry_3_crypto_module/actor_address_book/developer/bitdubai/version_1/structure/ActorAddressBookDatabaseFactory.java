package com.bitdubai.fermat_cry_plugin.layer.cry_3_crypto_module.actor_address_book.developer.bitdubai.version_1.structure;

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
 * Created by Natalia on 30/03/2015.
 */
 class ActorAddressBookDatabaseFactory implements DealsWithPluginDatabaseSystem {

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

    Database createDatabase(UUID ownerId, UUID walletId) throws CantCreateDatabaseException {

        Database database;

        /**
         * I will create the database where I am going to store the information of this wallet.
         */
        try {

            database = this.pluginDatabaseSystem.createDatabase(ownerId, walletId.toString());

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
            table = ((DatabaseFactory) database).newTableFactory(ownerId, ActorAddressBookDatabaseConstants.CRYPTO_ADDRESS_BOOK_TABLE_NAME);
            table.addColumn(ActorAddressBookDatabaseConstants.CRYPTO_ADDRESS_BOOK_TABLE_ID, DatabaseDataType.STRING, 36,true);
            table.addColumn(ActorAddressBookDatabaseConstants.CRYPTO_ADDRESS_BOOK_TABLE_ID_USER, DatabaseDataType.STRING, 36,false);
            table.addColumn(ActorAddressBookDatabaseConstants.CRYPTO_ADDRESS_BOOK_TABLE_USER_TYPE, DatabaseDataType.STRING, 3,false);
            table.addColumn(ActorAddressBookDatabaseConstants.CRYPTO_ADDRESS_BOOK_TABLE_CRYPTO_ADDRESS, DatabaseDataType.STRING, 33,false);
            table.addColumn(ActorAddressBookDatabaseConstants.CRYPTO_ADDRESS_BOOK_TABLE_TIME_STAMP, DatabaseDataType.LONG_INTEGER, 0,false);
            table.addIndex(ActorAddressBookDatabaseConstants.CRYPTO_ADDRESSES_FIRST_KEY_COLUMN_NAME);

            try {
                ((DatabaseFactory) database).createTable(ownerId, table);
            }
            catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException();
            }


        }
        catch (InvalidOwnerId invalidOwnerId) {
            /**
             * This shouldn't happen here because I was the one who gave the owner id to the database file system,
             * but anyway, if this happens, I can not continue.
             * * *
             */
            throw new CantCreateDatabaseException();
        }

        return database;
    }
}
