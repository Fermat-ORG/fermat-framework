package unit.com.bitdubai.fermat_ccp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.WalletContactsMiddlewarePluginRoot;

import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_ccp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.WalletContactsMiddlewarePluginRoot;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
import java.util.UUID;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * Created by natalia on 10/09/15.
 */

@RunWith(MockitoJUnitRunner.class)
public class GetDatabaseTableListTest {
    @Mock
    private PluginFileSystem mockPluginFileSystem;

    @Mock
    private DeveloperDatabaseTable developerDatabaseTable;

    @Mock
    private DeveloperDatabase developerDatabase;

    @Mock
    private ErrorManager errorManager;
    @Mock
    private DeveloperObjectFactory developerObjectFactory;

    @Mock
    private Database mockDatabase;

    @Mock
    private PluginDatabaseSystem mockPluginDatabaseSystem;


    private WalletContactsMiddlewarePluginRoot pluginRoot;

    @Test
    public void getDatabaseTableListTest() throws Exception {
        UUID testOwnerId = UUID.randomUUID();

        MockitoAnnotations.initMocks(this);

        pluginRoot = new WalletContactsMiddlewarePluginRoot();

        when(mockPluginDatabaseSystem.openDatabase(testOwnerId, testOwnerId.toString())).thenReturn(mockDatabase);

        pluginRoot.setPluginDatabaseSystem(mockPluginDatabaseSystem);
        pluginRoot.setId(testOwnerId);
        pluginRoot.setErrorManager(errorManager);

        List<DeveloperDatabaseTable> developerDatabaseTableList = pluginRoot.getDatabaseTableList(developerObjectFactory, developerDatabase);

        assertThat(developerDatabaseTableList).isInstanceOf(List.class);

    }

}

