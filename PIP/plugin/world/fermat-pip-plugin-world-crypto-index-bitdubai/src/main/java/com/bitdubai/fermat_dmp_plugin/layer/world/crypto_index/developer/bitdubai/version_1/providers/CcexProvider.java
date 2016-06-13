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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by francisco on 02/09/15.
 */
public class CcexProvider implements CryptoIndexProvider {
    public String getUrlAPI(String pair) {
        return "https://c-cex.com/t/" + pair + ".json";
    }

    public String getUrlAPIHistorical(String pair, long time) {
        Date date = new Date();
        date.setTime(time * 1000);
        int day = date.getDate();
        int month = date.getMonth() + 1;
        int year = date.getYear() + 1900;
        String stringDate = year + "-" + month + "-" + day;
        return "https://c-cex.com/t/s.html?a=tradehistory&d1=" + stringDate + "&d2=" + stringDate + "&pair=" + pair;
    }

    @Override
    public double getMarketPrice(CryptoCurrency cryptoCurrency, FiatCurrency fiatCurrency, long time) throws FiatCurrencyNotSupportedException, CryptoCurrencyNotSupportedException, CantGetMarketPriceException {
        HTTPJson jsonService = new HTTPJson();
        String pair = cryptoCurrency.getCode().toLowerCase() + "-" + fiatCurrency.getCode().toLowerCase();
        String urlApi = getUrlAPI(pair);
        String stringMarketPrice;
        try {
            stringMarketPrice = jsonService.getJSONFromUrl(urlApi).getJSONObject("ticker").get("lastprice").toString();
        } catch (JSONException ex) {
            throw new CantGetMarketPriceException("Cant get the Market Price for this Bitfinex Provider", ex, ex.getMessage(), "Maybe the JSON response is null or part of this JSON is missing");
        }
        return Double.valueOf(stringMarketPrice);
    }

    @Override
    public double getHistoricalExchangeRate(CryptoCurrency cryptoCurrency, FiatCurrency fiatCurrency, long time) throws FiatCurrencyNotSupportedException, CryptoCurrencyNotSupportedException, CantGetHistoricalExchangeRateException, HistoricalExchangeRateNotFoundException {
        HTTPJson jsonService = new HTTPJson();
        MarketPrice marketPrice = new MarketPrice();
        List<Double> list = new ArrayList<>();
        try {


            String pair = cryptoCurrency.getCode().toLowerCase() + "-" + fiatCurrency.getCode().toLowerCase();
            String urlApi = getUrlAPIHistorical(pair, time);
            double ExchangeRate = 0;

            int legth = jsonService.getJSONFromUrl(urlApi).getJSONArray("return").length();
            JSONObject jsonObject = jsonService.getJSONFromUrl(urlApi).getJSONArray("return").getJSONObject(0);
            for (int i = 0; i < legth; i++) {
                String price = jsonObject.get("rate").toString();
                ExchangeRate = Double.parseDouble(price);
                list.add(ExchangeRate);
            }
        } catch (Exception e) {
            throw new CantGetHistoricalExchangeRateException(CantGetHistoricalExchangeRateException.DEFAULT_MESSAGE, e, "Crypto Index CcexProvider ", "Cant Ge tHistorical ExchangeRate Exception");
        }
        System.out.println(marketPrice.getBestMarketPrice(list));
        return marketPrice.getBestMarketPrice(list);
    }

}
