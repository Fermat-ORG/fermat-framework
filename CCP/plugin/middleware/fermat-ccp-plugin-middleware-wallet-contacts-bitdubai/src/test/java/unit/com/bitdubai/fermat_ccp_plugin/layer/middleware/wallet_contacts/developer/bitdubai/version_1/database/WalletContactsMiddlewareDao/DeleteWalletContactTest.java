/*
package unit.com.bitdubai.fermat_ccp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.database.WalletContactsMiddlewareDao;


import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.exceptions.CantDeleteWalletContactException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_ccp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.database.WalletContactsMiddlewareDao;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;


*/
/**
 * Created by natalia on 11/09/15.
 *//*


@RunWith(MockitoJUnitRunner.class)
public class DeleteWalletContactTest {
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


    private UUID testOwnerId1;



    @Before
    public void setUp() throws Exception {
        testOwnerId1 = UUID.randomUUID();


        mockTableRecordList = Arrays.asList(mockTableRecord, mockTableRecord);

        Mockito.when(mockPluginDatabaseSystem.openDatabase(any(UUID.class), anyString())).thenReturn(mockDatabase);
        Mockito.when(mockDatabase.getTable(anyString())).thenReturn(mockTable);
        Mockito.when(mockTable.getRecords()).thenReturn(mockTableRecordList);
        walletContactsMiddlewareDao = new WalletContactsMiddlewareDao(mockPluginDatabaseSystem, testOwnerId1);
        walletContactsMiddlewareDao.initialize();
    }

    @Test
    public void updateWalletContactTest_CreateOK_ThrowsCantDeleteWalletContactException() throws Exception {

        catchException(walletContactsMiddlewareDao).deleteWalletContact(UUID.randomUUID());

        assertThat(caughtException())
                .isNull();

    }


    @Test
    public void updateWalletContactTest_listError_ThrowsCantDeleteWalletContactException() throws Exception {

        catchException(walletContactsMiddlewareDao).deleteWalletContact(null);

        assertThat(caughtException())
                .isNotNull().isInstanceOf(CantDeleteWalletContactException.class);

    }


}



*/
