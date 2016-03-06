package unit.StockTransactionCryptoMoneyDestockFactory;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilter;
import com.bitdubai.fermat_cbp_api.all_definition.business_transaction.CryptoMoneyTransaction;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.crypto_money_destock.developer.bitdubai.version_1.exceptions.DatabaseOperationException;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.crypto_money_destock.developer.bitdubai.version_1.structure.StockTransactionCryptoMoneyDestockFactory;

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
public class getCryptoMoneyTransactionListTest {

    @Test
    public void getCryptoMoneyTransactionList() throws DatabaseOperationException, InvalidParameterException {
        StockTransactionCryptoMoneyDestockFactory stockTransactionCryptoMoneyDestockFactory = mock(StockTransactionCryptoMoneyDestockFactory.class);
        when(stockTransactionCryptoMoneyDestockFactory.getCryptoMoneyTransactionList(Mockito.any(DatabaseTableFilter.class))).thenReturn(new ArrayList<CryptoMoneyTransaction>()).thenCallRealMethod();
        assertThat(stockTransactionCryptoMoneyDestockFactory.getCryptoMoneyTransactionList(Mockito.any(DatabaseTableFilter.class))).isNotNull();
    }

}
