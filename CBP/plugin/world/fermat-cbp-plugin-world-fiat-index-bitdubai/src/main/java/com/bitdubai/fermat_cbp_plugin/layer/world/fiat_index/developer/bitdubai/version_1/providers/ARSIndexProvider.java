package com.bitdubai.fermat_cbp_plugin.layer.world.fiat_index.developer.bitdubai.version_1.providers;

import java.io.*;
import com.bitdubai.fermat_api.layer.world.exceptions.CantGetIndexException;
import com.bitdubai.fermat_cbp_api.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_cbp_plugin.layer.world.fiat_index.developer.bitdubai.version_1.interfaces.IndexProvider;
import com.bitdubai.fermat_cbp_plugin.layer.world.fiat_index.developer.bitdubai.version_1.structure.FiatIndexImpl;
import com.bitdubai.fermat_cbp_plugin.layer.world.fiat_index.developer.bitdubai.version_1.structure.HttpReader;
import com.bitdubai.fermat_cbp_plugin.layer.world.fiat_index.developer.bitdubai.version_1.structure.IndexMathHelper;

import org.json.JSONObject;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by Alex on 11/13/2015.
 */
public class ARSIndexProvider implements IndexProvider {

    @Override
    public FiatIndexImpl getCurrentIndex(FiatCurrency currency) throws CantGetIndexException {

        String content = HttpReader.getHTTPContent("http://contenidos.lanacion.com.ar/json/dolar");
        content = content.substring(19, content.indexOf(");"));     //Quitar el "dolarjsonpCallback(" y el ");"
        //System.out.println("String JSON:" + content);

        JSONObject json = new JSONObject(content);

        double purchasePrice = 0;
        double salePrice = 0;
        try{
            String aux = json.get("InformalCompraValue").toString().replaceAll(",",".");
            purchasePrice = Double.valueOf(aux);
            aux = json.get("InformalVentaValue").toString().replaceAll(",",".");
            salePrice = Double.valueOf(aux);
        }catch (JSONException e) {
            new CantGetIndexException(CantGetIndexException.DEFAULT_MESSAGE, e, "Fiat Index ARSIndexProvider", "Cant Get ARS Index Exception");
        }

        List<Double> purchasePriceList=new ArrayList();
        purchasePriceList.add(6.00);
        purchasePriceList.add(4.70);       // 600mm, 470mm, 170mm, 430mm and 300mm.
        purchasePriceList.add(1.70);
        purchasePriceList.add(4.30);
        purchasePriceList.add(3.00);
        Collections.sort(purchasePriceList);

        double mean = IndexMathHelper.mean(purchasePriceList);
        double stdDev = IndexMathHelper.standardDeviation(purchasePriceList);
        System.out.println("mean:" + mean);
        System.out.println("stdDev:" + stdDev);



        FiatIndexImpl index = new FiatIndexImpl(FiatCurrency.ARGENTINE_PESO, FiatCurrency.US_DOLLAR, purchasePrice, salePrice, (new Date().getTime() / 1000));
        return index;
    }
}

