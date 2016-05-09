package unit.com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_resources.developer.bitdubai.version_1.SubAppResourcesInstallationNetworkServicePluginRoot;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_resources.developer.bitdubai.version_1.SubAppResourcesNetworkServicePluginRoot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Created by francisco on 30/09/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class SetLoggingLevelPerClassTest {

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

    private SubAppResourcesNetworkServicePluginRoot subAppResourcesNetworkServicePluginRoot;
//
    @Test
    public void setLoggingLevelPerClassTest() throws CantOpenDatabaseException, DatabaseNotFoundException {
        UUID testOwnerId = UUID.randomUUID();

        subAppResourcesNetworkServicePluginRoot = new SubAppResourcesNetworkServicePluginRoot();

        when(mockPluginDatabaseSystem.openDatabase(any(UUID.class), anyString())).thenReturn(mockDatabase);

        subAppResourcesNetworkServicePluginRoot.setPluginDatabaseSystem(mockPluginDatabaseSystem);
        subAppResourcesNetworkServicePluginRoot.setPluginFileSystem(mockPluginFileSystem);
        subAppResourcesNetworkServicePluginRoot.setId(testOwnerId);
        subAppResourcesNetworkServicePluginRoot.setErrorManager(errorManager);

        ECCKeyPair eccKeyPair = new ECCKeyPair();
        Map<String, LogLevel> newLoggingLevel = new HashMap<String, LogLevel>();
        newLoggingLevel.put(eccKeyPair.getPrivateKey(), LogLevel.AGGRESSIVE_LOGGING);

        subAppResourcesNetworkServicePluginRoot.setLoggingLevelPerClass(newLoggingLevel);



    }
}
