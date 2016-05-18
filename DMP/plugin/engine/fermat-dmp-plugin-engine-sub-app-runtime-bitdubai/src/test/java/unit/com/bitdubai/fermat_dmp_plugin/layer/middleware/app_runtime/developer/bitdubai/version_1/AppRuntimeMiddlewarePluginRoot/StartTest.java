package unit.com.bitdubai.fermat_dmp_plugin.layer.middleware.app_runtime.developer.bitdubai.version_1.AppRuntimeMiddlewarePluginRoot;

import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.enums.EventType;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;
import com.bitdubai.fermat_dmp_plugin.layer.engine.sub_app_runtime.developer.bitdubai.version_1.SubAppRuntimeEnginePluginRoot;

import junit.framework.TestCase;
import org.fest.assertions.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static com.googlecode.catchexception.CatchException.catchException;
import static org.mockito.Mockito.when;

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

    private SubAppRuntimeEnginePluginRoot testSubAppRuntimeMiddlewarePluginRoot;

    @Before
    public void setUp() throws Exception {
        testSubAppRuntimeMiddlewarePluginRoot = new SubAppRuntimeEnginePluginRoot();
        testSubAppRuntimeMiddlewarePluginRoot.setPluginFileSystem(mockPluginFileSystem);
        testSubAppRuntimeMiddlewarePluginRoot.setEventManager(mockEventManager);
        testSubAppRuntimeMiddlewarePluginRoot.setErrorManager(mockErrorManager);
    }


    @Test
    public void teststart_ThePlugInHasStartedOk_ThrowsCantStartPluginException() throws Exception {

        when(mockEventManager.getNewListener(EventType.WALLET_RESOURCES_INSTALLED)).thenReturn(mockFermatEventListener);
        catchException(testSubAppRuntimeMiddlewarePluginRoot).start();

        Assertions.assertThat(testSubAppRuntimeMiddlewarePluginRoot.getStatus()).isEqualTo(ServiceStatus.STARTED);
    }





}
