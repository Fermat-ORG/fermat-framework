package unit.com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.version_1.structure.IncomingExtraUserRegistry;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmetricCryptography;
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
import com.bitdubai.fermat_ccp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.version_1.exceptions.CantAcknowledgeTransactionException;
import com.bitdubai.fermat_ccp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.version_1.structure.IncomingExtraUserDataBaseConstants;
import com.bitdubai.fermat_ccp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.version_1.structure.IncomingExtraUserRegistry;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by jorgegonzalez on 2015.07.02..
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
    private IncomingExtraUserRegistry testRegistry;

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
        testRegistry = new IncomingExtraUserRegistry();
        testRegistry.setPluginDatabaseSystem(mockPluginDatabaseSystem);
        testRegistry.initialize(testId);
    }


    @Test
    public void AcknowledgeTransactions_EmptyList_MethodInvokedSuccesfully() throws Exception{
        when(mockPluginDatabaseSystem.openDatabase(testId, IncomingExtraUserDataBaseConstants.INCOMING_EXTRA_USER_DATABASE)).thenReturn(mockDatabase);
        when(mockDatabase.getTable(IncomingExtraUserDataBaseConstants.INCOMING_EXTRA_USER_REGISTRY_TABLE_NAME)).thenReturn(mockTable);

        setUpRegistry();
        testTransactionList.clear();

        catchException(testRegistry).acknowledgeTransactions(testTransactionList);
        assertThat(caughtException()).isNull();
    }

    @Test
    public void AcknowledgeTransactions_TableCantBeLoadToMemory_ThrowsCantAcknowledgeTransactionException() throws Exception{
        when(mockPluginDatabaseSystem.openDatabase(testId, IncomingExtraUserDataBaseConstants.INCOMING_EXTRA_USER_DATABASE)).thenReturn(mockDatabase);
        when(mockDatabase.getTable(IncomingExtraUserDataBaseConstants.INCOMING_EXTRA_USER_REGISTRY_TABLE_NAME)).thenReturn(mockTable);
        doThrow(new CantLoadTableToMemoryException("MOCK", null, null, null)).when(mockTable).loadToMemory();

        setUpRegistry();

        catchException(testRegistry).acknowledgeTransactions(testTransactionList);
        assertThat(caughtException()).isInstanceOf(CantAcknowledgeTransactionException.class);
    }

    @Test
    public void AcknowledgeTransactions_TableCantInsertRecord_ThrowsCantAcknowledgeTransactionException() throws Exception{
        when(mockPluginDatabaseSystem.openDatabase(testId, IncomingExtraUserDataBaseConstants.INCOMING_EXTRA_USER_DATABASE)).thenReturn(mockDatabase);
        when(mockDatabase.getTable(IncomingExtraUserDataBaseConstants.INCOMING_EXTRA_USER_REGISTRY_TABLE_NAME)).thenReturn(mockTable);
        when(mockTable.getRecords()).thenReturn(testTableRecordList);
        when(mockTable.getEmptyRecord()).thenReturn(new MockDatabaseTableRecord());
        doThrow(new CantInsertRecordException("MOCK", null, null, null)).when(mockTable).insertRecord(any(DatabaseTableRecord.class));

        setUpRegistry();


        catchException(testRegistry).acknowledgeTransactions(testTransactionList);
        assertThat(caughtException()).isInstanceOf(CantAcknowledgeTransactionException.class);
    }
}
