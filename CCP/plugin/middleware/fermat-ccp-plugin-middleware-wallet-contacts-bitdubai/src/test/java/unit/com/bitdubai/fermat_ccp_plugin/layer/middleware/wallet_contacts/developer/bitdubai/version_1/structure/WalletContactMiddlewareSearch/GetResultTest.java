/*
package unit.com.bitdubai.fermat_ccp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.structure.WalletContactMiddlewareSearch;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.exceptions.CantGetAllWalletContactsException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.interfaces.WalletContactRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_ccp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.database.WalletContactsMiddlewareDao;
import com.bitdubai.fermat_ccp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.structure.WalletContactsMiddlewareSearch;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.structure.search.SearchField;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.structure.search.SearchOrder;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;

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
public class GetResultTest {
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
    private ErrorManager mockErrorManager;


    @Mock
    private SearchField mockSearchField;

    @Mock
    private SearchOrder mockSearchOrder;

    private Actors mockActors;

    private CryptoCurrency mockCryptoCurrency;

    private UUID testOwnerId1;

    private WalletContactsMiddlewareSearch walletContactsMiddlewareSearch;

    @Before
    public void setUp() throws Exception {
        testOwnerId1 = UUID.randomUUID();
        mockActors = Actors.INTRA_USER;
        mockCryptoCurrency = CryptoCurrency.BITCOIN;

        mockTableRecordList = Arrays.asList(mockTableRecord, mockTableRecord);

        Mockito.when(mockPluginDatabaseSystem.openDatabase(any(UUID.class), anyString())).thenReturn(mockDatabase);
        Mockito.when(mockDatabase.getTable(anyString())).thenReturn(mockTable);
        Mockito.when(mockTable.getRecords()).thenReturn(mockTableRecordList);

        mockStatic(Actors.class);
        PowerMockito.when(mockActors.getByCode(anyString())).thenReturn(Actors.INTRA_USER);
        mockStatic(CryptoCurrency.class);

        PowerMockito.when(mockCryptoCurrency.getByCode(anyString())).thenReturn(CryptoCurrency.BITCOIN);

        walletContactsMiddlewareDao = new WalletContactsMiddlewareDao(mockPluginDatabaseSystem, testOwnerId1);
        walletContactsMiddlewareDao.initialize();

        walletContactsMiddlewareSearch = new WalletContactsMiddlewareSearch(mockErrorManager,walletContactsMiddlewareDao);
    }

    @Test
    public void getResultTest_GetOK_ThrowsCantGetAllWalletContactsException() throws Exception {

        List<WalletContactRecord> walletContactRecordList = walletContactsMiddlewareSearch.getResult("walletPublicKey");
        assertThat(walletContactRecordList).isInstanceOf(List.class);

    }

    @Test
    public void getResultTest_GetOKOffSet_ThrowsCantGetAllWalletContactsException() throws Exception {

        List<WalletContactRecord> walletContactRecordList = walletContactsMiddlewareSearch.getResult("walletPublicKey",10,0);
        assertThat(walletContactRecordList).isInstanceOf(List.class);

    }

    @Test
    public void getResultTest_GetError_ThrowsCantGetAllWalletContactsException() throws Exception {

        Mockito.when(mockTable.getRecords()).thenReturn(null);
        catchException(walletContactsMiddlewareSearch).getResult("walletPublicKey");

        assertThat(caughtException())
                .isNotNull().isInstanceOf(CantGetAllWalletContactsException.class);

    }


}
*/
