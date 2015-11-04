package unit.com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_resources.developer.bitdubai.version_1.database.NetworkServicesWalletResourcesDAO;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_resources.developer.bitdubai.version_1.database.NetworkServicesWalletResourcesDAO;
import com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_resources.developer.bitdubai.version_1.database.NetworkserviceswalletresourcesDatabaseConstants;
import com.googlecode.catchexception.CatchException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static com.googlecode.catchexception.CatchException.catchException;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Created by natalia on 09/09/15.
 */

@RunWith(MockitoJUnitRunner.class)
public class InitializeDatabaseTest
{
private NetworkServicesWalletResourcesDAO networkServicesWalletResourcesDAO;

@Mock
private Database mockDatabase;

@Mock
private PluginDatabaseSystem mockPluginDatabaseSystem;

    @Mock
    private  DatabaseTableFactory mockTable;

    @Mock
    private DatabaseFactory mockDatabaseFactory;

    private UUID testOwnerId;

        @Before
        public void setUp() throws Exception {
            testOwnerId = UUID.randomUUID();
            when(mockPluginDatabaseSystem.openDatabase(any(UUID.class), anyString())).thenReturn(mockDatabase);

            when(mockDatabaseFactory.newTableFactory(any(UUID.class), anyString())).thenReturn(mockTable);
            networkServicesWalletResourcesDAO = new NetworkServicesWalletResourcesDAO(mockPluginDatabaseSystem);
        }

        @Test
        public void initializeDatabaseTest_InitOk_ThrowsCantInitializeNetworkServicesWalletResourcesDatabaseException() throws Exception {

            catchException(networkServicesWalletResourcesDAO).initializeDatabase(testOwnerId, NetworkserviceswalletresourcesDatabaseConstants.DATABASE_NAME);
            assertThat(CatchException.<Exception>caughtException()).isNull();

        }

        @Test
        public void initializeDatabaseTest_ErrorNoOpen_ThrowsCantInitializeNetworkServicesWalletResourcesDatabaseException() throws Exception {

            when(mockPluginDatabaseSystem.openDatabase(any(UUID.class), anyString())).thenThrow(CantOpenDatabaseException.class);
            catchException(networkServicesWalletResourcesDAO).initializeDatabase(testOwnerId,NetworkserviceswalletresourcesDatabaseConstants.DATABASE_NAME);
            assertThat(CatchException.<Exception>caughtException()).isNotNull();

        }

    @Test
    public void initializeDatabaseTest_ErrorNoFound_ThrowsCantInitializeNetworkServicesWalletResourcesDatabaseException() throws Exception {

        when(mockPluginDatabaseSystem.openDatabase(any(UUID.class), anyString())).thenThrow(DatabaseNotFoundException.class);
        catchException(networkServicesWalletResourcesDAO).initializeDatabase(testOwnerId,NetworkserviceswalletresourcesDatabaseConstants.DATABASE_NAME);
        assertThat(CatchException.<Exception>caughtException()).isNotNull();

    }

}