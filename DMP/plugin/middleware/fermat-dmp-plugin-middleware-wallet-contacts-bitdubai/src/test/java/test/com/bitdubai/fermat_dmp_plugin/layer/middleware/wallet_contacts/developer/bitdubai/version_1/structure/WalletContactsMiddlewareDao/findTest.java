package test.com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.structure.WalletContactsMiddlewareDao;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTransaction;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.structure.WalletContactsMiddlewareDao;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.structure.WalletContactsMiddlewareDatabaseConstants;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import test.com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.structure.mocks.MockDatabaseTable;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

/**
 * Created by Nerio on 25/07/15.
 */
//@RunWith(MockitoJUnitRunner.class)
//public class findTest {
//    @Mock
//    private Database mockDatabase;
//    @Mock
//    private DatabaseTable mockWalletContacts;
////    @Mock
////    private DatabaseTable mockBalanceTable;
//    @Mock
//    private DatabaseTransaction mockTransaction;
//    @Mock
//    PluginDatabaseSystem mockPluginDatabaseSystem;
//
//    private List<DatabaseTableRecord> mockRecords;
//
//    private DatabaseTableRecord mockBalanceRecord;
//
//    private WalletContactsMiddlewareDao walletContactsMiddlewareDao;
//
//    @Before
//    public void setUpWalletDao(){
//        walletContactsMiddlewareDao = new WalletContactsMiddlewareDao(mockPluginDatabaseSystem);
//    }
//
//    @Before
//    public void setUpMocks(){
//        //mockTransactionRecord = new MockBitcoinWalletTransactionRecord();
//        mockBalanceRecord = new MockDatabaseTable();
//        //mockWalletRecord = new MockDatabaseTableRecord();
//        mockRecords = new ArrayList<>();
//        mockRecords.add(mockBalanceRecord);
//        setUpMockitoRules();
//    }
//
//    public void setUpMockitoRules(){
//        when(mockDatabase.getTable(WalletContactsMiddlewareDatabaseConstants.CRYPTO_WALLET_CONTACTS_ADDRESS_BOOK_TABLE_NAME)).thenReturn(mockWalletContacts);
//        //when(mockDatabase.getTable(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_BALANCE_TABLE_NAME)).thenReturn(mockBalanceTable);
////        when(mockBalanceTable.getRecords()).thenReturn(mockRecords);
////        when(mockWalletTable.getEmptyRecord()).thenReturn(mockWalletRecord);
////        when(mockDatabase.newTransaction()).thenReturn(mockTransaction);
//    }
//}
