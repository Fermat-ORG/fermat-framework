package CashMoneyRestockTransactionImpl;

import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.cash_money_restock.developer.bitdubai.version_1.structure.CashMoneyRestockTransactionImpl;

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
public class GetFiatCurrencyTest {
    private FiatCurrency cryptoCurrency = FiatCurrency.ARGENTINE_PESO;

    @Test
    public void getCryptoCurrency() {
        CashMoneyRestockTransactionImpl cashMoneyRestockTransaction = mock(CashMoneyRestockTransactionImpl.class);
        when(cashMoneyRestockTransaction.getFiatCurrency()).thenReturn(cryptoCurrency);
        assertThat(cashMoneyRestockTransaction.getFiatCurrency()).isNotNull();
    }
}
