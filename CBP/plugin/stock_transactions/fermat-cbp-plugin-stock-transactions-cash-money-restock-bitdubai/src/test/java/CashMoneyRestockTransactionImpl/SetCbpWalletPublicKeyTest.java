package CashMoneyRestockTransactionImpl;

import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.cash_money_restock.developer.bitdubai.version_1.structure.CashMoneyRestockTransactionImpl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;

/**
 * Created by jose Vilchez on 18/01/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class SetCbpWalletPublicKeyTest {

    @Test
    public void setCbpWalletPublicKey() {
        CashMoneyRestockTransactionImpl cashMoneyDestockTransaction = mock(CashMoneyRestockTransactionImpl.class, Mockito.RETURNS_DEEP_STUBS);
        doCallRealMethod().when(cashMoneyDestockTransaction).setCbpWalletPublicKey(Mockito.any(String.class));
    }

}
