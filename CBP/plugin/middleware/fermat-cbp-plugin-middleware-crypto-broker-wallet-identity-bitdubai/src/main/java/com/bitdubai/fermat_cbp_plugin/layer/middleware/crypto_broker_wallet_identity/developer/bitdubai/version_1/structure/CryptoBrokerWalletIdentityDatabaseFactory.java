package com.bitdubai.fermat_cbp_plugin.layer.middleware.crypto_broker_wallet_identity.developer.bitdubai.version_1.structure;

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
 * Created by angel on 23/9/15.
 */

public class CryptoBrokerWalletIdentityDatabaseFactory implements DealsWithPluginDatabaseSystem {

    private PluginDatabaseSystem pluginDatabaseSystem;

    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    public Database createDatabase(UUID ownerId) throws CantCreateDatabaseException {
        Database database = null;
        try {
            database = this.pluginDatabaseSystem.createDatabase(ownerId, CryptoBrokerWalletIdentityDatabaseConstants.WALLET_IDENTITY_DATABASE_NAME);
            createWalletIdentityTable(ownerId, database.getDatabaseFactory());
            database.closeDatabase();
            return database;
        } catch(CantCreateDatabaseException exception){
            throw exception;
        } catch(Exception exception){
            if(database != null)
                database.closeDatabase();
            throw new CantCreateDatabaseException(CantCreateDatabaseException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

    private void createWalletIdentityTable(final UUID ownerId, final DatabaseFactory databaseFactory) throws CantCreateTableException {
        try{
            DatabaseTableFactory tableFactory = createWalletIdentityTableFactory(ownerId, databaseFactory);
            databaseFactory.createTable(tableFactory);
        } catch(InvalidOwnerIdException exception){
            throw new CantCreateTableException(CantCreateTableException.DEFAULT_MESSAGE, exception, null, "The ownerId of the database factory didn't match with the given owner id");
        }
    }

    private DatabaseTableFactory createWalletIdentityTableFactory(final UUID ownerId, final DatabaseFactory databaseFactory) throws InvalidOwnerIdException{
        DatabaseTableFactory table = databaseFactory.newTableFactory(ownerId, CryptoBrokerWalletIdentityDatabaseConstants.WALLET_IDENTITY_TABLE_NAME);
            table.addColumn(CryptoBrokerWalletIdentityDatabaseConstants.WALLET_IDENTITY_PUBLIC_KEY_COLUMN_NAME , DatabaseDataType.STRING, 36, Boolean.FALSE);
            table.addColumn(CryptoBrokerWalletIdentityDatabaseConstants.WALLET_IDENTITY_TABLE_ID_COLUMN_NAME, DatabaseDataType.STRING, 100,Boolean.FALSE);
        return table;
    }

}
