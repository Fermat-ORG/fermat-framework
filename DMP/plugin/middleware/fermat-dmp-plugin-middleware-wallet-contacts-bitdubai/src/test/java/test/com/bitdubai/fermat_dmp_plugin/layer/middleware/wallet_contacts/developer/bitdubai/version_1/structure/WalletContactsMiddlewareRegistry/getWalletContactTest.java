package test.com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.structure.WalletContactsMiddlewareRegistry;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
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
 * Created by root on 04/08/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class getWalletContactTest {

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

    private UUID testPluginId;

    private UUID testActorId;

    private String walletPublicKey;

    private String testSearchName;

    WalletContactsMiddlewareDao walletContactsMiddlewareDao;
    WalletContactsMiddlewareRegistry walletContactsMiddlewareRegistry;

    @Before
    public void setUp() throws Exception {
        testPluginId = UUID.randomUUID();
        testActorId = UUID.randomUUID();
        walletPublicKey = new ECCKeyPair().getPublicKey();
        testSearchName = "Hector";
        walletContactsMiddlewareDao = new WalletContactsMiddlewareDao(mockPluginDatabaseSystem);
        walletContactsMiddlewareRegistry = new WalletContactsMiddlewareRegistry();
        walletContactsMiddlewareRegistry.setPluginDatabaseSystem(mockPluginDatabaseSystem);
        walletContactsMiddlewareRegistry.setPluginId(testPluginId);
        walletContactsMiddlewareRegistry.setErrorManager(mockErrorManager);
    }

    @Test
    public void getWalletContactByNameAndWalletId_setContactNull_walletContactRecord() throws Exception {
        when(mockPluginDatabaseSystem.openDatabase(testPluginId, testPluginId.toString())).thenReturn(mockDatabase);
        when(mockDatabase.getTable(anyString())).thenReturn(mockDatabaseTable);
        walletContactsMiddlewareRegistry.initialize();

        walletContactsMiddlewareRegistry.getWalletContactByNameAndWalletPublicKey(testSearchName, walletPublicKey);
    }

    @Test
    public void getWalletContactByNameAndWalletId_findContactValid_walletContactRecord() throws Exception {
        when(mockPluginDatabaseSystem.openDatabase(testPluginId, testPluginId.toString())).thenReturn(mockDatabase);
        when(mockDatabase.getTable(anyString())).thenReturn(mockDatabaseTable);
        List<DatabaseTableRecord> databaseTableRecordList = new ArrayList<>();
        databaseTableRecordList.add(mockDatabaseTableRecord);
        when(mockDatabaseTable.getRecords()).thenReturn(databaseTableRecordList);
        when(mockDatabaseTableRecord.getStringValue(anyString())).thenReturn(Actors.DEVICE_USER.getCode());
        when(mockDatabaseTableRecord.getStringValue(anyString())).thenReturn(CryptoCurrency.BITCOIN.getCode());
        walletContactsMiddlewareRegistry.initialize();

        walletContactsMiddlewareRegistry.getWalletContactByNameAndWalletPublicKey(testSearchName, walletPublicKey);
    }

    @Test
    public void getWalletContactByNameContainsAndWalletId_findContactValid_walletContactRecord() throws Exception {
        when(mockPluginDatabaseSystem.openDatabase(testPluginId, testPluginId.toString())).thenReturn(mockDatabase);
        when(mockDatabase.getTable(anyString())).thenReturn(mockDatabaseTable);
        List<DatabaseTableRecord> databaseTableRecordList = new ArrayList<>();
        databaseTableRecordList.add(mockDatabaseTableRecord);
        when(mockDatabaseTable.getRecords()).thenReturn(databaseTableRecordList);
        when(mockDatabaseTableRecord.getStringValue(anyString())).thenReturn(Actors.DEVICE_USER.getCode());
        when(mockDatabaseTableRecord.getStringValue(anyString())).thenReturn(CryptoCurrency.BITCOIN.getCode());
        walletContactsMiddlewareRegistry.initialize();

        walletContactsMiddlewareRegistry.getWalletContactByNameAndWalletPublicKey(testSearchName, walletPublicKey);
    }

    @Test
    public void getWalletContactByActorId_findContactValid_walletContactRecord() throws Exception {
        when(mockPluginDatabaseSystem.openDatabase(testPluginId, testPluginId.toString())).thenReturn(mockDatabase);
        when(mockDatabase.getTable(anyString())).thenReturn(mockDatabaseTable);
        List<DatabaseTableRecord> databaseTableRecordList = new ArrayList<>();
        databaseTableRecordList.add(mockDatabaseTableRecord);
        when(mockDatabaseTable.getRecords()).thenReturn(databaseTableRecordList);
        when(mockDatabaseTableRecord.getStringValue(anyString())).thenReturn(Actors.DEVICE_USER.getCode());
        when(mockDatabaseTableRecord.getStringValue(anyString())).thenReturn(CryptoCurrency.BITCOIN.getCode());
        walletContactsMiddlewareRegistry.initialize();

        walletContactsMiddlewareRegistry.getWalletContactsByActorId(testActorId);
    }
}
