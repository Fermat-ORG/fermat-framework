/*
package unit.com.bitdubai.fermat_ccp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.database.WalletContactsMiddlewareDao;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;

import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_ccp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.database.WalletContactsMiddlewareDao;
import com.bitdubai.fermat_ccp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.exceptions.CantInitializeWalletContactsMiddlewareDatabaseException;

import com.googlecode.catchexception.CatchException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static com.googlecode.catchexception.CatchException.catchException;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

*/
/**
 * Created by natalia on 11/09/15.
 *//*

@RunWith(MockitoJUnitRunner.class)
public class InitializeTest {

    private WalletContactsMiddlewareDao walletContactsMiddlewareDao;

    @Mock
    private Database mockDatabase;

    @Mock
    private PluginDatabaseSystem mockPluginDatabaseSystem;

    @Mock
    private DatabaseFactory mockDatabaseFactory;
    @Mock
    private DatabaseTable mockTable;

    @Mock
    private DatabaseTableFactory mockTableFactory;

    private UUID testOwnerId1;

    @Before
    public void setUp() throws Exception {
        testOwnerId1 = UUID.randomUUID();
        when(mockPluginDatabaseSystem.createDatabase(any(UUID.class), anyString())).thenReturn(mockDatabase);
        when(mockPluginDatabaseSystem.openDatabase(any(UUID.class), anyString())).thenReturn(mockDatabase);
        when(mockDatabase.getDatabaseFactory()).thenReturn(mockDatabaseFactory);
        when(mockDatabaseFactory.newTableFactory(any(UUID.class), anyString())).thenReturn(mockTableFactory);
        when(mockDatabase.getTable(anyString())).thenReturn(mockTable);


        walletContactsMiddlewareDao = new WalletContactsMiddlewareDao(mockPluginDatabaseSystem, testOwnerId1);
        walletContactsMiddlewareDao.setPluginDatabaseSystem(mockPluginDatabaseSystem);
        walletContactsMiddlewareDao.setPluginId(testOwnerId1);
    }

    @Test
    public void initializeDatabaseTest() throws CantInitializeWalletContactsMiddlewareDatabaseException, CantOpenDatabaseException, DatabaseNotFoundException {

        catchException(walletContactsMiddlewareDao).initialize();
        assertThat(CatchException.<Exception>caughtException()).isNull();

    }

    @Test
    public void initializeDatabaseTest_Error_ThrowsCantInitializeIntraUserIdentityDatabaseException() throws Exception {

        when(mockPluginDatabaseSystem.openDatabase(any(UUID.class), anyString())).thenThrow(CantOpenDatabaseException.class);
        catchException(walletContactsMiddlewareDao).initialize();
        assertThat(CatchException.<Exception>caughtException()).isNotNull();

    }

    @Test
    public void initializeDatabaseTest_DatabaseNotFound_ThrowsCantInitializeIntraUserIdentityDatabaseException() throws Exception {

        when(mockPluginDatabaseSystem.openDatabase(any(UUID.class), anyString())).thenThrow(DatabaseNotFoundException.class);
        catchException(walletContactsMiddlewareDao).initialize();
        assertThat(CatchException.<Exception>caughtException()).isNull();

    }

}
*/
