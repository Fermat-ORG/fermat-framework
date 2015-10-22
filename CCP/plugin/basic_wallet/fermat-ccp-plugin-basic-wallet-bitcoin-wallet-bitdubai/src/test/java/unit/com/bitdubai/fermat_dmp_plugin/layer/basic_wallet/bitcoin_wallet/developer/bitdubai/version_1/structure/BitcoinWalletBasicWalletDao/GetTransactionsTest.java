//package unit.com.bitdubai.fermat_dmp_plugin.layer.basic_wallet.bitcoin_wallet.developer.bitdubai.version_1.structure.BitcoinWalletBasicWalletDao;
//
//import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
//import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
//<<<<<<< HEAD:CCP/plugin/basic_wallet/fermat-ccp-plugin-basic-wallet-bitcoin-wallet-bitdubai/src/test/java/unit/com/bitdubai/fermat_dmp_plugin/layer/basic_wallet/bitcoin_wallet/developer/bitdubai/version_1/structure/BitcoinWalletBasicWalletDao/GetTransactionsTest.java
//import com.bitdubai.fermat_api.layer.dmp_basic_wallet.common.enums.BalanceType;
//import com.bitdubai.fermat_api.layer.dmp_basic_wallet.common.enums.TransactionType;
//import com.bitdubai.fermat_api.layer.dmp_basic_wallet.common.exceptions.CantListTransactionsException;
//=======
//import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.BalanceType;
//import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.TransactionType;
//import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantListTransactionsException;
//>>>>>>> 193a4ce563d3916b505332563ad81fe262f074f2:CCP/plugin/basic_wallet/fermat-ccp-plugin-basic-wallet-bitcoin-wallet-bitdubai/src/test/java/unit/com/bitdubai/fermat_dmp_plugin/layer/basic_wallet/bitcoin_wallet/developer/bitdubai/version_1/structure/BitcoinWalletBasicWalletDao/GetTransactionsTest.java
//import com.bitdubai.fermat_ccp_api.layer.basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletTransaction;
//import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
//import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
//import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
//import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
//import com.bitdubai.fermat_ccp_plugin.layer.basic_wallet.bitcoin_wallet.developer.bitdubai.version_1.structure.BitcoinWalletBasicWalletDao;
//import com.bitdubai.fermat_ccp_plugin.layer.basic_wallet.bitcoin_wallet.developer.bitdubai.version_1.structure.BitcoinWalletDatabaseConstants;
//import com.bitdubai.fermat_ccp_plugin.layer.basic_wallet.bitcoin_wallet.developer.bitdubai.version_1.util.BitcoinWalletTransactionWrapper;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.mockito.runners.MockitoJUnitRunner;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.UUID;
//
//import unit.com.bitdubai.fermat_dmp_plugin.layer.basic_wallet.bitcoin_wallet.developer.bitdubai.version_1.structure.mocks.MockDatabaseTableRecord;
//
//import static org.fest.assertions.api.Assertions.*;
//import static org.mockito.Mockito.*;
//import static com.googlecode.catchexception.CatchException.*;
///**
// * Created by jorgegonzalez on 2015.07.14..
// */
//@RunWith(MockitoJUnitRunner.class)
//public class GetTransactionsTest {
//
//    @Mock
//    private Database mockDatabase;
//    @Mock
//    private DatabaseTable mockTable;
//
//    private List<DatabaseTableRecord> mockRecords;
//
//    private DatabaseTableRecord mockRecord;
//
//    private BitcoinWalletTransaction mockTransaction;
//
//    private BitcoinWalletBasicWalletDao testWalletDao;
//
//    @Before
//    public void setUpWalletDao(){
//        testWalletDao = new BitcoinWalletBasicWalletDao(mockDatabase);
//    }
//
//    @Before
//    public void setUpMockElements(){
//        setUpMocks();
//        setUpMockitoRules();
//    }
//
//    public void setUpMocks(){
//        mockRecord = new MockDatabaseTableRecord();
//        mockRecords = new ArrayList<>();
//        mockRecords.add(mockRecord);
//        mockTransaction = constructBitcoinWalletTransactionFromRecord(mockRecord);
//    }
//
//    public void setUpMockitoRules(){
//        when(mockDatabase.getTable(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_NAME)).thenReturn(mockTable);
//        when(mockTable.getRecords()).thenReturn(mockRecords);
//    }
//
//    @Test
//    public void GetTransactions_SuccessfullyInvoked_ReturnsList() throws Exception{
//        BitcoinWalletTransaction checkTransaction = testWalletDao.getTransactions(1,0).get(0);
//        assertThat(checkTransaction.getAddressFrom().getAddress()).isEqualTo(mockTransaction.getAddressFrom().getAddress());
//        assertThat(checkTransaction.getAddressTo().getAddress()).isEqualTo(mockTransaction.getAddressTo().getAddress());
//        assertThat(checkTransaction.getBalanceType()).isEqualTo(mockTransaction.getBalanceType());
//    }
//
//    @Test
//    public void GetTransactions_LoadTableCantLoadTableToMemoryException_ThrowsCantGetTransactionsException() throws Exception{
//        doThrow(new CantLoadTableToMemoryException("MOCK", null, null, null)).when(mockTable).loadToMemory();
//        catchException(testWalletDao).getTransactions(1, 0);
//        assertThat(caughtException())
//                .isNotNull()
//                .isInstanceOf(CantListTransactionsException.class);
//    }
//
//    @Test
//    public void GetTransactions_GeneralException_ThrowsCantGetTransactionsException() throws Exception{
//        when(mockDatabase.getTable(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_NAME)).thenReturn(null);
//        catchException(testWalletDao).getTransactions(1, 0);
//        assertThat(caughtException())
//                .isNotNull()
//                .isInstanceOf(CantListTransactionsException.class);
//    }
//
//    private BitcoinWalletTransaction constructBitcoinWalletTransactionFromRecord(final DatabaseTableRecord record){
//        UUID transactionId = record.getUUIDValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_ID_COLUMN_NAME);
//        String transactionHash= record.getStringValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_ID_COLUMN_NAME);
//        TransactionType transactionType= TransactionType.getByCode(record.getStringValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_TYPE_COLUMN_NAME));
//        CryptoAddress addressFrom = new CryptoAddress();
//        addressFrom.setAddress(record.getStringValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_ADDRESS_FROM_COLUMN_NAME));
//        CryptoAddress addressTo = new CryptoAddress();
//        addressTo.setAddress(record.getStringValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_ADDRESS_TO_COLUMN_NAME));
//        UUID actorFrom = record.getUUIDValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_ID_COLUMN_NAME);
//        UUID actorTo = record.getUUIDValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_ID_COLUMN_NAME);
//        Actors actorFromType = Actors.getByCode(record.getStringValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_ACTOR_FROM_TYPE_COLUMN_NAME));
//        Actors actorToType = Actors.getByCode(record.getStringValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_ACTOR_TO_TYPE_COLUMN_NAME));
//        BalanceType balanceType =  BalanceType.getByCode(record.getStringValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_BALANCE_TYPE_COLUMN_NAME));
//        long amount = record.getLongValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_AMOUNT_COLUMN_NAME);
//        long runningBookBalance = record.getLongValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_RUNNING_BOOK_BALANCE_COLUMN_NAME);
//        long runningAvailableBalance = record.getLongValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_RUNNING_AVAILABLE_BALANCE_COLUMN_NAME);
//        long timeStamp = record.getLongValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_TIME_STAMP_COLUMN_NAME);
//        String memo = record.getStringValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_MEMO_COLUMN_NAME);
//
//        return new BitcoinWalletTransactionWrapper(transactionId, transactionHash, transactionType, addressFrom, addressTo,
//                actorFrom, actorTo, actorFromType, actorToType, balanceType, amount, runningBookBalance, runningAvailableBalance, timeStamp, memo);
//    }
//}
