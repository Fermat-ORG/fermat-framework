package unit.UnholdCryptoMoneyTransactionPluginRoot;

import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.Unhold.interfaces.CryptoUnholdTransaction;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.Unhold.interfaces.CryptoUnholdTransactionParameters;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.unhold.developer.bitdubai.version_1.UnHoldCryptoMoneyTransactionPluginRoot;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.unhold.developer.bitdubai.version_1.utils.UnHoldCryptoMoneyTransactionImpl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by José Vilchez on 21/01/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class CreateCryptoUnholdTransactionTest {

    CryptoUnholdTransaction unHoldCryptoMoneyTransaction = new UnHoldCryptoMoneyTransactionImpl();

    @Test
    public void createCryptoUnholdTransaction() throws Exception{
        UnHoldCryptoMoneyTransactionPluginRoot unHoldCryptoMoneyTransactionPluginRoot = mock(UnHoldCryptoMoneyTransactionPluginRoot.class);
        when(unHoldCryptoMoneyTransactionPluginRoot.createCryptoUnholdTransaction(Mockito.any(CryptoUnholdTransactionParameters.class))).thenReturn(unHoldCryptoMoneyTransaction).thenCallRealMethod();
        assertThat(unHoldCryptoMoneyTransactionPluginRoot.createCryptoUnholdTransaction(Mockito.any(CryptoUnholdTransactionParameters.class))).isNotNull();
    }

}
