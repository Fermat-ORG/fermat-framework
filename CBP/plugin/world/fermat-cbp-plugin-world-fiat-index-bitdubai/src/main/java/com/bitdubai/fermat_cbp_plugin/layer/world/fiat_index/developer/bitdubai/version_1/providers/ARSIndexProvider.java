package com.bitdubai.fermat_cbp_plugin.layer.world.fiat_index.developer.bitdubai.version_1.providers;

import com.bitdubai.fermat_api.layer.world.exceptions.CantGetIndexException;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_cbp_api.layer.world.interfaces.FiatIndex;
import com.bitdubai.fermat_cbp_plugin.layer.world.fiat_index.developer.bitdubai.version_1.exceptions.CantSelectBestIndexException;
import com.bitdubai.fermat_cbp_plugin.layer.world.fiat_index.developer.bitdubai.version_1.interfaces.IndexProvider;
import com.bitdubai.fermat_cbp_plugin.layer.world.fiat_index.developer.bitdubai.version_1.structure.FiatIndexImpl;
import com.bitdubai.fermat_cbp_plugin.layer.world.fiat_index.developer.bitdubai.version_1.structure.HttpReader;
import com.bitdubai.fermat_cbp_plugin.layer.world.fiat_index.developer.bitdubai.version_1.structure.IndexHelper;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by Alejandro Bicelis on 11/13/2015.
 */
public class ARSIndexProvider implements IndexProvider {

    @Override
    public FiatIndex getCurrentIndex(FiatCurrency currency) throws CantGetIndexException {

        String content, aux;
        JSONObject json;
        JSONArray jsonArr;
        double purchasePrice = 0;
        double salePrice = 0;
        List<FiatIndex> indexList = new ArrayList<>();




        //Get lanacion index
        try{
            content = HttpReader.getHTTPContent("http://contenidos.lanacion.com.ar/json/dolar");
            content = content.substring(19, content.indexOf(");"));     //Quitar el "dolarjsonpCallback(" y el ");"
            json = new JSONObject(content);

            aux = json.get("InformalCompraValue").toString().replaceAll(",",".");
            purchasePrice = Double.valueOf(aux);
            aux = json.get("InformalVentaValue").toString().replaceAll(",",".");
            salePrice = Double.valueOf(aux);

            FiatIndexImpl index = new FiatIndexImpl(FiatCurrency.ARGENTINE_PESO, FiatCurrency.US_DOLLAR, purchasePrice, salePrice, (new Date().getTime() / 1000), "lanacion");
            indexList.add(index);
        }catch (JSONException e) {
            //TODO: Report unusual exception and do nothing more!
        }





        //Get bluelytics index
        try{
            content = HttpReader.getHTTPContent("http://api.bluelytics.com.ar/v2/latest");
            json = new JSONObject(content);

            aux = json.getJSONObject("blue").get("value_buy").toString();
            purchasePrice = Double.valueOf(aux);
            aux = json.getJSONObject("blue").get("value_sell").toString();
            salePrice = Double.valueOf(aux);

            FiatIndexImpl index = new FiatIndexImpl(FiatCurrency.ARGENTINE_PESO, FiatCurrency.US_DOLLAR, purchasePrice, salePrice, (new Date().getTime() / 1000), "bluelytics");
            indexList.add(index);
        }catch (JSONException e) {
            //TODO: Report unusual exception and do nothing more!
        }




        //Get elcronista index
        try{
            content = HttpReader.getHTTPContent("http://api.bluelytics.com.ar/json/last_price");
            json = new JSONObject("{\"indexes\": " + content + "}");
            jsonArr = json.getJSONArray("indexes");

            for (int i = 0; i < jsonArr.length(); ++i) {
                JSONObject jsonIndex = jsonArr.getJSONObject(i);
                if(jsonIndex.getString("source").equals("elcronista"))
                {
                    aux = jsonIndex.get("value_buy").toString();
                    purchasePrice = Double.valueOf(aux);
                    aux = jsonIndex.get("value_sell").toString();
                    salePrice = Double.valueOf(aux);
                    break;
                }
            }

            FiatIndexImpl index = new FiatIndexImpl(FiatCurrency.ARGENTINE_PESO, FiatCurrency.US_DOLLAR, purchasePrice, salePrice, (new Date().getTime() / 1000), "elcronista");
            indexList.add(index);
        }catch (JSONException e) {
            //TODO: Report unusual exception and do nothing more!
        }





        //Get ambito_financiero index
        try{

            content = HttpReader.getHTTPContent("http://api.bluelytics.com.ar/json/last_price");
            json = new JSONObject("{\"indexes\": " + content + "}");
            jsonArr = json.getJSONArray("indexes");

            for (int i = 0; i < jsonArr.length(); ++i) {
                JSONObject jsonIndex = jsonArr.getJSONObject(i);
                if(jsonIndex.getString("source").equals("ambito_financiero"))
                {

                    aux = jsonIndex.get("value_buy").toString();
                    purchasePrice = Double.valueOf(aux);
                    aux = jsonIndex.get("value_sell").toString();
                    salePrice = Double.valueOf(aux);
                    break;
                }
            }

            FiatIndexImpl index = new FiatIndexImpl(FiatCurrency.ARGENTINE_PESO, FiatCurrency.US_DOLLAR, purchasePrice, salePrice, (new Date().getTime() / 1000), "ambito_financiero");
            indexList.add(index);
        }catch (JSONException e) {
            //TODO: Report unusual exception and do nothing more!
        }



        /*for(FiatIndex index : indexList)
        {
            System.out.println("");
            System.out.println("");
            System.out.println("PROVIDER DESC: " + index.getProviderDescription());
            System.out.println("CURRENCY: " + index.getCurrency().getCode());
            System.out.println("REFERENCE CURRENCY: " + index.getReferenceCurrency().getCode());
            System.out.println("TIMESTAMP: " + index.getTimestamp());
            System.out.println("PURCHASE: " + index.getPurchasePrice());
            System.out.println("SALE: " + index.getSalePrice());

        }
        */

        FiatIndex index;
        try{
            index = IndexHelper.selectBestIndex(indexList);
        } catch (CantSelectBestIndexException e) {
            throw new CantGetIndexException("Fatal error, cant get index", e, "ARSIndexProvider", null);
        }





        return index;
    }
}

