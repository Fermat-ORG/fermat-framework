package unit.com.bitdubai.fermat_cry_plugin.layer.crypto_module.crypto_address_book.developer.bitdubai.version_1.database.CryptoAddressBookCryptoModuleDao;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_cry_plugin.layer.crypto_module.crypto_address_book.developer.bitdubai.version_1.database.CryptoAddressBookCryptoModuleDao;
import com.bitdubai.fermat_cry_plugin.layer.crypto_module.crypto_address_book.developer.bitdubai.version_1.database.CryptoAddressBookCryptoModuleDatabaseConstants;
import com.bitdubai.fermat_cry_plugin.layer.crypto_module.crypto_address_book.developer.bitdubai.version_1.exceptions.CantInitializeCryptoAddressBookCryptoModuleDatabaseException;

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
public class InitializeTest  extends TestCase {


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

    private CryptoAddressBookCryptoModuleDao cryptoAddressBookCryptoModuleDao;

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
    public void initializeTest_InitOk_Trows_CantInitializeCryptoAddressBookCryptoModuleDatabaseException() throws Exception{
        cryptoAddressBookCryptoModuleDao = new CryptoAddressBookCryptoModuleDao(mockPluginDatabaseSystem,testOwnerId);

        cryptoAddressBookCryptoModuleDao.setPluginDatabaseSystem(mockPluginDatabaseSystem);
        cryptoAddressBookCryptoModuleDao.setPluginId(testOwnerId);

        catchException(cryptoAddressBookCryptoModuleDao).initialize();

        assertThat(caughtException())
                .isNull();
    }

    @Test
    public void initializeTest_InitOk_DatabaseNotFound_Trows_CantInitializeCryptoAddressBookCryptoModuleDatabaseException() throws Exception{
        when(mockPluginDatabaseSystem.openDatabase(testOwnerId, testOwnerId.toString())).thenThrow(new DatabaseNotFoundException("MOCK", null, null, null));


        cryptoAddressBookCryptoModuleDao = new CryptoAddressBookCryptoModuleDao(mockPluginDatabaseSystem,testOwnerId);
        cryptoAddressBookCryptoModuleDao.setPluginDatabaseSystem(mockPluginDatabaseSystem);
        cryptoAddressBookCryptoModuleDao.setPluginId(testOwnerId);

        catchException(cryptoAddressBookCryptoModuleDao).initialize();

        assertThat(caughtException())
                .isNull();
    }

    @Test
    public void initializeTest_InitError_Trows_CantInitializeCryptoAddressBookCryptoModuleDatabaseException() throws Exception{

        cryptoAddressBookCryptoModuleDao = new CryptoAddressBookCryptoModuleDao(null,testOwnerId);

        catchException(cryptoAddressBookCryptoModuleDao).initialize();

        assertThat(caughtException())
                .isNotNull().isInstanceOf(CantInitializeCryptoAddressBookCryptoModuleDatabaseException.class);
    }


}
