package unit.com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.version_1.providers.BtceProvider;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.providers.BtceProvider;

import org.junit.Test;

import java.util.Date;

/**
 * Created by francisco on 24/09/15.
 */
public class GetHistoricalExchangeRateTest {
    BtceProvider btceProvider = new BtceProvider();
    private CryptoCurrency cryptoCurrencyTest;
    private FiatCurrency fiatCurrencyTest;
    private long time;

    @Test
    public void TestGetHistoricalExchangeRateTest() throws Exception {
        cryptoCurrencyTest = CryptoCurrency.getByCode("BTC");
        fiatCurrencyTest = FiatCurrency.getByCode("USD");
        double price = 0;
        Date date = new Date();
        time = date.getTime() / 1000;
        price = btceProvider.getHistoricalExchangeRate(cryptoCurrencyTest, fiatCurrencyTest, time);
        System.out.println(price);
    }
}
