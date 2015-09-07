package unit.com.bitdubai.fermat_cry_plugin.layer.crypto_module.crypto_address_book.developer.bitdubai.version_1.database.CryptoAddressBookCryptoModuleDao;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Vaults;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_cry_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookRecord;
import com.bitdubai.fermat_cry_plugin.layer.crypto_module.crypto_address_book.developer.bitdubai.version_1.database.CryptoAddressBookCryptoModuleDao;
import com.bitdubai.fermat_cry_plugin.layer.crypto_module.crypto_address_book.developer.bitdubai.version_1.database.CryptoAddressBookCryptoModuleDatabaseConstants;
import com.bitdubai.fermat_cry_plugin.layer.crypto_module.crypto_address_book.developer.bitdubai.version_1.exceptions.CantInitializeCryptoAddressBookCryptoModuleDatabaseException;

import junit.framework.TestCase;

import org.fest.assertions.api.Assertions;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
import java.util.UUID;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Created by natalia on 07/09/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class GetCryptoAddressBookRecordByCryptoAddressTest extends TestCase {


    @Mock
    private PluginDatabaseSystem mockPluginDatabaseSystem;
    @Mock
    private Database mockDatabase;
    @Mock
    private DatabaseFactory mockDatabaseFactory;
    @Mock
    private DatabaseTable mockTable;

    @Mock
    private CryptoAddress mockCryptoAddress;
    @Mock
    private DatabaseTableFactory mockTableFactory;
    @Mock
    private List<DatabaseTableRecord> mockRecords;
    @Mock
    private DatabaseTableRecord mockRecord;

    @Mock
    private CryptoCurrency mockCryptoCurrency;

    private UUID testOwnerId;

    private CryptoAddressBookCryptoModuleDao cryptoAddressBookCryptoModuleDao;


    private void setUpIds(){
        testOwnerId = UUID.randomUUID();


    }

    private void setUpMockitoGeneralRules() throws Exception{
        when(mockPluginDatabaseSystem.createDatabase(testOwnerId, testOwnerId.toString())).thenReturn(mockDatabase);

        when(mockPluginDatabaseSystem.openDatabase(testOwnerId, testOwnerId.toString())).thenReturn(mockDatabase);

        when(mockDatabase.getDatabaseFactory()).thenReturn(mockDatabaseFactory);
        when(mockDatabaseFactory.newTableFactory(testOwnerId, CryptoAddressBookCryptoModuleDatabaseConstants.CRYPTO_ADDRESS_BOOK_TABLE_NAME)).thenReturn(mockTableFactory);
        when(mockDatabase.getTable(CryptoAddressBookCryptoModuleDatabaseConstants.CRYPTO_ADDRESS_BOOK_TABLE_NAME)).thenReturn(mockTable);
        when(mockTable.getRecords()).thenReturn(mockRecords);
        Mockito.doReturn(mockRecord).when(mockRecords).get(0);

        when(mockCryptoAddress.getAddress()).thenReturn("address");
        when(mockCryptoAddress.getCryptoCurrency()).thenReturn(mockCryptoCurrency);
        when(mockCryptoCurrency.getCode()).thenReturn("BTC");

    }

    @Before
    public void setUp() throws Exception{

        mockCryptoCurrency = CryptoCurrency.BITCOIN;
        setUpIds();
        setUpMockitoGeneralRules();
        cryptoAddressBookCryptoModuleDao = new CryptoAddressBookCryptoModuleDao(mockPluginDatabaseSystem,testOwnerId);
        cryptoAddressBookCryptoModuleDao.initialize();
    }

    @Ignore
    @Test
    public void getCryptoAddressBookRecordByCryptoAddressTest_GetOk_Trows_CantGetCryptoAddressBookRecordException() throws Exception{

        CryptoAddressBookRecord cryptoAddressBookRecord= cryptoAddressBookCryptoModuleDao.getCryptoAddressBookRecordByCryptoAddress(mockCryptoAddress);

        Assertions.assertThat(cryptoAddressBookRecord)
                .isNotNull();
    }



}
