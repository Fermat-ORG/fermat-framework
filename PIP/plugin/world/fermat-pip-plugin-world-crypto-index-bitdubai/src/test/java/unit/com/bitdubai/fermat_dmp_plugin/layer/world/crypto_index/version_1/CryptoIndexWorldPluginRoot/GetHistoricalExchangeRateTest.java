package unit.com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.version_1.CryptoIndexWorldPluginRoot;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.CryptoIndexWorldPluginRoot;
import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.database.CryptoIndexDao;

import org.fest.assertions.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.Date;
import java.util.UUID;

/**
 * Created by francisco on 24/09/15.
 */
public class GetHistoricalExchangeRateTest {

    private UUID testOwnerId;
    @Mock
    private CryptoIndexWorldPluginRoot cryptoIndexWorldPluginRoot;

    @Mock
    private CryptoIndexDao cryptoIndexDao;
    @Mock
    private CryptoCurrency cryptoCurrency;
    @Mock
    private FiatCurrency fiatCurrency;
    @Mock
    private long time;

    private void setUpIds() {
        testOwnerId = UUID.randomUUID();
    }

    private void setUpMockitoGeneralRules() throws Exception {

    }

    @Before
    public void setUp() throws Exception {
        setUpIds();

        setUpMockitoGeneralRules();
    }

    @Test
    public void TestGetHistoricalExchangeRate() throws Exception {
        double price;
        cryptoIndexWorldPluginRoot = new CryptoIndexWorldPluginRoot();
        cryptoCurrency = CryptoCurrency.getByCode("BTC");
        fiatCurrency = FiatCurrency.getByCode("USD");
        Date date = new Date();
        time = date.getTime() / 1000;
        price = cryptoIndexWorldPluginRoot.getHistoricalExchangeRate(cryptoCurrency, fiatCurrency, time);
        Assertions.assertThat(price).isNotNull();
    }
}
