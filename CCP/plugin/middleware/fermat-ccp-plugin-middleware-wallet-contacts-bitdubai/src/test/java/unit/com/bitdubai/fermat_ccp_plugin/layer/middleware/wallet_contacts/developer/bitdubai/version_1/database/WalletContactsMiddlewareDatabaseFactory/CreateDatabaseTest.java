package unit.com.bitdubai.fermat_ccp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.database.WalletContactsMiddlewareDatabaseFactory;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.InvalidOwnerIdException;
import com.bitdubai.fermat_ccp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.database.WalletContactsMiddlewareDatabaseFactory;

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

/**
 * Created by natalia on 10/09/15.
 */

@RunWith(MockitoJUnitRunner.class)
public class CreateDatabaseTest extends TestCase {

    @Mock
    private PluginDatabaseSystem mockPluginDatabaseSystem;
    @Mock
    private Database mockDatabase;
    @Mock
    private DatabaseFactory mockDatabaseFactory;
    @Mock
    private DatabaseTable mockTable;

    @Mock
    private DatabaseTableFactory mockTableFactory;


    private UUID testOwnerId;


    private WalletContactsMiddlewareDatabaseFactory testDatabaseFactory;

    private void setUpIds() {
        testOwnerId = UUID.randomUUID();
    }

    private void setUpMockitoGeneralRules() throws Exception {
        when(mockPluginDatabaseSystem.createDatabase(any(UUID.class), anyString())).thenReturn(mockDatabase);
        when(mockDatabase.getDatabaseFactory()).thenReturn(mockDatabaseFactory);
        when(mockDatabaseFactory.newTableFactory(any(UUID.class), anyString())).thenReturn(mockTableFactory);
        when(mockDatabase.getTable(anyString())).thenReturn(mockTable);

    }

    @Before
    public void setUp() throws Exception {
        setUpIds();
        setUpMockitoGeneralRules();
    }

    @Test
    public void CreateDatabase_DatabaseAndTablesProperlyCreated_ReturnsDatabase() throws Exception {
        testDatabaseFactory = new WalletContactsMiddlewareDatabaseFactory(mockPluginDatabaseSystem);

        Database checkDatabase = testDatabaseFactory.createDatabase(testOwnerId, testOwnerId.toString());

        assertThat(checkDatabase).isEqualTo(mockDatabase);
    }

    @Test
    public void CreateDatabase_PluginSystemCantCreateDatabase_ThrowsCantCreateDatabaseException() throws Exception {
        when(mockPluginDatabaseSystem.createDatabase(testOwnerId, testOwnerId.toString())).thenThrow(new CantCreateDatabaseException("MOCK", null, null, null));

        testDatabaseFactory = new WalletContactsMiddlewareDatabaseFactory(mockPluginDatabaseSystem);

        catchException(testDatabaseFactory).createDatabase(testOwnerId, testOwnerId.toString());

        assertThat(caughtException())
                .isNotNull()
                .isInstanceOf(CantCreateDatabaseException.class);
    }

    @Test
    public void CreateDatabase_CantCreateTable_ThrowsCantCreateDatabaseException() throws Exception {

        when(mockDatabaseFactory.newTableFactory(any(UUID.class), anyString())).thenThrow(new InvalidOwnerIdException("MOCK", null, null, null));
        testDatabaseFactory = new WalletContactsMiddlewareDatabaseFactory(mockPluginDatabaseSystem);

        catchException(testDatabaseFactory).createDatabase(testOwnerId, testOwnerId.toString());

        assertThat(caughtException())
                .isNotNull()
                .isInstanceOf(CantCreateDatabaseException.class);
    }

    @Test
    public void CreateDatabase_ConflictedIdWhenCreatingTable_ThrowsCantCreateDatabaseException() throws Exception {
        when(mockDatabaseFactory.newTableFactory(any(UUID.class), anyString())).thenThrow(new InvalidOwnerIdException("MOCK", null, null, null));
        testDatabaseFactory = new WalletContactsMiddlewareDatabaseFactory(mockPluginDatabaseSystem);

        catchException(testDatabaseFactory).createDatabase(testOwnerId, testOwnerId.toString());

        assertThat(caughtException())
                .isNotNull()
                .isInstanceOf(CantCreateDatabaseException.class);
    }

    @Test
    public void CreateDatabase_GeneralExceptionThrown_ThrowsCantCreateDatabaseException() throws Exception {
        when(mockDatabase.getDatabaseFactory()).thenReturn(null);

        testDatabaseFactory = new WalletContactsMiddlewareDatabaseFactory(mockPluginDatabaseSystem);

        catchException(testDatabaseFactory).createDatabase(testOwnerId, testOwnerId.toString());

        assertThat(caughtException())
                .isNotNull()
                .isInstanceOf(CantCreateDatabaseException.class);
    }
}
