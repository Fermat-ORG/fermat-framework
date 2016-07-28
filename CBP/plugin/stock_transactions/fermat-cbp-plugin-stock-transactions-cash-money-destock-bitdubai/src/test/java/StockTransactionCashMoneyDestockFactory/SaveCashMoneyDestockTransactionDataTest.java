package StockTransactionCashMoneyDestockFactory;


import com.bitdubai.fermat_cbp_api.all_definition.business_transaction.CashMoneyTransaction;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.cash_money_destock.developer.bitdubai.version_1.structure.StockTransactionCashMoneyDestockFactory;

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
public class SaveCashMoneyDestockTransactionDataTest {

    @Test
    public void saveCashMoneyDestockTransactionData() throws Exception {
        StockTransactionCashMoneyDestockFactory stockTransactionCashMoneyDestockFactory = mock(StockTransactionCashMoneyDestockFactory.class, Mockito.RETURNS_DEEP_STUBS);
        doCallRealMethod().when(stockTransactionCashMoneyDestockFactory).saveCashMoneyDestockTransactionData(Mockito.any(CashMoneyTransaction.class));
    }

}
