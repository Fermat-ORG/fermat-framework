package unit.com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.version_1.providers.CexioProvider;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.providers.CexioProvider;

import org.junit.Test;

/**
 * Created by francisco on 22/09/15.
 */
public class GetHistoricalExchangeRateTest {
    CexioProvider cexioProvider = new CexioProvider();
    private CryptoCurrency cryptoCurrencyTest;
    private FiatCurrency fiatCurrencyTest;
    private long time;

    @Test
    public void TestGetHistoricalExchangeRateTest() throws Exception {
        cryptoCurrencyTest = CryptoCurrency.getByCode("BTC");
        fiatCurrencyTest = FiatCurrency.getByCode("USD");
        double price = 0;
        long time = 1443038589;
        price = cexioProvider.getHistoricalExchangeRate(cryptoCurrencyTest, fiatCurrencyTest, time);
        System.out.println(price);
    }
}
