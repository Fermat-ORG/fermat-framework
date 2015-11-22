package com.bitdubai.fermat_cbp_plugin.layer.world.fiat_index.developer.bitdubai.version_1.exchanges;

import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.world.exceptions.CantGetIndexException;
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
public class EuropeanCentralBankExchange {

    public static FiatIndex getExchangeIndexForCurrency(FiatCurrency fiatCurrency) throws CantGetIndexException {

        String url = "http://api.fixer.io/latest?base=USD&symbols=" + fiatCurrency.getCode();
        String aux;
        FiatIndexImpl index = null;

        try {
            JSONObject json = new JSONObject(HttpReader.getHTTPContent(url));

            aux = json.getJSONObject("rates").get(fiatCurrency.getCode()).toString();
            double price = Double.valueOf(aux);

            index = new FiatIndexImpl(fiatCurrency, FiatCurrency.US_DOLLAR, price, price, (new Date().getTime() / 1000), "european_central_bank");

        } catch (JSONException e) {
            //TODO: Report unusual exception!
            new CantGetIndexException(CantGetIndexException.DEFAULT_MESSAGE,e,"EuropeanCentralBankExchange","Cant Get Index from" + fiatCurrency.getCode());
        }

    return index;
    }

}
