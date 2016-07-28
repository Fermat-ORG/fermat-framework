package unit.CryptoMoneyRestockTransactionImpl;

import com.bitdubai.fermat_cbp_api.all_definition.enums.OriginTransaction;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.crypto_money_restock.developer.bitdubai.version_1.structure.CryptoMoneyRestockTransactionImpl;

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
public class GetOriginTransactionTest {

    OriginTransaction originTransaction = OriginTransaction.STOCK_INITIAL;

    @Test
    public void getOriginTransaction() throws Exception {
        CryptoMoneyRestockTransactionImpl cryptoMoneyRestockTransaction = mock(CryptoMoneyRestockTransactionImpl.class);
        when(cryptoMoneyRestockTransaction.getOriginTransaction()).thenReturn(originTransaction);
        assertThat(cryptoMoneyRestockTransaction.getOriginTransaction()).isNotNull();
    }

}
