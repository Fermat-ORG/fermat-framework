package unit.StockBalanceImpl;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_cbp_api.all_definition.enums.BalanceType;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantAddDebitCryptoBrokerWalletException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerStockTransactionRecord;
import com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.structure.util.StockBalanceImpl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;

/**
 * Created by José Vilchez on 22/01/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class DebitTest {

    @Test
    public void DebitTest() throws CantAddDebitCryptoBrokerWalletException, CantStartPluginException {
        StockBalanceImpl stockBalance = mock(StockBalanceImpl.class, Mockito.RETURNS_DEEP_STUBS);
        doCallRealMethod().when(stockBalance).debit(Mockito.any(CryptoBrokerStockTransactionRecord.class), Mockito.any(BalanceType.class));
    }

}
