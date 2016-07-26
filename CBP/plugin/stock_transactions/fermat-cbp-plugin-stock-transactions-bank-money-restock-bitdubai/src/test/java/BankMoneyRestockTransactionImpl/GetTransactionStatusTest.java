package BankMoneyRestockTransactionImpl;

import com.bitdubai.fermat_cbp_api.all_definition.enums.TransactionStatusRestockDestock;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.bank_money_restock.developer.bitdubai.version_1.structure.BankMoneyRestockTransactionImpl;

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
        BankMoneyRestockTransactionImpl bankMoneyRestockTransaction = mock(BankMoneyRestockTransactionImpl.class);
        when(bankMoneyRestockTransaction.getTransactionStatus()).thenReturn(transactionStatusRestockDestock);
        assertThat(bankMoneyRestockTransaction.getTransactionStatus()).isNotNull();
    }

}
