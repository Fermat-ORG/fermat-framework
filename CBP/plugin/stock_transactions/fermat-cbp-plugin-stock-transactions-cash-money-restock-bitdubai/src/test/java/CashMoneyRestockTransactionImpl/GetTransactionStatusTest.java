package CashMoneyRestockTransactionImpl;

import com.bitdubai.fermat_cbp_api.all_definition.enums.TransactionStatusRestockDestock;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.cash_money_restock.developer.bitdubai.version_1.structure.CashMoneyRestockTransactionImpl;

import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Jose Vilchez on 18/01/16.
 */
public class GetTransactionStatusTest {

    private TransactionStatusRestockDestock transactionStatusRestockDestock = TransactionStatusRestockDestock.INIT_TRANSACTION;

    @Test
    public void getTransactionStatus() {
        CashMoneyRestockTransactionImpl cashMoneyRestockTransaction = mock(CashMoneyRestockTransactionImpl.class);
        when(cashMoneyRestockTransaction.getTransactionStatus()).thenReturn(transactionStatusRestockDestock);
        assertThat(cashMoneyRestockTransaction.getTransactionStatus()).isNotNull();
    }

}
