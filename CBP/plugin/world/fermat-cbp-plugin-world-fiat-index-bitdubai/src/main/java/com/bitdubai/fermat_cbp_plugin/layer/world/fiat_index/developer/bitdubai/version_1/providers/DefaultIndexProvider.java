package com.bitdubai.fermat_cbp_plugin.layer.world.fiat_index.developer.bitdubai.version_1.providers;

import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.world.exceptions.CantGetIndexException;
import com.bitdubai.fermat_cbp_api.layer.world.interfaces.FiatIndex;
import com.bitdubai.fermat_cbp_plugin.layer.world.fiat_index.developer.bitdubai.version_1.exceptions.CantSelectBestIndexException;
import com.bitdubai.fermat_cbp_plugin.layer.world.fiat_index.developer.bitdubai.version_1.exchanges.EuropeanCentralBankExchange;
import com.bitdubai.fermat_cbp_plugin.layer.world.fiat_index.developer.bitdubai.version_1.exchanges.YahooExchange;
import com.bitdubai.fermat_cbp_plugin.layer.world.fiat_index.developer.bitdubai.version_1.interfaces.IndexProvider;
import com.bitdubai.fermat_cbp_plugin.layer.world.fiat_index.developer.bitdubai.version_1.structure.FiatIndexImpl;
import com.bitdubai.fermat_cbp_plugin.layer.world.fiat_index.developer.bitdubai.version_1.structure.HttpReader;
import com.bitdubai.fermat_cbp_plugin.layer.world.fiat_index.developer.bitdubai.version_1.structure.IndexHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Alejandro Bicelis on 11/2/2015.
 */
public class DefaultIndexProvider implements IndexProvider {


    @Override
    public FiatIndex getCurrentIndex(FiatCurrency currency) throws CantGetIndexException {

        List<FiatIndex> indexList = new ArrayList<>();
        FiatIndex index;

        //Get index from Yahoo exchange API
        try{
            index = YahooExchange.getExchangeIndexForCurrency(currency);
            indexList.add(index);
        }catch (CantGetIndexException e) {
            //TODO: Report unusual exception and do nothing more!
        }

        //Get index from European Central Bank exchange API
        try{
            index = EuropeanCentralBankExchange.getExchangeIndexForCurrency(currency);
            indexList.add(index);
        }catch (CantGetIndexException e) {
            //TODO: Report unusual exception and do nothing more!
        }

        try{
            index = IndexHelper.selectBestIndex(indexList);
        } catch (CantSelectBestIndexException e) {
            throw new CantGetIndexException("Fatal error, cant get index", e, "DefaultIndexProvider", null);
        }

        return index;
    }
}
