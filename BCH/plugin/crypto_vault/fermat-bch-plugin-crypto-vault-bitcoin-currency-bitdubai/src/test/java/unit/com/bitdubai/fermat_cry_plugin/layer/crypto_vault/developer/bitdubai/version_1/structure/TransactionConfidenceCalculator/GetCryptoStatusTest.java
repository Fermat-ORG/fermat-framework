package unit.com.bitdubai.fermat_bch_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure.TransactionConfidenceCalculator;

import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoStatus;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_bch_plugin.layer.crypto_vault.developer.bitdubai.version_1.exceptions.CantCalculateTransactionConfidenceException;
import com.bitdubai.fermat_bch_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure.TransactionConfidenceCalculator;


import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.TransactionConfidence;
import org.bitcoinj.core.Wallet;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;


import java.util.List;
import java.util.UUID;


import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static org.fest.assertions.api.Assertions.assertThat;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Created by natalia on 26/08/15.
 */

@RunWith(MockitoJUnitRunner.class)
public class GetCryptoStatusTest {

    @Mock
    private PluginDatabaseSystem mockPluginDatabaseSystem;
    @Mock
    private Database mockDatabase;
    @Mock
    private DatabaseFactory mockDatabaseFactory;
    @Mock
    private DatabaseTable mockTable;

    @Mock
    private List<DatabaseTableRecord> mockRecords;

    @Mock
    private DatabaseTableRecord mockRecord;

    @Mock
    private DatabaseTableFactory mockTableFactory;

    @Mock
    private Wallet mockWallet;

    @Mock
    private Sha256Hash mockTxHash;

    @Mock
    private Transaction mockTransaction;


 //   private TransactionConfidence.ConfidenceType mockConfidenceType;

    private TransactionConfidenceCalculator transactionConfidenceCalculator;
    private UUID testOwnerId;

    private String deviceUserPublicKey;

    private void setUpIds(){
        testOwnerId = UUID.randomUUID();

        deviceUserPublicKey = UUID.randomUUID().toString();

    }

    private void setUpMockitoGeneralRules() throws Exception{


        when(mockPluginDatabaseSystem.createDatabase(testOwnerId, deviceUserPublicKey)).thenReturn(mockDatabase);
        when(mockDatabase.getDatabaseFactory()).thenReturn(mockDatabaseFactory);
        when(mockDatabase.getTable(anyString())).thenReturn(mockTable);
        when(mockTable.getRecords()).thenReturn(mockRecords);
        Mockito.doReturn(mockRecord).when(mockRecords).get(0);
        when(mockRecord.getStringValue(anyString())).thenReturn("1234567891254789632547895745214785698745820000002586879256987000");
        when(mockDatabaseFactory.newTableFactory(anyString())).thenReturn(mockTableFactory);

        Sha256Hash txHash = new Sha256Hash("1234567891254789632547895745214785698745820000002586879256987000");

        when(mockWallet.getTransaction(txHash)).thenReturn(mockTransaction);


    }

    @Before
    public void setUp() throws Exception{
        setUpIds();
        setUpMockitoGeneralRules();
    }

    @Test
    public void getCryptoStatusTest_ReturnCryptoStatusError_ThrowsCantCalculateTransactionConfidenceException() throws Exception{


        transactionConfidenceCalculator = new TransactionConfidenceCalculator(testOwnerId.toString(), mockDatabase, null);

        catchException(transactionConfidenceCalculator).getCryptoStatus();
        assertThat(caughtException())
                .isNotNull()
                .isInstanceOf(CantCalculateTransactionConfidenceException.class);
    }

    @Ignore
    public void getCryptoStatusTest_ReturnCryptoStatusOk_ThrowsCantCalculateTransactionConfidenceException() throws Exception{
 //TODO: No puedo hacer el mock del wallet transction
         mockWallet= new Wallet(NetworkParameters.fromID("org.bitcoin.test"));

        transactionConfidenceCalculator = new TransactionConfidenceCalculator(testOwnerId.toString(), mockDatabase, mockWallet);

       CryptoStatus status = transactionConfidenceCalculator.getCryptoStatus();
        assertThat(status).isNotNull();
    }
}
