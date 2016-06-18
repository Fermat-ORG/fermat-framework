package unit.com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_resources.developer.bitdubai.version_1.developerUtils.SubAppResourcesNetworkServiceDeveloperDatabaseFactoryTest;

import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_resources.developer.bitdubai.version_1.developerUtils.SubAppResourcesNetworkServiceDeveloperDatabaseFactory;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
import java.util.UUID;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Created by francisco on 30/09/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class GetDatabaseListTest {
    @Mock
    private DeveloperObjectFactory developerObjectFactory;

    @Mock
    private Database mockDatabase;

    @Mock
    private PluginDatabaseSystem mockPluginDatabaseSystem;

    private SubAppResourcesNetworkServiceDeveloperDatabaseFactory subAppResourcesNetworkServiceDeveloperDatabaseFactory;

    @Before
    public void setUp() throws Exception {
        UUID testOwnerId = UUID.randomUUID();

        when(mockPluginDatabaseSystem.openDatabase(any(UUID.class), anyString())).thenReturn(mockDatabase);

        subAppResourcesNetworkServiceDeveloperDatabaseFactory = new SubAppResourcesNetworkServiceDeveloperDatabaseFactory(mockPluginDatabaseSystem, testOwnerId);

        subAppResourcesNetworkServiceDeveloperDatabaseFactory.initializeDatabase();

    }

    @Test
    public void getDatabaseListTest() {

        assertThat(subAppResourcesNetworkServiceDeveloperDatabaseFactory.getDatabaseList(developerObjectFactory)).isInstanceOf(List.class);
    }

}