package CashMoneyDestockTransactionImpl;

import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.cash_money_destock.developer.bitdubai.version_1.structure.CashMoneyDestockTransactionImpl;

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
public class GetCashWalletPublicKeyTest {

    @Test
    public void getCashWalletPublicKey() {
        CashMoneyDestockTransactionImpl cashMoneyDestockTransaction = mock(CashMoneyDestockTransactionImpl.class);
        when(cashMoneyDestockTransaction.getCashWalletPublicKey()).thenReturn(new String());
        assertThat(cashMoneyDestockTransaction.getCashWalletPublicKey()).isNotNull();
    }

}
