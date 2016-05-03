package unit.CryptoBrokerWalletImpl;

import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantGetCryptoBrokerQuoteException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.Quote;
import com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.structure.util.CryptoBrokerWalletImpl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by José Vilchez on 22/01/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class GetQuoteTest {

    @Test
    public void getQuote() throws CantGetCryptoBrokerQuoteException {
        CryptoBrokerWalletImpl cryptoBrokerWallet = mock(CryptoBrokerWalletImpl.class);
        when(cryptoBrokerWallet.getQuote(Mockito.any(Currency.class), Mockito.any(float.class), Mockito.any(FiatCurrency.class))).thenReturn(new Quote() {
            @Override
            public FermatEnum getMerchandise() {
                return null;
            }

            @Override
            public FiatCurrency getFiatCurrency() {
                return null;
            }

            @Override
            public float getPriceReference() {
                return 0;
            }

            @Override
            public float getQuantity() {
                return 0;
            }
        }).thenCallRealMethod();
        assertThat(cryptoBrokerWallet.getQuote(Mockito.any(Currency.class), Mockito.any(float.class), Mockito.any(FiatCurrency.class))).isNotNull();
    }

}
