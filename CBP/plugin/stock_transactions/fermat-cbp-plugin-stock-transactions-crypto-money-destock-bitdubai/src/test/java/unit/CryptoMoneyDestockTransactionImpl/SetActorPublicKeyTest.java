package unit.CryptoMoneyDestockTransactionImpl;

import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.crypto_money_destock.developer.bitdubai.version_1.structure.CryptoMoneyDestockTransactionImpl;

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
public class SetActorPublicKeyTest {
    @Test
    public void setActorPublicKey() {
        CryptoMoneyDestockTransactionImpl cryptoMoneyDestockTransaction = mock(CryptoMoneyDestockTransactionImpl.class, Mockito.RETURNS_DEEP_STUBS);
        doCallRealMethod().when(cryptoMoneyDestockTransaction).setActorPublicKey(Mockito.any(String.class));
    }
}
