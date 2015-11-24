package com.bitdubai.fermat_cbp_plugin.layer.world.fiat_index.developer.bitdubai.version_1.exchanges;

import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.world.exceptions.CantGetIndexException;
import com.bitdubai.fermat_cbp_api.layer.world.interfaces.FiatIndex;
import com.bitdubai.fermat_cbp_plugin.layer.world.fiat_index.developer.bitdubai.version_1.structure.FiatIndexImpl;
import com.bitdubai.fermat_cbp_plugin.layer.world.fiat_index.developer.bitdubai.version_1.structure.HttpReader;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.Date;

/**
 * Created by Alejandro Bicelis on 11/22/2015.
 */
public class YahooExchange {

    public static FiatIndex getExchangeIndexForCurrency(FiatCurrency fiatCurrency) throws CantGetIndexException {

        String url = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.xchange%20where%20pair%20in%20(%22USD" + fiatCurrency.getCode() + "%22)&format=json&diagnostics=true&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys&callback=";
        String aux;
        FiatIndexImpl index = null;

        try {

            JSONObject json = new JSONObject(HttpReader.getHTTPContent(url));

            aux = json.getJSONObject("query").getJSONObject("results").getJSONObject("rate").get("Ask").toString();
            double salePrice =  Double.valueOf(aux);

            aux = json.getJSONObject("query").getJSONObject("results").getJSONObject("rate").get("Bid").toString();
            double purchasePrice =  Double.valueOf(aux);

            index = new FiatIndexImpl(fiatCurrency, FiatCurrency.US_DOLLAR, purchasePrice, salePrice, (new Date().getTime() / 1000), "yahoo");
        } catch (JSONException e) {
            throw new CantGetIndexException(CantGetIndexException.DEFAULT_MESSAGE,e,"YahooExchange","Cant Get Index from" + fiatCurrency.getCode());
        }

        return index;
    }

}
