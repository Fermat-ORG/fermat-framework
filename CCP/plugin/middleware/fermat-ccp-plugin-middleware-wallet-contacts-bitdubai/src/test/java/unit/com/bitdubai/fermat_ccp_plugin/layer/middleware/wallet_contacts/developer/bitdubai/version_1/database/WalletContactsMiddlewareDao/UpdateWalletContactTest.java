/*
package unit.com.bitdubai.fermat_ccp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.database.WalletContactsMiddlewareDao;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.exceptions.CantUpdateWalletContactException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.interfaces.WalletContactRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_ccp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.database.WalletContactsMiddlewareDao;
import com.bitdubai.fermat_ccp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.structure.WalletContactsMiddlewareRecord;

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
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

*/
/**
 * Created by natalia on 11/09/15.
 *//*


@RunWith(PowerMockRunner.class)
@PrepareForTest({CryptoCurrency.class,Actors.class})
public class UpdateWalletContactTest {
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
    private DatabaseTableRecord mockTableRecord;

    @Mock
    private List<DatabaseTableRecord> mockTableRecordList;

    @Mock
    private DatabaseTableFactory mockTableFactory;

    @Mock
    private CryptoAddress mockCryptoAddress;

    private CryptoCurrency mockCryptoCurrency;


    private UUID testOwnerId1;

    @Mock
    private List<CryptoAddress> cryptoAddressesList;

    private WalletContactRecord walletContactRecord;

    @Before
    public void setUp() throws Exception {
        testOwnerId1 = UUID.randomUUID();

        mockCryptoCurrency = CryptoCurrency.BITCOIN;
        cryptoAddressesList = Arrays.asList(mockCryptoAddress, mockCryptoAddress);
        mockTableRecordList = Arrays.asList(mockTableRecord, mockTableRecord);

        walletContactRecord = new WalletContactsMiddlewareRecord(UUID.randomUUID(),
                "actorAlias",
                "actorFirstName",
                "actorLastName",
                cryptoAddressesList);

        Mockito.when(mockPluginDatabaseSystem.openDatabase(any(UUID.class), anyString())).thenReturn(mockDatabase);
        Mockito.when(mockDatabase.getTable(anyString())).thenReturn(mockTable);
        Mockito.when(mockTable.getRecords()).thenReturn(mockTableRecordList);
        Mockito.when(mockTable.getEmptyRecord()).thenReturn(mockTableRecord);
        Mockito.when(mockCryptoAddress.getCryptoCurrency()).thenReturn(mockCryptoCurrency);


        mockStatic(CryptoCurrency.class);

        PowerMockito.when(mockCryptoCurrency.getByCode(anyString())).thenReturn(CryptoCurrency.BITCOIN);
        walletContactsMiddlewareDao = new WalletContactsMiddlewareDao(mockPluginDatabaseSystem, testOwnerId1);
        walletContactsMiddlewareDao.initialize();
    }

    @Test
    public void updateWalletContactTest_CreateOK_ThrowsCantCreateWalletContactException() throws Exception {

        catchException(walletContactsMiddlewareDao).updateWalletContact(walletContactRecord);

        assertThat(caughtException())
                .isNull();

    }


    @Test
    public void updateWalletContactTest_listError_ThrowsCantCreateWalletContactException() throws Exception {

        Mockito.when(mockTable.getEmptyRecord()).thenReturn(null);
        catchException(walletContactsMiddlewareDao).updateWalletContact(walletContactRecord);

        assertThat(caughtException())
                .isNotNull().isInstanceOf(CantUpdateWalletContactException.class);

    }


}


*/
