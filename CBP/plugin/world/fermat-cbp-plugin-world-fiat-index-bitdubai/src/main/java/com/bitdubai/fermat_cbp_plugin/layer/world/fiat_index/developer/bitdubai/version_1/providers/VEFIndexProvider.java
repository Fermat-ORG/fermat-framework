package com.bitdubai.fermat_cbp_plugin.layer.world.fiat_index.developer.bitdubai.version_1.providers;

import com.bitdubai.fermat_cbp_api.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_cbp_plugin.layer.world.fiat_index.developer.bitdubai.version_1.structure.FiatIndexImpl;
import com.bitdubai.fermat_api.layer.world.exceptions.CantGetIndexException;
import com.bitdubai.fermat_cbp_plugin.layer.world.fiat_index.developer.bitdubai.version_1.interfaces.IndexProvider;
import com.bitdubai.fermat_cbp_plugin.layer.world.fiat_index.developer.bitdubai.version_1.structure.HttpJsonReader;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Date;

/**
 * Created by Alex on 11/2/2015.
 */
public class VEFIndexProvider implements IndexProvider {


    @Override
    public FiatIndexImpl getCurrentIndex(FiatCurrency currency) throws CantGetIndexException {

        //ALEX_TODO: Quiza sea mejor not to rely on bitcoinvenezuela, e implementar directamente lo que hace DolarToday.php.

        JSONObject json = HttpJsonReader.getJSONFromUrl("http://api.bitcoinvenezuela.com/DolarToday.php?json=yes");
        //System.out.println("String JSON:" + json.toString());

        double purchasePrice = 0;
        double salePrice = 0;
        try{
            purchasePrice = (double) json.getJSONObject("USD").get("transferencia");
            salePrice = (double) json.getJSONObject("USD").get("transferencia");
        }catch (JSONException e) {
            new CantGetIndexException(CantGetIndexException.DEFAULT_MESSAGE,e,"Fiat Index VEFIndexProvider","Cant Get VEF Index Exception");
        }

        FiatIndexImpl index = new FiatIndexImpl(FiatCurrency.VENEZUELAN_BOLIVAR, FiatCurrency.US_DOLLAR, purchasePrice, salePrice, (new Date().getTime() / 1000));
        return index;
    }
}
