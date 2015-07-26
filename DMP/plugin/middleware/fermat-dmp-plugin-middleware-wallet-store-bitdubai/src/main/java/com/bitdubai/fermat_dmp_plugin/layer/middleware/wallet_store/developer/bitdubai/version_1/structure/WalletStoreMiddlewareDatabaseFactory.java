package com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_store.developer.bitdubai.version_1.structure;

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
 *  The Class  <code>com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_store.developer.bitdubai.version_1.structure.Wallet StoreMiddlewareDatabaseFactory</code>
 * is responsible for creating the tables in the database where it is to keep the information.
 * <p/>
 *
 * Created by Rodrigo Acosta - (acosta_rodrigo@hotmail.com) on 24/07/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class WalletStoreMiddlewareDatabaseFactory implements DealsWithPluginDatabaseSystem{
    /**
     * DealsWithPluginDatabaseSystem Interface member variables.
     */
    private PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * Constructor with parameters to instantiate class
     * .
     *
     * @param pluginDatabaseSystem DealsWithPluginDatabaseSystem
     */
    public WalletStoreMiddlewareDatabaseFactory(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /**
     * Create the database
     *
     * @param ownerId      the owner id
     * @param databaseName the database name
     * @return Database
     * @throws CantCreateDatabaseException
     */
    public Database createDatabase(UUID ownerId, String databaseName) throws CantCreateDatabaseException {
        Database database;

        /**
         * I will create the database where I am going to store the information of this wallet.
         */
        try {
            database = this.pluginDatabaseSystem.createDatabase(ownerId, databaseName);
        } catch (CantCreateDatabaseException cantCreateDatabaseException) {
            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateDatabaseException, "", "Exception not handled by the plugin, There is a problem and i cannot create the database.");
        } catch (Exception exception){
            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, exception, null, null);
        }


        /**
         * Next, I will add the needed tables.
         */
        try {
            DatabaseTableFactory table;

            /**
             * Create WalletStatus table.
             */
            table = ((DatabaseFactory) database).newTableFactory(ownerId, WalletStoreMiddlewareDatabaseConstants.WALLETSTATUS_TABLE_NAME);

            table.addColumn(WalletStoreMiddlewareDatabaseConstants.WALLETSTATUS_ID_COLUMN_NAME, DatabaseDataType.STRING, 36, Boolean.TRUE);
            table.addColumn(WalletStoreMiddlewareDatabaseConstants.WALLETSTATUS_INSTALATIONSTATUS_COLUMN_NAME, DatabaseDataType.STRING, 36, Boolean.FALSE);

            table.addIndex(WalletStoreMiddlewareDatabaseConstants.WALLETSTATUS_FIRST_KEY_COLUMN);

            ((DatabaseFactory) database).createTable(ownerId, table);

            /**
             * Create SkinStatus table.
             */
            table = ((DatabaseFactory) database).newTableFactory(ownerId, WalletStoreMiddlewareDatabaseConstants.SKINSTATUS_TABLE_NAME);

            table.addColumn(WalletStoreMiddlewareDatabaseConstants.SKINSTATUS_ID_COLUMN_NAME, DatabaseDataType.STRING, 36, Boolean.TRUE);
            table.addColumn(WalletStoreMiddlewareDatabaseConstants.SKINSTATUS_INSTALATIONSTATUS_COLUMN_NAME, DatabaseDataType.STRING, 36, Boolean.FALSE);

            table.addIndex(WalletStoreMiddlewareDatabaseConstants.SKINSTATUS_FIRST_KEY_COLUMN);

            ((DatabaseFactory) database).createTable(ownerId, table);

            /**
             * Create LanguageStatus table.
             */
            table = ((DatabaseFactory) database).newTableFactory(ownerId, WalletStoreMiddlewareDatabaseConstants.LANGUAGESTATUS_TABLE_NAME);

            table.addColumn(WalletStoreMiddlewareDatabaseConstants.LANGUAGESTATUS_ID_COLUMN_NAME, DatabaseDataType.STRING, 36, Boolean.TRUE);
            table.addColumn(WalletStoreMiddlewareDatabaseConstants.LANGUAGESTATUS_INSTALATIONSTATUS_COLUMN_NAME, DatabaseDataType.STRING, 36, Boolean.FALSE);

            table.addIndex(WalletStoreMiddlewareDatabaseConstants.LANGUAGESTATUS_FIRST_KEY_COLUMN);

            ((DatabaseFactory) database).createTable(ownerId, table);
        } catch (InvalidOwnerIdException invalidOwnerId) {
            /**
             * This shouldn't happen here because I was the one who gave the owner id to the database file system,
             * but anyway, if this happens, I can not continue.
             */
            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, invalidOwnerId, "", "There is a problem with the ownerId of the database.");
        } catch (CantCreateTableException cantCreateTableException) {
            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
        } catch (Exception exception){
            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, exception, null, null);
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
