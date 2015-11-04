package unit.com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_resources.developer.bitdubai.version_1.SubAppResourcesInstallationNetworkServicePluginRoot;

import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.enums.EventType;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;
import com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_resources.developer.bitdubai.version_1.SubAppResourcesInstallationNetworkServicePluginRoot;
import com.googlecode.catchexception.CatchException;

import junit.framework.TestCase;

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
 * Created by francisco on 30/09/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class StartTest extends TestCase {


    @Mock
    private Database mockDatabase;

    @Mock
    private PluginDatabaseSystem mockPluginDatabaseSystem;

    /**
     * DealsWithErrors interface Mocked
     */
    @Mock
    private ErrorManager mockErrorManager;

    /**
     * UsesFileSystem Interface member variables.
     */
    @Mock
    private PluginFileSystem mockPluginFileSystem;

    /**
     * DealWithEvents Iianterface member variables.
     */
    @Mock
    private EventManager mockEventManager;
    @Mock
    private FermatEventListener mockFermatEventListener;

    @Mock
    private LogManager mockLogManager;

    private SubAppResourcesInstallationNetworkServicePluginRoot subAppResourcesInstallationNetworkServicePluginRoot;

    @Before
    public void setUp() throws Exception {
        subAppResourcesInstallationNetworkServicePluginRoot = new SubAppResourcesInstallationNetworkServicePluginRoot();
        subAppResourcesInstallationNetworkServicePluginRoot.setPluginFileSystem(mockPluginFileSystem);
        subAppResourcesInstallationNetworkServicePluginRoot.setPluginDatabaseSystem(mockPluginDatabaseSystem);
        subAppResourcesInstallationNetworkServicePluginRoot.setLogManager(mockLogManager);
        subAppResourcesInstallationNetworkServicePluginRoot.setEventManager(mockEventManager);
        subAppResourcesInstallationNetworkServicePluginRoot.setErrorManager(mockErrorManager);


        when(mockPluginDatabaseSystem.openDatabase(any(UUID.class), anyString())).thenReturn(mockDatabase);
    }

    @Test
    public void teststart_ThePlugInHasStartedOk_ThrowsCantStartPluginException() throws Exception {
        when(mockEventManager.getNewListener(EventType.BEGUN_WALLET_INSTALLATION)).thenReturn(mockFermatEventListener);
        catchException(subAppResourcesInstallationNetworkServicePluginRoot).start();
        assertThat(CatchException.<Exception>caughtException()).isNull();
        assertThat(subAppResourcesInstallationNetworkServicePluginRoot.getStatus()).isEqualTo(ServiceStatus.STARTED);
    }


    @Test
    public void getStatusTest()  {


        subAppResourcesInstallationNetworkServicePluginRoot.resume();
        assertThat(subAppResourcesInstallationNetworkServicePluginRoot.getStatus()).isEqualTo(ServiceStatus.STARTED);

        subAppResourcesInstallationNetworkServicePluginRoot.pause();
        assertThat(subAppResourcesInstallationNetworkServicePluginRoot.getStatus()).isEqualTo(ServiceStatus.PAUSED);

        subAppResourcesInstallationNetworkServicePluginRoot.stop();
        assertThat(subAppResourcesInstallationNetworkServicePluginRoot.getStatus()).isEqualTo(ServiceStatus.STOPPED);

    }

}
