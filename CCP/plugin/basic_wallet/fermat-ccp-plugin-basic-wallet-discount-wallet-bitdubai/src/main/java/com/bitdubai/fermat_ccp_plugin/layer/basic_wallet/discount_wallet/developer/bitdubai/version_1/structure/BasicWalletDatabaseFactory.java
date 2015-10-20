package com.bitdubai.fermat_ccp_plugin.layer.basic_wallet.discount_wallet.developer.bitdubai.version_1.structure;


import com.bitdubai.fermat_api.layer.osa_android.database_system.*;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateTableException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.InvalidOwnerIdException;

import java.util.UUID;

/**
 * Created by ciencias on 3/24/15.
 */
class BasicWalletDatabaseFactory implements DealsWithPluginDatabaseSystem{

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

//            DatabaseTableFactory table;


            /**
             * Then the value chunks table.
             */
            DatabaseTableFactory table;
            DatabaseFactory databaseFactory = database.getDatabaseFactory();

            table = databaseFactory.newTableFactory(ownerId, BasicWalletDatabaseConstants.VALUE_CHUNKS_TABLE_NAME);
//            table = ((DatabaseFactory) database).newTableFactory(ownerId, BasicWalletDatabaseConstants.VALUE_CHUNKS_TABLE_NAME);
            table.addColumn(BasicWalletDatabaseConstants.VALUE_CHUNKS_TABLE_ID_COLUMN_NAME, DatabaseDataType.STRING, 36,true);
            table.addColumn(BasicWalletDatabaseConstants.VALUE_CHUNKS_TABLE_STATUS_COLUMN_NAME, DatabaseDataType.STRING, 3,false);
            table.addColumn(BasicWalletDatabaseConstants.VALUE_CHUNKS_TABLE_ID_PARENT_COLUMN_NAME, DatabaseDataType.STRING, 36,false);
            //table.addColumn(BasicWalletDatabaseConstants.VALUE_CHUNKS_TABLE_FIAT_CURRENCY_COLUMN_NAME, DatabaseDataType.STRING, 3);
            table.addColumn(BasicWalletDatabaseConstants.VALUE_CHUNKS_TABLE_FIAT_AMOUNT_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0,false);
            //table.addColumn(BasicWalletDatabaseConstants.VALUE_CHUNKS_TABLE_CRYPTO_CURRENCY_COLUMN_NAME, DatabaseDataType.STRING, 3);
            table.addColumn(BasicWalletDatabaseConstants.VALUE_CHUNKS_TABLE_CRYPTO_AMOUNT_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0,false);
            //table.addColumn(BasicWalletDatabaseConstants.VALUE_CHUNKS_TABLE_ID_ACCOUNT_COLUMN_NAME, DatabaseDataType.STRING, 36);
            table.addColumn(BasicWalletDatabaseConstants.VALUE_CHUNKS_TABLE_TIME_STAMP_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0,false);
            table.addColumn(BasicWalletDatabaseConstants.VALUE_CHUNKS_TABLE_ID_CREDIT_COLUMN_NAME, DatabaseDataType.STRING, 36,false);
            table.addColumn(BasicWalletDatabaseConstants.VALUE_CHUNKS_TABLE_ID_DEBIT_COLUMN_NAME, DatabaseDataType.STRING, 36,false);
            //table.addColumn(BasicWalletDatabaseConstants.VALUE_CHUNKS_TABLE_ID_TRANSFER_COLUMN_NAME, DatabaseDataType.STRING, 36);

            try {
                databaseFactory.createTable(ownerId, table);
//                ((DatabaseFactory) database).createTable(ownerId, table);
            }
            catch (CantCreateTableException cantCreateTableException) {
                System.err.println("CantCreateTableException: " + cantCreateTableException.getMessage());
                cantCreateTableException.printStackTrace();
                throw new CantCreateDatabaseException();
            }

            /**
             * Then the debits table.
             */
            table = databaseFactory.newTableFactory(ownerId, BasicWalletDatabaseConstants.DEBITS_TABLE_NAME);
//            table = ((DatabaseFactory) database).newTableFactory(ownerId, BasicWalletDatabaseConstants.DEBITS_TABLE_NAME);
            table.addColumn(BasicWalletDatabaseConstants.DEBITS_TABLE_ID_COLUMN_NAME, DatabaseDataType.STRING, 36,true);
            //table.addColumn(BasicWalletDatabaseConstants.DEBITS_TABLE_ID_FIAT_ACCOUNT_COLUMN_NAME, DatabaseDataType.STRING, 36);
            table.addColumn(BasicWalletDatabaseConstants.DEBITS_TABLE_FIAT_AMOUNT_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0,false);
            table.addColumn(BasicWalletDatabaseConstants.DEBITS_TABLE_CRYPTO_AMOUNT_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0,false);
            table.addColumn(BasicWalletDatabaseConstants.DEBITS_TABLE_TIME_STAMP_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0,false);

            try {
                databaseFactory.createTable(ownerId, table);
//                ((DatabaseFactory) database).createTable(ownerId, table);
            }
            catch (CantCreateTableException cantCreateTableException) {
                System.err.println("CantCreateTableException: " + cantCreateTableException.getMessage());
                cantCreateTableException.printStackTrace();
                throw new CantCreateDatabaseException();
            }

            /**
             * Then the credits table.
             */
            table = databaseFactory.newTableFactory(ownerId, BasicWalletDatabaseConstants.CREDITS_TABLE_NAME);
//            table = ((DatabaseFactory) database).newTableFactory(ownerId, BasicWalletDatabaseConstants.CREDITS_TABLE_NAME);
            table.addColumn(BasicWalletDatabaseConstants.CREDITS_TABLE_ID_COLUMN_NAME, DatabaseDataType.STRING, 36,true);
            //table.addColumn(BasicWalletDatabaseConstants.CREDITS_TABLE_ID_ACCOUNT_COLUMN_NAME, DatabaseDataType.STRING, 36);
            table.addColumn(BasicWalletDatabaseConstants.CREDITS_TABLE_FIAT_AMOUNT_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0,false);
            table.addColumn(BasicWalletDatabaseConstants.CREDITS_TABLE_CRYPTO_AMOUNT_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0,false);
            table.addColumn(BasicWalletDatabaseConstants.CREDITS_TABLE_TIME_STAMP_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0,false);

            try {
                databaseFactory.createTable(ownerId, table);
//                ((DatabaseFactory) database).createTable(ownerId, table);
            }
            catch (CantCreateTableException cantCreateTableException) {
                System.err.println("CantCreateTableException: " + cantCreateTableException.getMessage());
                cantCreateTableException.printStackTrace();
                throw new CantCreateDatabaseException();
            }

        }
        catch (InvalidOwnerIdException invalidOwnerId) {
            /**
             * This shouldn't happen here because I was the one who gave the owner id to the database file system, 
             * but anyway, if this happens, I can not continue.
             * * * 
             */
            System.err.println("InvalidOwnerIdException: " + invalidOwnerId.getMessage());
            invalidOwnerId.printStackTrace();
            throw new CantCreateDatabaseException();
        }

        return database;
    }
    
}
