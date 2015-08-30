package test.com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.structure.WalletContactsMiddlewareDeveloperDatabaseFactory;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.exceptions.CantInitializeWalletContactsDatabaseException;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.structure.WalletContactsMiddlewareDao;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.structure.WalletContactsMiddlewareDatabaseFactory;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.structure.WalletContactsMiddlewareDeveloperDatabaseFactory;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

/**
 * Created by Nerio on 25/07/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class InitializeDatabaseTest {

    @Mock
    private PluginDatabaseSystem mockPluginDatabaseSystem;
    @Mock
    private Database mockDatabase;
    @Mock
    private DatabaseFactory mockDatabaseFactory;
    @Mock
    private DatabaseTable mockTable;
    @Mock
    private DatabaseTableFactory mockWalletTableFactory;
    @Mock
    private DatabaseTableFactory mockBalanceTableFactory;
    @Mock
    private DatabaseTableRecord mockTableRecord;

    private UUID pluginId;
    private WalletContactsMiddlewareDeveloperDatabaseFactory testDatabaseFactory;

    @Before
    public void setUp() throws Exception {
        pluginId = UUID.randomUUID();
        testDatabaseFactory = new WalletContactsMiddlewareDeveloperDatabaseFactory(mockPluginDatabaseSystem,pluginId);
        testDatabaseFactory.setPluginDatabaseSystem(mockPluginDatabaseSystem);
        testDatabaseFactory.setPluginId(pluginId);

    }

    @Test
    public void testInitialize_NotNull() throws Exception{
        when(mockPluginDatabaseSystem.openDatabase(pluginId, pluginId.toString())).thenReturn(mockDatabase);
        testDatabaseFactory.initializeDatabase();
    }

    // cant open mockDatabase
    @Test(expected = CantInitializeWalletContactsDatabaseException.class)
    public void testInitialize_CantOpenDatabaseException() throws Exception {
        doThrow(new CantOpenDatabaseException()).when(mockPluginDatabaseSystem).openDatabase(any(UUID.class), anyString());
        testDatabaseFactory.initializeDatabase();
    }

    // mockDatabase not found exception, then cant create mockDatabase.
    @Test(expected = CantInitializeWalletContactsDatabaseException.class)
    public void testInitialize_DatabaseNotFoundException() throws Exception {
        doThrow(new DatabaseNotFoundException()).when(mockPluginDatabaseSystem).openDatabase(any(UUID.class), anyString());
        doThrow(new CantCreateDatabaseException()).when(mockPluginDatabaseSystem).createDatabase(any(UUID.class), anyString());
        testDatabaseFactory.initializeDatabase();
    }
}
