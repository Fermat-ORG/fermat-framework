package com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.osa_android_xxx.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android_xxx.database_system.DatabaseDataType;
import com.bitdubai.fermat_api.layer.osa_android_xxx.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android_xxx.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer.osa_android_xxx.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android_xxx.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android_xxx.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android_xxx.database_system.exceptions.CantCreateTableException;
import com.bitdubai.fermat_api.layer.osa_android_xxx.database_system.exceptions.InvalidOwnerId;

import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.structure.WalletContactsDatabaseFactory</code>
 * is responsible for creating the tables in the database where it is to keep the information.
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 05/06/15.
 * @version 1.0
 */
public class CryptoWalletContactsDatabaseFactory implements DealsWithPluginDatabaseSystem {


    /**
     * DealsWithPluginDatabaseSystem Interface member variables.
     */
    private PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * Constructor with parameters to instantiate class
     * .
     * @param pluginDatabaseSystem DealsWithPluginDatabaseSystem
     */
    public CryptoWalletContactsDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /**
     * Create the database
     *
     * @param ownerId the owner id
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

        } catch (CantCreateDatabaseException cantCreateDatabaseException){

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
             * Create the Wallet Contacts Address Book table.
             */
            table = ((DatabaseFactory) database).newTableFactory(ownerId, CryptoWalletContactsDatabaseConstants.CRYPTO_WALLET_CONTACTS_ADDRESS_BOOK_TABLE_NAME);

            table.addColumn(CryptoWalletContactsDatabaseConstants.CRYPTO_WALLET_CONTACTS_ADDRESS_BOOK_TABLE_CONTACT_ID_COLUMN_NAME, DatabaseDataType.STRING, 36, Boolean.TRUE);
            table.addColumn(CryptoWalletContactsDatabaseConstants.CRYPTO_WALLET_CONTACTS_ADDRESS_BOOK_TABLE_WALLET_ID_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(CryptoWalletContactsDatabaseConstants.CRYPTO_WALLET_CONTACTS_ADDRESS_BOOK_TABLE_USER_ID_COLUMN_NAME, DatabaseDataType.STRING, 36, Boolean.FALSE);
            table.addColumn(CryptoWalletContactsDatabaseConstants.CRYPTO_WALLET_CONTACTS_ADDRESS_BOOK_TABLE_USER_TYPE_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(CryptoWalletContactsDatabaseConstants.CRYPTO_WALLET_CONTACTS_ADDRESS_BOOK_TABLE_USER_NAME_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(CryptoWalletContactsDatabaseConstants.CRYPTO_WALLET_CONTACTS_ADDRESS_BOOK_TABLE_DELIVERED_USER_CRYPTO_ADDRESS_COLUMN_NAME, DatabaseDataType.STRING, 36, Boolean.FALSE);
            table.addColumn(CryptoWalletContactsDatabaseConstants.CRYPTO_WALLET_CONTACTS_ADDRESS_BOOK_TABLE_DELIVERED_ADDRESS_CRYPTO_CURRENCY_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addColumn(CryptoWalletContactsDatabaseConstants.CRYPTO_WALLET_CONTACTS_ADDRESS_BOOK_TABLE_RECEIVED_USER_CRYPTO_ADDRESS_COLUMN_NAME, DatabaseDataType.STRING, 36, Boolean.FALSE);
            table.addColumn(CryptoWalletContactsDatabaseConstants.CRYPTO_WALLET_CONTACTS_ADDRESS_BOOK_TABLE_RECEIVED_ADDRESS_CRYPTO_CURRENCY_COLUMN_NAME, DatabaseDataType.STRING, 100, Boolean.FALSE);
            table.addIndex(CryptoWalletContactsDatabaseConstants.CRYPTO_WALLET_CONTACTS_ADDRESS_BOOK_FIRST_KEY_COLUMN);

            try {
                //Create the table
                ((DatabaseFactory) database).createTable(ownerId, table);
            } catch (CantCreateTableException cantCreateTableException) {
                throw new CantCreateDatabaseException();
            }
        } catch (InvalidOwnerId invalidOwnerId) {
            /**
             * This shouldn't happen here because I was the one who gave the owner id to the database file system,
             * but anyway, if this happens, I can not continue.
             */
            throw new CantCreateDatabaseException();
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