package unit.com.bitdubai.fermat_ccp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.WalletContactsMiddlewarePluginRoot;

import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_ccp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.WalletContactsMiddlewarePluginRoot;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.user.device_user.interfaces.DeviceUser;
import com.bitdubai.fermat_pip_api.layer.user.device_user.interfaces.DeviceUserManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Created by natalia on 10/09/15.
 */

@RunWith(MockitoJUnitRunner.class)
public class GetDatabaseTableContentTest {
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
    private List<DeveloperDatabaseTableRecord> TableContent;

    @Mock
    private DatabaseTable mockTable;

    @Mock
    private DatabaseTableRecord mockDatabaseTableRecord;
    @Mock
    private List<DatabaseTableRecord> databaseTableRecordList;

    private WalletContactsMiddlewarePluginRoot pluginRoot;

    @Test
    public void getDatabaseListTest() throws Exception {
        UUID testOwnerId = UUID.randomUUID();

        MockitoAnnotations.initMocks(this);

        pluginRoot = new WalletContactsMiddlewarePluginRoot();

        when(mockPluginDatabaseSystem.openDatabase(testOwnerId, testOwnerId.toString())).thenReturn(mockDatabase);
        databaseTableRecordList = Arrays.asList(mockDatabaseTableRecord, mockDatabaseTableRecord, mockDatabaseTableRecord);

        when(mockPluginDatabaseSystem.openDatabase(any(UUID.class), anyString())).thenReturn(mockDatabase);
        when(mockDatabase.getTable(anyString())).thenReturn(mockTable);
        when(mockTable.getRecords()).thenReturn(databaseTableRecordList);

        pluginRoot.setPluginDatabaseSystem(mockPluginDatabaseSystem);
        pluginRoot.setId(testOwnerId);
        pluginRoot.setErrorManager(errorManager);

        List<DeveloperDatabaseTableRecord> developerDatabaseTableRecordList = pluginRoot.getDatabaseTableContent(developerObjectFactory,developerDatabase,developerDatabaseTable);

        assertThat(developerDatabaseTableRecordList).isInstanceOf(List.class);

    }

}
