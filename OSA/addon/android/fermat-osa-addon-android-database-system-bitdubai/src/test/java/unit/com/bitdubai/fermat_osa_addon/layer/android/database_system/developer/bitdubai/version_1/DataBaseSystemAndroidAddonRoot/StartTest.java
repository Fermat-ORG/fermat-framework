package unit.com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.DataBaseSystemAndroidAddonRoot;

import android.content.Context;

import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PlatformDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.DataBaseSystemAndroidAddonRoot;

import junit.framework.TestCase;

import org.fest.assertions.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.UUID;
import java.util.logging.ErrorManager;

import unit.com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.CustomBuildConfig;


/**
 * Created by natalia on 11/09/15.
 */

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = CustomBuildConfig.class)
public class StartTest extends TestCase
{

    /**
     * DealsWithEvents Interface member variables.
     */
    @Mock
    Context mockContext;

    @Mock
    Object mockObjectContext;

    /**
     * DealsWithErrors interface Mocked
     */
    @Mock
    private ErrorManager mockErrorManager;

    /**
     * PluginDatabaseSystem Interface member variables.
     */
    @Mock
    PluginDatabaseSystem pluginDatabaseSystem;
    @Mock
    PlatformDatabaseSystem platformDatabaseSystem;

    private DataBaseSystemAndroidAddonRoot pluginRoot;


    UUID pluginId;

    @Before
    public void setUp() throws Exception {

        pluginId= UUID.randomUUID();


        pluginRoot = new DataBaseSystemAndroidAddonRoot();
        pluginRoot.setContext(mockContext);


    }



    @Test
    public void teststart_StartedOk_ThrowsCantStartPluginException() throws Exception {

        pluginRoot.start();
        ServiceStatus serviceStatus=pluginRoot.getStatus();
        Assertions.assertThat(serviceStatus).isEqualTo(ServiceStatus.STARTED);
    }

    @Test
    public void teststart_StopOk_ThrowsCantStartPluginException() throws Exception {

        pluginRoot.start();
        pluginRoot.stop();
        ServiceStatus serviceStatus=pluginRoot.getStatus();
        Assertions.assertThat(serviceStatus).isEqualTo(ServiceStatus.STOPPED);
    }

    @Test
    public void teststart_PauseOk_ThrowsCantStartPluginException() throws Exception {

        pluginRoot.start();
        pluginRoot.pause();
        ServiceStatus serviceStatus=pluginRoot.getStatus();
        Assertions.assertThat(serviceStatus).isEqualTo(ServiceStatus.PAUSED);
    }


    @Test
    public void teststart_ResumeOk_ThrowsCantStartPluginException() throws Exception {

        pluginRoot.start();
        pluginRoot.resume();
        ServiceStatus serviceStatus=pluginRoot.getStatus();
        Assertions.assertThat(serviceStatus).isEqualTo(ServiceStatus.STARTED);
    }


}
