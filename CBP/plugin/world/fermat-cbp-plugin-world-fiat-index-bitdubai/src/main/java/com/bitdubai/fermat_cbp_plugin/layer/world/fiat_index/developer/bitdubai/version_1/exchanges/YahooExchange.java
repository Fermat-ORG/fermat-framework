package com.bitdubai.fermat_cbp_plugin.layer.world.fiat_index.developer.bitdubai.version_1.exchanges;

import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.world.exceptions.CantGetIndexException;
import com.bitdubai.fermat_api.layer.world.interfaces.Index;
import com.bitdubai.fermat_cbp_api.layer.world.interfaces.FiatIndex;
import com.bitdubai.fermat_cbp_plugin.layer.world.fiat_index.developer.bitdubai.version_1.structure.FiatIndexImpl;
import com.bitdubai.fermat_cbp_plugin.layer.world.fiat_index.developer.bitdubai.version_1.structure.HttpReader;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;

/**
 * Created by Alejandro Bicelis on 11/22/2015.
 */
public class YahooExchange {

    public static FiatIndex getExchangeIndexForCurrency(FiatCurrency fiatCurrency) throws CantGetIndexException {

        String yahooQuery = "select * from yahoo.finance.xchange where pair in (\"USD" + fiatCurrency.getCode() + "\")";
        String url = "http://query.yahooapis.com/v1/public/yql?q=" +  yahooQuery;
        String encodedUrl = "";
        String aux;
        FiatIndexImpl index = null;

        try {
            //URL Encode the string
            encodedUrl = URLEncoder.encode(url, "UTF-8");

            JSONObject json = new JSONObject(HttpReader.getHTTPContent(encodedUrl));

            aux = json.getJSONObject("query").getJSONObject("results").getJSONObject("rate").get("ask").toString();
            double salePrice =  Double.valueOf(aux);

            aux = json.getJSONObject("query").getJSONObject("results").getJSONObject("rate").get("bid").toString();
            double purchasePrice =  Double.valueOf(aux);

            index = new FiatIndexImpl(fiatCurrency, FiatCurrency.US_DOLLAR, purchasePrice, salePrice, (new Date().getTime() / 1000), "yahoo");

        } catch (UnsupportedEncodingException ignored) {
            // Can be safely ignored because UTF-8 is always supported
        }
        catch (JSONException e) {
            //TODO: Report unusual exception!
            new CantGetIndexException(CantGetIndexException.DEFAULT_MESSAGE,e,"YahooExchange","Cant Get Index from" + fiatCurrency.getCode());
        }

    return index;

    }

}
