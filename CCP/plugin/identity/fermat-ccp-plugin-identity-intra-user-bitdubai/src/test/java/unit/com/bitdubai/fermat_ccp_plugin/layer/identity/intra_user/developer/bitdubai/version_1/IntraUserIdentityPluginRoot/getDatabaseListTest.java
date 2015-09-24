package unit.com.bitdubai.fermat_ccp_plugin.layer.identity.intra_user.developer.bitdubai.version_1.IntraUserIdentityPluginRoot;

import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_ccp_plugin.layer.identity.intra_user.developer.bitdubai.version_1.IntraUserIdentityPluginRoot;
import com.bitdubai.fermat_ccp_plugin.layer.identity.intra_user.developer.bitdubai.version_1.database.IntraUserIdentityDatabaseConstants;
import com.bitdubai.fermat_ccp_plugin.layer.identity.intra_user.developer.bitdubai.version_1.exceptions.CantInitializeIntraUserIdentityDatabaseException;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_user.device_user.interfaces.DeviceUser;
import com.bitdubai.fermat_pip_api.layer.pip_user.device_user.interfaces.DeviceUserManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;

/**
 * Created by angel on 26/8/15.
 */

@RunWith(MockitoJUnitRunner.class)
public class getDatabaseListTest {
    @Mock
    private PluginFileSystem mockPluginFileSystem;

    @Mock
    private DeveloperDatabaseTable developerDatabaseTable;

    @Mock
    private DeveloperDatabase developerDatabase;

    @Mock
    private DeviceUserManager deviceUserManager;

    @Mock
    private ErrorManager errorManager;

    @Mock
    private DeviceUser loggedUser;

    @Mock
    private DeveloperObjectFactory developerObjectFactory;

    @Mock
    private Database mockDatabase;

    @Mock
    private PluginDatabaseSystem mockPluginDatabaseSystem;

    @Mock
    List<DeveloperDatabaseTableRecord> TableContent;


    private IntraUserIdentityPluginRoot pluginRoot;

    @Test
    public void getDatabaseListTest() throws CantOpenDatabaseException, DatabaseNotFoundException, CantInitializeIntraUserIdentityDatabaseException {
        UUID testOwnerId = UUID.randomUUID();

        MockitoAnnotations.initMocks(this);

        pluginRoot = new IntraUserIdentityPluginRoot();

        when(mockPluginDatabaseSystem.openDatabase(testOwnerId, IntraUserIdentityDatabaseConstants.INTRA_USER_DATABASE_NAME)).thenReturn(mockDatabase);

        pluginRoot.setPluginDatabaseSystem(mockPluginDatabaseSystem);
        pluginRoot.setPluginFileSystem(mockPluginFileSystem);
        pluginRoot.setId(testOwnerId);
        pluginRoot.setErrorManager(errorManager);
        pluginRoot.setDeviceUserManager(deviceUserManager);

        pluginRoot.getDatabaseList(developerObjectFactory);
        pluginRoot.getDatabaseTableList(developerObjectFactory, developerDatabase);
    }

}
