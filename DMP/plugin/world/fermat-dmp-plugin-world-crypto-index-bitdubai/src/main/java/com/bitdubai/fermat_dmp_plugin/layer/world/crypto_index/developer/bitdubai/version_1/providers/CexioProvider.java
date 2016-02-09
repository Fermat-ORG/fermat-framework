package com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.providers;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.dmp_world.crypto_index.exceptions.CryptoCurrencyNotSupportedException;
import com.bitdubai.fermat_api.layer.dmp_world.crypto_index.exceptions.FiatCurrencyNotSupportedException;
import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.exceptions.CantGetBufferedReaderException;
import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.exceptions.CantGetHistoricalExchangeRateException;
import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.exceptions.CantGetInputStreamException;
import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.exceptions.CantGetJsonObjectException;
import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.exceptions.CantGetMarketPriceException;
import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.exceptions.HistoricalExchangeRateNotFoundException;
import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.interfaces.CryptoIndexProvider;
import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.structure.HTTPJson;
import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.structure.MarketPrice;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by francisco on 02/09/15.
 */
public class CexioProvider implements CryptoIndexProvider {
    public String getUrlAPI(String pair) {
        return "https://cex.io/api/ticker/" + pair;
    }

    public String getUrlAPIHistorical(String pair) {
        return "https://cex.io/api/trade_history/" + pair;
    }

    @Override
    public double getMarketPrice(CryptoCurrency cryptoCurrency, FiatCurrency fiatCurrency, long time) throws FiatCurrencyNotSupportedException, CryptoCurrencyNotSupportedException, CantGetMarketPriceException {
        HTTPJson jsonService = new HTTPJson();
        String pair = cryptoCurrency.getCode().toUpperCase() + "/" + fiatCurrency.getCode().toUpperCase();
        String urlApi = getUrlAPI(pair);
        String stringMarketPrice;
        try {
            stringMarketPrice = jsonService.getJSONFromUrl(urlApi).get("last").toString();
        } catch (JSONException ex) {
            throw new CantGetMarketPriceException("Cant get the Market Price for this Cexio Provider", ex, ex.getMessage(), "Maybe the JSON response is null or part of this JSON is missing");
        }
        return Double.valueOf(stringMarketPrice);
    }

    @Override
    public double getHistoricalExchangeRate(CryptoCurrency cryptoCurrency, FiatCurrency fiatCurrency, long time) throws FiatCurrencyNotSupportedException, CryptoCurrencyNotSupportedException, CantGetHistoricalExchangeRateException, HistoricalExchangeRateNotFoundException {
        HTTPJson jsonService = new HTTPJson();
        MarketPrice marketPrice = new MarketPrice();
        String pair = cryptoCurrency.getCode().toUpperCase() + "/" + fiatCurrency.getCode().toUpperCase();
        String urlApi = getUrlAPIHistorical(pair);
        String stringJson;
        List<Double> list = new ArrayList<>();
        try {
            stringJson = jsonService.JsonToString(jsonService.getBufferedReader(jsonService.getInputStream(urlApi)));
            JSONArray jsonArray = new JSONArray(stringJson);
            int legth = jsonArray.length();
            for (int i = 0; i < legth; i++) {
                String timeJson = jsonArray.getJSONObject(i).getString("date");
                if (time == Long.valueOf(timeJson)) {
                    String price = jsonArray.getJSONObject(i).getString("price");
                    list.add(Double.valueOf(price));
                }
            }
        } catch (CantGetJsonObjectException e) {
            throw new CantGetHistoricalExchangeRateException(CantGetJsonObjectException.DEFAULT_MESSAGE, e, "Crypto Index Cexio Provider", "Cant Get JsonObject Exception");
        } catch (CantGetBufferedReaderException e) {
            throw new CantGetHistoricalExchangeRateException(CantGetBufferedReaderException.DEFAULT_MESSAGE, e, "Crypto Index Cexio Provider", "Cant Get BufferedReader Exception");
        } catch (CantGetInputStreamException e) {
            throw new CantGetHistoricalExchangeRateException(CantGetInputStreamException.DEFAULT_MESSAGE, e, "Crypto Index Cexio Provider", "Cant Get InputStream Exception");
        } catch (Exception e) {
            throw new CantGetHistoricalExchangeRateException(CantGetHistoricalExchangeRateException.DEFAULT_MESSAGE, e, "Crypto Index CexioProvider ", "Cant Ge tHistorical ExchangeRate Exception");
        }
        System.out.println(marketPrice.getBestMarketPrice(list));
        return marketPrice.getBestMarketPrice(list);
    }

}
