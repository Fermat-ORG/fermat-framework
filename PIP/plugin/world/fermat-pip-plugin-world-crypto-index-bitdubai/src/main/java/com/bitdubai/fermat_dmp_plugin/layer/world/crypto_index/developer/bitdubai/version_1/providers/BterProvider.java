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

import org.json.JSONException;

/**
 * Created by francisco on 31/08/15.
 */
public class BterProvider implements CryptoIndexProvider {

    private String UrlAPI;

    /**
     * @param pair
     * @return
     */
    public String getUrlAPI(String pair) {
        return "http://data.bter.com/api/1/ticker/" + pair;
    }

    /**
     * @param cryptoCurrency
     * @param fiatCurrency
     * @param time
     * @return
     * @throws FiatCurrencyNotSupportedException
     * @throws CryptoCurrencyNotSupportedException
     * @throws CantGetMarketPriceException
     */
    @Override
    public double getMarketPrice(CryptoCurrency cryptoCurrency, FiatCurrency fiatCurrency, long time) throws FiatCurrencyNotSupportedException, CryptoCurrencyNotSupportedException, CantGetMarketPriceException {
        HTTPJson jsonService = new HTTPJson();
        String pair = cryptoCurrency.getCode() + "_" + fiatCurrency.getCode();
        String urlApi = getUrlAPI(pair);
        String stringMarketPrice = null;
        try {
            stringMarketPrice = jsonService.getJSONFromUrl(urlApi).get("last").toString();
        } catch (JSONException ex) {
            throw new CantGetMarketPriceException("Cant get the Market Price for this Bter Provider", ex, ex.getMessage(), "Maybe the JSON response is null or part of this JSON is missing");
        }
        return Double.valueOf(stringMarketPrice);
    }

    @Override
    public double getHistoricalExchangeRate(CryptoCurrency cryptoCurrency, FiatCurrency fiatCurrency, long time) throws FiatCurrencyNotSupportedException, CryptoCurrencyNotSupportedException, CantGetHistoricalExchangeRateException, HistoricalExchangeRateNotFoundException {
        return 0;
    }

}
