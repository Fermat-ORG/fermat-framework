package test.com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.structure.IncomingCryptoRegistry;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmetricCryptography;
import com.bitdubai.fermat_api.layer.all_definition.crypto.util.CryptoHasher;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
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
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.structure.IncomingCryptoDataBaseConstants;
import com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.structure.IncomingCryptoRegistry;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;

import static org.mockito.Mockito.*;

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
    @Mock
    private DatabaseTableRecord mockRecord;

    @Mock
    private ErrorManager mockErrorManager;
    private UUID testId;
    private Transaction<CryptoTransaction> testTransaction;
    private List<Transaction<CryptoTransaction>> testTransactionList;
    private List<DatabaseTableRecord> testTableRecordList;

    private IncomingCryptoRegistry testRegistry;

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
        String from = AsymmetricCryptography.generateTestAddress(AsymmetricCryptography.derivePublicKey(AsymmetricCryptography.createPrivateKey()));
        String to = AsymmetricCryptography.generateTestAddress(AsymmetricCryptography.derivePublicKey(AsymmetricCryptography.createPrivateKey()));
        CryptoAddress fromAddress = new CryptoAddress(from, CryptoCurrency.BITCOIN);
        CryptoAddress toAddress = new CryptoAddress(to, CryptoCurrency.BITCOIN);
        String transactionHash = CryptoHasher.performSha256("TRANSACTION");

        CryptoTransaction cryptoTransaction = new CryptoTransaction(transactionHash, fromAddress, toAddress, CryptoCurrency.BITCOIN, 1L, CryptoStatus.ON_BLOCKCHAIN);
        testTransaction = new Transaction<>(UUID.randomUUID(), cryptoTransaction, Action.APPLY, System.currentTimeMillis());

        testTransactionList = new ArrayList<>();
        testTransactionList.add(testTransaction);
    }

    private void setUpRegistry() throws Exception{
        testRegistry = new IncomingCryptoRegistry();
        testRegistry.setPluginDatabaseSystem(mockPluginDatabaseSystem);
        testRegistry.setErrorManager(mockErrorManager);
        testRegistry.initialize(testId);
    }

    @Test
    public void AcknowledgeTransactions_TransactionProcessed_MethodInvokedSuccesfully() throws Exception{
        when(mockPluginDatabaseSystem.openDatabase(testId, IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_DATABASE)).thenReturn(mockDatabase);
        when(mockDatabase.getTable(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_NAME)).thenReturn(mockTable);
        when(mockTable.getEmptyRecord()).thenReturn(mockRecord);

        setUpRegistry();

        testRegistry.acknowledgeTransactions(testTransactionList);

        verifyZeroInteractions(mockErrorManager);
    }

    @Test
    public void AcknowledgeTransactions_EmptyList_MethodInvokedSuccesfully() throws Exception{
        when(mockPluginDatabaseSystem.openDatabase(testId, IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_DATABASE)).thenReturn(mockDatabase);
        when(mockDatabase.getTable(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_NAME)).thenReturn(mockTable);
        when(mockTable.getEmptyRecord()).thenReturn(mockRecord);

        setUpRegistry();
        testTransactionList.clear();

        testRegistry.acknowledgeTransactions(testTransactionList);

        verifyZeroInteractions(mockErrorManager);
    }

    @Test
    public void AcknowledgeTransactions_GeneralExceptionTriggered_MethodInvokedSuccesfully() throws Exception{
        when(mockPluginDatabaseSystem.openDatabase(testId, IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_DATABASE)).thenReturn(mockDatabase);
        when(mockDatabase.getTable(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_NAME)).thenReturn(null);

        setUpRegistry();

        testRegistry.acknowledgeTransactions(testTransactionList);

        verify(mockErrorManager).reportUnexpectedPluginException(any(Plugins.class), any(UnexpectedPluginExceptionSeverity.class), any(Exception.class));
    }

    @Test
    public void AcknowledgeTransactions_TableCantBeLoadToMemory_ThrowsCantAcknowledgeTransactionException() throws Exception{
        when(mockPluginDatabaseSystem.openDatabase(testId, IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_DATABASE)).thenReturn(mockDatabase);
        when(mockDatabase.getTable(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_NAME)).thenReturn(mockTable);

        CantLoadTableToMemoryException mockException = new CantLoadTableToMemoryException("MOCK", null, null, null);
        doThrow(mockException).when(mockTable).loadToMemory();

        setUpRegistry();

        testRegistry.acknowledgeTransactions(testTransactionList);

        verify(mockErrorManager).reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, mockException);
    }

    @Test
    public void AcknowledgeTransactions_TableCantInsertRecord_ThrowsCantAcknowledgeTransactionException() throws Exception {
        when(mockPluginDatabaseSystem.openDatabase(testId, IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_DATABASE)).thenReturn(mockDatabase);
        when(mockDatabase.getTable(IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_REGISTRY_TABLE_NAME)).thenReturn(mockTable);
        when(mockTable.getRecords()).thenReturn(testTableRecordList);
        when(mockTable.getEmptyRecord()).thenReturn(new MockDatabaseTableRecord());

        CantInsertRecordException mockException = new CantInsertRecordException("MOCK", null, null, null);
        doThrow(mockException).when(mockTable).insertRecord(any(DatabaseTableRecord.class));

        setUpRegistry();

        testRegistry.acknowledgeTransactions(testTransactionList);

        verify(mockErrorManager).reportUnexpectedPluginException(Plugins.BITDUBAI_INCOMING_CRYPTO_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, mockException);
    }
}
