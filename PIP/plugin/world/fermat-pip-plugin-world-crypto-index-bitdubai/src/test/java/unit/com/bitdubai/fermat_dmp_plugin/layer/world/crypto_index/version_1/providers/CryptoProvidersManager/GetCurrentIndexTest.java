package unit.com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.version_1.providers.CryptoProvidersManager;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.interfaces.CryptoIndex;
import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.providers.CryptoProvidersManager;

import org.fest.assertions.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;

/**
 * Created by francisco on 15/12/15.
 */
public class GetCurrentIndexTest {

    CryptoProvidersManager cryptoProvidersManager = new CryptoProvidersManager();
    private CryptoIndex cryptoIndex;
    private CryptoCurrency cryptoCurrency;
    private FiatCurrency fiatCurrency;
    String providers;

    @Before
    public void setValues() throws Exception {
        cryptoCurrency = CryptoCurrency.getByCode("BTC");
        fiatCurrency = FiatCurrency.getByCode("USD");
        cryptoIndex = cryptoProvidersManager.getCurrentIndex(cryptoCurrency, fiatCurrency);

    }

    @Test
    public void TestGetCurrentIndex() throws Exception {

        System.out.println(cryptoIndex.getProviderDescription());
        System.out.println(cryptoIndex.getPurchasePrice());
        System.out.println(cryptoIndex.getCurrency());
        System.out.println(cryptoIndex.getReferenceCurrency());
        System.out.println(cryptoIndex.getSalePrice());
        System.out.println(cryptoIndex.getTimestamp());
        Assertions.assertThat(cryptoIndex.getProviderDescription()).isNotNull();
        Assertions.assertThat(cryptoIndex.getPurchasePrice()).isNotNull();
        Assertions.assertThat(cryptoIndex.getCurrency()).isNotNull();
        Assertions.assertThat(cryptoIndex.getReferenceCurrency()).isNotNull();
        Assertions.assertThat(cryptoIndex.getSalePrice()).isNotNull();
        Assertions.assertThat(cryptoIndex.getTimestamp()).isNotNull();
    }

    @Test
    public void TestGetCurrentIndex_FiatCurrencyNotSupportedException() throws Exception {

        fiatCurrency = FiatCurrency.getByCode("USD");
        cryptoIndex = cryptoProvidersManager.getCurrentIndex(cryptoCurrency, fiatCurrency);
        catchException(cryptoProvidersManager).getCurrentIndex(null, fiatCurrency);
        Assertions.assertThat(caughtException()).isNotNull();
    }
}
