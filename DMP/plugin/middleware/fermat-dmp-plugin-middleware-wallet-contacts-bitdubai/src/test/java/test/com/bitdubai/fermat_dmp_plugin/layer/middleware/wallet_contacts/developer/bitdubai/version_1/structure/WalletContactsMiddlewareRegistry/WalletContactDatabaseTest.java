package test.com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.structure.WalletContactsMiddlewareRegistry;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTransaction;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.structure.WalletContactsMiddlewareDao;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.structure.WalletContactsMiddlewareRegistry;
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
import static org.mockito.Mockito.when;

/**
 * Created by Nerio on 04/08/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class WalletContactDatabaseTest {

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

    private UUID testPluginId;
    private CryptoAddress receivedCryptoAddress;
    private List<DatabaseTableRecord> databaseTableRecordList;
    private Actors actorType;

    WalletContactsMiddlewareDao walletContactsMiddlewareDao;
    WalletContactsMiddlewareRegistry walletContactsMiddlewareRegistry;

    @Before
    public void setUp() throws Exception {
        testPluginId = UUID.randomUUID();
        actorType = Actors.DEVICE_USER;
        databaseTableRecordList = new ArrayList<>();
        databaseTableRecordList.add(mockDatabaseTableRecord);
        walletContactsMiddlewareDao = new WalletContactsMiddlewareDao(mockPluginDatabaseSystem);
        walletContactsMiddlewareRegistry = new WalletContactsMiddlewareRegistry();
        walletContactsMiddlewareRegistry.setPluginDatabaseSystem(mockPluginDatabaseSystem);
        walletContactsMiddlewareRegistry.setPluginId(testPluginId);
        walletContactsMiddlewareRegistry.setErrorManager(mockErrorManager);
    }

    @Test
    public void createWalletContact_setValid_createContact() throws Exception {
        when(mockPluginDatabaseSystem.openDatabase(testPluginId, testPluginId.toString())).thenReturn(mockDatabase);
        when(mockDatabase.getTable(anyString())).thenReturn(mockDatabaseTable);
        when(mockDatabaseTable.getEmptyRecord()).thenReturn(mockDatabaseTableRecord);
        when(mockDatabase.newTransaction()).thenReturn(mockDatabaseTransaction);
        walletContactsMiddlewareRegistry.initialize();
        walletContactsMiddlewareRegistry.createWalletContact(testPluginId, testPluginId.toString(), actorType, receivedCryptoAddress, testPluginId);
    }

    @Test
    public void updateWalletContact_setValid_updateContact() throws Exception {
        when(mockPluginDatabaseSystem.openDatabase(testPluginId, testPluginId.toString())).thenReturn(mockDatabase);
        when(mockDatabase.getTable(anyString())).thenReturn(mockDatabaseTable);
        when(mockDatabaseTable.getRecords()).thenReturn(databaseTableRecordList);
        when(mockDatabase.newTransaction()).thenReturn(mockDatabaseTransaction);
        walletContactsMiddlewareRegistry.initialize();
        walletContactsMiddlewareRegistry.updateWalletContact(testPluginId, receivedCryptoAddress, testPluginId.toString());
    }

    @Test
    public void deleteWalletContact_setValid_deleteContact() throws Exception {
        when(mockPluginDatabaseSystem.openDatabase(testPluginId, testPluginId.toString())).thenReturn(mockDatabase);
        when(mockDatabase.getTable(anyString())).thenReturn(mockDatabaseTable);
        when(mockDatabaseTable.getRecords()).thenReturn(databaseTableRecordList);
        when(mockDatabase.newTransaction()).thenReturn(mockDatabaseTransaction);
        walletContactsMiddlewareRegistry.initialize();
        walletContactsMiddlewareRegistry.deleteWalletContact(testPluginId);
    }
}
