package com.bitdubai.fermat_ccp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.database;

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
 *  The Class  <code>com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.database.Wallet ContactsMiddlewareDatabaseFactory</code>
 * is responsible for creating the tables in the database where it is to keep the information.
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 04/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class WalletContactsMiddlewareDatabaseFactory {

    private final PluginDatabaseSystem pluginDatabaseSystem;

    public WalletContactsMiddlewareDatabaseFactory(final PluginDatabaseSystem pluginDatabaseSystem) {

        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    public Database createDatabase(UUID   ownerId     ,
                                   String databaseName) throws CantCreateDatabaseException {

        try {

            Database database = this.pluginDatabaseSystem.createDatabase(ownerId, databaseName);

            DatabaseTableFactory table;
            DatabaseFactory databaseFactory = database.getDatabaseFactory();

            /**
             * Create Wallet Contacts table.
             */
            table = databaseFactory.newTableFactory(ownerId, WalletContactsMiddlewareDatabaseConstants.WALLET_CONTACTS_TABLE_NAME);

            table.addColumn(WalletContactsMiddlewareDatabaseConstants.WALLET_CONTACTS_CONTACT_ID_COLUMN_NAME       , DatabaseDataType.STRING,  36, Boolean.TRUE );
            table.addColumn(WalletContactsMiddlewareDatabaseConstants.WALLET_CONTACTS_ACTOR_PUBLIC_KEY_COLUMN_NAME , DatabaseDataType.STRING, 130, Boolean.FALSE);
            table.addColumn(WalletContactsMiddlewareDatabaseConstants.WALLET_CONTACTS_ACTOR_TYPE_COLUMN_NAME       , DatabaseDataType.STRING,  10, Boolean.FALSE);
            table.addColumn(WalletContactsMiddlewareDatabaseConstants.WALLET_CONTACTS_ACTOR_ALIAS_COLUMN_NAME      , DatabaseDataType.STRING,  50, Boolean.FALSE);
            table.addColumn(WalletContactsMiddlewareDatabaseConstants.WALLET_CONTACTS_ACTOR_FIRST_NAME_COLUMN_NAME , DatabaseDataType.STRING,  50, Boolean.FALSE);
            table.addColumn(WalletContactsMiddlewareDatabaseConstants.WALLET_CONTACTS_ACTOR_LAST_NAME_COLUMN_NAME  , DatabaseDataType.STRING,  50, Boolean.FALSE);
            table.addColumn(WalletContactsMiddlewareDatabaseConstants.WALLET_CONTACTS_WALLET_PUBLIC_KEY_COLUMN_NAME, DatabaseDataType.STRING, 130, Boolean.FALSE);
            table.addColumn(WalletContactsMiddlewareDatabaseConstants.WALLET_CONTACTS_COMPATIBILITY_COLUMN_NAME    , DatabaseDataType.STRING,  10, Boolean.FALSE);

            table.addIndex(WalletContactsMiddlewareDatabaseConstants.WALLET_CONTACTS_FIRST_KEY_COLUMN);


            databaseFactory.createTable(ownerId, table);

             /**
             * Create Wallet Contact Addresses table.
             */
            table = databaseFactory.newTableFactory(ownerId, WalletContactsMiddlewareDatabaseConstants.WALLET_CONTACT_ADDRESSES_TABLE_NAME);

            table.addColumn(WalletContactsMiddlewareDatabaseConstants.WALLET_CONTACT_ADDRESSES_CONTACT_ID_COLUMN_NAME     , DatabaseDataType.STRING      , 36, Boolean.FALSE);
            table.addColumn(WalletContactsMiddlewareDatabaseConstants.WALLET_CONTACT_ADDRESSES_CRYPTO_ADDRESS_COLUMN_NAME , DatabaseDataType.STRING      , 50, Boolean.TRUE );
            table.addColumn(WalletContactsMiddlewareDatabaseConstants.WALLET_CONTACT_ADDRESSES_CRYPTO_CURRENCY_COLUMN_NAME, DatabaseDataType.STRING      , 10, Boolean.FALSE);
            table.addColumn(WalletContactsMiddlewareDatabaseConstants.WALLET_CONTACT_ADDRESSES_TIME_STAMP_COLUMN_NAME     , DatabaseDataType.LONG_INTEGER,  0, Boolean.FALSE);

            table.addIndex(WalletContactsMiddlewareDatabaseConstants.WALLET_CONTACT_ADDRESSES_FIRST_KEY_COLUMN);

            databaseFactory.createTable(ownerId, table);

            return database;

        } catch (CantCreateTableException cantCreateTableException) {

            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateTableException, "", "Exception not handled by the plugin, There is a problem and i cannot create the table.");
        } catch (CantCreateDatabaseException cantCreateDatabaseException) {

            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, cantCreateDatabaseException, "", "Exception not handled by the plugin, There is a problem and i cannot create the database.");
        } catch (InvalidOwnerIdException invalidOwnerId) {

            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, invalidOwnerId, "", "There is a problem with the ownerId of the database.");
        } catch (Exception e) {

            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, e, "", "Generic Error.");
        }
    }
}
