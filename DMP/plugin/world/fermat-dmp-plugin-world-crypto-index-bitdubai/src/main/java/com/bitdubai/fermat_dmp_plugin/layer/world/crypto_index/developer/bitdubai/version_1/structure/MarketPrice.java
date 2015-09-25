package com.bitdubai.fermat_ccp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.ccp_world.crypto_index.CryptoIndexManager;
import com.bitdubai.fermat_api.layer.ccp_world.crypto_index.exceptions.CryptoCurrencyNotSupportedException;
import com.bitdubai.fermat_api.layer.ccp_world.crypto_index.exceptions.FiatCurrencyNotSupportedException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_ccp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.database.CryptoIndexDao;
import com.bitdubai.fermat_ccp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.enums.Providers;
import com.bitdubai.fermat_ccp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.exceptions.CantGetMarketPriceException;
import com.bitdubai.fermat_ccp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.interfaces.CryptoIndexInterface;
import com.bitdubai.fermat_ccp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.interfaces.CryptoIndexProvider;
import com.bitdubai.fermat_ccp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.interfaces.MarketPriceInterface;
import com.bitdubai.fermat_ccp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.providers.BtceProvider;
import com.bitdubai.fermat_ccp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.providers.BtceServiceAPI;
import com.bitdubai.fermat_ccp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.providers.BterServiceAPI;
import com.bitdubai.fermat_ccp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.providers.CcexServiceAPI;
import com.bitdubai.fermat_ccp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.providers.CexioServiceAPI;
import com.bitdubai.fermat_ccp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.providers.CryptocoinchartsServiceAPI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Created by francisco on 12/08/15.
 */
public class MarketPrice implements MarketPriceInterface, CryptoIndexManager {
    Double marketExchangeRate = null;
    HTTPJson jsonService = new HTTPJson();
    BtceServiceAPI btceServiceAPI = new BtceServiceAPI();
    BterServiceAPI bterServiceAPI = new BterServiceAPI();
    CcexServiceAPI ccexServiceAPI = new CcexServiceAPI();
    CexioServiceAPI cexioServiceAPI = new CexioServiceAPI();
    CryptoIndexDao cryptoIndexDao;
    CryptocoinchartsServiceAPI cryptocoinchartsServiceAPI = new CryptocoinchartsServiceAPI();
    UUID pluginId;
    PluginDatabaseSystem pluginDatabaseSystem;
    String url = null;

    /**
     * @param cryptoCurrency
     * @param fiatCurrency
     * @param time
     * @return
     */
    @Override
    public double getHistoricalExchangeRate(CryptoCurrency cryptoCurrency, FiatCurrency fiatCurrency, long time) {
        /**
         * get market price from database, filtering by time
         */
        List<CryptoIndexInterface> list;
        String crypto = cryptoCurrency.getCode();
        String fiat = fiatCurrency.getCode();
        cryptoIndexDao = new CryptoIndexDao(pluginDatabaseSystem, pluginId);
        list = cryptoIndexDao.getHistoricalExchangeRateList(crypto, fiat, time);
        marketExchangeRate = Double.valueOf(list.get(0).toString());
        return marketExchangeRate;
    }

    /**
     * Through getBestMarketPrice method, the best market price returns
     *
     * @param fiatCurrency
     * @param cryptoCurrency
     * @param time
     * @return
     * @throws FiatCurrencyNotSupportedException
     * @throws CryptoCurrencyNotSupportedException
     */
    @Override
    public double getMarketPrice(FiatCurrency fiatCurrency, CryptoCurrency cryptoCurrency, long time) throws FiatCurrencyNotSupportedException, CryptoCurrencyNotSupportedException {
        // TODO MIRA ESTO Y DECIME QUE TE PARECE
        List<Double> priceList = new ArrayList<>();

        for (Providers provider : Providers.values()) {
            CryptoIndexProvider cryptoIndexProvider = provider.getProviderInstance();
            try {
                priceList.add(cryptoIndexProvider.getMarketPrice(cryptoCurrency, fiatCurrency, time));
            } catch (CantGetMarketPriceException e) {
                //TODO es necesario manejar esta excepcion? aunque haya error, esto no debería parar
            }
        }

        Collections.sort(priceList);

        // TODO que pasa si no hay precios en la lista?
        return priceList.get(priceList.size() - 1);
    }

    /**
     * The url obtained providers is stored in different variable type String.
     * These are added to a list so, then it is sorted from lowest to highest, and thus return the best market price.
     * TODO que es el mejor valor de mercado? el mayor?
     * TODO por favor, tene en cuenta que la idea de usar una logica aca es para sacar el mas adecuado, no el mayor.
     * @param crypto
     * @param fiat
     * @return
     */
    public double getBestMarketPrice(String crypto, String fiat) {
        marketExchangeRate = null;
        url = null;
        List<String> priceList = new ArrayList<>();

        // TODO la idea era abstraer a esta clase de tener que hacer esto por cada uno de los proveedores
        String urlAPI1 = btceServiceAPI.getUrlAPI(crypto, fiat);
        String urlAPI2 = bterServiceAPI.getUrlAPI(crypto, fiat);
        String urlAPI3 = ccexServiceAPI.getUrlAPI(crypto, fiat);
        String urlAPI4 = cexioServiceAPI.getUrlAPI(crypto, fiat);
        String urlAPI5 = cryptocoinchartsServiceAPI.getUrlAPI(crypto, fiat);

        priceList.add(jsonService.getJSONFromUrl(urlAPI1).getJSONObject(crypto.toLowerCase() + "_" + fiat.toLowerCase()).get("last").toString());
        priceList.add(jsonService.getJSONFromUrl(urlAPI2).get("last").toString());
        priceList.add(jsonService.getJSONFromUrl(urlAPI3).getJSONObject("ticker").get("lastprice").toString());
        priceList.add(jsonService.getJSONFromUrl(urlAPI4).getString("last"));
        priceList.add(jsonService.getJSONFromUrl(urlAPI5).getString("price"));

        Collections.sort(priceList);
        marketExchangeRate = Double.valueOf(priceList.get(priceList.size() - 1));
        return marketExchangeRate;
    }


}









