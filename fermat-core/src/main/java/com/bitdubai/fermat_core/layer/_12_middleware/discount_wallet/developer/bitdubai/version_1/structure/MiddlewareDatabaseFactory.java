package com.bitdubai.fermat_core.layer._12_middleware.discount_wallet.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer._2_os.database_system.*;
import com.bitdubai.fermat_api.layer._2_os.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer._2_os.database_system.exceptions.CantCreateTableException;
import com.bitdubai.fermat_api.layer._2_os.database_system.exceptions.InvalidOwnerId;

import java.util.UUID;

/**
 * Created by ciencias on 3/24/15.
 */
class MiddlewareDatabaseFactory implements DealsWithPluginDatabaseSystem{

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
            System.err.println("CantCreateDatabaseException: " + cantCreateDatabaseException.getMessage());
            cantCreateDatabaseException.printStackTrace();
            throw new CantCreateDatabaseException();
        }
        

        /**
         * Next, I will add the needed tables.
         */
        try {

            DatabaseTableFactory table;

            /**
             * First the fiat accounts table.
             */
            table = ((DatabaseFactory) database).newTableFactory(ownerId, MiddlewareDatabaseConstants.ACCOUNTS_TABLE_NAME);
            table.addColumn(MiddlewareDatabaseConstants.ACCOUNTS_TABLE_ID_COLUMN_NAME, DatabaseDataType.STRING, 36);
            table.addColumn(MiddlewareDatabaseConstants.ACCOUNTS_TABLE_LABEL_COLUMN_NAME, DatabaseDataType.STRING, 100);
            table.addColumn(MiddlewareDatabaseConstants.ACCOUNTS_TABLE_NAME_COLUMN_NAME, DatabaseDataType.STRING, 100);
            table.addColumn(MiddlewareDatabaseConstants.ACCOUNTS_TABLE_FIAT_CURRENCY_COLUMN_NAME, DatabaseDataType.STRING, 3);
            table.addColumn(MiddlewareDatabaseConstants.ACCOUNTS_TABLE_STATUS_COLUMN_NAME, DatabaseDataType.STRING, 3);

            try {
                ((DatabaseFactory) database).createTable(ownerId, table);
            }
            catch (CantCreateTableException cantCreateTableException) {
                System.err.println("CantCreateTableException: " + cantCreateTableException.getMessage());
                cantCreateTableException.printStackTrace();
                throw new CantCreateDatabaseException();
            }

            /**
             * Then the transfers table.
             */
            table = ((DatabaseFactory) database).newTableFactory(ownerId, MiddlewareDatabaseConstants.TRANSFERS_TABLE_NAME);
            table.addColumn(MiddlewareDatabaseConstants.TRANSFERS_TABLE_ID_COLUMN_NAME, DatabaseDataType.STRING, 36);
            table.addColumn(MiddlewareDatabaseConstants.TRANSFERS_TABLE_MEMO_COLUMN_NAME, DatabaseDataType.STRING, 100);
            table.addColumn(MiddlewareDatabaseConstants.TRANSFERS_TABLE_ID_ACCOUNT_FROM_COLUMN_NAME, DatabaseDataType.STRING, 36);
            table.addColumn(MiddlewareDatabaseConstants.TRANSFERS_TABLE_AMOUNT_FROM_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0);
            table.addColumn(MiddlewareDatabaseConstants.TRANSFERS_TABLE_ID_ACCOUNT_TO_COLUMN_NAME, DatabaseDataType.STRING, 36);
            table.addColumn(MiddlewareDatabaseConstants.TRANSFERS_TABLE_AMOUNT_TO_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0);
            table.addColumn(MiddlewareDatabaseConstants.TRANSFERS_TABLE_TIME_STAMP_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0);

            try {
                ((DatabaseFactory) database).createTable(ownerId, table);
            }
            catch (CantCreateTableException cantCreateTableException) {
                System.err.println("CantCreateTableException: " + cantCreateTableException.getMessage());
                cantCreateTableException.printStackTrace();
                throw new CantCreateDatabaseException();
            }

            /**
             * Then the value chunks table.
             */
            table = ((DatabaseFactory) database).newTableFactory(ownerId, MiddlewareDatabaseConstants.VALUE_CHUNKS_TABLE_NAME);
            table.addColumn(MiddlewareDatabaseConstants.VALUE_CHUNKS_TABLE_ID_COLUMN_NAME, DatabaseDataType.STRING, 36);
            table.addColumn(MiddlewareDatabaseConstants.VALUE_CHUNKS_TABLE_STATUS_COLUMN_NAME, DatabaseDataType.STRING, 3);
            table.addColumn(MiddlewareDatabaseConstants.VALUE_CHUNKS_TABLE_ID_PARENT_COLUMN_NAME, DatabaseDataType.STRING, 36);
            table.addColumn(MiddlewareDatabaseConstants.VALUE_CHUNKS_TABLE_FIAT_CURRENCY_COLUMN_NAME, DatabaseDataType.STRING, 3);
            table.addColumn(MiddlewareDatabaseConstants.VALUE_CHUNKS_TABLE_FIAT_AMOUNT_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0);
            table.addColumn(MiddlewareDatabaseConstants.VALUE_CHUNKS_TABLE_CRYPTO_CURRENCY_COLUMN_NAME, DatabaseDataType.STRING, 3);
            table.addColumn(MiddlewareDatabaseConstants.VALUE_CHUNKS_TABLE_CRYPTO_AMOUNT_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0);
            table.addColumn(MiddlewareDatabaseConstants.VALUE_CHUNKS_TABLE_ID_ACCOUNT_COLUMN_NAME, DatabaseDataType.STRING, 36);
            table.addColumn(MiddlewareDatabaseConstants.VALUE_CHUNKS_TABLE_TIME_STAMP_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0);
            table.addColumn(MiddlewareDatabaseConstants.VALUE_CHUNKS_TABLE_ID_CREDIT_COLUMN_NAME, DatabaseDataType.STRING, 36);
            table.addColumn(MiddlewareDatabaseConstants.VALUE_CHUNKS_TABLE_ID_DEBIT_COLUMN_NAME, DatabaseDataType.STRING, 36);
            table.addColumn(MiddlewareDatabaseConstants.VALUE_CHUNKS_TABLE_ID_TRANSFER_COLUMN_NAME, DatabaseDataType.STRING, 36);

            try {
                ((DatabaseFactory) database).createTable(ownerId, table);
            }
            catch (CantCreateTableException cantCreateTableException) {
                System.err.println("CantCreateTableException: " + cantCreateTableException.getMessage());
                cantCreateTableException.printStackTrace();
                throw new CantCreateDatabaseException();
            }

            /**
             * Then the debits table.
             */
            table = ((DatabaseFactory) database).newTableFactory(ownerId, MiddlewareDatabaseConstants.DEBITS_TABLE_NAME);
            table.addColumn(MiddlewareDatabaseConstants.DEBITS_TABLE_ID_COLUMN_NAME, DatabaseDataType.STRING, 36);
            table.addColumn(MiddlewareDatabaseConstants.DEBITS_TABLE_ID_FIAT_ACCOUNT_COLUMN_NAME, DatabaseDataType.STRING, 36);
            table.addColumn(MiddlewareDatabaseConstants.DEBITS_TABLE_FIAT_AMOUNT_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0);
            table.addColumn(MiddlewareDatabaseConstants.DEBITS_TABLE_CRYPTO_AMOUNT_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0);
            table.addColumn(MiddlewareDatabaseConstants.DEBITS_TABLE_TIME_STAMP_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0);

            try {
                ((DatabaseFactory) database).createTable(ownerId, table);
            }
            catch (CantCreateTableException cantCreateTableException) {
                System.err.println("CantCreateTableException: " + cantCreateTableException.getMessage());
                cantCreateTableException.printStackTrace();
                throw new CantCreateDatabaseException();
            }

            /**
             * Then the credits table.
             */
            table = ((DatabaseFactory) database).newTableFactory(ownerId, MiddlewareDatabaseConstants.CREDITS_TABLE_NAME);
            table.addColumn(MiddlewareDatabaseConstants.CREDITS_TABLE_ID_COLUMN_NAME, DatabaseDataType.STRING, 36);
            table.addColumn(MiddlewareDatabaseConstants.CREDITS_TABLE_ID_ACCOUNT_COLUMN_NAME, DatabaseDataType.STRING, 36);
            table.addColumn(MiddlewareDatabaseConstants.CREDITS_TABLE_FIAT_AMOUNT_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0);
            table.addColumn(MiddlewareDatabaseConstants.CREDITS_TABLE_CRYPTO_AMOUNT_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0);
            table.addColumn(MiddlewareDatabaseConstants.CREDITS_TABLE_TIME_STAMP_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0);

            try {
                ((DatabaseFactory) database).createTable(ownerId, table);
            }
            catch (CantCreateTableException cantCreateTableException) {
                System.err.println("CantCreateTableException: " + cantCreateTableException.getMessage());
                cantCreateTableException.printStackTrace();
                throw new CantCreateDatabaseException();
            }

        }
        catch (InvalidOwnerId invalidOwnerId) {
            /**
             * This shouldn't happen here because I was the one who gave the owner id to the database file system, 
             * but anyway, if this happens, I can not continue.
             * * * 
             */
            System.err.println("InvalidOwnerId: " + invalidOwnerId.getMessage());
            invalidOwnerId.printStackTrace();
            throw new CantCreateDatabaseException();
        }

        return database;
    }
    
}
