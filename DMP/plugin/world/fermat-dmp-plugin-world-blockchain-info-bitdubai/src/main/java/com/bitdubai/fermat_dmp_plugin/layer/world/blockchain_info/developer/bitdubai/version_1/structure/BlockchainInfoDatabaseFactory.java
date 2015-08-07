package com.bitdubai.fermat_dmp_plugin.layer.world.blockchain_info.developer.bitdubai.version_1.structure;

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
 * Created by natalia on 30/03/2015.
 */
public class BlockchainInfoDatabaseFactory implements DealsWithPluginDatabaseSystem {

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

            //DatabaseTableFactory table;

            /**
             * First the incoming crypto table.
             */
            DatabaseTableFactory table;
            DatabaseFactory databaseFactory = database.getDatabaseFactory();
//            table = ((DatabaseFactory) database).newTableFactory(ownerId, BlockchainInfoDatabaseConstants.INCOMING_CRYPTO_TABLE_NAME);
            table = databaseFactory.newTableFactory(ownerId, BlockchainInfoDatabaseConstants.INCOMING_CRYPTO_TABLE_NAME);

            table.addColumn(BlockchainInfoDatabaseConstants.INCOMING_CRYPTO_TABLE_TRX_HASH_COLUMN_NAME, DatabaseDataType.STRING, 36,true);
            table.addColumn(BlockchainInfoDatabaseConstants.INCOMING_CRYPTO_TABLE_AMOUNT_COLUMN_NAME, DatabaseDataType.LONG_INTEGER, 0,false);
            table.addColumn(BlockchainInfoDatabaseConstants.INCOMING_CRYPTO_TABLE_CRYPTO_ADDRESS_TO_COLUMN_NAME, DatabaseDataType.STRING, 100,false);
            table.addColumn(BlockchainInfoDatabaseConstants.INCOMING_CRYPTO_TABLE_CRYPTO_ADDRESS_FROM_COLUMN_NAME, DatabaseDataType.STRING, 100,false);
            table.addColumn(BlockchainInfoDatabaseConstants.INCOMING_CRYPTO_TABLE_STATUS_COLUMN_NAME, DatabaseDataType.STRING, 3,false);
            table.addColumn(BlockchainInfoDatabaseConstants.INCOMING_CRYPTO_TABLE_CURRENT_CONFIRMATIONS_COLUMN_NAME, DatabaseDataType.INTEGER, 0,false);
            table.addColumn(BlockchainInfoDatabaseConstants.INCOMING_CRYPTO_TABLE_PREVIOUS_CONFIRMATIONS_COLUMN_NAME, DatabaseDataType.INTEGER, 0,false);

            try {
                //((DatabaseFactory) database).createTable(ownerId, table);
                databaseFactory.createTable(ownerId, table);
            }
            catch (CantCreateTableException cantCreateTableException) {
                System.err.println("CantCreateTableException: " + cantCreateTableException.getMessage());
                cantCreateTableException.printStackTrace();
                throw new CantCreateDatabaseException();
            }

            /**
             * Then the OUTGOING_CRYPTO table.
             */
           // table = ((DatabaseFactory) database).newTableFactory(ownerId, BlockchainInfoDatabaseConstants.OUTGOING_CRYPTO_TABLE_NAME);
            table = databaseFactory.newTableFactory(ownerId, BlockchainInfoDatabaseConstants.OUTGOING_CRYPTO_TABLE_NAME);
            table.addColumn(BlockchainInfoDatabaseConstants.OUTGOING_CRYPTO_TABLE_ID_COLUMN_NAME, DatabaseDataType.STRING, 36,true);
            table.addColumn(BlockchainInfoDatabaseConstants.OUTGOING_CRYPTO_TABLE_TRX_COLUMN_NAME, DatabaseDataType.STRING, 100,false);
            table.addColumn(BlockchainInfoDatabaseConstants.OUTGOING_CRYPTO_TABLE_WALLET_COLUMN_NAME, DatabaseDataType.STRING, 100,false);

            try {
                //((DatabaseFactory) database).createTable(ownerId, table);
                databaseFactory.createTable(ownerId, table);
            }
            catch (CantCreateTableException cantCreateTableException) {
                System.err.println("CantCreateTableException: " + cantCreateTableException.getMessage());
                cantCreateTableException.printStackTrace();
                throw new CantCreateDatabaseException();
            }

            /**
             * Then the Crypto Address table.
             */
//            table = ((DatabaseFactory) database).newTableFactory(ownerId, BlockchainInfoDatabaseConstants.CRYPTO_ADDRESSES_TABLE_NAME);
            table = databaseFactory.newTableFactory(ownerId, BlockchainInfoDatabaseConstants.CRYPTO_ADDRESSES_TABLE_NAME);
            table.addColumn(BlockchainInfoDatabaseConstants.CRYPTO_ADDRESSES_TABLE_ID_COLUMN_NAME, DatabaseDataType.STRING, 36,true);
            table.addColumn(BlockchainInfoDatabaseConstants.CRYPTO_ADDRESSES_TABLE_WALLET_COLUMN_NAME, DatabaseDataType.STRING, 100,false);
            table.addColumn(BlockchainInfoDatabaseConstants.CRYPTO_ADDRESSES_TABLE_ADDRESS_COLUMN_NAME, DatabaseDataType.STRING, 100,false);
            table.addColumn(BlockchainInfoDatabaseConstants.CRYPTO_ADDRESSES_TABLE_LABEL_COLUMN_NAME, DatabaseDataType.STRING, 100,false);


            try {
                //((DatabaseFactory) database).createTable(ownerId, table);
                databaseFactory.createTable(ownerId, table);
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
