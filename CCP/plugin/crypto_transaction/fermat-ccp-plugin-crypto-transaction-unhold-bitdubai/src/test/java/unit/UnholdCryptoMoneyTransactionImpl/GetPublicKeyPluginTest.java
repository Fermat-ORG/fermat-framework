package unit.UnholdCryptoMoneyTransactionImpl;

import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.unhold.developer.bitdubai.version_1.utils.UnHoldCryptoMoneyTransactionImpl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by José Vilchez on 21/01/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class GetPublicKeyPluginTest {

    @Test
    public void getPublicKeyPlugin(){
        UnHoldCryptoMoneyTransactionImpl unHoldCryptoMoneyTransaction = mock(UnHoldCryptoMoneyTransactionImpl.class);
        when(unHoldCryptoMoneyTransaction.getPublicKeyPlugin()).thenReturn(new String());
        assertThat(unHoldCryptoMoneyTransaction.getPublicKeyPlugin()).isNotNull();
    }

}
