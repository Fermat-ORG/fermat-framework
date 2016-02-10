package com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.providers;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.dmp_world.crypto_index.exceptions.CryptoCurrencyNotSupportedException;
import com.bitdubai.fermat_api.layer.dmp_world.crypto_index.exceptions.FiatCurrencyNotSupportedException;
import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.exceptions.CantGetHistoricalExchangeRateException;
import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.exceptions.CantGetMarketPriceException;
import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.exceptions.HistoricalExchangeRateNotFoundException;
import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.interfaces.CryptoIndexProvider;
import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.structure.HTTPJson;
import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.structure.MarketPrice;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * The class <code>com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.providers.BtceProvider</code>
 * haves all the logic to bring the market price for the provider Btce.<p/>
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 16/09/2015.
 *
 * @version 1.0
 * @since Java JDK 1.8
 */
public class BtceProvider implements CryptoIndexProvider {

    private String getUrlAPI(String pair) {
        return "https://btc-e.com/api/3/ticker/" + pair;
    }

    private String getUrlAPIHistorical(String pair) {
        return "https://btc-e.com/api/3/trades/" + pair;
    }

    @Override
    public double getMarketPrice(CryptoCurrency cryptoCurrency,
                                 FiatCurrency fiatCurrency,
                                 long time) throws FiatCurrencyNotSupportedException, CryptoCurrencyNotSupportedException, CantGetMarketPriceException {

        HTTPJson jsonService = new HTTPJson();
        String pair = cryptoCurrency.getCode().toLowerCase() + "_" + fiatCurrency.getCode().toLowerCase();
        String urlApi = getUrlAPI(pair);
        String stringMarketPrice;
        try {
            stringMarketPrice = jsonService.getJSONFromUrl(urlApi).getJSONObject(pair).get("last").toString();
        } catch (JSONException ex) {
            throw new CantGetMarketPriceException("Cant get the Market Price for this Btce Provider", ex, ex.getMessage(), "Maybe the JSON response is null or part of this JSON is missing");
        }
        return Double.valueOf(stringMarketPrice);
    }

    @Override
    public double getHistoricalExchangeRate(CryptoCurrency cryptoCurrency, FiatCurrency fiatCurrency, long time) throws FiatCurrencyNotSupportedException, CryptoCurrencyNotSupportedException, CantGetHistoricalExchangeRateException, HistoricalExchangeRateNotFoundException {
        HTTPJson jsonService = new HTTPJson();
        MarketPrice marketPrice = new MarketPrice();
        List<Double> list = new ArrayList<>();
        double exchangeRate;
        try {
            String pair = cryptoCurrency.getCode().toLowerCase() + "_" + fiatCurrency.getCode().toLowerCase();
            String urlApi = getUrlAPIHistorical(pair);
            JSONObject jsonObject = jsonService.getJSONFromUrl(urlApi);
            JSONArray jsonArray = jsonObject.getJSONArray(pair);
            int length = jsonArray.length();
            for (int i = 0; i < length; i++) {
                long timeJson = jsonArray.getJSONObject(i).getLong("timestamp");
                if (time == timeJson) {
                    exchangeRate = jsonArray.getJSONObject(i).getDouble("price");
                    list.add(exchangeRate);
                }
            }
        } catch (Exception e) {
            throw new CantGetHistoricalExchangeRateException(CantGetHistoricalExchangeRateException.DEFAULT_MESSAGE, e, "Crypto Index BtceProvider ", "Cant Ge tHistorical ExchangeRate Exception");
        }
        System.out.println(marketPrice.getBestMarketPrice(list));
        return marketPrice.getBestMarketPrice(list);
    }

}
