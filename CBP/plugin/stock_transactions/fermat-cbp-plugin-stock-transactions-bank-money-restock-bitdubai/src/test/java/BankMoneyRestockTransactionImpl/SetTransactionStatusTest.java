package BankMoneyRestockTransactionImpl;

import com.bitdubai.fermat_cbp_api.all_definition.enums.TransactionStatusRestockDestock;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.bank_money_restock.developer.bitdubai.version_1.structure.BankMoneyRestockTransactionImpl;

import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;

/**
 * Created by Jose Vilchez on 18/01/16.
 */
public class SetTransactionStatusTest {

    @Test
    public void setTransactionStatus() {
        BankMoneyRestockTransactionImpl bankMoneyRestockTransaction = mock(BankMoneyRestockTransactionImpl.class, Mockito.RETURNS_DEEP_STUBS);
        doCallRealMethod().when(bankMoneyRestockTransaction).setTransactionStatus(Mockito.any(TransactionStatusRestockDestock.class));
    }

}
