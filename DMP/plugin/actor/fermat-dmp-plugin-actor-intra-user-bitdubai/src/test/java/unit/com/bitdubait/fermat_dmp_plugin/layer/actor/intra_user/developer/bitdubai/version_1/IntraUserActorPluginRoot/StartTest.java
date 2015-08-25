package unit.com.bitdubait.fermat_dmp_plugin.layer.actor.intra_user.developer.bitdubai.version_1.IntraUserActorPluginRoot;

import com.bitdubai.fermat_api.CantStartPluginException;

import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.enums.EventType;

import com.bitdubai.fermat_api.layer.dmp_network_service.intra_user.interfaces.IntraUserManager;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_dmp_plugin.layer.actor.intra_user.developer.bitdubai.version_1.IntraUserActorPluginRoot;
import com.bitdubai.fermat_dmp_plugin.layer.actor.intra_user.developer.bitdubai.version_1.database.IntraUserActorDatabaseConstants;
import com.bitdubai.fermat_dmp_plugin.layer.actor.intra_user.developer.bitdubai.version_1.database.IntraUserActorDatabaseFactory;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventListener;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventManager;

import junit.framework.TestCase;

import org.fest.assertions.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * Created by natalia on 21/08/15.
 */

@RunWith(MockitoJUnitRunner.class)
public class StartTest extends TestCase
{

    /**
     * DealsWithEvents Interface member variables.
     */
    @Mock
    private EventManager mockEventManager;

    /**
     * DealsWithErrors interface Mocked
     */
    @Mock
    private ErrorManager mockErrorManager;

    /**
     * PluginDatabaseSystem Interface member variables.
     */

    @Mock
    PluginDatabaseSystem mockPluginDatabaseSystem;

    @Mock
    IntraUserActorDatabaseFactory mockIntraUserActorDatabaseFactory;

    @Mock
    DatabaseFactory mockDatabaseFactory;


    /**
     * DealsWithIntraUsersNetworkService interface member variable
     */
    @Mock
    private IntraUserManager mockIintraUserNetworkServiceManager;

    DatabaseTable mockDatabaseTable= Mockito.mock(DatabaseTable.class);
    DatabaseTableRecord mockDatabaseTableRecord=Mockito.mock(DatabaseTableRecord.class);
    Database mockDatabase= Mockito.mock(Database.class);

    EventListener mockEventListener = Mockito.mock(EventListener.class);
    private IntraUserActorPluginRoot testIntraUserActorPluginRoot;


    UUID pluginId;

    @Before
    public void setUp() throws Exception {

        pluginId= UUID.randomUUID();


        testIntraUserActorPluginRoot = new IntraUserActorPluginRoot();
        testIntraUserActorPluginRoot.setErrorManager(mockErrorManager);
        testIntraUserActorPluginRoot.setIntraUserNetworkServiceManager(mockIintraUserNetworkServiceManager);
        testIntraUserActorPluginRoot.setEventManager(mockEventManager);
        testIntraUserActorPluginRoot.setPluginDatabaseSystem(mockPluginDatabaseSystem);
        testIntraUserActorPluginRoot.setId(pluginId);

        setUpMockitoRules();


    }

    public void setUpMockitoRules()  throws Exception{


        when(mockDatabase.getDatabaseFactory()).thenReturn(mockDatabaseFactory);
        when(mockDatabaseTable.getEmptyRecord()).thenReturn(mockDatabaseTableRecord);
        when(mockDatabase.getTable(IntraUserActorDatabaseConstants.INTRA_USER_TABLE_NAME)).thenReturn(mockDatabaseTable);
        when(mockPluginDatabaseSystem.openDatabase(pluginId, IntraUserActorDatabaseConstants.INTRA_USER_DATABASE_NAME)).thenReturn(mockDatabase);

        when(mockIntraUserActorDatabaseFactory.createDatabase(pluginId, IntraUserActorDatabaseConstants.INTRA_USER_DATABASE_NAME)).thenReturn(mockDatabase);

        when(mockEventManager.getNewListener(EventType.INTRA_USER_CONNECTION_ACCEPTED)).thenReturn(mockEventListener);
        when(mockEventManager.getNewListener(EventType.INTRA_USER_DISCONNECTION_REQUEST_RECEIVED)).thenReturn(mockEventListener);
        when(mockEventManager.getNewListener(EventType.INTRA_USER_REQUESTED_CONNECTION)).thenReturn(mockEventListener);
        when(mockEventManager.getNewListener(EventType.INTRA_USER_CONNECTION_DENIED)).thenReturn(mockEventListener);
    }

    @Test
    public void teststart_IntraUserActorPluginRootHasStartedOk_ThrowsCantStartPluginException() throws Exception {

        testIntraUserActorPluginRoot.start();
        ServiceStatus serviceStatus=testIntraUserActorPluginRoot.getStatus();
        Assertions.assertThat(serviceStatus).isEqualTo(ServiceStatus.STARTED);
    }


    @Test
    public void startTest_IntraUserActorPluginRootCanStarted_throwsCantStartPluginException() throws Exception {

        testIntraUserActorPluginRoot.setIntraUserNetworkServiceManager(null);


        catchException(testIntraUserActorPluginRoot).start();

        assertThat(caughtException())
                .isNotNull()
                .isInstanceOf(CantStartPluginException.class);

    }
}
