package unit.com.bitdubai.fermat_cry_plugin.layer.crypto_module.wallet_address_book.developer.bitdubai.version_1.structure.WalletAddressBookCryptoModuleDatabaseFactory;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_cry_plugin.layer.crypto_module.wallet_address_book.developer.bitdubai.version_1.structure.WalletAddressBookCryptoModuleDatabaseFactory;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import static org.mockito.Mockito.*;

import java.util.UUID;

import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class CreateDatabaseTest extends TestCase {

    @Mock
    PluginDatabaseSystem pluginDatabaseSystem;

    @Mock
    DatabaseTableFactory mockTable;

    @Mock
    Database mockDatabase;

    @Mock
    DatabaseFactory mockDatabaseFactory;


    WalletAddressBookCryptoModuleDatabaseFactory databaseFactory;

    UUID pluginId;

    @Before
    public void setUp() throws Exception {
        pluginId = UUID.randomUUID();
        databaseFactory = new WalletAddressBookCryptoModuleDatabaseFactory();
        databaseFactory.setPluginDatabaseSystem(pluginDatabaseSystem);
        when(pluginDatabaseSystem.createDatabase(any(UUID.class), anyString())).thenReturn(mockDatabase);
        when(mockDatabase.getDatabaseFactory()).thenReturn(mockDatabaseFactory);
        when(mockDatabaseFactory.newTableFactory(any(UUID.class), anyString())).thenReturn(mockTable);
    }

    @Test
    public void testCreateDatabase_NotNull() throws Exception {
        Database database = databaseFactory.createDatabase(pluginId);
        assertNotNull(database);
    }

    @Test(expected=CantCreateDatabaseException.class)
    public void testCreateDatabase_CantCreateDatabaseException() throws Exception {
        when(pluginDatabaseSystem.createDatabase(any(UUID.class), anyString())).thenThrow(new CantCreateDatabaseException());

        databaseFactory.createDatabase(pluginId);
    }
}
