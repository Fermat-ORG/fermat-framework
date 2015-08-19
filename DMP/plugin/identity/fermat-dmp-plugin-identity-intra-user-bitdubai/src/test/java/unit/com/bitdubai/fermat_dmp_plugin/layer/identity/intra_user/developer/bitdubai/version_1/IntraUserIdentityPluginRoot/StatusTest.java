package unit.com.bitdubai.fermat_dmp_plugin.layer.identity.intra_user.developer.bitdubai.version_1.IntraUserIdentityPluginRoot;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_dmp_plugin.layer.identity.intra_user.developer.bitdubai.version_1.IntraUserIdentityPluginRoot;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
import java.util.UUID;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by angel on 18/8/15.
 */

@RunWith(MockitoJUnitRunner.class)
public class StatusTest {

    @Mock
    private Database mockDatabase;

    @Mock
    private PluginDatabaseSystem mockPluginDatabaseSystem;

    @Mock
    private PluginFileSystem mockPluginFileSystem;

    @Mock
    private DeveloperDatabaseTable developerDatabaseTable;

    @Mock
    private ErrorManager errorManager;

    private IntraUserIdentityPluginRoot pluginRoot;

    @Test
    public void getClassTest() throws CantStartPluginException {

        UUID testOwnerId = UUID.randomUUID();

        pluginRoot = new IntraUserIdentityPluginRoot();

        pluginRoot.setPluginDatabaseSystem(mockPluginDatabaseSystem);
        pluginRoot.setPluginFileSystem(mockPluginFileSystem);
        pluginRoot.setId(testOwnerId);

        pluginRoot.setErrorManager(errorManager);

        // pluginRoot.start();

        assertThat(pluginRoot.getStatus()).isEqualTo(ServiceStatus.CREATED);

        pluginRoot.stop();
        assertThat(pluginRoot.getStatus()).isEqualTo(ServiceStatus.STOPPED);

        pluginRoot.resume();
        assertThat(pluginRoot.getStatus()).isEqualTo(ServiceStatus.STARTED);

        pluginRoot.pause();
        assertThat(pluginRoot.getStatus()).isEqualTo(ServiceStatus.PAUSED);


        assertThat(pluginRoot.getClassesFullPath()).isInstanceOf(List.class);

    }
}
