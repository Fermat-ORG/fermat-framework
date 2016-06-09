package unit.com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_resources.developer.bitdubai.version_1.developerUtils.SubAppResourcesNetworkServiceDeveloperDatabaseFactoryTest;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_resources.developer.bitdubai.version_1.developerUtils.SubAppResourcesNetworkServiceDeveloperDatabaseFactory;
import com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_resources.developer.bitdubai.version_1.exceptions.CantInitializeNetworkServicesSubAppResourcesDatabaseException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Created by francisco on 30/09/15.
 */
@RunWith(MockitoJUnitRunner.class)

public class InitializeDatabaseTest {
    @Mock
    private Database mockDatabase;
    @Mock
    private com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory mockDatabaseFactory;
    @Mock
    private PluginDatabaseSystem mockPluginDatabaseSystem;

    @Mock
    private DatabaseTableFactory mockTable;

    private SubAppResourcesNetworkServiceDeveloperDatabaseFactory subAppResourcesNetworkServiceDeveloperDatabaseFactory;


    @Before
    public void SetUp() throws Exception {
        UUID testOwnerId = UUID.randomUUID();

        when(mockPluginDatabaseSystem.openDatabase(any(UUID.class), anyString())).thenReturn(mockDatabase);
        when(mockPluginDatabaseSystem.createDatabase(any(UUID.class), anyString())).thenReturn(mockDatabase);
        when(mockDatabaseFactory.newTableFactory(any(UUID.class), anyString())).thenReturn(mockTable);
        when(mockDatabase.getDatabaseFactory()).thenReturn(mockDatabaseFactory);
        when(mockDatabaseFactory.newTableFactory(any(UUID.class), anyString())).thenReturn(mockTable);
        subAppResourcesNetworkServiceDeveloperDatabaseFactory = new SubAppResourcesNetworkServiceDeveloperDatabaseFactory(mockPluginDatabaseSystem, testOwnerId);

        subAppResourcesNetworkServiceDeveloperDatabaseFactory.setPluginDatabaseSystem(mockPluginDatabaseSystem);
        subAppResourcesNetworkServiceDeveloperDatabaseFactory.setPluginId(testOwnerId);
    }

    @Test
    public void initializeDatabaseTest() throws CantInitializeNetworkServicesSubAppResourcesDatabaseException {


        catchException(subAppResourcesNetworkServiceDeveloperDatabaseFactory).initializeDatabase();

        assertThat(caughtException()).isNull();
    }

    @Test
    public void initializeDatabaseTest_InitError_ThrowsCantInitializeNetworkServicesWalletResourcesDatabaseException() throws Exception {

        subAppResourcesNetworkServiceDeveloperDatabaseFactory.setPluginDatabaseSystem(null);

        catchException(subAppResourcesNetworkServiceDeveloperDatabaseFactory).initializeDatabase();
        assertThat(caughtException()).isNotNull().isInstanceOf(CantInitializeNetworkServicesSubAppResourcesDatabaseException.class);

    }

    @Test
    public void initializeDatabaseTest_DatabaseNotFound_ThrowsCantInitializeNetworkServicesWalletResourcesDatabaseException() throws Exception {
        when(mockPluginDatabaseSystem.openDatabase(any(UUID.class), anyString())).thenThrow(DatabaseNotFoundException.class);
        catchException(subAppResourcesNetworkServiceDeveloperDatabaseFactory).initializeDatabase();
        assertThat(caughtException()).isNull();
    }

}
