package unit.com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_resources.developer.bitdubai.version_1.developerUtils.SubAppResourcesNetworkServiceDeveloperDatabaseFactoryTest;

import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_resources.developer.bitdubai.version_1.developerUtils.SubAppResourcesNetworkServiceDeveloperDatabaseFactory;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Created by francisco on 30/09/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class GetDatabaseTableContentTest {
    @Mock
    private Database mockDatabase;

    @Mock
    private DeveloperObjectFactory developerObjectFactory;
    @Mock
    private DeveloperDatabaseTable developerDatabaseTable;

    @Mock
    private PluginDatabaseSystem mockPluginDatabaseSystem;

    @Mock
    private DatabaseTable mockTable;

    @Mock
    DatabaseTableRecord mockDatabaseTableRecord;
    @Mock
    List<DatabaseTableRecord> databaseTableRecordList;

    private SubAppResourcesNetworkServiceDeveloperDatabaseFactory subAppResourcesNetworkServiceDeveloperDatabaseFactory;

    @Before
    public void setUp() throws Exception {
        UUID testOwnerId = UUID.randomUUID();
        databaseTableRecordList = Arrays.asList(mockDatabaseTableRecord, mockDatabaseTableRecord, mockDatabaseTableRecord);

        when(mockPluginDatabaseSystem.openDatabase(any(UUID.class), anyString())).thenReturn(mockDatabase);
        when(mockDatabase.getTable(anyString())).thenReturn(mockTable);
        when(mockTable.getRecords()).thenReturn(databaseTableRecordList);

        subAppResourcesNetworkServiceDeveloperDatabaseFactory = new SubAppResourcesNetworkServiceDeveloperDatabaseFactory(mockPluginDatabaseSystem, testOwnerId);

        subAppResourcesNetworkServiceDeveloperDatabaseFactory.initializeDatabase();

    }

    @Test
    public void getDatabaseTableContentTest() {

        assertThat(subAppResourcesNetworkServiceDeveloperDatabaseFactory.getDatabaseTableContent(developerObjectFactory, developerDatabaseTable)).isInstanceOf(List.class);


    }

}