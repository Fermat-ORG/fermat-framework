package unit.com.bitdubai.fermat_cry_plugin.layer.crypto_module.crypto_address_book.developer.bitdubai.version_1.database.CryptoAddressBookCryptoModuleDatabaseFactory;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_cry_plugin.layer.crypto_module.crypto_address_book.developer.bitdubai.version_1.database.CryptoAddressBookCryptoModuleDatabaseConstants;
import com.bitdubai.fermat_cry_plugin.layer.crypto_module.crypto_address_book.developer.bitdubai.version_1.database.CryptoAddressBookCryptoModuleDatabaseFactory;

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
import static org.mockito.Mockito.when;

/**
 * Created by natalia on 07/09/15.
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
    private CryptoAddressBookCryptoModuleDatabaseFactory testDatabaseFactory;

    private void setUpIds(){
        testOwnerId = UUID.randomUUID();


    }

    private void setUpMockitoGeneralRules() throws Exception{
        when(mockPluginDatabaseSystem.createDatabase(testOwnerId, testOwnerId.toString())).thenReturn(mockDatabase);
        when(mockDatabase.getDatabaseFactory()).thenReturn(mockDatabaseFactory);
         when(mockDatabaseFactory.newTableFactory(testOwnerId, CryptoAddressBookCryptoModuleDatabaseConstants.CRYPTO_ADDRESS_BOOK_TABLE_NAME)).thenReturn(mockTableFactory);
    }

    @Before
    public void setUp() throws Exception{
        setUpIds();
        setUpMockitoGeneralRules();
    }

    @Test
    public void createDatabase_DatabaseAndTablesProperlyCreated_ReturnsDatabase() throws Exception{
        testDatabaseFactory = new CryptoAddressBookCryptoModuleDatabaseFactory(mockPluginDatabaseSystem);
        testDatabaseFactory.setPluginDatabaseSystem(mockPluginDatabaseSystem);
        Database checkDatabase = testDatabaseFactory.createDatabase(testOwnerId, testOwnerId.toString());

        assertThat(checkDatabase).isEqualTo(mockDatabase);
    }

    @Test
    public void createDatabase_PluginSystemCantCreateDatabase_ThrowsCantCreateDatabaseException() throws Exception{

        when(mockPluginDatabaseSystem.createDatabase(testOwnerId, testOwnerId.toString())).thenThrow(new CantCreateDatabaseException("MOCK", null, null, null));

        testDatabaseFactory = new CryptoAddressBookCryptoModuleDatabaseFactory(mockPluginDatabaseSystem);
        testDatabaseFactory.setPluginDatabaseSystem(mockPluginDatabaseSystem);
        catchException(testDatabaseFactory).createDatabase(testOwnerId, testOwnerId.toString());

        assertThat(caughtException())
                .isNotNull()
                .isInstanceOf(CantCreateDatabaseException.class);
    }

    @Test
    public void createDatabase_CantCreateTables_ThrowsCantCreateDatabaseException() throws Exception{

        when(mockDatabaseFactory.newTableFactory(testOwnerId, CryptoAddressBookCryptoModuleDatabaseConstants.CRYPTO_ADDRESS_BOOK_TABLE_NAME)).thenReturn(null);
        testDatabaseFactory = new CryptoAddressBookCryptoModuleDatabaseFactory(mockPluginDatabaseSystem);
        testDatabaseFactory.setPluginDatabaseSystem(mockPluginDatabaseSystem);
        catchException(testDatabaseFactory).createDatabase(testOwnerId, testOwnerId.toString());

        assertThat(caughtException())
                .isNotNull()
                .isInstanceOf(CantCreateDatabaseException.class);
    }



    @Test
    public void createDatabase_ConflictedIdWhenCreatingTables_ThrowsCantCreateDatabaseException() throws Exception{
        when(mockDatabaseFactory.newTableFactory(testOwnerId, CryptoAddressBookCryptoModuleDatabaseConstants.CRYPTO_ADDRESS_BOOK_TABLE_NAME)).thenReturn(null);
        testDatabaseFactory = new CryptoAddressBookCryptoModuleDatabaseFactory(mockPluginDatabaseSystem);
        testDatabaseFactory.setPluginDatabaseSystem(mockPluginDatabaseSystem);
        catchException(testDatabaseFactory).createDatabase(testOwnerId,testOwnerId.toString());

        assertThat(caughtException())
                .isNotNull()
                .isInstanceOf(CantCreateDatabaseException.class);
    }



    @Test
    public void createDatabase_GeneralExceptionThrown_ThrowsCantCreateDatabaseException() throws Exception{
        when(mockDatabase.getDatabaseFactory()).thenReturn(null);

        testDatabaseFactory = new CryptoAddressBookCryptoModuleDatabaseFactory(mockPluginDatabaseSystem);
        testDatabaseFactory.setPluginDatabaseSystem(mockPluginDatabaseSystem);

        catchException(testDatabaseFactory).createDatabase(testOwnerId, testOwnerId.toString());

        assertThat(caughtException())
                .isNotNull()
                .isInstanceOf(CantCreateDatabaseException.class);
    }
}
