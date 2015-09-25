/*
 * @#CoinapultDatabaseFactory.java - 2015
 * Copyright bitDubai.com., All rights reserved.
  * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_ccp_plugin.layer.world.coinapult.developer.bitdubai.version_1.structure;

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
 * The Class <code>com.bitdubai.fermat_ccp_plugin.layer._11_world.coinapult.developer.bitdubai.version_1.structure.CoinapultDatabaseFactory</code>
 * is responsible for creating the tables in the database where it is to keep the information.
 *
 * Created by Roberto Requena - (rrequena) on 30/04/15.
 * @version 1.0
 */
public class CoinapultDatabaseFactory implements DealsWithPluginDatabaseSystem {

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

    /**
     * Create the data base
     *
     * @param ownerId the owner id
     * @param walletId the wallet id
     * @return Database
     * @throws CantCreateDatabaseException
     */
    protected  Database createDatabase(UUID ownerId, UUID walletId) throws CantCreateDatabaseException {

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

            //DatabaseTableFactory table;

            /**
             * First define the Addresses table.
             */
            DatabaseTableFactory table;
            DatabaseFactory databaseFactory = database.getDatabaseFactory();

            table = databaseFactory.newTableFactory(ownerId, CoinapultDatabaseConstants.ADDRESSES_TABLE_NAME);
//            table = ((DatabaseFactory) database).newTableFactory(ownerId, CoinapultDatabaseConstants.ADDRESSES_TABLE_NAME);
            table.addColumn(CoinapultDatabaseConstants.ADDRESSES_TABLE_ADDRESS_COLUMN_NAME, DatabaseDataType.STRING, 100,false);
            table.addColumn(CoinapultDatabaseConstants.ADDRESSES_TABLE_TYPE_COLUMN_NAME, DatabaseDataType.STRING, 10,false);
            table.addColumn(CoinapultDatabaseConstants.ADDRESSES_TABLE_WALLET_ID_COLUMN_NAME, DatabaseDataType.STRING, 50,false);


            try {

                //Create the table
                databaseFactory.createTable(ownerId, table);
//                ((DatabaseFactory) database).createTable(ownerId, table);
            }
            catch (CantCreateTableException cantCreateTableException) {
                System.err.println("CantCreateTableException: " + cantCreateTableException.getMessage());
                cantCreateTableException.printStackTrace();
                throw new CantCreateDatabaseException();
            }

            /**
             * Then define the balances history table.
             */
            table = databaseFactory.newTableFactory(ownerId, CoinapultDatabaseConstants.BALANCES_HISTORY_TABLE_NAME);
//            table = ((DatabaseFactory) database).newTableFactory(ownerId, CoinapultDatabaseConstants.BALANCES_HISTORY_TABLE_NAME);
            table.addColumn(CoinapultDatabaseConstants.BALANCES_HISTORY_TABLE_AMOUNT_COLUMN_NAME, DatabaseDataType.MONEY, 0, true);
            table.addColumn(CoinapultDatabaseConstants.BALANCES_HISTORY_TABLE_CURRENCY_COLUMN_NAME, DatabaseDataType.STRING, 5,false);
            table.addColumn(CoinapultDatabaseConstants.BALANCES_HISTORY_TABLE_TIMESTAMP_COLUMN_NAME, DatabaseDataType.STRING, 20,false);
            table.addColumn(CoinapultDatabaseConstants.BALANCES_HISTORY_TABLE_WALLET_ID_COLUMN_NAME, DatabaseDataType.STRING, 50,false);

            try {

                //Create the table
                databaseFactory.createTable(ownerId, table);
//                ((DatabaseFactory) database).createTable(ownerId, table);
            }
            catch (CantCreateTableException cantCreateTableException) {
                System.err.println("CantCreateTableException: " + cantCreateTableException.getMessage());
                cantCreateTableException.printStackTrace();
                throw new CantCreateDatabaseException();
            }

            /**
             * Then define the TRANSACTION history table.
             */
            table = databaseFactory.newTableFactory(ownerId, CoinapultDatabaseConstants.TRANSACTION_HISTORY_TABLE_NAME);
//            table = ((DatabaseFactory) database).newTableFactory(ownerId, CoinapultDatabaseConstants.TRANSACTION_HISTORY_TABLE_NAME);
            table.addColumn(CoinapultDatabaseConstants.TRANSACTION_HISTORY_TABLE_ADDRESS_COLUMN_NAME, DatabaseDataType.STRING, 100, true);
            table.addColumn(CoinapultDatabaseConstants.TRANSACTION_HISTORY_TABLE_COMPLETE_TIME_NAME, DatabaseDataType.STRING, 20,false);
            table.addColumn(CoinapultDatabaseConstants.TRANSACTION_HISTORY_TABLE_EXPIRATION_NAME, DatabaseDataType.STRING, 20,false);
            table.addColumn(CoinapultDatabaseConstants.TRANSACTION_HISTORY_TABLE_IN_AMOUNT_NAME, DatabaseDataType.MONEY, 0, true);
            table.addColumn(CoinapultDatabaseConstants.TRANSACTION_HISTORY_TABLE_IN_CURRENCY_NAME, DatabaseDataType.STRING, 5,false);
            table.addColumn(CoinapultDatabaseConstants.TRANSACTION_HISTORY_TABLE_IN_EXPECTED_NAME, DatabaseDataType.MONEY, 0,false);
            table.addColumn(CoinapultDatabaseConstants.TRANSACTION_HISTORY_TABLE_OUT_AMOUNT_NAME, DatabaseDataType.MONEY, 0, true);
            table.addColumn(CoinapultDatabaseConstants.TRANSACTION_HISTORY_TABLE_OUT_CURRENCY_NAME, DatabaseDataType.STRING, 5,false);
            table.addColumn(CoinapultDatabaseConstants.TRANSACTION_HISTORY_TABLE_OUT_EXPECTED_NAME, DatabaseDataType.MONEY, 0,false);
            table.addColumn(CoinapultDatabaseConstants.TRANSACTION_HISTORY_TABLE_QUOTE_ASK, DatabaseDataType.MONEY, 0, true);
            table.addColumn(CoinapultDatabaseConstants.TRANSACTION_HISTORY_TABLE_QUOTE_BID_NAME, DatabaseDataType.STRING, 5,false);
            table.addColumn(CoinapultDatabaseConstants.TRANSACTION_HISTORY_TABLE_STATE_NAME, DatabaseDataType.STRING, 10,false);
            table.addColumn(CoinapultDatabaseConstants.TRANSACTION_HISTORY_TABLE_TIMESTAMP_NAME, DatabaseDataType.STRING, 20,false);
            table.addColumn(CoinapultDatabaseConstants.TRANSACTION_HISTORY_TABLE_TRANSACTION_ID_COLUMN_NAME, DatabaseDataType.STRING, 50,false);
            table.addColumn(CoinapultDatabaseConstants.TRANSACTION_HISTORY_TABLE_TYPE_COLUMN_NAME, DatabaseDataType.STRING, 20,false);
            table.addColumn(CoinapultDatabaseConstants.TRANSACTION_HISTORY_TABLE_WALLET_ID_COLUMN_NAME, DatabaseDataType.STRING, 50,false);

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
        catch (InvalidOwnerId invalidOwnerId) {
            /**
             * This shouldn't happen here because I was the one who gave the owner id to the database file system,
             * but anyway, if this happens, I can not continue.
             */
            System.err.println("InvalidOwnerId: " + invalidOwnerId.getMessage());
            invalidOwnerId.printStackTrace();
            throw new CantCreateDatabaseException();
        }

        return database;
    }
}
