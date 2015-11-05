package unit.com.bitdubai.fermat_ccp_plugin.layer.identity.intra_wallet_user.developer.bitdubai.version_1.IntraUserIdentityPluginRoot;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_ccp_plugin.layer.identity.intra_wallet_user.developer.bitdubai.version_1.IntraWalletUserIdentityPluginRoot;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

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

    private IntraWalletUserIdentityPluginRoot pluginRoot;

    @Test
    public void getStatusTest() throws CantOpenDatabaseException, DatabaseNotFoundException, CantStartPluginException {

        UUID testOwnerId = UUID.randomUUID();

        pluginRoot = new IntraWalletUserIdentityPluginRoot();

        when(mockPluginDatabaseSystem.openDatabase(any(UUID.class), anyString())).thenReturn(mockDatabase);

        pluginRoot.setPluginDatabaseSystem(mockPluginDatabaseSystem);
        pluginRoot.setPluginFileSystem(mockPluginFileSystem);
        pluginRoot.setId(testOwnerId);
        pluginRoot.setErrorManager(errorManager);

        assertThat(pluginRoot.getStatus()).isEqualTo(ServiceStatus.CREATED);

        pluginRoot.start();
        assertThat(pluginRoot.getStatus()).isEqualTo(ServiceStatus.STARTED);

        pluginRoot.resume();
        assertThat(pluginRoot.getStatus()).isEqualTo(ServiceStatus.STARTED);

        pluginRoot.pause();
        assertThat(pluginRoot.getStatus()).isEqualTo(ServiceStatus.PAUSED);

        pluginRoot.stop();
        assertThat(pluginRoot.getStatus()).isEqualTo(ServiceStatus.STOPPED);

    }
}
