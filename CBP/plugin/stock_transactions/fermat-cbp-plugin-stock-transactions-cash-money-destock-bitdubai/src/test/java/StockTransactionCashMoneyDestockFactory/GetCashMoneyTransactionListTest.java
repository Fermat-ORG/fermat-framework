package StockTransactionCashMoneyDestockFactory;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilter;
import com.bitdubai.fermat_cbp_api.all_definition.business_transaction.CashMoneyTransaction;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.cash_money_destock.developer.bitdubai.version_1.exceptions.DatabaseOperationException;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.cash_money_destock.developer.bitdubai.version_1.structure.StockTransactionCashMoneyDestockFactory;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Jose Vilchez on 18/01/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class GetCashMoneyTransactionListTest {

    @Test
    public void getCashMoneyTransactionList() throws InvalidParameterException, DatabaseOperationException {
        StockTransactionCashMoneyDestockFactory stockTransactionCashMoneyDestockFactory = mock(StockTransactionCashMoneyDestockFactory.class);
        when(stockTransactionCashMoneyDestockFactory.getCashMoneyTransactionList(Mockito.any(DatabaseTableFilter.class))).thenReturn(new ArrayList<CashMoneyTransaction>()).thenCallRealMethod();
        assertThat(stockTransactionCashMoneyDestockFactory.getCashMoneyTransactionList(Mockito.any(DatabaseTableFilter.class))).isNotNull();
    }

}
