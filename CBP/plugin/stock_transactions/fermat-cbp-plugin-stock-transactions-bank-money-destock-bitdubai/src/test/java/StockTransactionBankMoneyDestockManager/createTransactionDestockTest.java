package StockTransactionBankMoneyDestockManager;

import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_cbp_api.all_definition.enums.OriginTransaction;
import com.bitdubai.fermat_cbp_api.layer.stock_transactions.bank_money_destock.exceptions.CantCreateBankMoneyDestockException;
import com.bitdubai.fermat_cbp_api.layer.stock_transactions.cash_money_destock.exceptions.CantCreateCashMoneyDestockException;
import com.bitdubai.fermat_cbp_api.layer.stock_transactions.crypto_money_destock.exceptions.CantCreateCryptoMoneyDestockException;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.bank_money_destock.developer.bitdubai.version_1.structure.StockTransactionBankMoneyDestockManager;

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
    public void createTransactionDestock() throws CantCreateCryptoMoneyDestockException, CantCreateCashMoneyDestockException, CantCreateBankMoneyDestockException {
        StockTransactionBankMoneyDestockManager stockTransactionBankMoneyDestockManager = mock(StockTransactionBankMoneyDestockManager.class, Mockito.RETURNS_DEEP_STUBS);
        doCallRealMethod().when(stockTransactionBankMoneyDestockManager).createTransactionDestock(
                Mockito.any(String.class),
                Mockito.any(FiatCurrency.class),
                Mockito.any(String.class), Mockito.any(String.class),
                Mockito.any(String.class),
                Mockito.any(BigDecimal.class),
                Mockito.any(String.class),
                Mockito.any(BigDecimal.class),
                Mockito.any(OriginTransaction.class),
                Mockito.any(String.class));
    }

}
