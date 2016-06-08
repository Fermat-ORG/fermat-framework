package unit.com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.version_1.providers.CexioProvider;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.providers.CexioProvider;
import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.structure.HTTPJson;

import org.fest.assertions.api.Assertions;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStream;

/**
 * Created by francisco on 17/09/15.
 */
public class GetMarketPriceTest {
    CexioProvider cexioProvider = new CexioProvider();
    private CryptoCurrency cryptoCurrencyTest;
    private FiatCurrency fiatCurrencyTest;
    private long time;

    @Test
    public void TestGetMarketPriceTest_successful() throws Exception {
        cryptoCurrencyTest = CryptoCurrency.getByCode("BTC");
        fiatCurrencyTest = FiatCurrency.getByCode("USD");
        double values = cexioProvider.getMarketPrice(cryptoCurrencyTest, fiatCurrencyTest, time);
        System.out.println(values);
        Assertions.assertThat(values).isNotNull();
    }

    @Test
    public void TestgetHistoricalExchangeRate_successful() throws Exception {
        cryptoCurrencyTest = CryptoCurrency.getByCode("BTC");
        fiatCurrencyTest = FiatCurrency.getByCode("USD");
        double values = cexioProvider.getHistoricalExchangeRate(cryptoCurrencyTest, fiatCurrencyTest, time);
        System.out.println(values);
        Assertions.assertThat(values).isNotNull();
    }

    @Test
    public void Test() throws Exception {
        HTTPJson jsonService = new HTTPJson();
        String strJSON = "https://cex.io/api/trade_history/BTC/USD"; // your string goes here
        //JSONObject jObject = new JSONObject(strJSON);
        InputStream io;
        io = jsonService.getInputStream(strJSON);
        BufferedReader br;
        br = jsonService.getBufferedReader(io);

        StringBuilder stringBuilder = new StringBuilder();
        String line;

        while ((line = br.readLine()) != null) {
            stringBuilder.append(line);
            stringBuilder.append("\n");
        }
        String json = stringBuilder.toString();
        JSONArray jArray = new JSONArray(json);
        JSONObject jsonObject = new JSONObject();

        for (int i = 0; i < jArray.length(); i++) {
            String time = "1442956095";
            if (time.equals(jArray.getJSONObject(i).get("date"))) {
                System.out.println(jArray.getJSONObject(i).get("price") + "  " + i);
            }
            // System.out.println(jArray.getJSONObject(i).get("date"));

        }


    }
}
