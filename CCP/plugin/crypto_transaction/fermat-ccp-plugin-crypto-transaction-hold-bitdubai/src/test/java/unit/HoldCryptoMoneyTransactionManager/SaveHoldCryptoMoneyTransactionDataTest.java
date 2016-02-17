package unit.HoldCryptoMoneyTransactionManager;

import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.hold.interfaces.CryptoHoldTransaction;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.hold.developer.bitdubai.version_1.exceptions.DatabaseOperationException;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.hold.developer.bitdubai.version_1.exceptions.MissingHoldCryptoDataException;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.hold.developer.bitdubai.version_1.structure.HoldCryptoMoneyTransactionManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;

/**
 * Created by José Vilchez on 21/01/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class SaveHoldCryptoMoneyTransactionDataTest {

    @Test
    public void saveHoldCryptoMoneyTransactionData() throws DatabaseOperationException, MissingHoldCryptoDataException {
        HoldCryptoMoneyTransactionManager holdCryptoMoneyTransactionManager = mock(HoldCryptoMoneyTransactionManager.class, Mockito.RETURNS_DEEP_STUBS);
        doCallRealMethod().when(holdCryptoMoneyTransactionManager).saveHoldCryptoMoneyTransactionData(Mockito.any(CryptoHoldTransaction.class));
    }

}
