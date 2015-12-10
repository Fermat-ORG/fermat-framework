package unit.com.bitdubai.fermat_bch_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure.CryptoVaultDatabaseFactory;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;

import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_bch_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure.CryptoVaultDatabaseFactory;


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

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Created by natalia on 26/08/15.
 */

@RunWith(MockitoJUnitRunner.class)
public class CreateDatabaseTest  extends TestCase {


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

    private String deviceUserPublicKey;

    private CryptoVaultDatabaseFactory testDatabaseFactory;

    private void setUpIds(){
        testOwnerId = UUID.randomUUID();

        deviceUserPublicKey = UUID.randomUUID().toString();

    }

    private void setUpMockitoGeneralRules() throws Exception{
        when(mockPluginDatabaseSystem.createDatabase(testOwnerId, deviceUserPublicKey)).thenReturn(mockDatabase);
        when(mockDatabase.getDatabaseFactory()).thenReturn(mockDatabaseFactory);
        when(mockDatabaseFactory.newTableFactory(anyString())).thenReturn(mockTableFactory);

    }

    @Before
    public void setUp() throws Exception{
        setUpIds();
        setUpMockitoGeneralRules();
    }

    @Test
    public void CreateDatabase_DatabaseAndTablesProperlyCreated_ReturnsDatabase() throws Exception{
        testDatabaseFactory = new CryptoVaultDatabaseFactory();
        testDatabaseFactory.setPluginDatabaseSystem(mockPluginDatabaseSystem);
        Database checkDatabase = testDatabaseFactory.createDatabase(testOwnerId, deviceUserPublicKey);

        assertThat(checkDatabase).isEqualTo(mockDatabase);
    }

    @Test
    public void CreateDatabase_PluginSystemCantCreateDatabase_ThrowsCantCreateDatabaseException() throws Exception{
        when(mockPluginDatabaseSystem.createDatabase(testOwnerId, deviceUserPublicKey)).thenThrow(new CantCreateDatabaseException("MOCK", null, null, null));

        testDatabaseFactory = new CryptoVaultDatabaseFactory();
        testDatabaseFactory.setPluginDatabaseSystem(mockPluginDatabaseSystem);
        catchException(testDatabaseFactory).createDatabase(testOwnerId, deviceUserPublicKey);

        assertThat(caughtException())
                .isNotNull()
                .isInstanceOf(CantCreateDatabaseException.class);
    }

    @Test
    public void CreateDatabase_CantCreateTables_ThrowsCantCreateDatabaseException() throws Exception{

        when(mockDatabaseFactory.newTableFactory(anyString())).thenReturn(null);
        testDatabaseFactory = new CryptoVaultDatabaseFactory();
        testDatabaseFactory.setPluginDatabaseSystem(mockPluginDatabaseSystem);
        catchException(testDatabaseFactory).createDatabase(testOwnerId, deviceUserPublicKey);

        assertThat(caughtException())
                .isNotNull()
                .isInstanceOf(CantCreateDatabaseException.class);
    }



    @Test
    public void CreateDatabase_ConflictedIdWhenCreatingTables_ThrowsCantCreateDatabaseException() throws Exception{
        when(mockDatabaseFactory.newTableFactory(anyString())).thenReturn(null);
        testDatabaseFactory = new CryptoVaultDatabaseFactory();
        testDatabaseFactory.setPluginDatabaseSystem(mockPluginDatabaseSystem);
        catchException(testDatabaseFactory).createDatabase(testOwnerId,deviceUserPublicKey);

        assertThat(caughtException())
                .isNotNull()
                .isInstanceOf(CantCreateDatabaseException.class);
    }



    @Test
    public void CreateDatabase_GeneralExceptionThrown_ThrowsCantCreateDatabaseException() throws Exception{
        when(mockDatabase.getDatabaseFactory()).thenReturn(null);

        testDatabaseFactory = new CryptoVaultDatabaseFactory();
        testDatabaseFactory.setPluginDatabaseSystem(mockPluginDatabaseSystem);

        catchException(testDatabaseFactory).createDatabase(testOwnerId, deviceUserPublicKey);

        assertThat(caughtException())
                .isNotNull()
                .isInstanceOf(CantCreateDatabaseException.class);
    }
}
