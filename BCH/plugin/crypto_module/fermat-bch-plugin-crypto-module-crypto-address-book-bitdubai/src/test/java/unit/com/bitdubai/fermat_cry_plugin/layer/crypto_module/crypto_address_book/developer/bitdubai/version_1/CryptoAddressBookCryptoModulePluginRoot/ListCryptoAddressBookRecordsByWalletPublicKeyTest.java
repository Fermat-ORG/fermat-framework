package unit.com.bitdubai.fermat_bch_plugin.layer.crypto_module.crypto_address_book.developer.bitdubai.version_1.CryptoAddressBookCryptoModulePluginRoot;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrencyVault;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_cry_api.layer.crypto_module.crypto_address_book.exceptions.CantListCryptoAddressBookRecordsException;
import com.bitdubai.fermat_cry_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookRecord;
import com.bitdubai.fermat_bch_plugin.layer.crypto_module.crypto_address_book.developer.bitdubai.version_1.CryptoAddressBookCryptoModulePluginRoot;
import com.bitdubai.fermat_bch_plugin.layer.crypto_module.crypto_address_book.developer.bitdubai.version_1.database.CryptoAddressBookCryptoModuleDatabaseConstants;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

import junit.framework.TestCase;

import org.fest.assertions.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.anyString;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

/**
 * Created by natalia on 08/09/15.
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest({CryptoCurrency.class,Actors.class,Platforms.class,CryptoCurrency.class, CryptoCurrencyVault.class, ReferenceWallet.class})

public class ListCryptoAddressBookRecordsByWalletPublicKeyTest extends TestCase {

    @Mock
    ErrorManager errorManager;

    @Mock
    LogManager logManager;
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

    private CryptoCurrency mockCryptoCurrency;

    private Actors mockActors;

    private Platforms mockPlatforms;

    private CryptoCurrencyVault mockCryptoCurrencyVault;

    private ReferenceWallet mockReferenceWallet;

    CryptoAddressBookCryptoModulePluginRoot cryptoAddressBookCryptoModulePluginRoot;

    private UUID pluginId;

    private void setUpIds(){
        pluginId = UUID.randomUUID();
        mockActors = Actors.INTRA_USER;
        mockPlatforms = Platforms.CRYPTO_BROKER_PLATFORM;
        mockCryptoCurrencyVault = CryptoCurrencyVault.BITCOIN_VAULT;

        mockReferenceWallet = ReferenceWallet.COMPOSITE_WALLET_MULTI_ACCOUNT;

    }

    private void setUpMockitoGeneralRules() throws Exception{

        cryptoAddressBookCryptoModulePluginRoot = new CryptoAddressBookCryptoModulePluginRoot();

        cryptoAddressBookCryptoModulePluginRoot.setErrorManager(errorManager);
        cryptoAddressBookCryptoModulePluginRoot.setId(pluginId);

        cryptoAddressBookCryptoModulePluginRoot.setLogManager(logManager);

        cryptoAddressBookCryptoModulePluginRoot.setPluginDatabaseSystem(mockPluginDatabaseSystem);

        mockCryptoCurrency = CryptoCurrency.BITCOIN;

        mockRecords = Arrays.asList(mockRecord, mockRecord, mockRecord);
        Mockito.when(mockPluginDatabaseSystem.createDatabase(pluginId, pluginId.toString())).thenReturn(mockDatabase);

        Mockito.when(mockPluginDatabaseSystem.openDatabase(pluginId, pluginId.toString())).thenReturn(mockDatabase);

        Mockito.when(mockDatabase.getDatabaseFactory()).thenReturn(mockDatabaseFactory);
        Mockito.when(mockDatabaseFactory.newTableFactory(pluginId, CryptoAddressBookCryptoModuleDatabaseConstants.CRYPTO_ADDRESS_BOOK_TABLE_NAME)).thenReturn(mockTableFactory);
        Mockito.when(mockDatabase.getTable(CryptoAddressBookCryptoModuleDatabaseConstants.CRYPTO_ADDRESS_BOOK_TABLE_NAME)).thenReturn(mockTable);
        Mockito.when(mockTable.getRecords()).thenReturn(mockRecords);
        Mockito.when(mockRecord.getStringValue(anyString())).thenReturn(UUID.randomUUID().toString());

        Mockito.when(mockCryptoAddress.getAddress()).thenReturn("address");
        Mockito.when(mockCryptoAddress.getCryptoCurrency()).thenReturn(mockCryptoCurrency);


        mockStatic(CryptoCurrency.class);

        PowerMockito.when(mockCryptoCurrency.getByCode(anyString())).thenReturn(CryptoCurrency.BITCOIN);
        mockStatic(Actors.class);
        PowerMockito.when(mockActors.getByCode(anyString())).thenReturn(Actors.INTRA_USER);
        mockStatic(Platforms.class);
        PowerMockito.when(mockPlatforms.getByCode(anyString())).thenReturn(Platforms.CRYPTO_CURRENCY_PLATFORM);
        mockStatic(CryptoCurrencyVault.class);
        PowerMockito.when(mockCryptoCurrencyVault.getByCode(anyString())).thenReturn(CryptoCurrencyVault.BITCOIN_VAULT);
        mockStatic(ReferenceWallet.class);
        PowerMockito.when(mockReferenceWallet.getByCode(anyString())).thenReturn(ReferenceWallet.BASIC_WALLET_BITCOIN_WALLET);

        cryptoAddressBookCryptoModulePluginRoot.start();
    }

    @Before
    public void setUp() throws Exception{


        setUpIds();
        setUpMockitoGeneralRules();

    }



    @Test
    public void listCryptoAddressBookRecordsByDeliveredByActorPublicKeyTest_GetOk_Trows_CantListCryptoAddressBookRecordsException() throws Exception{


        List<CryptoAddressBookRecord> cryptoAddressBookRecordList = cryptoAddressBookCryptoModulePluginRoot.listCryptoAddressBookRecordsByDeliveredByActorPublicKey(UUID.randomUUID().toString());

        Assertions.assertThat(cryptoAddressBookRecordList)
                .isNotNull();
    }


    @Test
    public void listCryptoAddressBookRecordsByDeliveredByActorPublicKeyTest_GetErrorWalletNull_Trows_CantListCryptoAddressBookRecordsException() throws Exception{

        catchException(cryptoAddressBookCryptoModulePluginRoot).listCryptoAddressBookRecordsByWalletPublicKey(null);

        assertThat(caughtException())
                .isNotNull().isInstanceOf(CantListCryptoAddressBookRecordsException.class);
    }



}
