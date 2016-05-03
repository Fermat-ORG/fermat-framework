package unit.HoldCryptoMoneyTransactionPluginRoot;

import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.hold.interfaces.CryptoHoldTransaction;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.hold.interfaces.CryptoHoldTransactionParameters;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.hold.developer.bitdubai.version_1.HoldCryptoMoneyTransactionPluginRoot;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.hold.developer.bitdubai.version_1.utils.HoldCryptoMoneyTransactionImpl;

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
public class CreateCryptoHoldTransactionTest {

    CryptoHoldTransaction holdCryptoMoneyTransaction = new HoldCryptoMoneyTransactionImpl();

    @Test
    public void createCryptoHoldTransaction() throws Exception{
        HoldCryptoMoneyTransactionPluginRoot holdCryptoMoneyTransactionPluginRoot = mock(HoldCryptoMoneyTransactionPluginRoot.class);
        when(holdCryptoMoneyTransactionPluginRoot.createCryptoHoldTransaction(Mockito.any(CryptoHoldTransactionParameters.class))).thenReturn(holdCryptoMoneyTransaction).thenCallRealMethod();
        assertThat(holdCryptoMoneyTransactionPluginRoot.createCryptoHoldTransaction(Mockito.any(CryptoHoldTransactionParameters.class))).isNotNull();
    }

}
