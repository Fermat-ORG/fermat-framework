package unit.CryptoBrokerWalletImpl;

import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cbp_api.all_definition.enums.MoneyType;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantGetCryptoBrokerMarketRateException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.FiatIndex;
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
public class GetMarketRateTest {

    @Test
    public void getMarketRate() throws CantGetCryptoBrokerMarketRateException {
        CryptoBrokerWalletImpl cryptoBrokerWallet = mock(CryptoBrokerWalletImpl.class);
        when(cryptoBrokerWallet.getMarketRate(Mockito.any(Currency.class), Mockito.any(FiatCurrency.class), Mockito.any(MoneyType.class))).thenReturn(new FiatIndex() {
            @Override
            public FermatEnum getMerchandise() {
                return null;
            }

            @Override
            public float getSalePrice() {
                return 0;
            }

            @Override
            public float getPurchasePrice() {
                return 0;
            }

            @Override
            public float getSalePriceUpSpread() {
                return 0;
            }

            @Override
            public float getPurchasePriceDownSpread() {
                return 0;
            }

            @Override
            public float getSalePurchaseUpSpread() {
                return 0;
            }

            @Override
            public float getPurchasePurchaseDownSpread() {
                return 0;
            }

            @Override
            public float getPriceReference() {
                return 0;
            }

            @Override
            public float getPriceVolatility() {
                return 0;
            }

            @Override
            public FiatCurrency getFiatCurrency() {
                return null;
            }
        }).thenCallRealMethod();

        assertThat(cryptoBrokerWallet.getMarketRate(Mockito.any(Currency.class), Mockito.any(FiatCurrency.class), Mockito.any(MoneyType.class))).isNotNull();
    }

}
