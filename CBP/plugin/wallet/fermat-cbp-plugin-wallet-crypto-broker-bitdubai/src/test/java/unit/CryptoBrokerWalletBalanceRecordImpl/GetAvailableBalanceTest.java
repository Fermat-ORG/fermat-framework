package unit.CryptoBrokerWalletBalanceRecordImpl;

import com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.structure.util.CryptoBrokerWalletBalanceRecordImpl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by José Vilchez on 21/01/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class GetAvailableBalanceTest {

    @Test
    public void getAvailableBalance() {
        CryptoBrokerWalletBalanceRecordImpl cryptoBrokerWalletBalanceRecord = mock(CryptoBrokerWalletBalanceRecordImpl.class);
        when(cryptoBrokerWalletBalanceRecord.getAvailableBalance()).thenReturn(BigDecimal.ONE);
        assertThat(cryptoBrokerWalletBalanceRecord.getAvailableBalance()).isNotNull();
    }

}
