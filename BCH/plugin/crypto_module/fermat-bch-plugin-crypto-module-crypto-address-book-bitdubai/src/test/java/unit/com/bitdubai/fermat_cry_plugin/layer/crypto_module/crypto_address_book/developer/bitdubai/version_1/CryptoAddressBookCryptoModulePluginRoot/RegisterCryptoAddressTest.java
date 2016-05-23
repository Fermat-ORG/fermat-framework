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
import com.bitdubai.fermat_cry_api.layer.crypto_module.crypto_address_book.exceptions.CantRegisterCryptoAddressBookRecordException;
import com.bitdubai.fermat_cry_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookRecord;
import com.bitdubai.fermat_bch_plugin.layer.crypto_module.crypto_address_book.developer.bitdubai.version_1.CryptoAddressBookCryptoModulePluginRoot;
import com.bitdubai.fermat_bch_plugin.layer.crypto_module.crypto_address_book.developer.bitdubai.version_1.database.CryptoAddressBookCryptoModuleDatabaseConstants;
import com.bitdubai.fermat_bch_plugin.layer.crypto_module.crypto_address_book.developer.bitdubai.version_1.structure.CryptoAddressBookCryptoModuleRecord;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.List;
import java.util.UUID;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by natalia on 08/09/15.
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest({CryptoCurrency.class,Actors.class,Platforms.class,CryptoCurrency.class, CryptoCurrencyVault.class, ReferenceWallet.class})

public class RegisterCryptoAddressTest extends TestCase {

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

    @Mock
    private CryptoAddressBookRecord mockCryptoAddressBookRecord;

    private UUID pluginId;

    CryptoAddressBookCryptoModulePluginRoot cryptoAddressBookCryptoModulePluginRoot;


    private void setUpIds(){
        pluginId = UUID.randomUUID();

    }

    private void setUpMockitoGeneralRules() throws Exception{

        mockCryptoAddressBookRecord = new CryptoAddressBookCryptoModuleRecord(mockCryptoAddress,
                "actorkey",
                Actors.EXTRA_USER,
                "actorToKey",
                Actors.INTRA_USER,
                Platforms.CRYPTO_CURRENCY_PLATFORM,
                CryptoCurrencyVault.BITCOIN_VAULT,
                "",
                ReferenceWallet.BASIC_WALLET_BITCOIN_WALLET);


        Mockito.when(mockPluginDatabaseSystem.createDatabase(pluginId, pluginId.toString())).thenReturn(mockDatabase);

        Mockito.when(mockPluginDatabaseSystem.openDatabase(pluginId, pluginId.toString())).thenReturn(mockDatabase);

        Mockito.when(mockDatabase.getDatabaseFactory()).thenReturn(mockDatabaseFactory);
        Mockito.when(mockDatabaseFactory.newTableFactory(pluginId, CryptoAddressBookCryptoModuleDatabaseConstants.CRYPTO_ADDRESS_BOOK_TABLE_NAME)).thenReturn(mockTableFactory);

        Mockito.when(mockDatabase.getTable(CryptoAddressBookCryptoModuleDatabaseConstants.CRYPTO_ADDRESS_BOOK_TABLE_NAME)).thenReturn(mockTable);
        Mockito.when(mockTable.getEmptyRecord()).thenReturn(mockRecord);

       Mockito.when(mockCryptoAddressBookRecord.getCryptoAddress().getAddress()).thenReturn("address");
        Mockito.when(mockCryptoAddressBookRecord.getCryptoAddress().getCryptoCurrency()).thenReturn(CryptoCurrency.BITCOIN);
    }

    @Before
    public void setUp() throws Exception{


        setUpIds();
        setUpMockitoGeneralRules();

        cryptoAddressBookCryptoModulePluginRoot = new CryptoAddressBookCryptoModulePluginRoot();
        cryptoAddressBookCryptoModulePluginRoot = new CryptoAddressBookCryptoModulePluginRoot();

        cryptoAddressBookCryptoModulePluginRoot.setErrorManager(errorManager);
        cryptoAddressBookCryptoModulePluginRoot.setId(pluginId);

        cryptoAddressBookCryptoModulePluginRoot.setLogManager(logManager);

        cryptoAddressBookCryptoModulePluginRoot.setPluginDatabaseSystem(mockPluginDatabaseSystem);

        cryptoAddressBookCryptoModulePluginRoot.start();
    }



    @Test
    public void registerCryptoAddressTest_GetOk_Trows_CantCantRegisterCryptoAddressBookRecordException() throws Exception{


        catchException(cryptoAddressBookCryptoModulePluginRoot).registerCryptoAddress(mockCryptoAddress,
                "actorkey",
                Actors.EXTRA_USER,
                "actorToKey",
                Actors.INTRA_USER,
                Platforms.CRYPTO_CURRENCY_PLATFORM,
                CryptoCurrencyVault.BITCOIN_VAULT,
                "",
                ReferenceWallet.BASIC_WALLET_BITCOIN_WALLET);

        assertThat(caughtException())
                .isNull();
    }


    @Test
    public void registerCryptoAddressTest_GetErrorCryptoNull_Trows_CantRegisterCryptoAddressBookRecordException() throws Exception{

        catchException(cryptoAddressBookCryptoModulePluginRoot).registerCryptoAddress(null,
                "actorkey",
                Actors.EXTRA_USER,
                "actorToKey",
                Actors.INTRA_USER,
                Platforms.CRYPTO_CURRENCY_PLATFORM,
                CryptoCurrencyVault.BITCOIN_VAULT,
                "",
                ReferenceWallet.BASIC_WALLET_BITCOIN_WALLET);

        assertThat(caughtException())
                .isNotNull().isInstanceOf(CantRegisterCryptoAddressBookRecordException.class);
    }



}
