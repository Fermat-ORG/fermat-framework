package unit.CryptoMoneyRestockTransactionImpl;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
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
public class getCryptoCurrencyTest {
    private CryptoCurrency cryptoCurrency = CryptoCurrency.BITCOIN;

    @Test
    public void getCryptoCurrency() {
        CryptoMoneyRestockTransactionImpl cryptoMoneyRestockTransaction = mock(CryptoMoneyRestockTransactionImpl.class);
        when(cryptoMoneyRestockTransaction.getCryptoCurrency()).thenReturn(cryptoCurrency);
        assertThat(cryptoMoneyRestockTransaction.getCryptoCurrency()).isNotNull();
    }
}
