package unit.com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.version_1.providers.CryptoProvidersManager;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.interfaces.CryptoIndex;
import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.providers.CryptoProvidersManager;

import org.fest.assertions.api.Assertions;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by francisco on 15/12/15.
 */
public class GetCurrentIndexTest {

    CryptoProvidersManager cryptoProvidersManager = new CryptoProvidersManager();
    private CryptoIndex cryptoIndex;
    private CryptoCurrency cryptoCurrency ;
    private FiatCurrency fiatCurrency ;
    String providers;

    @Before
    public void setValues() throws Exception {
        cryptoCurrency = CryptoCurrency.getByCode("BTC");
        fiatCurrency = FiatCurrency.getByCode("USD");
    }

    @Test
    public void TestGetCurrentIndex() throws Exception{

        cryptoIndex = cryptoProvidersManager.getCurrentIndex(cryptoCurrency,fiatCurrency);
        providers = cryptoIndex.getProviderDescription();
        System.out.println(providers);
        Assertions.assertThat(providers).isNotNull();
    }
}
