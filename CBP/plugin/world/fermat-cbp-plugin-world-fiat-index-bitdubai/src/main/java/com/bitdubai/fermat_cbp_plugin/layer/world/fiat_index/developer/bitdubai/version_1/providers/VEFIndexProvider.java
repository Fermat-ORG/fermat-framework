package com.bitdubai.fermat_cbp_plugin.layer.world.fiat_index.developer.bitdubai.version_1.providers;

import com.bitdubai.fermat_cbp_api.all_definition.enums.FiatCurrencyType;
import com.bitdubai.fermat_cbp_api.all_definition.world.Index;
import com.bitdubai.fermat_cbp_api.layer.cbp_world.cbp_fiat_index.exceptions.CantGetIndexException;
import com.bitdubai.fermat_cbp_plugin.layer.world.fiat_index.developer.bitdubai.version_1.interfaces.IndexProvider;
import com.bitdubai.fermat_cbp_plugin.layer.world.fiat_index.developer.bitdubai.version_1.structure.FiatIndex;
import com.bitdubai.fermat_cbp_plugin.layer.world.fiat_index.developer.bitdubai.version_1.structure.HttpJsonReader;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * Created by Alex on 11/2/2015.
 */
public class VEFIndexProvider implements IndexProvider {


    @Override
    public Index getCurrentIndex(FiatCurrencyType currency) throws CantGetIndexException {

       //ALEX_TODO: Quiza sea mejor not to rely on bitcoinvenezuela, e implementar directamente lo que hace DolarToday.php.

        JSONObject json = HttpJsonReader.getJSONFromUrl("http://api.bitcoinvenezuela.com/DolarToday.php?json=yes");
        //System.out.println("String JSON:" + json.toString());

        FiatIndex index = new FiatIndex(FiatCurrencyType.VENEZUELAN_BOLIVAR);
        try{
            index.setPurchasePrice((double) json.getJSONObject("USD").get("transferencia"));
            index.setSalePrice((double) json.getJSONObject("USD").get("transferencia"));
            index.setTimeStamp((new Date().getTime() / 1000));
        }catch (JSONException e) {
            new CantGetIndexException(CantGetIndexException.DEFAULT_MESSAGE,e,"Fiat Index VEFIndexProvider","Cant Get VEF Index Exception");
        }

        return index;
    }
}
