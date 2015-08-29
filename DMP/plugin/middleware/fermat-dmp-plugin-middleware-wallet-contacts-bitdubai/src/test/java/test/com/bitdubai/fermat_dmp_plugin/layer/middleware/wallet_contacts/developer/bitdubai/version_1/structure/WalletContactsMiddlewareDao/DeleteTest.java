package test.com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.structure.WalletContactsMiddlewareDao;

import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.exceptions.CantDeleteWalletContactException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.interfaces.WalletContactRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTransaction;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantDeleteRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.structure.WalletContactsMiddlewareDao;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

/**
 * Created by Leon Acosta (laion.cj91@gmail.com) on 21/08/2015.
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class DeleteTest {

    @Mock
    private PluginDatabaseSystem mockPluginDatabaseSystem;
    @Mock
    private ErrorManager mockErrorManager;
    @Mock
    private Database mockDatabase;
    @Mock
    private DatabaseTable mockDatabaseTable;
    @Mock
    private DatabaseTableRecord mockDatabaseTableRecord;
    @Mock
    private DatabaseTransaction mockDatabaseTransaction;
    @Mock
    private WalletContactRecord mockWalletContactRecord;

    private WalletContactsMiddlewareDao walletContactsMiddlewareDao;

    private UUID testContactId;
    private List<DatabaseTableRecord> databaseTableRecordList;

    @Before
    public void setUp() throws Exception{
        UUID testPluginId = UUID.randomUUID();
        testContactId = UUID.randomUUID();
        databaseTableRecordList = new ArrayList<>();
        databaseTableRecordList.add(mockDatabaseTableRecord);

        walletContactsMiddlewareDao = new WalletContactsMiddlewareDao(mockPluginDatabaseSystem);

        when(mockPluginDatabaseSystem.openDatabase(testPluginId, testPluginId.toString())).thenReturn(mockDatabase);
        when(mockDatabase.getTable(anyString())).thenReturn(mockDatabaseTable);
        when(mockDatabaseTable.getRecords()).thenReturn(databaseTableRecordList);
        when(mockDatabase.newTransaction()).thenReturn(mockDatabaseTransaction);

        walletContactsMiddlewareDao.initializeDatabase(testPluginId, testPluginId.toString());
    }

    @Test
    public void deleteWalletContact_setValid_deleteContact() throws Exception {

        walletContactsMiddlewareDao.delete(testContactId);
    }

    @Test (expected = CantDeleteWalletContactException.class)
    public void deleteWalletContact_ContactIdIsRequiredException() throws Exception {

        walletContactsMiddlewareDao.delete(null);
    }

    @Test (expected = CantDeleteWalletContactException.class)
    public void deleteWalletContact_ContactNotFoundException() throws Exception {
        databaseTableRecordList.clear();

        walletContactsMiddlewareDao.delete(testContactId);
    }

    @Test (expected = CantDeleteWalletContactException.class)
    public void deleteWalletContact_CantDeleteRecordException() throws Exception {

        doThrow(new CantDeleteRecordException()).when(mockDatabaseTable).deleteRecord(any(DatabaseTableRecord.class));

        walletContactsMiddlewareDao.delete(testContactId);
    }

    @Test (expected = CantDeleteWalletContactException.class)
    public void deleteWalletContact_CantLoadTableToMemoryException() throws Exception {

        doThrow(new CantLoadTableToMemoryException()).when(mockDatabaseTable).loadToMemory();

        walletContactsMiddlewareDao.delete(testContactId);
    }

    @Test (expected = CantDeleteWalletContactException.class)
    public void deleteWalletContact_CantOpenDatabaseException() throws Exception {

        doThrow(new CantOpenDatabaseException()).when(mockDatabase).openDatabase();

        walletContactsMiddlewareDao.delete(testContactId);
    }

    @Test (expected = CantDeleteWalletContactException.class)
    public void deleteWalletContact_DatabaseNotFoundException() throws Exception {

        doThrow(new DatabaseNotFoundException()).when(mockDatabase).openDatabase();

        walletContactsMiddlewareDao.delete(testContactId);
    }
}
