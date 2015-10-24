/*
package unit.com.bitdubai.fermat_ccp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.database.WalletContactsMiddlewareDeveloperDatabaseFactory;

import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_ccp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.database.WalletContactsMiddlewareDeveloperDatabaseFactory;
import com.bitdubai.fermat_ccp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.exceptions.CantInitializeWalletContactsMiddlewareDatabaseException;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

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
public class InitializeDatabaseTest extends TestCase {


    @Mock
    private Database mockDatabase;
    @Mock
    private DatabaseFactory mockDatabaseFactory;
    @Mock
    private DatabaseTable mockTable;

    @Mock
    private DatabaseTableFactory mockExtraUserTableFactory;
    @Mock
    private DeveloperObjectFactory developerObjectFactory;

    private UUID testOwnerId;
    @Mock
    private PluginDatabaseSystem mockPluginDatabaseSystem;

    private WalletContactsMiddlewareDeveloperDatabaseFactory developerDatabaseFactory;


    private void setUpIds() {
        testOwnerId = UUID.randomUUID();
    }

    private void setUpMockitoGeneralRules() throws Exception {
        when(mockPluginDatabaseSystem.createDatabase(any(UUID.class), anyString())).thenReturn(mockDatabase);
        when(mockDatabase.getDatabaseFactory()).thenReturn(mockDatabaseFactory);
        when(mockDatabaseFactory.newTableFactory(any(UUID.class), anyString())).thenReturn(mockExtraUserTableFactory);
        when(mockDatabase.getTable(anyString())).thenReturn(mockTable);

    }

    @Before
    public void setUp() throws Exception {
        setUpIds();
        setUpMockitoGeneralRules();

        when(mockPluginDatabaseSystem.openDatabase(any(UUID.class), anyString())).thenReturn(mockDatabase);

        developerDatabaseFactory = new WalletContactsMiddlewareDeveloperDatabaseFactory(mockPluginDatabaseSystem, testOwnerId);

        developerDatabaseFactory.setPluginDatabaseSystem(mockPluginDatabaseSystem);
        developerDatabaseFactory.setPluginId(testOwnerId);

    }
    @Test
    public void initializeDatabase_initOK_throwsCantInitializeWalletContactsMiddlewareDatabaseException() throws Exception {

        catchException(developerDatabaseFactory).initializeDatabase();
        assertThat(caughtException())
                .isNull();
    }

    @Test
    public void initializeDatabase_initError_throwCantInitializeWalletContactsMiddlewareDatabaseException() throws Exception {
           developerDatabaseFactory.setPluginDatabaseSystem(null);

        catchException(developerDatabaseFactory).initializeDatabase();

        assertThat(caughtException())
                .isNotNull()
                .isInstanceOf(CantInitializeWalletContactsMiddlewareDatabaseException.class);
    }

    @Test
    public void initializeDatabase_DataBaseNotFound_throwCantInitializeWalletContactsMiddlewareDatabaseException() throws Exception {

        when(mockPluginDatabaseSystem.openDatabase(any(UUID.class), anyString())).thenThrow(DatabaseNotFoundException.class);

        catchException(developerDatabaseFactory).initializeDatabase();

        assertThat(caughtException())
                .isNull();

    }
}
*/
