package test.com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.structure.IncomingCryptoRegistry;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmectricCryptography;
import com.bitdubai.fermat_api.layer.all_definition.crypto.util.CryptoHasher;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Action;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Transaction;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoStatus;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.exceptions.CantGetRecordException;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.structure.IncomingCryptoDataBaseConstants;
import com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.structure.IncomingCryptoRegistry;

import static org.fest.assertions.api.Assertions.*;
import static org.mockito.Mockito.*;
import static com.googlecode.catchexception.CatchException.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
/**
 * Created by Franklin Marcano 04/08/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class AcknowledgeTransactionsTest {
    @Mock
    private PluginDatabaseSystem mockPluginDatabaseSystem = mock(PluginDatabaseSystem.class);
    @Mock
    private Database mockDatabase = mock(Database.class);
    @Mock
    private DatabaseTable mockTable = mock(DatabaseTable.class);

    private UUID testId;
    private Transaction<CryptoTransaction> testTransaction;
    private List<Transaction<CryptoTransaction>> testTransactionList;
    private List<DatabaseTableRecord> testTableRecordList;

    private IncomingCryptoRegistry mockRegistry;

    @Before
    public void setUpId(){
        testId = UUID.randomUUID();
    }

    @Before
    public void setUpDatabaseTableRecordList(){
        testTableRecordList = new ArrayList<>();
    }

    @Before
    public void setUpTransactionList(){
        String from = AsymmectricCryptography.generateTestAddress(AsymmectricCryptography.derivePublicKey(AsymmectricCryptography.createPrivateKey()));
        String to = AsymmectricCryptography.generateTestAddress(AsymmectricCryptography.derivePublicKey(AsymmectricCryptography.createPrivateKey()));
        CryptoAddress fromAddress = new CryptoAddress(from, CryptoCurrency.BITCOIN);
        CryptoAddress toAddress = new CryptoAddress(to, CryptoCurrency.BITCOIN);
        String transactionHash = CryptoHasher.performSha256("TRANSACTION");

        CryptoTransaction cryptoTransaction = new CryptoTransaction(transactionHash, fromAddress, toAddress, CryptoCurrency.BITCOIN, 1L, CryptoStatus.ON_BLOCKCHAIN);
        testTransaction = new Transaction<>(UUID.randomUUID(), cryptoTransaction, Action.APPLY, System.currentTimeMillis());

        testTransactionList = new ArrayList<>();
        testTransactionList.add(testTransaction);
    }

    private void setUpRegistry() throws Exception{
        mockRegistry = new IncomingCryptoRegistry();
        mockRegistry.setPluginDatabaseSystem(mockPluginDatabaseSystem);
        mockRegistry.initialize(testId);
    }

    @Test
    public void AcknowledgeTransactions_EmptyList_MethodInvokedSuccesfully() throws Exception{
        when(mockPluginDatabaseSystem.openDatabase(testId, IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_NAME)).thenReturn(mockDatabase);
        when(mockDatabase.getTable(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_NAME)).thenReturn(mockTable);

        setUpRegistry();
        testTransactionList.clear();

        catchException(mockRegistry).acknowledgeTransactions(testTransactionList);
        assertThat(caughtException()).isNotNull();//isNull();
    }

    @Test
    public void AcknowledgeTransactions_TableCantBeLoadToMemory_ThrowsCantAcknowledgeTransactionException() throws Exception{
        when(mockPluginDatabaseSystem.openDatabase(testId, IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_NAME)).thenReturn(mockDatabase);
        when(mockDatabase.getTable(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_NAME)).thenReturn(mockTable);
        doThrow(new CantLoadTableToMemoryException("MOCK", null, null, null)).when(mockTable).loadToMemory();

        setUpRegistry();

        catchException(mockRegistry).acknowledgeTransactions(testTransactionList);
        assertThat(caughtException()).isNotNull();//isInstanceOf(CantGetRecordException.class);
    }

    @Test
    public void AcknowledgeTransactions_TableCantInsertRecord_ThrowsCantAcknowledgeTransactionException() throws Exception{
        when(mockPluginDatabaseSystem.openDatabase(testId, IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_DATABASE)).thenReturn(mockDatabase);
        when(mockDatabase.getTable(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_NAME)).thenReturn(mockTable);
        when(mockTable.getRecords()).thenReturn(testTableRecordList);
        //when(mockTable.getEmptyRecord()).thenReturn(new MockDatabaseTableRecord());
        doThrow(new CantInsertRecordException("MOCK", null, null, null)).when(mockTable).insertRecord(any(DatabaseTableRecord.class));

        setUpRegistry();


        catchException(mockRegistry).acknowledgeTransactions(testTransactionList);
        assertThat(caughtException()).isNotNull();//isInstanceOf(CantGetRecordException.class);
    }
}
