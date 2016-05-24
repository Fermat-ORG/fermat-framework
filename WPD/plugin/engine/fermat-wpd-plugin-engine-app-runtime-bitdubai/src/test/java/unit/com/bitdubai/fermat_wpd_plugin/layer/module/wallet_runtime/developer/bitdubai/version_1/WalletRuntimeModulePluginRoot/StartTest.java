package unit.com.bitdubai.fermat_wpd_plugin.layer.module.wallet_runtime.developer.bitdubai.version_1.WalletRuntimeModulePluginRoot;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;
import com.bitdubai.fermat_wpd_plugin.layer.engine.wallet_runtime.developer.bitdubai.version_1.AppRuntimeEnginePluginRoot;

import junit.framework.TestCase;

import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Created by natalia on 06/07/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class StartTest extends TestCase {

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
    private EventManager mockEventManager1;
    @Mock
    private FermatEventListener mockFermatEventListener1;

    private AppRuntimeEnginePluginRoot testAppRuntimeEnginePluginRoot;

//    @Before
//    public void setUp() throws Exception {
//        testAppRuntimeEnginePluginRoot = new AppRuntimeEnginePluginRoot();
//        testAppRuntimeEnginePluginRoot.setPluginFileSystem(mockPluginFileSystem);
//        testAppRuntimeEnginePluginRoot.setEventManager(mockEventManager);
//        testAppRuntimeEnginePluginRoot.setErrorManager(mockErrorManager);
//    }
//
//
//    @Test
//    public void teststart_ThePlugInHasStartedOk_ThrowsCantStartPluginException() throws Exception {
//
//
//        when(mockEventManager.getNewListener(EventType.WALLET_OPENED)).thenReturn(mockFermatEventListener);
//
//        catchException(testAppRuntimeEnginePluginRoot).start();
//
//        Assertions.assertThat(testAppRuntimeEnginePluginRoot.getStatus()).isEqualTo(ServiceStatus.CREATED);
//    }

}
