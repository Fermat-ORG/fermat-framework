/*
package unit.com.bitdubai.fermat_ccp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.WalletContactsMiddlewarePluginRoot;

import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.exceptions.CantGetWalletContactRegistryException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.interfaces.WalletContactsRegistry;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_ccp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.WalletContactsMiddlewarePluginRoot;
import com.bitdubai.fermat_ccp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.database.WalletContactsMiddlewareDatabaseConstants;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
import java.util.UUID;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

*/
/**
 * Created by natalia on 10/09/15.
 *//*


@RunWith(MockitoJUnitRunner.class)
public class GetWalletContactsRegistryTest
{
    @Mock
private Database mockDatabase;

@Mock
private PluginDatabaseSystem mockPluginDatabaseSystem;

@Mock
private ErrorManager errorManager;

    @Mock
    private LogManager mockLogManager;

@Mock
private DatabaseTable mockTable;

@Mock
private List<DatabaseTableRecord> mockRecords;

@Mock
private DatabaseTableRecord mockRecord;


private WalletContactsMiddlewarePluginRoot pluginRoot;

        @Before
        public void setUp() throws Exception {
            UUID testOwnerId = UUID.randomUUID();

            pluginRoot = new WalletContactsMiddlewarePluginRoot();

            when(mockPluginDatabaseSystem.openDatabase(any(UUID.class), anyString())).thenReturn(mockDatabase);
            when(mockDatabase.getTable(WalletContactsMiddlewareDatabaseConstants.WALLET_CONTACTS_TABLE_NAME)).thenReturn(mockTable);

            pluginRoot.setPluginDatabaseSystem(mockPluginDatabaseSystem);
            pluginRoot.setId(testOwnerId);
            pluginRoot.setLogManager(mockLogManager);
            pluginRoot.setErrorManager(errorManager);
            when(mockTable.getRecords()).thenReturn(mockRecords);
            when(mockTable.getEmptyRecord()).thenReturn(mockRecord);

            when(mockTable.getRecords()).thenReturn(mockRecords);
            when(mockPluginDatabaseSystem.openDatabase(any(UUID.class), anyString())).thenReturn(mockDatabase);

            pluginRoot.start();
        }


        @Test
        public void getWalletContactsRegistryTest_Ok_ThrowsCantGetWalletContactRegistryException() throws Exception{

            WalletContactsRegistry walletContactsRegistry = pluginRoot.getWalletContactsRegistry();
            assertThat(walletContactsRegistry).isNotNull().isInstanceOf(WalletContactsRegistry.class);

        }

    @Test
    public void getWalletContactsRegistryTest_ErrorNullObject_ThrowsCantGetWalletContactRegistryException() throws Exception{

        pluginRoot.setLogManager(null);
        pluginRoot.setErrorManager(null);
        catchException(pluginRoot).getWalletContactsRegistry();
        assertThat(caughtException()).isInstanceOf(CantGetWalletContactRegistryException.class);

    }
}
*/
