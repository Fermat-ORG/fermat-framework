package unit.UnholdCryptoMoneyTransactionManager;

import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.Unhold.interfaces.CryptoUnholdTransaction;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.unhold.developer.bitdubai.version_1.exceptions.DatabaseOperationException;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.unhold.developer.bitdubai.version_1.exceptions.MissingUnHoldCryptoDataException;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.unhold.developer.bitdubai.version_1.structure.UnHoldCryptoMoneyTransactionManager;

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
public class SaveUnHoldCryptoMoneyTransactionDataTest {

    @Test
    public void saveUnHoldCryptoMoneyTransactionData() throws DatabaseOperationException, MissingUnHoldCryptoDataException {
        UnHoldCryptoMoneyTransactionManager unHoldCryptoMoneyTransactionManager = mock(UnHoldCryptoMoneyTransactionManager.class, Mockito.RETURNS_DEEP_STUBS);
        doCallRealMethod().when(unHoldCryptoMoneyTransactionManager).saveUnHoldCryptoMoneyTransactionData(Mockito.any(CryptoUnholdTransaction.class));
    }

}
