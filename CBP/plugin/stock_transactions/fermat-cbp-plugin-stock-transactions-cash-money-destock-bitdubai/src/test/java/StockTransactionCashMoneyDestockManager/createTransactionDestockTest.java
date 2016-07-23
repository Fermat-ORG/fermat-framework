package StockTransactionCashMoneyDestockManager;

import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_cbp_api.all_definition.enums.OriginTransaction;
import com.bitdubai.fermat_cbp_api.layer.stock_transactions.cash_money_destock.exceptions.CantCreateCashMoneyDestockException;
import com.bitdubai.fermat_cbp_api.layer.stock_transactions.crypto_money_destock.exceptions.CantCreateCryptoMoneyDestockException;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.cash_money_destock.developer.bitdubai.version_1.structure.StockTransactionCashMoneyDestockManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;

/**
 * Created by Jose Vilchez on 18/01/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class createTransactionDestockTest {

    @Test
    public void createTransactionDestock() throws CantCreateCryptoMoneyDestockException, CantCreateCashMoneyDestockException {
        StockTransactionCashMoneyDestockManager stockTransactionCashMoneyDestockManager = mock(StockTransactionCashMoneyDestockManager.class, Mockito.RETURNS_DEEP_STUBS);
        doCallRealMethod().when(stockTransactionCashMoneyDestockManager).createTransactionDestock(Mockito.any(String.class),
                Mockito.any(FiatCurrency.class), Mockito.any(String.class), Mockito.any(String.class), Mockito.any(String.class),
                Mockito.any(BigDecimal.class), Mockito.any(String.class), Mockito.any(BigDecimal.class), Mockito.any(OriginTransaction.class), Mockito.anyString());
    }

}
