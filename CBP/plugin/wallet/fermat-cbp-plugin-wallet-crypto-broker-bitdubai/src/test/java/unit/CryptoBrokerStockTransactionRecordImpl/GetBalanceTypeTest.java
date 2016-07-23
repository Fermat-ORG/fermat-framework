package unit.CryptoBrokerStockTransactionRecordImpl;

import com.bitdubai.fermat_cbp_api.all_definition.enums.BalanceType;
import com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.structure.util.CryptoBrokerStockTransactionRecordImpl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by José Vilchez on 22/01/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class GetBalanceTypeTest {

    @Test
    public void getBalanceType() {
        CryptoBrokerStockTransactionRecordImpl cryptoBrokerStockTransactionRecord = mock(CryptoBrokerStockTransactionRecordImpl.class);
        when(cryptoBrokerStockTransactionRecord.getBalanceType()).thenReturn(BalanceType.AVAILABLE);
        assertThat(cryptoBrokerStockTransactionRecord.getBalanceType()).isNotNull();
    }

}
