package StockTransactionCashMoneyRestockFactory;


import com.bitdubai.fermat_cbp_api.all_definition.business_transaction.CashMoneyTransaction;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.cash_money_restock.developer.bitdubai.version_1.structure.StockTransactionCashMoneyRestockFactory;

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
public class SaveCashMoneyRestockTransactionDataTest {

    @Test
    public void saveCashMoneyDestockTransactionData() throws Exception {
        StockTransactionCashMoneyRestockFactory stockTransactionCashMoneyDestockFactory = mock(StockTransactionCashMoneyRestockFactory.class, Mockito.RETURNS_DEEP_STUBS);
        doCallRealMethod().when(stockTransactionCashMoneyDestockFactory).saveCashMoneyRestockTransactionData(Mockito.any(CashMoneyTransaction.class));
    }

}
