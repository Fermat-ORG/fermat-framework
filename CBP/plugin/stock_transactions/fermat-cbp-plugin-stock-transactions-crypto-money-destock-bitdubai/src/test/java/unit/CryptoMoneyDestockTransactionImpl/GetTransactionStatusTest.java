package unit.CryptoMoneyDestockTransactionImpl;

import com.bitdubai.fermat_cbp_api.all_definition.enums.TransactionStatusRestockDestock;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.crypto_money_destock.developer.bitdubai.version_1.structure.CryptoMoneyDestockTransactionImpl;

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
        CryptoMoneyDestockTransactionImpl cryptoMoneyDestockTransaction = mock(CryptoMoneyDestockTransactionImpl.class);
        when(cryptoMoneyDestockTransaction.getTransactionStatus()).thenReturn(transactionStatusRestockDestock);
        assertThat(cryptoMoneyDestockTransaction.getTransactionStatus()).isNotNull();
    }

}
