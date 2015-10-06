package unit.com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_resources.developer.bitdubai.version_1.developerUtils.NetworkserviceswalletresourcesDeveloperDatabaseFactory;

import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_resources.developer.bitdubai.version_1.developerUtils.NetworkserviceswalletresourcesDeveloperDatabaseFactory;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
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

    private NetworkserviceswalletresourcesDeveloperDatabaseFactory developerDatabaseFactory;

    @Before
    public void setUp() throws Exception {
        UUID testOwnerId = UUID.randomUUID();
        databaseTableRecordList = Arrays.asList(mockDatabaseTableRecord, mockDatabaseTableRecord, mockDatabaseTableRecord);

        when(mockPluginDatabaseSystem.openDatabase(any(UUID.class), anyString())).thenReturn(mockDatabase);
        when(mockDatabase.getTable(anyString())).thenReturn(mockTable);
        when(mockTable.getRecords()).thenReturn(databaseTableRecordList);

        developerDatabaseFactory = new NetworkserviceswalletresourcesDeveloperDatabaseFactory(mockPluginDatabaseSystem, testOwnerId);

        developerDatabaseFactory.initializeDatabase();

    }
    @Test
    public void getDatabaseTableContentTest() {

    assertThat(developerDatabaseFactory.getDatabaseTableContent(developerObjectFactory, developerDatabaseTable)).isInstanceOf(List.class);



    }

}
