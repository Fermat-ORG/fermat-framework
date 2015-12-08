package unit.com.bitdubai.fermat_bch_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure.TransactionConfidenceCalculator;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_bch_plugin.layer.crypto_vault.developer.bitdubai.version_1.exceptions.CantCalculateTransactionConfidenceException;
import com.bitdubai.fermat_bch_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure.TransactionConfidenceCalculator;

import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.Wallet;
import org.junit.Test;
import org.mockito.Mock;

import java.util.UUID;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by natalia on 26/08/15.
 */
public class ConstructorTest {

    @Mock
    private Database mockDatabase;

    @Mock
    private Wallet mockWallet;

    @Mock
    private Transaction mockTransaction;

    @Test
    public void ConstructorTest(){


        TransactionConfidenceCalculator transactionConfidenceCalculator = new TransactionConfidenceCalculator( UUID.randomUUID().toString(), mockDatabase, mockWallet);
        assertThat(transactionConfidenceCalculator).isNotNull();

    }

    @Test
    public void ConstructorTest_With_TwoParams(){


        TransactionConfidenceCalculator transactionConfidenceCalculator = new TransactionConfidenceCalculator(mockTransaction, mockWallet);
        assertThat(transactionConfidenceCalculator).isNotNull();

    }
}
