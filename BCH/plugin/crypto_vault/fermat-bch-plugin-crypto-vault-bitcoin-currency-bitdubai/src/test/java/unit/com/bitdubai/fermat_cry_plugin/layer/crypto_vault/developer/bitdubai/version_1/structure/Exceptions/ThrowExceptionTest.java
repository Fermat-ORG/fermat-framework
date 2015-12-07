package unit.com.bitdubai.fermat_bch_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure.Exceptions;

import com.bitdubai.fermat_bch_plugin.layer.crypto_vault.developer.bitdubai.version_1.exceptions.CantCalculateTransactionConfidenceException;
import com.bitdubai.fermat_bch_plugin.layer.crypto_vault.developer.bitdubai.version_1.exceptions.CantExecuteQueryException;
import com.bitdubai.fermat_bch_plugin.layer.crypto_vault.developer.bitdubai.version_1.exceptions.TransactionProtocolAgentMaxIterationsReachedException;
import com.bitdubai.fermat_bch_plugin.layer.crypto_vault.developer.bitdubai.version_1.exceptions.UnexpectedResultReturnedFromDatabaseException;

import org.junit.Test;

/**
 * Created by rodrigo on 2015.07.15..
 */
public class ThrowExceptionTest {

    @Test(expected = CantCalculateTransactionConfidenceException.class)
    public void testCantCalculateTransactionConfidenceException() throws CantCalculateTransactionConfidenceException {
        throw new CantCalculateTransactionConfidenceException("Test", null, null, null);
    }

    @Test(expected = CantExecuteQueryException.class)
    public void testCantExecuteQueryException() throws CantExecuteQueryException {
        throw new CantExecuteQueryException("Test", null, null, null);
    }

    @Test(expected = TransactionProtocolAgentMaxIterationsReachedException.class)
    public void testTransactionProtocolAgentMaxIterationsReachedException() throws TransactionProtocolAgentMaxIterationsReachedException {
        throw new TransactionProtocolAgentMaxIterationsReachedException("Test", null, null, null);
    }

    @Test(expected = UnexpectedResultReturnedFromDatabaseException.class)
    public void testUnexpectedResultReturnedFromDatabaseException() throws UnexpectedResultReturnedFromDatabaseException {
        throw new UnexpectedResultReturnedFromDatabaseException("Test", null, null, null);
    }
}
