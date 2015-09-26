package unit.com.bitdubait.fermat_dmp_plugin.layer.actor.intra_user.developer.bitdubai.version_1.database.IntraUserActorDeveloperDatabaseFactory;

import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_dmp_plugin.layer.actor.intra_user.developer.bitdubai.version_1.database.IntraUserActorDeveloperDatabaseFactory;
import com.bitdubai.fermat_dmp_plugin.layer.actor.intra_user.developer.bitdubai.version_1.exceptions.CantInitializeIntraUserActorDatabaseException;

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
 * Created by natalia on 24/08/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class InitializeDatabaseTest {

    @Mock
    private DeveloperObjectFactory developerObjectFactory;

    @Mock
    private Database mockDatabase;

    @Mock
    private PluginDatabaseSystem mockPluginDatabaseSystem;

    private IntraUserActorDeveloperDatabaseFactory intraUserActorDeveloperDatabaseFactory;



    @Test
    public void initializeDatabase_initOK_throwsCantInitializeIntraUserActorDatabaseException() throws Exception {
        UUID testOwnerId = UUID.randomUUID();

        when(mockPluginDatabaseSystem.openDatabase(any(UUID.class), anyString())).thenReturn(mockDatabase);

        intraUserActorDeveloperDatabaseFactory = new IntraUserActorDeveloperDatabaseFactory(mockPluginDatabaseSystem, testOwnerId);

        intraUserActorDeveloperDatabaseFactory.setPluginDatabaseSystem(mockPluginDatabaseSystem);
        intraUserActorDeveloperDatabaseFactory.setPluginId(testOwnerId);

        intraUserActorDeveloperDatabaseFactory.initializeDatabase();
    }

    @Test
    public void initializeDatabase_initError_throwCantInitializeIntraUserActorDatabaseException() throws Exception {
        UUID testOwnerId = UUID.randomUUID();

        when(mockPluginDatabaseSystem.openDatabase(any(UUID.class), anyString())).thenReturn(mockDatabase);

        intraUserActorDeveloperDatabaseFactory = new IntraUserActorDeveloperDatabaseFactory(mockPluginDatabaseSystem, testOwnerId);

        intraUserActorDeveloperDatabaseFactory.setPluginDatabaseSystem(null);
        intraUserActorDeveloperDatabaseFactory.setPluginId(testOwnerId);

        catchException(intraUserActorDeveloperDatabaseFactory).initializeDatabase();

        assertThat(caughtException())
                .isNotNull()
                .isInstanceOf(CantInitializeIntraUserActorDatabaseException.class);
    }
}
