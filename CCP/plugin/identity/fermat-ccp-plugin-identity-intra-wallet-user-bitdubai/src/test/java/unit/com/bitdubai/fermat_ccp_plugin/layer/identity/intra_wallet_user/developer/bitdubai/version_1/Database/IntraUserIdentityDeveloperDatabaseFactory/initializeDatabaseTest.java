package unit.com.bitdubai.fermat_ccp_plugin.layer.identity.intra_wallet_user.developer.bitdubai.version_1.Database.IntraUserIdentityDeveloperDatabaseFactory;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_ccp_plugin.layer.identity.intra_wallet_user.developer.bitdubai.version_1.database.IntraWalletUserIdentityDeveloperDatabaseFactory;
import com.bitdubai.fermat_ccp_plugin.layer.identity.intra_wallet_user.developer.bitdubai.version_1.exceptions.CantInitializeIntraWalletUserIdentityDatabaseException;

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
 * Created by angel on 20/8/15.
 */

@RunWith(MockitoJUnitRunner.class)
public class initializeDatabaseTest{

    @Mock
    private Database mockDatabase;

    @Mock
    private PluginDatabaseSystem mockPluginDatabaseSystem;

    private IntraWalletUserIdentityDeveloperDatabaseFactory DatabaseFactory;


    @Before
    public void SetUp() throws CantCreateDatabaseException, CantInitializeIntraWalletUserIdentityDatabaseException, CantOpenDatabaseException, DatabaseNotFoundException {
        UUID testOwnerId = UUID.randomUUID();

        when(mockPluginDatabaseSystem.openDatabase(any(UUID.class), anyString())).thenReturn(mockDatabase);

        DatabaseFactory = new IntraWalletUserIdentityDeveloperDatabaseFactory(mockPluginDatabaseSystem, testOwnerId);

        DatabaseFactory.setPluginDatabaseSystem(mockPluginDatabaseSystem);
        DatabaseFactory.setPluginId(testOwnerId);
    }
    @Test
    public void initializeDatabaseTest() throws CantOpenDatabaseException, DatabaseNotFoundException, CantInitializeIntraWalletUserIdentityDatabaseException {


        catchException(DatabaseFactory).initializeDatabase();

        assertThat(caughtException()).isNull();
    }

    @Test
    public void initializeDatabaseTest_InitError_ThrowsCantInitializeIntraUserIdentityDatabaseException() throws Exception{

        DatabaseFactory.setPluginDatabaseSystem(null);

        catchException(DatabaseFactory).initializeDatabase();
        assertThat(caughtException()).isNotNull().isInstanceOf(CantInitializeIntraWalletUserIdentityDatabaseException.class);

    }

    @Test
    public void initializeDatabaseTest_DatabaseNotFound_ThrowsCantInitializeIntraUserIdentityDatabaseException() throws Exception{
        when(mockPluginDatabaseSystem.openDatabase(any(UUID.class), anyString())).thenThrow(DatabaseNotFoundException.class);
         catchException(DatabaseFactory).initializeDatabase();
        assertThat(caughtException()).isNotNull().isInstanceOf(CantInitializeIntraWalletUserIdentityDatabaseException.class);

    }

}
