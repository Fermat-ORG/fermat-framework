package unit.CryptoBrokerWalletBalanceRecordImpl;

import com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.structure.util.CryptoBrokerWalletBalanceRecordImpl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;

/**
 * Created by José Vilchez on 21/01/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class SetBookBalanceTest {

    @Test
    public void setBookBalance() {
        CryptoBrokerWalletBalanceRecordImpl cryptoBrokerWalletBalanceRecord = mock(CryptoBrokerWalletBalanceRecordImpl.class, Mockito.RETURNS_DEEP_STUBS);
        doCallRealMethod().when(cryptoBrokerWalletBalanceRecord).setBookBalance(Mockito.any(BigDecimal.class));
    }

}
