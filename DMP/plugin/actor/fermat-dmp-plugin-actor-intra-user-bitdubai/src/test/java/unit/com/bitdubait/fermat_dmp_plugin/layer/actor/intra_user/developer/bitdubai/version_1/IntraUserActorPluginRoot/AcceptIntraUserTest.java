package unit.com.bitdubait.fermat_dmp_plugin.layer.actor.intra_user.developer.bitdubai.version_1.IntraUserActorPluginRoot;

import com.bitdubai.fermat_api.layer.all_definition.enums.DeviceDirectory;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.enums.EventType;
import com.bitdubai.fermat_api.layer.dmp_actor.intra_user.exceptions.CantAcceptIntraUserException;
import com.bitdubai.fermat_api.layer.dmp_network_service.intra_user.interfaces.IntraUserManager;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginBinaryFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_dmp_plugin.layer.actor.intra_user.developer.bitdubai.version_1.IntraUserActorPluginRoot;
import com.bitdubai.fermat_dmp_plugin.layer.actor.intra_user.developer.bitdubai.version_1.database.IntraUserActorDatabaseConstants;
import com.bitdubai.fermat_dmp_plugin.layer.actor.intra_user.developer.bitdubai.version_1.database.IntraUserActorDatabaseFactory;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventManager;

import junit.framework.TestCase;

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
public class AcceptIntraUserTest extends TestCase
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
     * UsesFileSystem Interface member variables.
     */
    @Mock
    private PluginFileSystem mockPluginFileSystem;

    @Mock
    private PluginBinaryFile mockFile;
    /**
     * DealsWithIntraUsersNetworkService interface member variable
     */
    @Mock
    private IntraUserManager mockIintraUserNetworkServiceManager;

    DatabaseTable mockDatabaseTable= Mockito.mock(DatabaseTable.class);
    DatabaseTableRecord mockDatabaseTableRecord=Mockito.mock(DatabaseTableRecord.class);
    Database mockDatabase= Mockito.mock(Database.class);
    FermatEventListener mockFermatEventListener = Mockito.mock(FermatEventListener.class);
    private IntraUserActorPluginRoot testIntraUserActorPluginRoot;

    private String intraUserPublicKey ;
    private String intraUserLoggedPublicKey ;

    UUID pluginId;

    @Before
    public void setUp() throws Exception {

        pluginId= UUID.randomUUID();

        intraUserLoggedPublicKey = UUID.randomUUID().toString();
        intraUserPublicKey = UUID.randomUUID().toString();

        testIntraUserActorPluginRoot = new IntraUserActorPluginRoot();
        testIntraUserActorPluginRoot.setPluginFileSystem(mockPluginFileSystem);
        testIntraUserActorPluginRoot.setErrorManager(mockErrorManager);
        testIntraUserActorPluginRoot.setIntraUserNetworkServiceManager(mockIintraUserNetworkServiceManager);
        testIntraUserActorPluginRoot.setEventManager(mockEventManager);
        testIntraUserActorPluginRoot.setPluginDatabaseSystem(mockPluginDatabaseSystem);
        testIntraUserActorPluginRoot.setId(pluginId);

        setUpMockitoRules();

        testIntraUserActorPluginRoot.start();


    }

    public void setUpMockitoRules()  throws Exception{


        when(mockDatabase.getDatabaseFactory()).thenReturn(mockDatabaseFactory);
        when(mockDatabaseTable.getEmptyRecord()).thenReturn(mockDatabaseTableRecord);
        when(mockDatabase.getTable(IntraUserActorDatabaseConstants.INTRA_USER_TABLE_NAME)).thenReturn(mockDatabaseTable);
        when(mockPluginDatabaseSystem.openDatabase(pluginId, IntraUserActorDatabaseConstants.INTRA_USER_DATABASE_NAME)).thenReturn(mockDatabase);

        when(mockIntraUserActorDatabaseFactory.createDatabase(pluginId, IntraUserActorDatabaseConstants.INTRA_USER_DATABASE_NAME)).thenReturn(mockDatabase);

        when(mockPluginFileSystem.getBinaryFile(pluginId, DeviceDirectory.LOCAL_USERS.getName(), "intraUserActorProfileImage" + "_" + intraUserPublicKey, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT)).thenReturn(null);

        when(mockPluginFileSystem.createBinaryFile(pluginId, DeviceDirectory.LOCAL_USERS.getName(), "intraUserActorProfileImage" + "_" +  intraUserPublicKey, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT)).thenReturn(mockFile);

        when(mockEventManager.getNewListener(EventType.INTRA_USER_CONNECTION_ACCEPTED)).thenReturn(mockFermatEventListener);
        when(mockEventManager.getNewListener(EventType.INTRA_USER_DISCONNECTION_REQUEST_RECEIVED)).thenReturn(mockFermatEventListener);
        when(mockEventManager.getNewListener(EventType.INTRA_USER_REQUESTED_CONNECTION)).thenReturn(mockFermatEventListener);
        when(mockEventManager.getNewListener(EventType.INTRA_USER_CONNECTION_DENIED)).thenReturn(mockFermatEventListener);
    }

    @Test
    public void AcceptIntraUserTest_UpdateStatusOk_ThrowsCantAcceptIntraUserException() throws Exception {

        catchException(testIntraUserActorPluginRoot).acceptIntraUser(intraUserLoggedPublicKey, intraUserPublicKey);
        assertThat(caughtException()).isNull();
    }


    @Test
    public void AcceptIntraUserTest_UpdateStatusError_throwsCantAcceptIntraUserException() throws Exception {

        when(mockDatabase.getTable(IntraUserActorDatabaseConstants.INTRA_USER_TABLE_NAME)).thenReturn(null);

        catchException(testIntraUserActorPluginRoot).acceptIntraUser(intraUserLoggedPublicKey, intraUserPublicKey);

        assertThat(caughtException())
                .isNotNull()
                .isInstanceOf(CantAcceptIntraUserException.class);

    }
}
