package unit.HoldCryptoMoneyTransactionImpl;

import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.hold.developer.bitdubai.version_1.utils.HoldCryptoMoneyTransactionImpl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;

/**
 * Created by Jose Vilchez on 18/01/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class SetPublicKeyWalletTest {

    @Test
    public void setPublicKeyWallet(){
        HoldCryptoMoneyTransactionImpl holdCryptoMoneyTransaction = mock(HoldCryptoMoneyTransactionImpl.class, Mockito.RETURNS_DEEP_STUBS);
        doCallRealMethod().when(holdCryptoMoneyTransaction).setPublicKeyWallet(Mockito.any(String.class));
    }

}
