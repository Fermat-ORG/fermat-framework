package unit.HoldCryptoMoneyTransactionImpl;

import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.hold.developer.bitdubai.version_1.utils.HoldCryptoMoneyTransactionImpl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Jose Vilchez on 18/01/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class GetPublicKeyWalletTest {

    @Test
    public void getPublicKeyWallet(){
        HoldCryptoMoneyTransactionImpl holdCryptoMoneyTransaction = mock(HoldCryptoMoneyTransactionImpl.class);
        when(holdCryptoMoneyTransaction.getPublicKeyWallet()).thenReturn(new String());
        assertThat(holdCryptoMoneyTransaction.getPublicKeyWallet()).isNotNull();
    }

}
