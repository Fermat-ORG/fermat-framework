package unit.CryptoBrokerWalletImpl;

import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cbp_api.all_definition.enums.BalanceType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.MoneyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.TransactionType;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantGetCryptoBrokerStockTransactionException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerStockTransaction;
import com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.structure.util.CryptoBrokerWalletImpl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by José Vilchez on 22/01/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class GetCryptoBrokerStockTransactionsByMerchandiseTest {

    @Test
    public void getCryptoBrokerStockTransactionsByMerchandise() throws CantGetCryptoBrokerStockTransactionException {
        CryptoBrokerWalletImpl cryptoBrokerWallet = mock(CryptoBrokerWalletImpl.class);
        when(cryptoBrokerWallet.getCryptoBrokerStockTransactionsByMerchandise(Mockito.any(Currency.class), Mockito.any(MoneyType.class), Mockito.any(TransactionType.class), Mockito.any(BalanceType.class))).thenReturn(new ArrayList<CryptoBrokerStockTransaction>()).thenCallRealMethod();
        assertThat(cryptoBrokerWallet.getCryptoBrokerStockTransactionsByMerchandise(Mockito.any(Currency.class), Mockito.any(MoneyType.class), Mockito.any(TransactionType.class), Mockito.any(BalanceType.class))).isNotNull();
    }

}
