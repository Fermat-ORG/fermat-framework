package unit.com.bitdubai.fermat_cry_plugin.layer.crypto_module.actor_address_book.developer.bitdubai.version_1.structure.ActorAddressBookCryptoModuleDatabaseFactory;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_cry_plugin.layer.crypto_module.actor_address_book.developer.bitdubai.version_1.structure.ActorAddressBookCryptoModuleDatabaseFactory;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class CreateDatabaseTest extends TestCase {

    @Mock
    PluginDatabaseSystem pluginDatabaseSystem;

    @Mock
    DatabaseTableFactory table;

    @Mock
    Database database;

    ActorAddressBookCryptoModuleDatabaseFactory databaseFactory;

    UUID pluginId;

    @Before
    public void setUp() throws Exception {
        pluginId = UUID.randomUUID();
        databaseFactory = new ActorAddressBookCryptoModuleDatabaseFactory();
        databaseFactory.setPluginDatabaseSystem(pluginDatabaseSystem);
    }

    @Ignore
    public void testCreateDatabase_NotNull() throws Exception {
        /*
         * TODO This test should pass but there is a wrong design decision that makes a cast of the Database interface into the DatabaseFactory; we should really look into that
         */
        doReturn(database).when(pluginDatabaseSystem).createDatabase(any(UUID.class), anyString());
        Database database = databaseFactory.createDatabase(pluginId, pluginId);
        assertNotNull(database);
    }

    @Test(expected=CantCreateDatabaseException.class)
    public void testCreateDatabase_CantCreateDatabaseException() throws Exception {
        when(pluginDatabaseSystem.createDatabase(pluginId, pluginId.toString())).thenThrow(new CantCreateDatabaseException());

        databaseFactory.createDatabase(pluginId, pluginId);
    }
}
