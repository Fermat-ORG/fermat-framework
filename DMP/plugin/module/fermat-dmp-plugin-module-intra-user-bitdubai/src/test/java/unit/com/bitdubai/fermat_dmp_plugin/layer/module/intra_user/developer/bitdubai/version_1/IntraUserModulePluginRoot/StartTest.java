package unit.com.bitdubai.fermat_dmp_plugin.layer.module.intra_user.developer.bitdubai.version_1.IntraUserModulePluginRoot;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.event.EventType;
import com.bitdubai.fermat_api.layer.dmp_actor.intra_user.interfaces.ActorIntraUserManager;
import com.bitdubai.fermat_api.layer.dmp_identity.intra_user.interfaces.IntraUserIdentityManager;
import com.bitdubai.fermat_api.layer.dmp_network_service.intra_user.interfaces.IntraUserManager;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_dmp_plugin.layer.module.intra_user.developer.bitdubai.version_1.IntraUserModulePluginRoot;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_user.device_user.interfaces.DeviceUserManager;

import junit.framework.TestCase;

import org.fest.assertions.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static com.googlecode.catchexception.CatchException.catchException;
import static org.mockito.Mockito.when;

/**
 * Created by natalia on 19/08/15.
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
     * DealWithDeviceUserManager Interface member variables.
     */
    @Mock
    private DeviceUserManager mockDeviceUserManager;

    /**
     * DealWithIntraUserIdentityManager Interface member variables.
     */
    @Mock
    private IntraUserIdentityManager mockIntraUserIdentityManager;

    /**
     * DealWithPluginDatabaseSystem Interface member variables.
     */
    @Mock
    private PluginDatabaseSystem mockPluginDatabaseSystem;


    /**
     * DealWithActorIntraUserManager Interface member variables.
     */
    @Mock
    private ActorIntraUserManager mockActorIntraUserManager;


    /**
     * DealWithIntraUserNetworkServiceManager Interface member variables.
     */
    @Mock
    private IntraUserManager mockIntraUserNetworkServiceManager;


    private IntraUserModulePluginRoot testIntraUserModulePluginRoot;

    UUID pluginId;

    @Before
    public void setUp() throws Exception {

        pluginId= UUID.randomUUID();
        testIntraUserModulePluginRoot = new IntraUserModulePluginRoot();
        testIntraUserModulePluginRoot.setPluginFileSystem(mockPluginFileSystem);
        testIntraUserModulePluginRoot.setDeviceUserManager(mockDeviceUserManager);
        testIntraUserModulePluginRoot.setErrorManager(mockErrorManager);
        testIntraUserModulePluginRoot.setIntraUserManager(mockIntraUserIdentityManager);

        testIntraUserModulePluginRoot.setActorIntraUserManager(mockActorIntraUserManager);
        testIntraUserModulePluginRoot.setIntraUserNetworkServiceManager(mockIntraUserNetworkServiceManager);
    }


    @Test
    public void teststart_ThePlugInHasStartedOk_ThrowsCantStartPluginException() throws Exception {


        when(testIntraUserModulePluginRoot.getStatus()).thenReturn(ServiceStatus.STARTED);
        testIntraUserModulePluginRoot.start();
        ServiceStatus serviceStatus=testIntraUserModulePluginRoot.getStatus();
        Assertions.assertThat(serviceStatus).isEqualTo(ServiceStatus.STARTED);
    }


    @Test(expected=CantStartPluginException.class)
    public void startTest_ExtraUserUserAddonRootCanNotInicializate_throwsAnException() throws Exception {


        testIntraUserModulePluginRoot.setErrorManager(mockErrorManager);
        testIntraUserModulePluginRoot.setId(pluginId);

        testIntraUserModulePluginRoot.start();
        //System.out.println(mockErrorManager.getReportedException());

    }
}
