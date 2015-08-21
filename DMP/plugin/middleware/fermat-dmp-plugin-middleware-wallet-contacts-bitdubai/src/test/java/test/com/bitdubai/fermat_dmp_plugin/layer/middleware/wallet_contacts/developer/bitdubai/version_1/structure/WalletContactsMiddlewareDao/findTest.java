package test.com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.structure.WalletContactsMiddlewareDao;

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
 * Created by Nerio on 25/07/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class findTest {
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

    private String walletPublicKey;

    private WalletContactsMiddlewareDao walletContactsMiddlewareDao;
    WalletContactsMiddlewareRegistry walletContactsMiddlewareRegistry;


    @Before
    public void setUp() throws Exception{
        testPluginId = UUID.randomUUID();
        walletPublicKey = new ECCKeyPair().getPublicKey();
        walletContactsMiddlewareDao = new WalletContactsMiddlewareDao(mockPluginDatabaseSystem);
    }

    @Test
    public void findAll_setValid_WalletContactRecord() throws Exception{
        when(mockPluginDatabaseSystem.openDatabase(testPluginId, testPluginId.toString())).thenReturn(mockDatabase);
        walletContactsMiddlewareDao.initializeDatabase(testPluginId, testPluginId.toString());
        when(mockDatabase.getTable(anyString())).thenReturn(mockDatabaseTable);
        List<DatabaseTableRecord> databaseTableRecordList = new ArrayList<>();
        databaseTableRecordList.add(mockDatabaseTableRecord);
        when(mockDatabaseTable.getRecords()).thenReturn(databaseTableRecordList);
        when(mockDatabaseTableRecord.getStringValue(anyString())).thenReturn(Actors.DEVICE_USER.getCode());
        when(mockDatabaseTableRecord.getStringValue(anyString())).thenReturn(CryptoCurrency.BITCOIN.getCode());
        walletContactsMiddlewareDao.findAll(walletPublicKey);
    }

    @Test
    public void findAllScrolling_setValid_WalletContactRecord() throws Exception{
        when(mockPluginDatabaseSystem.openDatabase(testPluginId, testPluginId.toString())).thenReturn(mockDatabase);
        walletContactsMiddlewareDao.initializeDatabase(testPluginId, testPluginId.toString());
        when(mockDatabase.getTable(anyString())).thenReturn(mockDatabaseTable);
        List<DatabaseTableRecord> databaseTableRecordList = new ArrayList<>();
        databaseTableRecordList.add(mockDatabaseTableRecord);
        when(mockDatabaseTable.getRecords()).thenReturn(databaseTableRecordList);
        when(mockDatabaseTableRecord.getStringValue(anyString())).thenReturn(Actors.DEVICE_USER.getCode());
        when(mockDatabaseTableRecord.getStringValue(anyString())).thenReturn(CryptoCurrency.BITCOIN.getCode());

        walletContactsMiddlewareDao.findAllScrolling(walletPublicKey, 1, 0);
    }

    @Test
    public void findByActorId_setValid_WalletContactRecord() throws Exception{
        when(mockPluginDatabaseSystem.openDatabase(testPluginId, testPluginId.toString())).thenReturn(mockDatabase);
        walletContactsMiddlewareDao.initializeDatabase(testPluginId, testPluginId.toString());
        when(mockDatabase.getTable(anyString())).thenReturn(mockDatabaseTable);
        List<DatabaseTableRecord> databaseTableRecordList = new ArrayList<>();
        databaseTableRecordList.add(mockDatabaseTableRecord);
        when(mockDatabaseTable.getRecords()).thenReturn(databaseTableRecordList);
        when(mockDatabaseTableRecord.getStringValue(anyString())).thenReturn(Actors.DEVICE_USER.getCode());
        when(mockDatabaseTableRecord.getStringValue(anyString())).thenReturn(CryptoCurrency.BITCOIN.getCode());

        walletContactsMiddlewareDao.findByActorId(testPluginId);
    }

    @Test
    public void findByNameContainsAndWalletId_setValid_WalletContactRecord() throws Exception{
        when(mockPluginDatabaseSystem.openDatabase(testPluginId, testPluginId.toString())).thenReturn(mockDatabase);
        walletContactsMiddlewareDao.initializeDatabase(testPluginId, testPluginId.toString());
        when(mockDatabase.getTable(anyString())).thenReturn(mockDatabaseTable);
        List<DatabaseTableRecord> databaseTableRecordList = new ArrayList<>();
        databaseTableRecordList.add(mockDatabaseTableRecord);
        when(mockDatabaseTable.getRecords()).thenReturn(databaseTableRecordList);
        when(mockDatabaseTableRecord.getStringValue(anyString())).thenReturn(Actors.DEVICE_USER.getCode());
        when(mockDatabaseTableRecord.getStringValue(anyString())).thenReturn(CryptoCurrency.BITCOIN.getCode());

        walletContactsMiddlewareDao.findByNameContainsAndWalletPublicKey(testPluginId.toString(), walletPublicKey);
    }

    @Test
    public void findByNameAndWalletId_setValid_WalletContactRecord() throws Exception{
        when(mockPluginDatabaseSystem.openDatabase(testPluginId, testPluginId.toString())).thenReturn(mockDatabase);
        walletContactsMiddlewareDao.initializeDatabase(testPluginId, testPluginId.toString());
        when(mockDatabase.getTable(anyString())).thenReturn(mockDatabaseTable);
        List<DatabaseTableRecord> databaseTableRecordList = new ArrayList<>();
        databaseTableRecordList.add(mockDatabaseTableRecord);
        when(mockDatabaseTable.getRecords()).thenReturn(databaseTableRecordList);
        when(mockDatabaseTableRecord.getStringValue(anyString())).thenReturn(Actors.DEVICE_USER.getCode());
        when(mockDatabaseTableRecord.getStringValue(anyString())).thenReturn(CryptoCurrency.BITCOIN.getCode());

        walletContactsMiddlewareDao.findByNameAndWalletPublicKey(testPluginId.toString(), walletPublicKey);
    }
}
