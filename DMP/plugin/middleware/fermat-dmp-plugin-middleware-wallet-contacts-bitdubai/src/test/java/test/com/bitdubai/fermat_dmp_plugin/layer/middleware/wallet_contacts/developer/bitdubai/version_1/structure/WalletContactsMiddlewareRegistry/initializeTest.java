package test.com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.structure.WalletContactsMiddlewareRegistry;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTransaction;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.structure.WalletContactsMiddlewareDao;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.structure.WalletContactsMiddlewareDatabaseConstants;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.structure.WalletContactsMiddlewareRegistry;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;

import org.junit.Before;
import org.junit.Ignore;
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
 * Created by Nerio on 26/07/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class initializeTest {
    @Mock
    PluginDatabaseSystem mockPluginDatabaseSystem;
    @Mock
    ErrorManager mockErrorManager;
    @Mock
    Database mockDatabase;
    @Mock
    private DatabaseTable mockDatabaseTable;
    @Mock
    DatabaseTableRecord mockDatabaseTableRecord;
    @Mock
    DatabaseTransaction mockDatabaseTransaction;

    UUID testPluginId;

   // @Mock
    Actors actors;

    CryptoAddress receivedCryptoAddress;

    WalletContactsMiddlewareDao walletContactsMiddlewareDao;
    WalletContactsMiddlewareRegistry walletContactsMiddlewareRegistry;

    @Before
    public void setUp() throws Exception {
        testPluginId = UUID.randomUUID();
        receivedCryptoAddress = new CryptoAddress("asdasdasd", CryptoCurrency.BITCOIN);
        walletContactsMiddlewareDao = new WalletContactsMiddlewareDao(mockPluginDatabaseSystem);
        walletContactsMiddlewareRegistry = new WalletContactsMiddlewareRegistry();
        walletContactsMiddlewareRegistry.setPluginDatabaseSystem(mockPluginDatabaseSystem);
        walletContactsMiddlewareRegistry.setPluginId(testPluginId);
        walletContactsMiddlewareRegistry.setErrorManager(mockErrorManager);
    }

    @Test
    public void testInitialize_NotNull() throws Exception {
        when(mockPluginDatabaseSystem.openDatabase(testPluginId, testPluginId.toString())).thenReturn(mockDatabase);
        walletContactsMiddlewareRegistry.initialize();
    }

    @Test
    public void listWalletContacts_NotNull() throws Exception {
        when(mockPluginDatabaseSystem.openDatabase(testPluginId, testPluginId.toString())).thenReturn(mockDatabase);
        when(mockDatabase.getTable(anyString())).thenReturn(mockDatabaseTable);
        walletContactsMiddlewareRegistry.initialize();
        walletContactsMiddlewareRegistry.listWalletContacts(testPluginId);
    }

    @Test
    public void listWalletContactsScrolling_NotNull() throws Exception {
        when(mockPluginDatabaseSystem.openDatabase(testPluginId, testPluginId.toString())).thenReturn(mockDatabase);
        when(mockDatabase.getTable(anyString())).thenReturn(mockDatabaseTable);
        walletContactsMiddlewareRegistry.initialize();
        walletContactsMiddlewareRegistry.listWalletContactsScrolling(testPluginId, 1, 0);
    }

    @Test
    public void createWalletContact_setValid_createContact() throws Exception {
        when(mockPluginDatabaseSystem.openDatabase(testPluginId, testPluginId.toString())).thenReturn(mockDatabase);
        when(mockDatabase.getTable(anyString())).thenReturn(mockDatabaseTable);
        when(mockDatabaseTable.getEmptyRecord()).thenReturn(mockDatabaseTableRecord);
        when(mockDatabase.newTransaction()).thenReturn(mockDatabaseTransaction);
        Actors actorType = Actors.DEVICE_USER;
        walletContactsMiddlewareRegistry.initialize();
        walletContactsMiddlewareRegistry.createWalletContact(testPluginId, testPluginId.toString(), actorType, receivedCryptoAddress, testPluginId);
    }

    @Test
    public void updateWalletContact_setValid_updateContact() throws Exception {
        when(mockPluginDatabaseSystem.openDatabase(testPluginId, testPluginId.toString())).thenReturn(mockDatabase);
        when(mockDatabase.getTable(anyString())).thenReturn(mockDatabaseTable);
        List<DatabaseTableRecord> databaseTableRecordList = new ArrayList<>();
        databaseTableRecordList.add(mockDatabaseTableRecord);
        when(mockDatabaseTable.getRecords()).thenReturn(databaseTableRecordList);
        when(mockDatabase.newTransaction()).thenReturn(mockDatabaseTransaction);
        walletContactsMiddlewareRegistry.initialize();
        walletContactsMiddlewareRegistry.updateWalletContact(testPluginId, receivedCryptoAddress, testPluginId.toString());
    }

    @Test
    public void deleteWalletContact_setValid_deleteContact() throws Exception {
        when(mockPluginDatabaseSystem.openDatabase(testPluginId, testPluginId.toString())).thenReturn(mockDatabase);
        when(mockDatabase.getTable(anyString())).thenReturn(mockDatabaseTable);
        List<DatabaseTableRecord> databaseTableRecordList = new ArrayList<>();
        databaseTableRecordList.add(mockDatabaseTableRecord);
        when(mockDatabaseTable.getRecords()).thenReturn(databaseTableRecordList);
        when(mockDatabase.newTransaction()).thenReturn(mockDatabaseTransaction);
        walletContactsMiddlewareRegistry.initialize();
        walletContactsMiddlewareRegistry.deleteWalletContact(testPluginId);
    }

    @Test
         public void getWalletContactByNameAndWalletId_setContactNull_walletContactRecord() throws Exception {
        when(mockPluginDatabaseSystem.openDatabase(testPluginId, testPluginId.toString())).thenReturn(mockDatabase);
        when(mockDatabase.getTable(anyString())).thenReturn(mockDatabaseTable);
        //List<DatabaseTableRecord> databaseTableRecordList = new ArrayList<>();
        //databaseTableRecordList.add(mockDatabaseTableRecord);
        //when(mockDatabaseTable.getRecords()).thenReturn(databaseTableRecordList);
        //when(mockDatabase.newTransaction()).thenReturn(mockDatabaseTransaction);
        walletContactsMiddlewareRegistry.initialize();
        walletContactsMiddlewareRegistry.getWalletContactByNameAndWalletId(testPluginId.toString(), testPluginId);
    }

    @Ignore
    @Test
    public void getWalletContactByNameAndWalletId_setContactValid_walletContactRecord() throws Exception {
        when(mockPluginDatabaseSystem.openDatabase(testPluginId, testPluginId.toString())).thenReturn(mockDatabase);
        when(mockDatabase.getTable(anyString())).thenReturn(mockDatabaseTable);
        List<DatabaseTableRecord> databaseTableRecordList = new ArrayList<>();
        databaseTableRecordList.add(mockDatabaseTableRecord);
        when(mockDatabaseTable.getRecords()).thenReturn(databaseTableRecordList);
        //Actors actorType = Actors.DEVICE_USER;
        Actors actors = Actors.getByCode("DUS");
        //when(Actors.getByCode(mockDatabaseTableRecord.getStringValue(WalletContactsMiddlewareDatabaseConstants.CRYPTO_WALLET_CONTACTS_ADDRESS_BOOK_TABLE_ACTOR_TYPE_COLUMN_NAME))).thenReturn(actorType);
        //when(Actors.getByCode(mockDatabaseTableRecord.getStringValue(anyString()))).thenReturn(actorType);
        //when(Actors.getByCode(actorType.getCode())).thenReturn(actors);

   // when(mockDatabase.newTransaction()).thenReturn(mockDatabaseTransaction);
        walletContactsMiddlewareRegistry.initialize();
        walletContactsMiddlewareRegistry.getWalletContactByNameAndWalletId(testPluginId.toString(),testPluginId);
    }
}
