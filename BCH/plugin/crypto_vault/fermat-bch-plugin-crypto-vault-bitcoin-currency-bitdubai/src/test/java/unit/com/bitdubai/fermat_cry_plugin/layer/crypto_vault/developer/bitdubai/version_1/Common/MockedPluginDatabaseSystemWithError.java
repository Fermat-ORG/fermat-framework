package unit.com.bitdubai.fermat_bch_plugin.layer.crypto_vault.developer.bitdubai.version_1.Common;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;

import java.util.UUID;

/**
 * Created by rodrigo on 2015.07.15..
 */
public class MockedPluginDatabaseSystemWithError implements PluginDatabaseSystem {
    @Override
    public Database openDatabase(UUID ownerId, String databaseName) throws CantOpenDatabaseException, DatabaseNotFoundException {
        throw new CantOpenDatabaseException("error");
    }

    @Override
    public void deleteDatabase(UUID ownerId, String databaseName) throws CantOpenDatabaseException, DatabaseNotFoundException {

    }

    @Override
    public Database createDatabase(UUID ownerId, String databaseName) throws CantCreateDatabaseException {
        return null;
    }

    @Override
    public void setContext(Object context) {

    }
}
