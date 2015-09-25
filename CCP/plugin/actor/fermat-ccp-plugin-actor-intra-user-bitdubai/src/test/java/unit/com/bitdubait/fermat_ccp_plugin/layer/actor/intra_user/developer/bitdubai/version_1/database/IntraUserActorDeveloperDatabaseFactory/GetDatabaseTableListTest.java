package unit.com.bitdubait.fermat_ccp_plugin.layer.actor.intra_user.developer.bitdubai.version_1.database.IntraUserActorDeveloperDatabaseFactory;

import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_ccp_plugin.layer.actor.intra_user.developer.bitdubai.version_1.database.IntraUserActorDeveloperDatabaseFactory;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
import java.util.UUID;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Created by natalia on 24/08/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class GetDatabaseTableListTest {
    @Mock
    private Database mockDatabase;

    @Mock
    private DeveloperObjectFactory mockDeveloperObjectFactory;

    @Mock
    private PluginDatabaseSystem mockPluginDatabaseSystem;

    private IntraUserActorDeveloperDatabaseFactory intraUserActorDeveloperDatabaseFactory;

    @Test
    public void getDatabaseTableListTest_GetOk() throws Exception {
        UUID testOwnerId = UUID.randomUUID();

        when(mockPluginDatabaseSystem.openDatabase(any(UUID.class), anyString())).thenReturn(mockDatabase);

        intraUserActorDeveloperDatabaseFactory = new IntraUserActorDeveloperDatabaseFactory(mockPluginDatabaseSystem, testOwnerId);

        intraUserActorDeveloperDatabaseFactory.initializeDatabase();

        assertThat(intraUserActorDeveloperDatabaseFactory.getDatabaseTableList(mockDeveloperObjectFactory)).isInstanceOf(List.class);
    }

}
